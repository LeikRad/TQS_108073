Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.
 
  Scenario: Search books by publication year
    Given I have the following books in the store
      | title                                | author      | published |
      | The Devil in the White City          | Erik Larson | 2013-03-14|
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
      | In the Garden of Beasts              | Erik Larson | 1998-12-05|
    When the customer searches for books published between 2000-01-01 and 2014-01-01
    Then 2 books should have been found
      | title                                | author      | published |
      | The Devil in the White City          | Erik Larson | 2013-03-14|
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|

  Scenario: Search books by author
    Given I have the following books in the store
      | title                                | author      | published |
      | The Devil in the White City          | Erik Larson | 2013-03-14|
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
      | In the Garden of Beasts              | Erik Larson | 1998-12-05|
    When the customer searches for books written by 'C.S. Lewis'
    Then 1 books should have been found
      | title                                | author      | published |
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
  
  Scenario: Search books by title
    Given I have the following books in the store
      | title                                | author      | published |
      | The Devil in the White City          | Erik Larson | 2013-03-14|
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
      | In the Garden of Beasts              | Erik Larson | 1998-12-05|
    When the customer searches for books with the title 'In the Garden of Beasts'
    Then 1 books should have been found
      | title                                | author      | published |
      | In the Garden of Beasts              | Erik Larson | 1998-12-05|
  
  Scenario: No books found
    Given I have the following books in the store
      | title                                | author      | published |
      | The Devil in the White City          | Erik Larson | 2013-03-14|
      | The Lion, the Witch and the Wardrobe | C.S. Lewis  | 2004-02-24|
      | In the Garden of Beasts              | Erik Larson | 1998-12-05|
    When the customer searches for books written by 'George Orwell'
    Then 0 books should have been found
      | title                                | author      | published |