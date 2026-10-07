package co.reto.sofka.opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class SiteChrome {
    public static final Target BITNAMI_BANNER = Target.the("Bitnami promotional banner")
            .located(By.id("bitnami-banner"));
    public static final Target CLOSE_BITNAMI_BANNER = Target.the("close Bitnami banner button")
            .located(By.id("bitnami-close-banner-button"));

    private SiteChrome() {
    }
}
