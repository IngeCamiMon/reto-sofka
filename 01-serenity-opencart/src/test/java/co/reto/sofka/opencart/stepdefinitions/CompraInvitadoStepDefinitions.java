package co.reto.sofka.opencart.stepdefinitions;

import co.reto.sofka.opencart.models.GuestDetails;
import co.reto.sofka.opencart.questions.CartContents;
import co.reto.sofka.opencart.questions.OrderConfirmationMessage;
import co.reto.sofka.opencart.tasks.AddTwoProducts;
import co.reto.sofka.opencart.tasks.CompleteGuestCheckout;
import co.reto.sofka.opencart.tasks.OpenCart;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class CompraInvitadoStepDefinitions {
    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("Guest buyer");
    }

    @Given("I add two available products to the shopping cart")
    public void addTwoAvailableProductsToTheShoppingCart() {
        actor().attemptsTo(AddTwoProducts.toTheCart());
    }

    @And("the shopping cart contains both products")
    public void theShoppingCartContainsBothProducts() {
        actor().attemptsTo(OpenCart.andReviewProducts());
        actor().should(seeThat(CartContents.text(), allOf(
                containsString("iPhone"),
                containsString("HTC Touch HD")
        )));
    }

    @When("I complete guest checkout with {string}, {string}, {string}, {string}, {string}, {string} and {string}")
    public void completeGuestCheckout(
            String firstName,
            String lastName,
            String email,
            String telephone,
            String address,
            String city,
            String postcode
    ) {
        actor().attemptsTo(CompleteGuestCheckout.withDetails(
                new GuestDetails(firstName, lastName, email, telephone, address, city, postcode)
        ));
    }

    @Then("the order confirmation message is {string}")
    public void theOrderConfirmationMessageIs(String expectedMessage) {
        actor().should(seeThat(OrderConfirmationMessage.text(), equalTo(expectedMessage)));
    }

    private Actor actor() {
        return OnStage.theActorInTheSpotlight();
    }
}
