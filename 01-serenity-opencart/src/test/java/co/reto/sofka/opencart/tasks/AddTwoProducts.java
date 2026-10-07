package co.reto.sofka.opencart.tasks;

import co.reto.sofka.opencart.interactions.AddProductToCart;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class AddTwoProducts implements Task {
    public static AddTwoProducts toTheCart() {
        return new AddTwoProducts();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                AddProductToCart.withId("40", "iPhone"),
                AddProductToCart.withId("28", "HTC Touch HD")
        );
    }
}
