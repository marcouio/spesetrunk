# Code Review — spesetrunk (`gestionespese`)

Revisione del modulo `spesetrunk` e del layer DB condiviso `utility` da cui dipende.
Tutti i punti sono stati verificati leggendo il codice. I test attuali passano ma non coprono questi percorsi.

---

## Critici

### 1. Il connection pool è rotto (`utility`) — RISOLTO
`utility/src/main/java/com/molinari/utility/database/ConnectionPool.java:82-107`

- `getConnection()` rimuove l'indice 0 alla riga 89, poi alla riga 106 restituisce `freeConnections.get(0)` **senza rimuoverlo**: la stessa connessione può essere consegnata a due thread e una connessione chiusa resta nel pool.
- `while (freeConnections.isEmpty()) {}` (righe 103-105) è un busy-wait che consuma CPU, senza sleep né timeout.
- `useConnection()` (righe 67-72) chiude una connessione del pool; `executeUpdate()` (righe 161-172) la chiude e poi chiama `releaseNewConnection()`, che crea sempre una **nuova** connessione fisica. Il pool non riusa mai e le connessioni si accumulano.
- `getResulSet()` (righe 180-192) non chiude mai lo `Statement`.

**Fix applicato:** prelievo/rimozione atomici in `getConnection()`, attesa con `wait(timeout)` e `notifyAll()` al rilascio, limite massimo di connessioni, `useConnection()`/`executeUpdate()` che rilasciano (senza chiudere) la stessa connessione, scarto delle connessioni chiuse e chiusura degli `Statement` associati al rilascio. Aggiunto `ConnectionPoolTest` (`utility`) con 3 casi di regressione. Verificati `mvn test` su `utility`, `spesetrunk` e `mp3`.

### 2. Costruttore di default di `WrapNote` lascia `base` a null — RISOLTO
`src/main/java/com/molinari/gestionespese/domain/wrapper/WrapNote.java:39-41`

`base` viene inizializzato solo nell'altro costruttore, ma `delete` (:213), `update` (:220), `deleteAll` (:258) e `selectWhere` (:290) lo usano → NPE per ogni `new WrapNote()`.

**Fix applicato:** il costruttore di default ora inizializza `base = new WrapBase<Note>((Note) note)`, come l'altro costruttore.

### 3. `DeleteLastNote` genera SQL non valido e restituisce sempre false — RISOLTO
`WrapNote.java:267-279`

Produce `DELETE ... WHERE idNote(SELECT ...)` (manca `= (`). `ok` non viene mai impostato a `true`. Inoltre concatena l'id utente nella query.

**Fix applicato:** il metodo legge l'id dell'ultima nota dell'utente loggato con `ExecuteResultSet` e poi chiama `delete(idNote)`, restituendo correttamente `true`/`false`. Aggiunto il controllo su utente loggato nullo e rimosso l'import inutilizzato di `ConnectionPool`.

### 4. `WrapRisparmio.insert` scrive l'oggetto sbagliato — RISOLTO
`src/main/java/com/molinari/gestionespese/domain/wrapper/WrapRisparmio.java` (insert, ~:99)

`ps.setDouble(1, risparmio.getPerSulTotale())` usa il campo del wrapper invece di `obj`: l'entità passata viene ignorata → corruzione silenziosa dei dati. Usare `obj.getPerSulTotale()`.

**Fix applicato:** `insert` usa ora `obj.getPerSulTotale()`.

### 5. Autenticazione: SQL injection + password in chiaro + colonne sbagliate — RISOLTO
`src/main/java/com/molinari/gestionespese/domain/wrapper/WrapUtenti.java`

- `utenteLogin` (~:101) e `selectByUserAndPass` (~:211) concatenano `username`/`password` nella SQL → bypass del login con `' OR '1'='1`.
- `utenteLogin` legge username/password dalle colonne 2/3 (`nome`/`cognome`) mentre `selectByUserAndPass` usa 4/5 → risultati di login incoerenti.
- Le password sono confrontate e salvate in chiaro (`Controllore.java:125`, `WrapUtenti.java:145`).
- `update` (~:179) genera `password = <valore>` senza apici → errore SQL e injection.

**Fix applicato:** aggiunto `ExecuteResultSet.executePrepared(...)` in `utility`; `selectByUsername` usa un `PreparedStatement` con parametro e `selectByUserAndPass`/`utenteLogin` delegano ad esso leggendo le colonne corrette (4/5). Le password sono salvate come hash SHA-256 (`PasswordHasher`) e confrontate in Java, con fallback per i valori legacy in chiaro; `update` usa `ExecutePreparedStatement`.

---

## Alti

### 6. `ListaMovimentiEntrate` lancia ClassCastException — RISOLTO
`src/main/java/com/molinari/gestionespese/view/componenti/movimenti/ListaMovimentiEntrate.java:71,78`

`new JComboBox<>(INCOMETYPE.values())` (enum) e poi `categoria = (String) comboBoxCat.getSelectedItem()` → CCE appena si sceglie una categoria.

**Fix applicato:** la combo contiene una voce vuota seguita dagli `INCOMETYPE`; `getCategoria` converte l'enum nell'ordinale senza cast a `String`.

### 7. Codifica dell'importanza delle categorie incoerente — RISOLTO
`src/main/java/com/molinari/gestionespese/view/impostazioni/CategorieView.java:145,182`

