package co.reto.sofka.opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class OrderConfirmationPage {
    public static final Target MESSAGE = Target.the("order placed confirmation")
            .located(By.cssSelector("#content h1"));

    private OrderConfirmationPage() {
    }
}
