package co.reto.sofka.opencart.interactions;

import co.reto.sofka.opencart.ui.SiteChrome;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.interactions.Actions;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static co.reto.sofka.opencart.ui.SiteChrome.BITNAMI_BANNER;
import static co.reto.sofka.opencart.ui.SiteChrome.CLOSE_BITNAMI_BANNER;

public class DismissBitnamiBanner implements Interaction {
    public static DismissBitnamiBanner fromPage() {
        return new DismissBitnamiBanner();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(WaitUntil.the(BITNAMI_BANNER, isVisible()).forNoMoreThan(10).seconds());
        new Actions(BrowseTheWeb.as(actor).getDriver())
                .moveToElement(BITNAMI_BANNER.resolveFor(actor))
                .perform();
        actor.attemptsTo(
                WaitUntil.the(CLOSE_BITNAMI_BANNER, isClickable()).forNoMoreThan(5).seconds(),
                net.serenitybdd.screenplay.actions.Click.on(CLOSE_BITNAMI_BANNER)
        );
    }
}
