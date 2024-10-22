@BookingList
Feature: Get Booking List
  Background: User wants to get list of bookings

  Scenario: Retrieve all booking IDs
    When the user sends a GET request to "/booking"
    Then the response status code should be 200
    And the response should contain a list of booking IDs

  Scenario: Retrieve booking IDs with a specific firstname
    Given the user filters bookings by firstname "John"
    When the user sends a GET request to "/booking?firstname=John"
    Then the response status code should be 200
    And the response should contain a list of booking IDs

  Scenario: Retrieve booking IDs with a specific lastname
    Given the user filters bookings by lastname "Doe"
    When the user sends a GET request to "/booking?lastname=Doe"
    Then the response status code should be 200
    And the response should contain a list of booking IDs

  Scenario: Retrieve booking IDs with checkin date
    Given the user filters bookings by checkin date "2023-10-01"
    When the user sends a GET request to "/booking?checkin=2023-10-01"
    Then the response status code should be 200
    And the response should contain a list of booking IDs

  Scenario: Retrieve booking IDs with both checkin and checkout dates
    Given the user filters bookings by checkin date "2023-10-01" and checkout date "2023-10-10"
    When the user sends a GET request to "/booking?checkin=2023-10-01&checkout=2023-10-10"
    Then the response status code should be 200
    And the response should contain a list of booking IDs

  Scenario: Retrieve booking IDs with non-existent firstname
    Given the user filters bookings by firstname "NonExistent"
    When the user sends a GET request to "/booking?firstname=NonExistent"
    Then the response status code should be 200
    And the response should contain an empty list

  Scenario: Invalid date format for checkin or checkout
    Given the user filters bookings with an invalid checkin date "01-10-2023"
    When the user sends a GET request to "/booking?checkin=01-10-2023"
    Then the response status code should be 400
    And the response reason should be "Bad request"
