#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: VodafoneUk Smoke Test
  I want to use this template for my feature file

  @VodafoneApp
  Scenario: Test Login with Valid Credentials
    Given Open "Chrome" and launch the Home Page URL
    When user enter the valid "multiservicepmown" and "Testing1"
    And click on Login CTA
    Then User is navigated to account summary Taskflow
    And verify the account summary title
