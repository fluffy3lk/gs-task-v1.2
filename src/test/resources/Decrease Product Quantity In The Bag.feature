Feature: Decrease Product Quantity In The Bag

  Scenario Outline: Decrease Product Quantity In The Bag
    Given the user is on a product page
    And adding the product to the Bag
    And increase product <productId> quantity <quantityIncrease> in the Bag
    When decrease product <productId> quantity <quantityDecrease> in the Bag
    Then quantity <quantityDecrease> of product <productId> should be decreased
    And product <productId> price should be changed
    Examples:
      | quantityIncrease | productId      | quantityDecrease |
      | 4                | 39654522814667 | 2                |
