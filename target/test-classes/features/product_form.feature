Feature: Product Form

Scenario: Add product successfully
  Given the product API is available
  When I fill in the product form with the following data:
    | name   | price | description  |
    | Shirt  | 500   | Cotton shirt |
  Then the product should be added successfully