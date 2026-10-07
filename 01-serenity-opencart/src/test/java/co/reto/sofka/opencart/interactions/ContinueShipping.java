package co.reto.sofka.opencart.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.reto.sofka.opencart.ui.CheckoutPage.SHIPPING_CONTINUE;
import static co.reto.sofka.opencart.ui.CheckoutPage.SHIPPING_METHOD;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ContinueShipping implements Interaction {
    public static ContinueShipping toPayment() {
        return new ContinueShipping();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(SHIPPING_METHOD, isVisible()).forNoMoreThan(30).seconds(),
                Click.on(SHIPPING_METHOD),
                Click.on(SHIPPING_CONTINUE)
        );
    }
}
