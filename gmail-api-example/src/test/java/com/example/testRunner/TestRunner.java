package com.example.testRunner;


import io.cucumber.junit.Cucumber;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "C:\\Users\\dell\\eclipse-workspace\\gmail-api-example\\gmail-api-example\\src\\test\\resources\\sendEmail.feature",
    glue = {"com.example.stepDefinitions"},
    plugin = {
    		"pretty",
    		"html:target/cucumber-reports/cucumber.html",
            "json:target/cucumber-reports/cucumber.json",
            "junit:target/cucumber-reports/cucumber.xml"
    		},
    monochrome = true
)
public class TestRunner {
	
}
