@Authentication
Feature: Authentication API

  Scenario: Create a new token with valid credentials
    Given the user has username "admin" and password "password123"
    When the user requests a token
    Then the response status code should be 200
    And the response should contain a token

  Scenario: Attempt to authenticate with invalid credentials
    Given the user has username "admin11" and password "password123"
    When the user requests a token
    Then the response status code should be 200
    And the response reason should be "Bad credentials"

  Scenario: Attempt to authenticate with missing username
    Given the user has no username and password "password123"
    When the user requests a token
    Then the response status code should be 400
    And the response reason should be "Bad request"

  Scenario: Attempt to authenticate with missing password
    Given the user has username "admin" and no password
    When the user requests a token
    Then the response status code should be 400
    And the response reason should be "Bad request"

  Scenario: Attempt to authenticate with invalid content type
    Given the user has username "admin" and password "password123"
    When the user requests a token with content type "text/plain"
    Then the response status code should be 415
    And the response reason should be "Unsupported content type"

  Scenario: Attempt to authenticate with long username
    Given the user has a long username and password "password123"
    When the user requests a token
    Then the response status code should be 400
    And the response reason should be "Bad request"
