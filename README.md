# Reto técnico — Analista de Automatización

Repositorio con los entregables de automatización funcional, API, carga y
análisis de resultados. Los documentos originales del reto están en
[`docs/`](docs/).

## Índice de entregables

| Carpeta | Entregable | Ejecución |
| --- | --- | --- |
| [`01-serenity-opencart/`](01-serenity-opencart/README.md) | Prueba E2E OpenCart con Serenity BDD, Cucumber y Screenplay; incluye reporte HTML. | `cd 01-serenity-opencart; mvn clean verify` |
| [`02-karate-petstore/`](02-karate-petstore/README.md) | Flujo CRUD de usuario PetStore con Karate y JUnit 5; incluye reporte HTML. | `cd 02-karate-petstore; mvn clean test` |
| [`03-k6-login/`](03-k6-login/README.md) | Prueba de carga del login FakeStore, CSV de prueba y resúmenes JSON/HTML. | `cd 03-k6-login; k6 run login.js` |
| [`04-informe-resultados/`](04-informe-resultados/README.md) | Informe de análisis en DOCX y DOC. | Abrir el documento; no requiere compilación. |
| [`docs/`](docs/README.md) | Enunciados originales en PDF. | No aplica. |

Cada módulo incluye `README.md`, `readme.txt` y `conclusiones.txt`. Los
resultados conservados en `reporte/` y `reportes/` son evidencia de las
ejecuciones documentadas; Maven los regenera bajo `target/` y K6 bajo
`reportes/`.

## Requisitos para ejecutar

| Herramienta | Requisito probado |
| --- | --- |
| Java | JDK 17 o superior (los proyectos compilan con `release 17`). |
| Maven | 3.9.3 o compatible. |
| Google Chrome | 153.0.8010.53 probado para Serenity; Selenium Manager obtiene ChromeDriver. |
| K6 | 1.2.3. |
| Red | Maven Central y los endpoints públicos OpenCart, PetStore, FakeStore y CDN PapaParse. |

En el equipo donde se prepararon los entregables se detectaron Temurin JDK
21.0.7, Maven 3.9.3 (que utilizó Oracle Java 19.0.2), K6 1.2.3 y Chrome
153.0.8010.53. Maven debe ejecutarse con un JDK 17+ (`mvn -v` muestra la JVM
efectivamente usada). Chrome estaba instalado aunque el comando `chrome` no
estuviera en `PATH`.

## Ejecución desde una extracción limpia

Desde PowerShell en la raíz del repositorio:

```powershell
Set-Location .\01-serenity-opencart
mvn clean verify

Set-Location ..\02-karate-petstore
mvn clean test

Set-Location ..\03-k6-login
k6 run login.js
```

Los comandos descargan dependencias cuando sea necesario y requieren conexión
a Internet. Serenity puede finalizar una compra de prueba en el sitio público;
K6 envía solicitudes reales al endpoint de autenticación. Revisa las guías y
conclusiones de cada módulo antes de ejecutarlos. Los reportes generados por
Maven quedan en `01-serenity-opencart/target/site/serenity/` y
`02-karate-petstore/target/karate-reports/`; los reportes K6 quedan en
`03-k6-login/reportes/`.

## Versiones fijadas por proyecto

- Serenity BDD 5.3.11, JUnit 4.13.2 y Java release 17.
- Karate JUnit 5 1.4.1 y Java release 17.
- K6 1.2.3; PapaParse 5.1.1 se carga durante la inicialización desde el CDN
  documentado.

Los artefactos generados de Maven (`target/`), dependencias de Node
(`node_modules/`) y `.git/` no forman parte de `reto-sofka.zip`.
