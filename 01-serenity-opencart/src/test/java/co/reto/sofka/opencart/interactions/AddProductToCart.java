package co.reto.sofka.opencart.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.reto.sofka.opencart.ui.ProductPage.ADD_TO_CART;
import static co.reto.sofka.opencart.ui.ProductPage.SUCCESS_ALERT;
import static co.reto.sofka.opencart.ui.ProductPage.TITLE;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AddProductToCart implements Interaction {
    private final String productId;
    private final String productName;

    public AddProductToCart(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public static AddProductToCart withId(String productId, String productName) {
        return new AddProductToCart(productId, productName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String baseUrl = System.getProperty("webdriver.base.url", "http://opencart.abstracta.us");
        actor.attemptsTo(
                Open.url(baseUrl + "/index.php?route=product/product&product_id=" + productId),
                WaitUntil.the(TITLE, isVisible()).forNoMoreThan(15).seconds(),
                WaitUntil.the(TITLE, containsText(productName)).forNoMoreThan(10).seconds(),
                Click.on(ADD_TO_CART),
                WaitUntil.the(SUCCESS_ALERT, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
