@UpdateBooking
Feature: Update Booking API
  As a user of the booking system
  I want to manage and validate bookings
  So that I can maintain accurate booking records

  Background:
    Given I have a valid authentication token

  Rule: Basic Booking Updates
    Scenario: Successfully update an existing booking with valid data
      When I update booking with id 1 with the following details:
        | firstname       | James      |
        | lastname        | Brown      |
        | totalprice      | 111        |
        | depositpaid     | true       |
        | checkin         | 2018-01-01 |
        | checkout        | 2019-01-01 |
        | additionalneeds | Breakfast  |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with special characters in names
      When I update booking with id 1 with the following details:
        | firstname       | James-Über     |
        | lastname        | O'Brown-Müller |
        | totalprice      | 111            |
        | depositpaid     | true           |
        | checkin         | 2018-01-01     |
        | checkout        | 2019-01-01     |
        | additionalneeds | Breakfast      |
      Then the response status code should be 200
      And the response should contain the updated booking details

    Scenario: Update booking with maximum price value
      When I update booking with id 1 with the following details:
        | firstname       | James      |
        | lastname        | Brown      |
        | totalprice      | 2147483647 |
        | depositpaid     | true       |
        | checkin         | 2018-01-01 |
        | checkout        | 2019-01-01 |
        | additionalneeds | Breakfast  |
      Then the response status code should be 200
      And the response should contain the updated booking details

  Rule: Date Validation
    Scenario Outline: Update booking with invalid dates
      When I update booking with id 1 with the following details:
        | firstname       | James      |
        | lastname        | Brown      |
        | totalprice      | 111        |
        | depositpaid     | true       |
        | checkin         | <checkin>  |
        | checkout        | <checkout> |
        | additionalneeds | Breakfast  |
      Then the response status code should be 400
      And the error message should contain "Invalid date format"

      Examples:
        | checkin    | checkout   |
        | 2018-13-01 | 2019-01-01 |
        | 2018-01-32 | 2019-01-01 |
        | 2018/01/01 | 2019-01-01 |
        | 2018-01-01 | 2018-01-01 |

  Rule: Error Handling
    Scenario: Update non-existent booking
      When I update booking with id 999999 with valid booking details
      Then the response status code should be 403
      And the error message should contain "Booking not found"

    Scenario: Update booking with invalid auth token
      Given I have an invalid authentication token "invalid_token"
      When I update booking with id 1 with valid booking details
      Then the response status code should be 403
      And the error message should contain "Forbidden"

    Scenario: Update booking with missing required fields
      When I update booking with id 1 with missing required fields:
        | firstname   |            |
        | lastname    | Brown      |
        | totalprice  | 111        |
        | depositpaid | true       |
        | checkin     | 2018-01-01 |
        | checkout    | 2019-01-01 |
      Then the response status code should be 400
      And the error message should contain "Missing required fields"

  Rule: Business Validation Rules
    Scenario: Update booking with checkin date after checkout date
      When I update booking with id 1 with the following details:
        | firstname       | James      |
        | lastname        | Brown      |
        | totalprice      | 111        |
        | depositpaid     | true       |
        | checkin         | 2019-01-01 |
        | checkout        | 2018-01-01 |
        | additionalneeds | Breakfast  |
      Then the response status code should be 400
      And the error message should contain "Checkin date must be before checkout date"

    Scenario: Update booking with very long input strings
      When I update booking with id 1 with the following details:
        | firstname       | lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua |
        | lastname        | lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua |
        | totalprice      | 111                                                                                                                      |
        | depositpaid     | true                                                                                                                     |
        | checkin         | 2018-01-01                                                                                                               |
        | checkout        | 2019-01-01                                                                                                               |
        | additionalneeds | lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua |
      Then the response status code should be 400
      And the error message should contain "Input exceeds maximum length"

    Scenario Outline: Validate total price boundaries
      When I update booking with id 1 with the following details:
        | firstname       | James      |
        | lastname        | Brown      |
        | totalprice      | 123123     |
        | depositpaid     | true       |
        | checkin         | 2018-01-01 |
        | checkout        | 2019-01-01 |
        | additionalneeds | Breakfast  |
      Then the response status code should be <status>
      And the response should contain <message>

      Examples:
        | price      | status | message                     |
        | -1         | 400    | Price cannot be negative    |
        | 0          | 200    | Success                     |
        | 2147483648 | 400    | Price exceeds maximum value |

  Rule: Concurrency Handling
    Scenario: Concurrent booking updates
      Given I have 2 concurrent update requests for booking id 1
      When I send the update requests simultaneously
      Then one request should succeed with status code 200
      And one request should fail with status code 409
      And the error message should contain "Concurrent update detected"