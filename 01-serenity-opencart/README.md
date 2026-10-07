# 01 - Serenity BDD y OpenCart

Proyecto Maven de automatización E2E con Serenity BDD, Cucumber, JUnit 4 y el
patrón Screenplay. El escenario añade iPhone y HTC Touch HD al carrito, valida
ambos productos, compra como invitado y comprueba el mensaje final del pedido.

- Guía de ejecución: [`readme.txt`](readme.txt)
- Hallazgos de la ejecución: [`conclusiones.txt`](conclusiones.txt)
- Escenario: [`src/test/resources/features/compra_invitado.feature`](src/test/resources/features/compra_invitado.feature)
- Reporte Serenity: [`reporte/index.html`](reporte/index.html)

El reporte se actualiza al ejecutar `mvn clean verify`; para copiar la versión
generada a `reporte/`, usa el comando documentado en `readme.txt`. La tienda es
un sitio demo externo y sus disponibilidad y comportamiento pueden cambiar.
