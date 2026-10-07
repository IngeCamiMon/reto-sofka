package co.reto.sofka.opencart.tasks;

import co.reto.sofka.opencart.interactions.OpenShoppingCart;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class OpenCart implements Task {
    public static OpenCart andReviewProducts() {
        return new OpenCart();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(OpenShoppingCart.now());
    }
}
