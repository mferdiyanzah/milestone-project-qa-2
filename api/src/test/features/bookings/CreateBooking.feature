@CreateBooking
Feature: Create Booking API
  As a user
  I want to be able to create a booking
  So that I can store the booking details

  Rule: User must provide valid booking details to create a booking
    Scenario: Successful creation of a booking
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | Brown    | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      When the user requests to create a booking
      Then the response status code should be 200
      And the user get the booking id

    Scenario: Attempt to create a booking with invalid additional needs
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | lorem    | 111        | true        | 2018-01-01 | 2019-01-01 | 123124444       |
      When the user requests to create a booking
      Then the response status code should be 200
      And the user get the booking id

    Scenario: Attempt to create a booking with invalid date format
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
        | Jim       | lorem    | 111        | true        | 2018-01-01 | 2019-01-01 | Breakfast       |
      When the user requests to create a booking
      Then the response status code should be 200
      And the user get the booking id

    Scenario: Attempt to create a booking with missing details
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin | checkout | additionalneeds |
        | Jim       |          | 111        | true        |         |          | Breakfast       |
      When the user requests to create a booking
      Then the response status code should be 500

    Scenario: Attempt to create a booking with invalid details
      Given the user provides booking details:
        | firstname | lastname | totalprice | depositpaid | checkin | checkout | additionalneeds |
        | Jim       | lorem    | 111        | true        |         |          | Breakfast       |
      When the user requests to create a booking
      Then the response status code should be 500


