package co.reto.sofka.opencart.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.reto.sofka.opencart.ui.CheckoutPage.PAYMENT_AGREEMENT;
import static co.reto.sofka.opencart.ui.CheckoutPage.PAYMENT_CONTINUE;
import static co.reto.sofka.opencart.ui.CheckoutPage.PAYMENT_METHOD;
import static co.reto.sofka.opencart.ui.CheckoutPage.CONFIRM_ORDER;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ContinuePayment implements Interaction {
    public static ContinuePayment afterAcceptingTerms() {
        return new ContinuePayment();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(PAYMENT_METHOD, isVisible()).forNoMoreThan(30).seconds(),
                Click.on(PAYMENT_METHOD),
                Click.on(PAYMENT_AGREEMENT),
                Click.on(PAYMENT_CONTINUE),
                WaitUntil.the(CONFIRM_ORDER, isVisible()).forNoMoreThan(30).seconds()
        );
    }
}