`cbImportanza` è un `JComboBox<String>` popolato con etichette localizzate ("Futili/Variabili/Fisse"), ma `setFields()` inserisce `CATEGORYTYPE.values()[Integer.parseInt(...)]` e `aggiornaModelDaVista` fa il cast dell'elemento selezionato a `String`. A seconda dei dati si ottiene `NumberFormatException`, `IndexOutOfBoundsException` o CCE, e si può persistere l'etichetta invece dell'ordinale. `PanelCategories` (`view/datainsert`, :167/:232) usa correttamente l'ordinale: le due UI non sono allineate. Verificare se la view `impostazioni` è ancora raggiungibile.

**Fix applicato:** `cbImportanza` è ora `JComboBox<CATEGORYTYPE>` popolato con `CATEGORYTYPE.values()`; lettura/scrittura usano l'ordinale con conversione difensiva (`toCategoryType`). La view `impostazioni` è raggiungibile da `ApplicationMenu`.

### 8. `WrapCatSpese.deleteAll` svuota la cache sbagliata — RISOLTO
`src/main/java/com/molinari/gestionespese/domain/wrapper/WrapCatSpese.java` (deleteAll, ~:162)

Elimina le categorie ma pulisce `CacheUscite` invece della cache delle categorie → categorie vecchie restano in memoria.

**Fix applicato:** `deleteAll` pulisce `CacheCategorie`.

### 9. NPE nei filtri delle cache — RISOLTO
`src/main/java/com/molinari/gestionespese/business/cache/CacheEntrate.java:66-73`
`src/main/java/com/molinari/gestionespese/business/cache/CacheNote.java:59,84`

- `CacheEntrate.getFilterForUser`: `utenti.getidUtente()` è dereferenziato prima del controllo `utenti != null`; `entrata.getUtenti()` prima di `entrata != null`.
- `CacheNote.addNota` / `addNote`: `"guest".equals(nota.getUtenti().getnome())` è protetto da `||`, quindi con `getUtenti()` null il lato destro lo dereferenzia comunque. Serve `&&` con controllo esplicito di null.
- Il confronto dell'anno usa `"YYYY"` (week-year) invece di `"yyyy"` (`CacheEntrate` ~:89).

**Fix applicato:** null-check espliciti in entrambe le cache, `substring` dell'anno protetta e formato cambiato in `"yyyy"`.

### 10. `WrapNote.selectAll` scambia i campi data — RISOLTO
`WrapNote.java:165,168` rispetto a `selectById:131-132`

`selectAll` mappa `data` dalla colonna 6 e `dataIns` dalla 5; `selectById` fa l'opposto. Almeno uno è sbagliato. Inoltre `selectById` crea una nuova `Note` ma restituisce il campo `note` del wrapper (:126-136), quindi restituisce sempre la stessa istanza.

**Fix applicato:** `selectAll` legge `data` dalla colonna 5 e `dataIns` dalla 6; `selectById` restituisce la `Note` caricata (o `null`) e valorizza utente/idUtente.

### 11. `Database.riempiLista` tronca i double — RISOLTO
`src/main/java/com/molinari/gestionespese/business/Database.java:308`

`Double.toString(rs.getInt(i))` su una colonna `DOUBLE` → valore troncato silenziosamente. Usare `getDouble`.

**Fix applicato:** usato `rs.getDouble(i)`.

### 12. Validazione invertita — RISOLTO
`src/main/java/com/molinari/gestionespese/view/impostazioni/AbstractCategorieView.java:81`

`return a == null && b == null && c == null;` significa "non valido" solo quando *tutti* i campi sono vuoti. Deve essere `||`.

**Fix applicato:** usato `||`.

---

## Medi

- **Gruppi null (RISOLTO):** `WrapCatSpese.insert` e `update` impostano `setNull(4, Types.INTEGER)` quando `getGruppi()` è null; analogo trattamento in `WrapEntrate`/`WrapSingleSpesa` per categoria/utente nulli.
- **SQL concatenata ovunque (RISOLTO):** `WrapNote`, `WrapCatSpese`, `WrapUtenti`, `WrapEntrate`, `WrapSingleSpesa`, `WrapBudget`, `WrapGruppi` e `WrapLookAndFeel` usano `PreparedStatement`/`ExecutePreparedStatement`; anche i filtri di movimenti entrate/uscite sono parametrici. I `selectById`/`delete` restano parametrici solo su id numerici. Aggiunti `ExecuteResultSet.executePrepared(...)` ed `executePreparedList(...)` in `utility`; nuovo `PasswordHasherTest` a copertura dell'hash password.
- **Costanti sbagliate in `WrapNote` (RISOLTO):** insert/update usano `Note.COL_*`.
- **Ambiente hardcoded (RISOLTO):** `config-core.xml` usa un percorso relativo (`./GestioneSpese.sqlite`); `DriveQuickstart` legge file id/output da argomenti, token dir da `drive.tokens.dir` e chiude le risorse senza `printStackTrace`.
- **Codice morto/duplicato (RISOLTO):** rimossa `DBUtil.creaStringStessaDimensione`.
- **JPA non realmente usata e incoerente:** invariato (fuori scope; runtime basato su DAO JDBC custom).
- **Due UI parallele per le categorie:** invariato; i formati di importanza sono ora allineati sull'ordinale.

---

## Ordine suggerito

1. Sistemare `ConnectionPool` (blocca tutto: concorrenza e integrità dati).
2. `WrapNote` (costruttore + `DeleteLastNote`), `WrapRisparmio.insert`, autenticazione `WrapUtenti` (injection/password in chiaro).
3. NPE nelle cache, cache sbagliata in `deleteAll`, CCE in `ListaMovimentiEntrate`, importanza categorie.
4. Convertire le SQL dei wrapper a `PreparedStatement`; spostare path e credenziali hardcoded in configurazione.
