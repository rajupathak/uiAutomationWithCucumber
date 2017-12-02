Feature: Login functionalty With diffrent Credential
  I want to use this template for my feature file

  @Vodafone
  Scenario Outline: Login with multiple credential
    Given Open "Chrome" and launch the Home Page URL
    When user enter the valid "<userName>" and "<password>"
    And click on Login CTA
    Then User is navigated to account summary Taskflow
    And verify the account summary title

    Examples: 
      | userName          | password |
      | multiservicepmown | Testing1 |
      | steve.dearns27    | testing1 |
