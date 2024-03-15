Feature: BlazeDemo
    
    Scenario: Normal Flow
        Given I am on the BlazeDemo home page
        When I write/select "Boston" on the "fromPort" input
        And I write/select "Berlin" on the "toPort" input
        And I click "Find Flights"
        Then I should be redirected to a page with the title "BlazeDemo - reserve"
        When I click Choose This Flight on flight 43
        Then I should be redirected to a page with the title "BlazeDemo Purchase"
        When I write/select "John Doe" on the "inputName" input
        And I write/select "123 Main St." on the "address" input
        And I write/select "Anytown" on the "city" input
        And I write/select "State" on the "state" input
        And I write/select "12345" on the "zipCode" input
        And I write/select "American Express" on the "cardType" input
        And I write/select "123456789000" on the "creditCardNumber" input
        And I write/select "01" on the "creditCardMonth" input
        And I write/select "2025" on the "creditCardYear" input
        And I write/select "John Doe" on the "nameOnCard" input
        And I click "Purchase Flight"
        Then I should be redirected to a page with the title "BlazeDemo Confirmation"
