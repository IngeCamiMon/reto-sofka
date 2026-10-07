package co.reto.sofka.opencart.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.reto.sofka.opencart.interactions.DismissBitnamiBanner.fromPage;
import static co.reto.sofka.opencart.ui.CheckoutPage.ACCOUNT_CONTINUE;
import static co.reto.sofka.opencart.ui.CheckoutPage.FIRST_NAME;
import static co.reto.sofka.opencart.ui.CheckoutPage.GUEST_CHECKOUT;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectGuestCheckout implements Interaction {
    public static SelectGuestCheckout option() {
        return new SelectGuestCheckout();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String baseUrl = System.getProperty("webdriver.base.url", "http://opencart.abstracta.us");
        actor.attemptsTo(
                Open.url(baseUrl + "/index.php?route=checkout/checkout"),
                WaitUntil.the(GUEST_CHECKOUT, isVisible()).forNoMoreThan(15).seconds(),
                fromPage(),
                Click.on(GUEST_CHECKOUT),
                Click.on(ACCOUNT_CONTINUE),
                WaitUntil.the(FIRST_NAME, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
