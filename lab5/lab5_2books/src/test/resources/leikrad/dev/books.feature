Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
 
  Scenario: Search books by publication year
    Given I have the following books in the store
    | title                                | author      | published |
    | The Devil in the White City          | Erik Larson | 2013-03-14|
    | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
    | In the Garden of Beasts              | Erik Larson | 1998-12-05|
    When the customer searches for books published between 2000 and 2014
    Then 2 books should have been found
      And Book 1 should have the title 'The Devil in the White City'
      And Book 2 should have the title 'The Lion, the Witch and the Wardrobe'

  Scenario: Search books by author
    Given I have the following books in the store
    | title                                | author      | published |
    | The Devil in the White City          | Erik Larson | 2013-03-14|
    | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
    | In the Garden of Beasts              | Erik Larson | 1998-12-05|
    When the customer searches for books written by 'C.S. Lewis'
    Then 1 book should have been found
      And Book 1 should have the title 'The Lion, the Witch and the Wardrobe'
  
  Scenario: Search books by title
    Given I have the following books in the store
    | title                                | author      | published |
    | The Devil in the White City          | Erik Larson | 2013-03-14|
    | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
    | In the Garden of Beasts              | Erik Larson | 1998-12-05|
    When the customer searches for books with the title 'In the Garden of Beasts'
    Then 1 book should have been found
      And Book 1 should have the title 'In the Garden of Beasts'
  
  Scenario: No books found
    Given I have the following books in the store
    | title                                | author      | published |
    | The Devil in the White City          | Erik Larson | 2013-03-14|
    | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
    | In the Garden of Beasts              | Erik Larson | 1998-12-05|
    When the customer searches for books written by 'George Orwell'
    Then no books should have been found