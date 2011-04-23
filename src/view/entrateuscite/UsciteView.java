package view.entrateuscite;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import view.font.ButtonF;
import view.font.LabelTesto;
import view.font.LabelTitolo;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.DBUtil;
import business.Database;
import business.cache.CacheCategorie;
import business.comandi.CommandInserisciSpesa;
import business.comandi.CommandDeleteSpesa;
import domain.CatSpese;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;

public class UsciteView extends AbstractUsciteView {

	private static final long serialVersionUID = 1L;
	private TextFieldF tfNome;
	private TextFieldF tfData;
	private TextFieldF tfEuro;
	private TextAreaF	taDescrizione;
	private static JComboBox cCategorie;
	
	/**
	 * Create the panel.
	 */
	public UsciteView(WrapSingleSpesa spesa) {
		super(spesa);
		modelUscita.addObserver(this);
		this.setLayout(null);
		
		initLabel();
		
		taDescrizione = new TextAreaF();
		taDescrizione.setText("Inserisci qui la descrizione della spesa");
		taDescrizione.setBounds(42, 167, 318, 75);
		taDescrizione.setLineWrap(true);
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);
		add(taDescrizione);
		
		final TextAreaF descCateg = new TextAreaF();
		descCateg.setText("Qui compare la descrizione delle categorie");
		descCateg.setBounds(393, 167, 318, 75);
		descCateg.setLineWrap(true);
		descCateg.setWrapStyleWord(true);
		descCateg.setAutoscrolls(true);
		add(descCateg);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(41, 278, 820, 10);
		add(separator);
		
		tfNome = new TextFieldF();
		tfNome.setBounds(41, 90, 150, 27);
		add(tfNome);
		tfNome.setColumns(10);
		
		cCategorie = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
		cCategorie.setBounds(210, 90, 150, 27);
		add(cCategorie);
		
		cCategorie.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				CatSpese spese = null;
				if (cCategorie.getSelectedIndex() != 0) {
					spese = (CatSpese) cCategorie.getSelectedItem();
					// int indice = categorie.getSelectedIndex();
					// il campo sotto serve per inserire la descrizione nel
					// caso
					// si selezioni
					// una categoria e si vogliono maggiori info

					descCateg.setText(spese!=null?spese.getdescrizione():"");
				}

				
			}
		});
		
		tfData = new TextFieldF();
		tfData.setColumns(10);
		GregorianCalendar gc = new GregorianCalendar();
		tfData.setText(DBUtil.dataToString(gc.getTime(), "yyyy/MM/dd"));
		tfData.setBounds(393, 90, 150, 27);
		add(tfData);
		
		tfEuro = new TextFieldF();
		tfEuro.setColumns(10);
		tfEuro.setBounds(564, 90, 150, 27);
		add(tfEuro);
		
		
		
		//Bottone Elimina
		ButtonF eliminaUltima = new ButtonF();
		eliminaUltima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//TODO metodo che restituisce ultima spesa oppure usare getLast() del CommandManager
//					if(modelUscita.deleteLastSpesa()){
					if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteSpesa(modelUscita), SingleSpesa.NOME_TABELLA)){
						update(modelUscita, null);
						Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
						JOptionPane.showMessageDialog(null,"Ok, ultima uscita eliminata correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Non ci siamo!", JOptionPane.ERROR_MESSAGE, new ImageIcon("./imgUtil/index.jpeg"));
					e1.printStackTrace();
				}
			}
		});
		
		
		eliminaUltima.setText("Elimina Ultima");
		eliminaUltima.setBounds(735, 134, 126, 36);
		add(eliminaUltima);
		
		ButtonF inserisci = new ButtonF();
		inserisci.setText("Inserisci");
		inserisci.setBounds(735, 90, 126, 36);
		add(inserisci);
		

		inserisci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setUscite();
				if(getcNome()!=null && getcDescrizione() !=null && getcData()!=null && getDataIns()!=null && getCategoria()!=null && getdEuro()!=0.0 && getUtenti()!=null){
					if(Controllore.getSingleton().getCommandManager().invocaComando(new CommandInserisciSpesa(modelUscita), SingleSpesa.NOME_TABELLA)){
						JOptionPane.showMessageDialog(null, "Ok, uscita inserita correttamente!", "Perfetto!!!", JOptionPane.INFORMATION_MESSAGE);
						log.fine("Uscita inserita, nome: "
								+ modelUscita.getnome() +", id: " +modelUscita.getidSpesa());
					}
				}else{
					JOptionPane.showMessageDialog(null,"E' necessario riempire tutti i campi","Non ci siamo!",JOptionPane.ERROR_MESSAGE,new ImageIcon("./imgUtil/index.jpeg"));
							log.severe("Uscita non inserita: e' necessario riempire tutti i campi");
				}
				
			}
			{
				update(modelUscita, null);
			}
		});
	}

	private void initLabel() {
		LabelTesto lblNomeSpesa = new LabelTesto("Nome Spesa");
		lblNomeSpesa.setBounds(42, 64, 118, 27);
		add(lblNomeSpesa);
		
		LabelTesto lblEuro = new LabelTesto("Euro");
		lblEuro.setBounds(564, 64, 77, 27);
		add(lblEuro);
		
		LabelTesto lblCategorie = new LabelTesto("Categorie");
		lblCategorie.setBounds(210, 64, 125, 27);
		add(lblCategorie);
		
		LabelTesto lblData = new LabelTesto("Data");
		lblData.setBounds(393, 64, 77, 27);
		add(lblData);
		
		LabelTesto lblDescrizione = new LabelTesto("Descrizione Spesa");
		lblDescrizione.setBounds(43, 142, 212, 25);
		add(lblDescrizione);
		
		LabelTesto lblDescrizione_1 = new LabelTesto("Descrizione Categoria");
		lblDescrizione_1.setBounds(393, 141, 232, 27);
		add(lblDescrizione_1);
		
		LabelTitolo lblPannelloUscite = new LabelTitolo("Pannello Uscite");
		lblPannelloUscite.setBounds(42, 24, 136, 36);
		add(lblPannelloUscite);
	}
	
	private void setUscite(){
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		setCategoria((CatSpese) cCategorie.getSelectedItem());
		if(AltreUtil.checkData(tfData.getText())){
			setcData(tfData.getText());
		}else{
			String messaggio = "La data va inserita con il seguente formato: aaaa/mm/gg";
			JOptionPane.showMessageDialog(null,messaggio,"Non ci siamo!", JOptionPane.ERROR_MESSAGE,new ImageIcon("./imgUtil/index.jpeg"));
		}
		if(AltreUtil.checkDouble(tfEuro.getText())){
			Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		}else{
			String messaggio = "Valore in Euro inserito non correttamente";
			JOptionPane.showMessageDialog(null,messaggio,"Non ci siamo!", JOptionPane.ERROR_MESSAGE,new ImageIcon("./imgUtil/index.jpeg"));
		}
		setUtenti(Controllore.getSingleton().getUtenteLogin());
		setDataIns(DBUtil.dataToString(new Date(), "yyyy/MM/dd"));
	}
	
	/**
	 * @return the categorie
	 */
	public JComboBox getComboCategorie() {
			return cCategorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setComboCategorie(JComboBox categorie) {
		UsciteView.cCategorie = categorie;
	}


	@Override
	public void update(Observable o, Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cCategorie.setSelectedItem(getCategoria());
		tfData.setText(getcData());
		tfEuro.setText(getdEuro().toString());
	}
}
