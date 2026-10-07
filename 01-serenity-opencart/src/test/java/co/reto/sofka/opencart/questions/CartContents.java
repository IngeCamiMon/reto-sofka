package co.reto.sofka.opencart.questions;

import co.reto.sofka.opencart.ui.CartPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.WebElement;

import java.util.stream.Collectors;

public class CartContents implements Question<String> {
    public static CartContents text() {
        return new CartContents();
    }

    @Override
    public String answeredBy(Actor actor) {
        return CartPage.PRODUCT_NAMES.resolveAllFor(actor).stream()
                .map(WebElement::getText)
                .collect(Collectors.joining(" | "));
    }
}
