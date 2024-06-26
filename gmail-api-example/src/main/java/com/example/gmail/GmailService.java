package com.example.gmail;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;
import java.util.Properties;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class GmailService {
	
	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final java.util.List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
	private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\dell\\eclipse-workspace\\gmail-api-example\\gmail-api-example\\src\\main\\resources\\client_secret_225500927065-dcu3nblrmq7opg1sgvr57f7ilvgqg0da.apps.googleusercontent.com.json";
	private static final String fromUser="rohitdemo578@gmail.com";
	
	
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        FileInputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
	
	
	public static Gmail getGmailService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
	
	 public static void sendEmail(String recipientEmail, String subject, String bodyText) throws MessagingException, IOException, GeneralSecurityException {
	        MimeMessage email = createEmail(recipientEmail, fromUser, subject, bodyText);
	        sendMessage(getGmailService(), "me", email);
	    }
	
	 
	  public static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        Session session = Session.getDefaultInstance(props, null);

	        MimeMessage email = new MimeMessage(session);
	        email.setFrom(new InternetAddress(from));
	        email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
	        email.setSubject(subject);
	        email.setText(bodyText);
	        return email;
	    }
	  
	  public static void sendMessage(Gmail service, String userId, MimeMessage email) throws MessagingException, IOException {
	        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	        email.writeTo(buffer);
	        byte[] raw = buffer.toByteArray();
	        String encodedEmail = Base64.getEncoder().encodeToString(raw);
	        Message message = new Message();
	        message.setRaw(encodedEmail);
	        service.users().messages().send(userId, message).execute();
	    }
	  

}
