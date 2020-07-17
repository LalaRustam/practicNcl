@login
Feature: login

  @smoke  @run
  Scenario: login link
    Given a Guest
    And I am on Homepage
    And I navigated to "Shore Excursion" page
    When I search for destination "Alaska Cruises"
    Then Shore Excursions page is present
    And Results are filtered by "Alaska Cruises"
    And Filter By Ports are only belong to "Alaska" and "British Columbia"


