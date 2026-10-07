package co.reto.sofka.opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class CheckoutPage {
    public static final Target GUEST_CHECKOUT = Target.the("guest checkout option")
            .located(By.cssSelector("input[name='account'][value='guest']"));
    public static final Target ACCOUNT_CONTINUE = Target.the("continue with guest checkout")
            .located(By.id("button-account"));
    public static final Target FIRST_NAME = Target.the("billing first name").located(By.id("input-payment-firstname"));
    public static final Target LAST_NAME = Target.the("billing last name").located(By.id("input-payment-lastname"));
    public static final Target EMAIL = Target.the("billing email").located(By.id("input-payment-email"));
    public static final Target TELEPHONE = Target.the("billing telephone").located(By.id("input-payment-telephone"));
    public static final Target ADDRESS = Target.the("billing address").located(By.id("input-payment-address-1"));
    public static final Target CITY = Target.the("billing city").located(By.id("input-payment-city"));
    public static final Target POSTCODE = Target.the("billing postcode").located(By.id("input-payment-postcode"));
    public static final Target COUNTRY = Target.the("billing country").located(By.id("input-payment-country"));
    public static final Target ZONE = Target.the("billing region").located(By.id("input-payment-zone"));
    public static final Target NEW_YORK_ZONE = Target.the("New York region option")
            .located(By.cssSelector("#input-payment-zone option[value='3655']"));
    public static final Target GUEST_CONTINUE = Target.the("continue from guest billing details")
            .located(By.id("button-guest"));
    public static final Target SHIPPING_METHOD = Target.the("available shipping method")
            .located(By.cssSelector("input[name='shipping_method']"));
    public static final Target SHIPPING_CONTINUE = Target.the("continue from shipping method")
            .located(By.id("button-shipping-method"));
    public static final Target PAYMENT_METHOD = Target.the("Cash On Delivery payment method")
            .located(By.cssSelector("input[name='payment_method'][value='cod']"));
    public static final Target PAYMENT_AGREEMENT = Target.the("payment terms agreement")
            .located(By.cssSelector("input[name='agree']"));
    public static final Target PAYMENT_CONTINUE = Target.the("continue from payment method")
            .located(By.id("button-payment-method"));
    public static final Target CONFIRM_ORDER = Target.the("confirm order button")
            .located(By.id("button-confirm"));

    private CheckoutPage() {
    }
}
