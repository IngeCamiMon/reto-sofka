package co.reto.sofka.opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class ProductPage {
    public static final Target TITLE = Target.the("product title").located(By.cssSelector("#content h1"));
    public static final Target ADD_TO_CART = Target.the("add to cart button").located(By.id("button-cart"));
    public static final Target SUCCESS_ALERT = Target.the("product added confirmation")
            .located(By.cssSelector("#product-product .alert-success"));

    private ProductPage() {
    }
}
