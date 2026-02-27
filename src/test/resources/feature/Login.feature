Feature: Login Tests

  Scenario: Positive login test
    Given I open login page
    When I enter email "user.email"
    And I enter password "user.password"
    And I click submit
    Then I am logged in
