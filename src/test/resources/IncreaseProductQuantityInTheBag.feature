Feature: Increase Product Quantity In The Bag

  Scenario Outline: Increase Product Quantity In The Bag
    Given the user is on a product page
    And adding the product to the Bag
    When increase product <productId> quantity <quantityIncrease> in the Bag
    Then quantity <quantityIncrease> of product <productId> should be increased
    And product <productId> price should be changed

    Examples:
      | quantityIncrease | productId      |
      | 4                | 39654522814667 |
