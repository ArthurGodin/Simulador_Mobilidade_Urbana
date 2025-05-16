public class Inicializador {
    public static void main(String[] args) {
        // Inicializa simulador
        Main.iniciarSimulador(args);

        // Inicia a interface gráfica JavaFX
        SimuladorVisual.launch(SimuladorVisual.class, args);
    }
}
