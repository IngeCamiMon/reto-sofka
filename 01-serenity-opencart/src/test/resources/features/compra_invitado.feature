# language: en
Feature: Guest checkout in OpenCart
  As a store visitor
  I want to buy two available products as a guest
  So that I can place an order without creating an account

  Scenario Outline: Place an order using guest checkout
    Given I add two available products to the shopping cart
    And the shopping cart contains both products
    When I complete guest checkout with "<firstName>", "<lastName>", "<email>", "<telephone>", "<address>", "<city>" and "<postcode>"
    Then the order confirmation message is "Your order has been placed!"

    Examples:
      | firstName | lastName | email                    | telephone  | address             | city     | postcode |
      | Camila    | Pruebas  | camila.serenity@example.com | 5551234567 | 123 Automation Ave  | New York | 10001    |
