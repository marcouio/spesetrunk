package com.molinari.gestionespese.business;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DriveQuickstart {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = System.getProperty("drive.tokens.dir", "tokens");
    private static final String CREDENTIALS_RESOURCE_PATH = "/credentials.json";
    private static final String CREDENTIALS_PROPERTY = "drive.credentials.path";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_READONLY);
    private static final int OAUTH_PORT = Integer.getInteger("drive.oauth.port", 8888);
    /** Secondi di attesa per il completamento dell'autorizzazione OAuth (default 120). */
    private static final int OAUTH_TIMEOUT_SECONDS = Integer.getInteger("drive.oauth.timeout", 120);
    private static final Pattern FILE_ID_PATH_PATTERN = Pattern.compile("/file/d/([a-zA-Z0-9_-]+)");
    private static final Pattern ID_PARAM_PATTERN = Pattern.compile("[?&]id=([a-zA-Z0-9_-]+)");
    private static final Pattern RAW_FILE_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{10,}$");

    public static void downloadFile(String pathToSave, String fileId) throws IOException {
        if (fileId == null || fileId.trim().isEmpty()) {
            throw new IOException("File id di Google Drive mancante");
        }
        final File target = new File(pathToSave).getAbsoluteFile();
        final File parent = target.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("Impossibile creare la cartella " + parent.getAbsolutePath());
        }
        final File temp = File.createTempFile(target.getName(), ".part", parent);
        try {
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            final Drive service = new Drive.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            try (FileOutputStream fos = new FileOutputStream(temp)) {
                service.files().get(fileId).executeMediaAndDownloadTo(fos);
            }
            if (temp.length() == 0) {
                throw new IOException("Il file scaricato da Google Drive e' vuoto: " + fileId);
            }
            Files.move(temp.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (GeneralSecurityException e) {
            deleteQuietly(temp);
            throw new IOException("Errore di sicurezza nell'autenticazione a Google Drive", e);
        } catch (IOException e) {
            deleteQuietly(temp);
            throw e;
        }
    }

    public static void downloadFileFromUrl(String pathToSave, String driveUrl) throws IOException {
        final String fileId = extractFileId(driveUrl);
        if (fileId == null) {
            throw new IOException("Impossibile ricavare il file id dall'URL Google Drive: " + driveUrl);
        }
        downloadFile(pathToSave, fileId);
    }

    public static boolean isDriveUrl(String url) {
        return url != null && (url.contains("drive.google.com") || url.contains("docs.google.com"));
    }

    public static String extractFileId(String url) {
        if (url == null) {
            return null;
        }
        final String trimmed = url.trim();
        Matcher matcher = FILE_ID_PATH_PATTERN.matcher(trimmed);
        if (matcher.find()) {
            return matcher.group(1);
        }
        matcher = ID_PARAM_PATTERN.matcher(trimmed);
        if (matcher.find()) {
            return matcher.group(1);
        }
        if (RAW_FILE_ID_PATTERN.matcher(trimmed).matches()) {
            return trimmed;
        }
        return null;
    }

    private static void deleteQuietly(File file) {
        if (file.exists() && !file.delete()) {
            file.deleteOnExit();
        }
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        final InputStream in = openCredentials();
        final GoogleClientSecrets clientSecrets;
        try (InputStreamReader reader = new InputStreamReader(in)) {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
        }

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(OAUTH_PORT).build();
        return authorizeWithTimeout(new AuthorizationCodeInstalledApp(flow, receiver), receiver);
    }

    /**
     * Esegue l'autorizzazione OAuth su un thread separato per poterla interrompere se l'utente
     * non completa il login entro {@link #OAUTH_TIMEOUT_SECONDS} secondi.
     * {@link LocalServerReceiver#waitForCode()} blocca indefinitamente, quindi allo scadere del
     * timeout il receiver viene fermato e viene lanciata una {@link IOException}.
     */
    private static Credential authorizeWithTimeout(final AuthorizationCodeInstalledApp app,
            final LocalServerReceiver receiver) throws IOException {
        final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                final Thread thread = new Thread(runnable, "google-drive-oauth");
                thread.setDaemon(true);
                return thread;
            }
        });
        try {
            final Future<Credential> future = executor.submit(new Callable<Credential>() {
                @Override
                public Credential call() throws Exception {
                    return app.authorize("user");
                }
            });
            try {
                final Credential credential = future.get(OAUTH_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                if (credential == null) {
                    throw new IOException("Autorizzazione Google Drive non completata");
                }
                return credential;
            } catch (TimeoutException e) {
                future.cancel(true);
                throw new IOException("Timeout: autorizzazione Google Drive non completata entro "
                        + OAUTH_TIMEOUT_SECONDS + " secondi. Completare il login nel browser e riavviare"
                        + " l'applicazione (oppure aumentare -Ddrive.oauth.timeout)", e);
            } catch (InterruptedException e) {
                future.cancel(true);
                Thread.currentThread().interrupt();
                throw new IOException("Autorizzazione Google Drive interrotta", e);
            } catch (ExecutionException e) {
                final Throwable cause = e.getCause();
                if (cause instanceof IOException) {
                    throw (IOException) cause;
                }
                throw new IOException("Errore durante l'autorizzazione Google Drive", cause);
            }
        } finally {
            // Sblocca waitForCode() se ancora in attesa e libera la porta del server locale.
            stopQuietly(receiver);
            executor.shutdownNow();
        }
    }

    private static void stopQuietly(LocalServerReceiver receiver) {
        try {
            receiver.stop();
        } catch (IOException e) {
            // La chiusura del receiver non deve mascherare l'errore principale.
        }
    }

    private static InputStream openCredentials() throws IOException {
        final String credentialsPath = System.getProperty(CREDENTIALS_PROPERTY);
        if (credentialsPath != null) {
            final File credentialsFile = new File(credentialsPath);
            if (credentialsFile.isFile()) {
                return new FileInputStream(credentialsFile);
            }
        }
        final InputStream in = DriveQuickstart.class.getResourceAsStream(CREDENTIALS_RESOURCE_PATH);
        if (in == null) {
            throw new IOException("File di credenziali non trovato: " + CREDENTIALS_RESOURCE_PATH
                    + " (oppure impostare -D" + CREDENTIALS_PROPERTY + "=percorso/credentials.json)");
        }
        return in;
    }

    public static void main(String... args) throws IOException {
        if (args.length == 0) {
            System.out.println("Uso: DriveQuickstart <fileId|urlDrive> [fileOutput]");
            return;
        }
        final String outputFile = args.length > 1 ? args[1] : "database.sqlite";
        if (isDriveUrl(args[0])) {
            downloadFileFromUrl(outputFile, args[0]);
        } else {
            downloadFile(outputFile, args[0]);
        }
    }
}
