@UpdateBooking
Feature: Update Booking API
  As a user of the booking system
  I want to manage and validate bookings
  So that I can maintain accurate booking records

  Background:
    Given I have a valid authentication token

  Rule: Basic Booking Updates
    Scenario: Successfully update an existing booking with valid data
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | Brown    | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      When the user requests to create a booking
      Then the response status code should be 200
      And I update booking with existing id with the following details:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | Brownnnn | 111312     | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with special characters in names
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | Brown    | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      When the user requests to create a booking
      Then the response status code should be 200
      When I update booking with existing id with the following details:
        | firstname  | lastname       | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | James-Über | O'Brown-Müller | 111312     | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      Then the response status code should be 200
      And the response should contain the updated booking details


  Rule: Date Validation
    Scenario Outline: Update booking with invalid dates
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | Brown    | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      When the user requests to create a booking
      Then the response status code should be 200
      And the user get the booking id
      When I update booking with existing id with the following details:
        | firstname  | lastname       | totalprice | depositpaid | checkin   | checkout   | additionalneeds |
        | James-Über | O'Brown-Müller | 111312     | true        | <checkin> | <checkout> | Breakfast       |
      Then the response status code should be 200

      Examples:
        | checkin    | checkout   |
        | 2018-13-01 | 2019-01-01 |
        | 2018-01-32 | 2019-01-01 |
        | 2018/01/01 | 2019-01-01 |
        | 2018-01-01 | 2018-01-01 |

  Rule: Error Handling
    Scenario: Update non-existent booking
      When I update booking with id 999999 with valid booking details
      Then the response status code should be 405

    Scenario: Update booking with invalid auth token
      Given I have an invalid authentication token "invalid_token"
      When I update booking with existing id with the following details:
        | firstname  | lastname       | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | James-Über | O'Brown-Müller | 111312     | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      Then the response status code should be 403

    Scenario: Update booking with missing required fields
      When I update booking with id 1 with missing required fields:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | Brown    | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      Then the response status code should be 405

  Rule: Business Validation Rules
    Scenario: Update booking with checkin date after checkout date
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | Brown    | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      When the user requests to create a booking
      Then the response status code should be 200
      When I update booking with existing id with the following details:
        | firstname  | lastname       | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | James-Über | O'Brown-Müller | 111312     | true        | 2020-01-01 | 2019-01-01 | Breakfast       |
      Then the response status code should be 200