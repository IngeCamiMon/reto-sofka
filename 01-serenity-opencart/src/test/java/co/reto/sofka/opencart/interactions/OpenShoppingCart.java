package co.reto.sofka.opencart.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.reto.sofka.opencart.ui.CartPage.PRODUCT_NAMES;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class OpenShoppingCart implements Interaction {
    public static OpenShoppingCart now() {
        return new OpenShoppingCart();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String baseUrl = System.getProperty("webdriver.base.url", "http://opencart.abstracta.us");
        actor.attemptsTo(
                Open.url(baseUrl + "/index.php?route=checkout/cart"),
                WaitUntil.the(PRODUCT_NAMES, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
