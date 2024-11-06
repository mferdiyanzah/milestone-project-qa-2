@LoginPage
Feature: Login Page
  As a user
  I want to be able to login to the application
  So that I can access the features

  Background:
    Given I am on the login page

  Rule: I have a valid username and password
    Scenario: I can login with valid credentials
      When I enter my username and password:
        | username      | password     |
        | standard_user | secret_sauce |
      And I click the login button
      Then I am redirected to the home page

  Rule: I have an invalid username or password
    Scenario: I cannot login with invalid credentials
      When I enter my username and password:
        | username        | password     |
        | locked_out_user | secret_sauce |
      And I click the login button
      Then I get an error message

    Scenario: I cannot login with long username
      When I enter my username and password:
        | username                                                                                                                                                                                                                                | password  |
        | Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. | 123124123 |
      And I click the login button
      Then I get an error message

    Scenario: I cannot login with long password
      When I enter my username and password:
        | username      | password                                                                                                                                                                                                                                |
        | standard_user | Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. |
      And I click the login button
      Then I get an error message

    Scenario: I cannot login with empty username
      When I enter my username and password:
        | username | password     |
        |          | secret_sauce |
      And I click the login button
      Then I get an error message
      And I get an error message with text "Epic sadface: Username is required"

    Scenario: I cannot login with empty password
      When I enter my username and password:
        | username      | password |
        | standard_user |          |
      And I click the login button
      Then I get an error message
      And I get an error message with text "Epic sadface: Password is required"

    Scenario: I cannot login with empty username and password
      When I enter my username and password:
        | username | password |
        |          |          |
      And I click the login button
      Then I get an error message
      And I get an error message with text "Epic sadface: Username is required"

    Scenario: I cannot login with sql injection in the fields
      When I enter my username and password:
        | username | password |
        | ' or "   | ' or "   |
      And I click the login button
      Then I get an error message

