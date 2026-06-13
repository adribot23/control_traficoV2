# Simulador de Tráfico

Proyecto de la asignatura **TP2 (Tecnologías de la Programación 2)**.

Este repositorio contiene un **simulador de tráfico con interfaz gráfica Swing**, desarrollado en Java, con soporte tanto para ejecución en modo visual como en modo consola mediante eventos en JSON.

## Descripción

El simulador representa una red de carreteras y cruces donde circulan vehículos y se ejecutan eventos que modifican el estado de la simulación. La aplicación permite:

- Crear carreteras y cruces.
- Añadir vehículos y eventos de simulación.
- Cambiar el clima y la clase de emisiones.
- Visualizar el estado de la red en tablas y mapas.
- Ejecutar la simulación desde una interfaz Swing o desde línea de comandos.

## Tecnologías

- Java 17
- Swing para la interfaz gráfica
- JUnit 5 para pruebas
- Apache Commons CLI para argumentos de consola
- JSON para la carga de eventos y estados

## Estructura principal

```text
control_traficoV2/
|- src/
|  |- simulator/
|  |  |- launcher/
|  |  |- control/
|  |  |- factories/
|  |  |- model/
|  |  |- view/
|  |  \- misc/
|  \- extra/
|- tests/
|- resources/
|- lib/
|- README
|- .classpath
\- .project
```

## Arquitectura

El proyecto sigue una organización por capas:

- **simulator.model**: lógica principal del simulador, vehículos, carreteras, cruces, eventos y estrategias.
- **simulator.control**: controlador que carga eventos y ejecuta la simulación.
- **simulator.factories**: factorías y builders para crear estrategias y eventos a partir de JSON.
- **simulator.view**: interfaz gráfica Swing, tablas, panel de control, mapa y barra de estado.
- **extra**: ejemplos y utilidades adicionales.
- **tests**: pruebas unitarias y de integración.

## Cómo ejecutar

### En Eclipse

1. Importa el proyecto con `File > Import... > Existing Projects into Workspace`.
2. Selecciona la carpeta `control_traficoV2`.
3. Verifica que el proyecto usa **JavaSE-17**.
4. Ejecuta la clase principal: `simulator.launcher.Main`.

### En modo gráfico

Por defecto la aplicación arranca en modo GUI:

```bash
java simulator.launcher.Main
```

También puedes forzar el modo gráfico:

```bash
java simulator.launcher.Main -m gui
```

### En modo consola

El launcher admite los siguientes argumentos:

- `-m, --mode`: `gui` o `console`
- `-i, --input`: fichero JSON con eventos de entrada
- `-o, --output`: fichero de salida con el resultado de la simulación
- `-t, --ticks`: número de ticks a ejecutar
- `-h, --help`: muestra la ayuda

Ejemplo:

```bash
java simulator.launcher.Main -m console -i input.json -o output.json -t 10
```

## Formato de entrada

La simulación en consola usa un JSON con un campo `events`.

Ejemplo simplificado:

```json
{
	"events": [
		{
			"time": 1,
			"type": "new_junction",
			"id": "j1"
		}
	]
}
```

En `resources/examples/` hay ejemplos de entrada y salida esperada.

## Ejemplos

La carpeta `resources/examples` incluye casos de prueba preparados.

Según su README interno:

- Cada fichero `exname.json` tiene su salida esperada en `exname.expout.json`.
- El fichero `db.json` indica el número de ticks que hay que usar para cada ejemplo.
- No conviene modificar esos ficheros.

También puedes ejecutar la clase `extra.testing.TestExamples` para comprobar automáticamente los ejemplos.

## Pruebas

El proyecto incluye pruebas en `tests/` para:

- modelo del simulador
- factorías
- lanzador principal

Ejemplos de clases de prueba:

- `tests/simulator/model/TrafficSimulatorTest.java`
- `tests/simulator/launcher/MainTest.java`
- `tests/simulator/factories/*Test.java`

## Requisitos

- JDK 17
- Eclipse o un IDE compatible con Java
- Librerías incluidas en `lib/`

Repositorio desarrollado con fines docentes para la asignatura **TP2 (Tecnologías de la Programación 2)**.
