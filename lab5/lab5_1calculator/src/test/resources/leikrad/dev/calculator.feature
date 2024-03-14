Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I enter 4, 9 and +
    Then the result is 9

  Scenario: Substraction
    When I enter 7, 2 and - 
    Then the result is 5

  Scenario Outline: Several additions
    When I enter <a>, <b> and +
    Then the result is <c>

  Examples: Single digits
    | a | b | c  |
    | 1 | 2 | 3  |
    | 3 | 7 | 10 |