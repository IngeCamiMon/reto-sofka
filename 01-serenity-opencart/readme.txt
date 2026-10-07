Serenity BDD + Cucumber + JUnit + Screenplay: OpenCart E2E
===========================================================

Prerequisites
-------------
- JDK 17 or newer. The Maven compiler target is Java 17. The validation machine
  has Temurin 21.0.7 and Maven 3.9.3 running on Oracle Java 19.0.2.
- Maven 3.9.3 (tested).
- Google Chrome 153.0.8010.53 (tested); Selenium Manager resolves a compatible
  ChromeDriver when the first test is run.
- Network access to http://opencart.abstracta.us/ and Maven Central.

Pinned project versions
-----------------------
- Serenity BDD and Serenity Maven Plugin: 5.3.11
- Cucumber: 7.34.2, aligned by the Serenity 5.3.11 dependency management
- JUnit: 4.13.2
- Selenium: 4.46.0, aligned by the Serenity 5.3.11 dependency management
- Maven Compiler Plugin: 3.14.1
- Maven Surefire Plugin: 3.5.4
- Java compiler release: 17

`mvn clean verify` completed successfully with Chrome normal and headless on
this machine. `java -version` reports Temurin 21.0.7, while `mvn -v` reports
Oracle Java 19.0.2; both satisfy the configured Java 17 compilation target.

Run from this directory
-----------------------
1. Check `mvn -v` and confirm that Maven is using JDK 17 or newer.
2. Run the end-to-end scenario and generate the Serenity report:

   mvn clean verify

3. Open `target/site/serenity/index.html` in a browser.
4. To run Chrome headless:

   mvn clean verify "-Dheadless.mode=true"

5. To regenerate the checked-in report folder after a successful run, copy the
   generated site contents:

   PowerShell:
   Copy-Item -Path target/site/serenity/. -Destination reporte -Recurse -Force

Scenario
--------
The Cucumber Scenario Outline is in
`src/test/resources/features/compra_invitado.feature`. The example customer
data is test-only. The scenario adds the in-stock iPhone and HTC Touch HD,
checks both names in the cart, completes Guest Checkout, chooses the United
States / New York billing address and validates the exact final confirmation.

The OpenCart demo is a third-party website; its inventory, checkout flow,
availability and response times can change independently of this project.
The tested checkout closes the site's fixed Bitnami banner, selects the
Cash On Delivery option and waits for the enabled order-confirmation control.
