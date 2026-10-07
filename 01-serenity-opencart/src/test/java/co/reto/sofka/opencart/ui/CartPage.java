package co.reto.sofka.opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class CartPage {
    public static final Target PRODUCT_NAMES = Target.the("product names in the shopping cart")
            .located(By.cssSelector("#content .table-responsive tbody tr td:nth-child(2) a"));

    private CartPage() {
    }
}
