package co.reto.sofka.opencart.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.reto.sofka.opencart.ui.CheckoutPage.CONFIRM_ORDER;
import static co.reto.sofka.opencart.ui.OrderConfirmationPage.MESSAGE;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class SubmitOrder implements Interaction {
    public static SubmitOrder now() {
        return new SubmitOrder();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CONFIRM_ORDER, isClickable()).forNoMoreThan(30).seconds(),
                Click.on(CONFIRM_ORDER),
                WaitUntil.the(MESSAGE, containsText("Your order has been placed!"))
                        .forNoMoreThan(45).seconds()
        );
    }
}
