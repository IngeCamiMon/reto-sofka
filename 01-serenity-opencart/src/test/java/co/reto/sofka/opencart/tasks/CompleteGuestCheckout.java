package co.reto.sofka.opencart.tasks;

import co.reto.sofka.opencart.interactions.ContinuePayment;
import co.reto.sofka.opencart.interactions.ContinueShipping;
import co.reto.sofka.opencart.interactions.SelectGuestCheckout;
import co.reto.sofka.opencart.interactions.SubmitOrder;
import co.reto.sofka.opencart.models.GuestDetails;
import co.reto.sofka.opencart.ui.CheckoutPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CompleteGuestCheckout implements Task {
    private final GuestDetails details;

    public CompleteGuestCheckout(GuestDetails details) {
        this.details = details;
    }

    public static CompleteGuestCheckout withDetails(GuestDetails details) {
        return new CompleteGuestCheckout(details);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SelectGuestCheckout.option(),
                Enter.theValue(details.firstName()).into(CheckoutPage.FIRST_NAME),
                Enter.theValue(details.lastName()).into(CheckoutPage.LAST_NAME),
                Enter.theValue(details.email()).into(CheckoutPage.EMAIL),
                Enter.theValue(details.telephone()).into(CheckoutPage.TELEPHONE),
                Enter.theValue(details.address()).into(CheckoutPage.ADDRESS),
                Enter.theValue(details.city()).into(CheckoutPage.CITY),
                Enter.theValue(details.postcode()).into(CheckoutPage.POSTCODE),
                SelectFromOptions.byValue("223").from(CheckoutPage.COUNTRY),
                WaitUntil.the(CheckoutPage.NEW_YORK_ZONE, isVisible()).forNoMoreThan(15).seconds(),
                SelectFromOptions.byValue("3655").from(CheckoutPage.ZONE),
                Click.on(CheckoutPage.GUEST_CONTINUE),
                ContinueShipping.toPayment(),
                ContinuePayment.afterAcceptingTerms(),
                SubmitOrder.now()
        );
    }
}
