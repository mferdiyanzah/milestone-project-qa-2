@DeleteBooking
Feature: Delete Booking API
  As a user
  I want to be able to delete a booking
  So that I can remove a booking from the system

  Background:
    Given I have a valid authentication token

  Rule: Delete Booking
    Scenario: Successfully delete a booking
      When I delete booking with id 1
      Then the response status code should be 201

    Scenario: Delete booking with invalid id
      When I delete booking with id 123124123213
      Then the response status code should be 405

    Scenario: Delete booking with non-integer id
      When I delete booking with id "abc"
      Then the response status code should be 405

    Scenario: Delete booking with negative id
      When I delete booking with id -1
      Then the response status code should be 405

    Scenario: Delete booking with zero id
      When I delete booking with id 0
      Then the response status code should be 405

    Scenario: Delete booking with large id
      When I delete booking with id 2147483647213
      Then the response status code should be 405

    Scenario: Delete booking with non-numeric id
      When I delete booking with id "abc123"
      Then the response status code should be 405

    Scenario: Delete booking with empty id
      When I delete booking with id ""
      Then the response status code should be 404

    Scenario: Delete booking with null id
      When I delete booking with id null
      Then the response status code should be 405
