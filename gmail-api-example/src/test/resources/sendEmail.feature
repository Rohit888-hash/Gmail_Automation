Feature: Send Email via Gmail API

  Scenario: Send an email with the subject "Testing Automation email"
    Given User has set up gmail API credentials
    When User sends an email with subject "Testing Automation email" to "Krishankumar.hpr1997@gmail.com"
    Then The email should be sent successfully
