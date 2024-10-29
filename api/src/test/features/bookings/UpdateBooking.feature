Feature: Update Booking
  As a user of the booking system
  I want to update an existing booking
  So that I can modify the booking details

  Background:
    Given I have a valid authentication token

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

  Scenario: Update non-existent booking
    When I update booking with id 999999 with valid booking details
    Then the response status code should be 404
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
      | firstname       | <generate_long_string_255>  |
      | lastname        | <generate_long_string_255>  |
      | totalprice      | 111                         |
      | depositpaid     | true                        |
      | checkin         | 2018-01-01                  |
      | checkout        | 2019-01-01                  |
      | additionalneeds | <generate_long_string_1000> |
    Then the response status code should be 400
    And the error message should contain "Input exceeds maximum length"

Feature: Booking Validation Rules
  As a system administrator
  I want to enforce booking validation rules
  So that the booking data remains consistent and valid

  Background:
    Given I have a valid authentication token "abc123"

  Scenario Outline: Validate total price boundaries
    When I update booking with id 1 with the following details:
      | firstname       | James      |
      | lastname        | Brown      |
      | totalprice      | <price>    |
      | depositpaid     | true       |
      | checkin         | 2018-01-01 |
      | checkout        | 2019-01-01 |
      | additionalneeds | Breakfast  |
    Then the response status code should be <status>
    And the response should contain "<message>"

    Examples:
      | price      | status | message                     |
      | -1         | 400    | Price cannot be negative    |
      | 0          | 200    | Success                     |
      | 2147483648 | 400    | Price exceeds maximum value |

  Scenario: Concurrent booking updates
    Given I have 2 concurrent update requests for booking id 1
    When I send the update requests simultaneously
    Then one request should succeed with status code 200
    And one request should fail with status code 409
    And the error message should contain "Concurrent update detected"