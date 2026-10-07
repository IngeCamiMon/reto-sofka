Karate + JUnit 5: API PetStore
==============================

Requisitos
----------
- JDK 17 o superior (release de compilación Maven: 17).
- Maven 3.9.3 (validado).
- Acceso de red a Maven Central y https://petstore.swagger.io/v2.

Versiones fijadas en pom.xml
----------------------------
- Karate JUnit 5: 1.4.1 (última versión estable consultada en Maven Central
  el 7 de octubre de 2026).
- JUnit Jupiter API y Engine: 5.9.3, alineados por karate-junit5 1.4.1.
- Maven Compiler Plugin: 3.14.1.
- Maven Surefire Plugin: 3.5.4.
- Java compiler release: 17.
- Java del equipo: Temurin 21.0.7; `mvn -v` utiliza Oracle Java 19.0.2.

Ejecución paso a paso
---------------------
1. Abre una terminal en `02-karate-petstore/`.
2. Comprueba `java -version` y `mvn -v`.
3. Ejecuta todo el flujo y genera el reporte:

   mvn clean test

4. Consulta el reporte HTML de la ejecución en:

   target/karate-reports/karate-summary.html

   La copia entregada está en `reporte/karate-summary.html`.

Flujo automatizado
------------------
El runner JUnit 5 está en
`src/test/java/co/reto/sofka/petstore/PetStoreRunnerTest.java`. El escenario
`user-crud.feature` genera un username UUID único y encadena:

1. POST `/user`, comprueba el status y el esquema de salida.
2. GET `/user/{username}`, reintenta hasta recibir HTTP 200 y valida los
   campos enviados.
3. PUT `/user/{username}` con nombre y correo actualizados; comprueba la
   respuesta.
4. GET del usuario actualizado, reintentando la lectura hasta observar los
   nuevos valores.
5. DELETE `/user/{username}`, comprueba el status y la respuesta.
6. GET posterior, reintentando hasta HTTP 404 y validando `User not found`.

`karate-config.js` configura `baseUrl`, timeout de conexión y timeout de
lectura. Los reintentos son cinco como máximo, separados por un segundo, y
se limitan a GET para tolerar consistencia eventual sin repetir escrituras.
Las entradas y salidas aparecen como pasos impresos en el reporte Karate.

Los datos son sintéticos de prueba. El API PetStore es público y compartido:
puede presentar latencia, indisponibilidad o cambios independientes de este
proyecto.
