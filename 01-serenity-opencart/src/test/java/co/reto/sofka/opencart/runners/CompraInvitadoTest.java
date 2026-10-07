package co.reto.sofka.opencart.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/compra_invitado.feature",
        glue = "co.reto.sofka.opencart.stepdefinitions",
        plugin = {"pretty"}
)
public class CompraInvitadoTest {
}
