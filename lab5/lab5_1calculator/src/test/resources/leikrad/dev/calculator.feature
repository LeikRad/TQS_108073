@calc_sample
Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I enter 4, 9 and +
    Then the result is 13

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

  Scenario: Multiplication
    When I enter 10, 2 and *
    Then the result is 20
  
  Scenario: Division
    When I enter 10, 2 and /
    Then the result is 5

  Scenario Outline: Several divisions
    When I enter <a>, <b> and /
    Then the result is <c>

  Examples: Operations
    | a | b  | c    |
    | 3 | 4  | 0.75 |
    | 2 | 10 | 0.2  |

  Scenario: Invalid Operation
    When I enter 1 and /
    Then an error should be thrown