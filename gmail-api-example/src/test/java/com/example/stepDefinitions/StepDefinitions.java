package com.example.stepDefinitions;

	
	import io.cucumber.java.en.Given;

	import io.cucumber.java.en.Then;
	import io.cucumber.java.en.When;
	import jakarta.mail.MessagingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.GmailScopes;

	public class StepDefinitions {
		
		private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
		private static final java.util.List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
		private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\dell\\eclipse-workspace\\gmail-api-example\\gmail-api-example\\src\\main\\resources\\client_secret_225500927065-dcu3nblrmq7opg1sgvr57f7ilvgqg0da.apps.googleusercontent.com.json";
		

	    @Given("User has set up gmail API credentials")
	    public void user_has_set_up_gmail_api_credentials() throws IOException {
	         //Setting Up credentials correctly
	    	 FileInputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));
	         GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
	    }
	    
	    @When("User sends an email with subject {string} to {string}")
	    public void user_sends_an_email_with_subject_to(String subject, String recipientEmail) {
	        try {
	        	
	            com.example.gmail.GmailService.sendEmail(recipientEmail, subject, "Automation QA test for Incubyte by Rohit");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    @Then("The email should be sent successfully")
	    public void the_email_should_be_sent_successfully() {
	        System.out.println("Email sent successfully!");
	    }

}
