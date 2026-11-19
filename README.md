# Simulador Fase de Batalla - Yu-Gi-Oh!

Aplicacion de escritorio en Java que simula la Fase de Batalla del juego de cartas Yu-Gi-Oh!, aplicando las reglas de calculo de daño segun posición (Ataque vs Ataque, Ataque vs Defensa y Ataque Directo), gestion de Puntos de Vida (LP) y registro persistente del historial de combates.

## Arquitectura del Proyecto
El proyecto sigue el patron Modelo-Vista-Controlador (MVC) con interfaz grafica construida mediante JFrame Form en NetBeans:

```text
src/
├── main/java/
│   ├── modelo/
│   │   ├── ICombate.java
│   │   ├── Carta.java
│   │   ├── MonstruoNormal.java
│   │   ├── MonstruoEfecto.java
│   │   ├── Jugador.java
│   │   ├── MotorCombate.java
│   │   └── ArchivoHistorial.java
│   ├── vista/
│   │   ├── VentanaBatalla.java
│   │   └── VentanaBatalla.form
│   ├── controlador/
│   │   └── ControladorBatalla.java
│   └── main/
│       └── Main.java
└── test/java/
    └── modelo/
        └── MotorCombateTest.java
```

## Requisitos y Ejecución
- JDK 17 o superior y Apache NetBeans con Maven.

- Ejecutar la clase principal main.Main.

- Para correr las pruebas unitarias, ejecutar modelo.MotorCombateTest con JUnit 5.

- Cada combate se registra automaticamente en el archivo historial_combate.txt en la raiz del proyecto.