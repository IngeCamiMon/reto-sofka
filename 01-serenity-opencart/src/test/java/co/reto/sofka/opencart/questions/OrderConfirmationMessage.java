package co.reto.sofka.opencart.questions;

import co.reto.sofka.opencart.ui.OrderConfirmationPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class OrderConfirmationMessage implements Question<String> {
    public static OrderConfirmationMessage text() {
        return new OrderConfirmationMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        return OrderConfirmationPage.MESSAGE.resolveFor(actor).getText().trim();
    }
}
