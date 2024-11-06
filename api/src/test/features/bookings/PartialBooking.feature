@PartialUpdateBooking
Feature: Partial Booking API
  As a user
  I want to be able to update a booking partially
  So that I can modify specific details of the booking

  Background:
    Given I have a valid authentication token

  Rule: Partial Booking Updates
    Scenario: Successfully update a booking with partial details
      When I update booking with id 1 with the following details:
        | firstname | James |
        | lastname  | Brown |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with special characters in names
      When I update booking with id 1 with the following details:
        | firstname | James-Über     |
        | lastname  | O'Brown-Müller |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with additional needs
      When I update booking with id 1 with the following details:
        | additionalneeds | Breakfast |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with empty additional needs
      When I update booking with id 1 with the following details:
        | additionalneeds |  |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with empty firstname
      When I update booking with id 1 with the following details:
        | firstname |  |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with empty lastname
      When I update booking with id 1 with the following details:
        | lastname |  |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with empty payload
      When I update booking with id 1 with empty payload
      Then the response status code should be 200
