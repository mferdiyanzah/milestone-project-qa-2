@BookingDetail
Feature: Booking Detail
  As a user
  I want to use the API to get booking details
  So that I can handle reservations effectively

  Background:
    Given the booking service is available

  Rule: View booking details
    Scenario: Successfully retrieve complete booking details
      When the user requests booking details for ID "1"
      Then the response status code should be 200
      And the booking details should contain:
        | Field            | Value    |
        | First Name       | Present  |
        | Last Name        | Present  |
        | Total Price      | Present  |
        | Deposit Paid     | Present  |
        | Checkin Date     | Present  |
        | Checkout Date    | Present  |
        | Additional Needs | Optional |

    Scenario: Attempt to retrieve non-existent booking
      When the user requests booking details for ID "999999"
      Then the response status code should be 404
      And the detail response should be "Not Found"

    Scenario: Attempt to retrieve booking with invalid ID format
      When the user requests booking details for ID "invalid-id"
      Then the response status code should be 404
      And the detail response should be "Not Found"

  Rule: Validate booking details format
    Scenario: Verify booking dates are in correct format
      When the user requests booking details for ID "1"
      Then the response status code should be 200
      And the booking dates should be in "YYYY-MM-DD" format

    Scenario: Verify price is a positive number
      When the user requests booking details for ID "1"
      Then the response status code should be 200
      And the total price should be a positive number
