@Authentication
Feature: Authentication API
  As a user
  I want to be able to authenticate with the API
  So that I can access protected resources.

  Background:
    Given the authentication service is available

  Rule: User must provide valid credentials to get authentication token
    Scenario: Successful authentication with valid credentials
      Given the user provides credentials:
        | username | password    |
        | admin    | password123 |
      When the user requests authentication token
      Then the response status code should be 200
      And the response should contain a valid token

    Scenario: Attempt to authenticate with missing username
      Given the user provides credentials:
        | username | password    |
        |          | password123 |
      When the user requests authentication token
      Then the response status code should be 200
      And the response reason should be "Bad credentials"

    Scenario: Attempt to authenticate with missing password
      Given the user provides credentials:
        | username | password |
        | admin    |          |
      When the user requests authentication token
      Then the response status code should be 200
      And the response reason should be "Bad credentials"

    Scenario: Attempt to authenticate with invalid username
      Given the user provides credentials:
        | username   | password    |
        | loremipsum | password123 |
      When the user requests authentication token
      Then the response status code should be 200
      And the response reason should be "Bad credentials"

    Scenario: Attempt to authenticate with invalid password
      Given the user provides credentials:
        | username | password  |
        | admin    | wrongpass |
      When the user requests authentication token
      Then the response status code should be 200
      And the response reason should be "Bad credentials"
