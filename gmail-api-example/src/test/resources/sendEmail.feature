Feature: Send Email via Gmail API

  Scenario: Send an email with the subject "Incubyte"
    Given User has set up gmail API credentials
    When User sends an email with subject "Incubyte" to "shubham.solanki@sourcingxpress.com"
    Then The email should be sent successfully
