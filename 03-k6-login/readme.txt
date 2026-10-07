K6: prueba de carga del login
============================

Requisitos
----------
- K6 v1.2.3 (instalado y utilizado en esta ejecucion).
- Acceso de red a https://fakestoreapi.com y https://jslib.k6.io.

Entrada de datos
----------------
`data/usuarios.csv` contiene cinco usuarios de prueba, con las columnas
`user,passwd`. El script importa PapaParse 5.1.1 desde el CDN oficial de
jslib.k6.io y lee el archivo una sola vez durante la inicializacion con
`SharedArray`. Las credenciales son los datos de prueba especificados por el
enunciado; no las sustituyas por credenciales personales o de produccion.

Ejecucion reproducible
----------------------
Desde esta carpeta, ejecuta exactamente:

   k6 run login.js

El escenario `constant-arrival-rate` genera 20 iteraciones por segundo durante
30 segundos (600 solicitudes programadas); cada iteracion hace exactamente un
POST al endpoint `https://fakestoreapi.com/auth/login`. Se configuran 40 VUs
preasignados y un maximo de 80. A 20 solicitudes por segundo, 40 VUs permiten
sostener hasta dos segundos de tiempo de servicio medio antes de necesitar
escalar el numero de VUs.

El resumen distingue el arrival rate de 20 iteraciones/s configurado en el
executor y mostrado durante la ventana de carga, del throughput nativo de
respuestas HTTP completadas por K6, que incluye el drenaje de respuestas en
curso despues de cerrar la ventana. El executor aplica el ritmo de llegada;
el umbral de al menos 600 iteraciones completadas y cero iteraciones
descartadas comprueba que el generador pudo inyectar la carga solicitada.

El cuerpo JSON se crea para cada usuario del CSV y cada solicitud incluye el
header `Content-Type: application/json`. Los umbrales hacen fallar la
ejecucion si el p95 no es menor a 1500 ms, la tasa de errores HTTP alcanza
3 %, se incumple cualquiera de los checks (status 200/201, token no vacio o
respuesta individual de hasta 1500 ms), la tasa de iteraciones fallidas
alcanza 3 %, o se omiten iteraciones.

Reporte
-------
`handleSummary` exporta el resumen original y un HTML legible a
`reportes/resumen.json` y `reportes/resumen.html`. Las conclusiones con las
metricas reales se encuentran en `conclusiones.txt`.
