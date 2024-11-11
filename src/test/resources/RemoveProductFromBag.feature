Feature: Remove Product From Bag

  Scenario Outline: Remove added product from the Bag
    Given the user is on a product page
    When adding the product to the Bag
    And remove product <productId> from the Bag
    Then the product <productId> should be removed from the Bag
    Examples:
      | productId      |
      | 39654522814667 |
