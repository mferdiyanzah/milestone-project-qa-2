@Booking
Feature: Get All Booking IDs
  As a user
  I want to use the API to get all booking IDs
  So that I can handle reservations effectively

  Background:
    Given the booking service is available

  Rule: Retrieve booking information
    Scenario: Get all booking IDs
      When the user requests all bookings
      Then the response status code should be 200
      And the response should contain booking IDs

    Scenario Outline: Filter bookings by guest information
      When the user filters bookings by "<filter_type>" with value "<value>"
      Then the response status code should be 200
      And the response should contain filtered booking IDs

      Examples:
        | filter_type | value |
        | firstname   | John  |
        | lastname    | Smith |

    Scenario: Filter bookings by date range
      When the user filters bookings by date range:
        | checkin  | 2024-01-01 |
        | checkout | 2024-01-10 |
      Then the response status code should be 200
      And the response should contain filtered booking IDs

  Rule: Retrieve specific booking details
    Scenario: Get existing booking details
      When the user requests booking details for ID "1"
      Then the response status code should be 200
      And the booking details should be complete

    Scenario: Request non-existent booking
      When the user requests booking details for ID "999999"
      Then the response status code should be 404
