import cidade.Grafo;
import cidade.Intersecao;
import estruturas.Lista;
import heuristica.HeuristicaCicloFixo;
import heuristica.HeuristicaControle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import json.LeitorOSMJson;

public class SimulacaoApp extends Application {

    private TextField duracaoField;
    private Button iniciarButton;
    private Label statusLabel;
    private ProgressBar progressoSimulacao;
    private Simulador simulador;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        Label duracaoLabel = new Label("Duração da Simulação (em unidades):");
        duracaoField = new TextField();
        duracaoField.setPromptText("Insira a duração");

        iniciarButton = new Button("Iniciar Simulação");
        statusLabel = new Label("Status: Aguardando Início");

        progressoSimulacao = new ProgressBar(0);
        progressoSimulacao.setPrefWidth(250);

        iniciarButton.setOnAction(e -> iniciarSimulacao());

        vbox.getChildren().addAll(duracaoLabel, duracaoField, iniciarButton, statusLabel, progressoSimulacao);

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setTitle("Simulação de Mobilidade Urbana");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void iniciarSimulacao() {
        try {
            int duracao = Integer.parseInt(duracaoField.getText());
            statusLabel.setText("Status: Simulação em andamento...");
            progressoSimulacao.setProgress(0);

            // Criar grafo e interseções do JSON
            Grafo grafo = LeitorOSMJson.carregar("json/Morada_do_Sol.json");
            Lista<Intersecao> intersecoes = grafo.converterParaIntersecoes();

            // Configurar heurística (pode trocar para outra se quiser)
            HeuristicaControle heuristica = new HeuristicaCicloFixo(10, 5, 10);

            simulador = new Simulador(intersecoes, heuristica, duracao, grafo);

            // Rodar a simulação em uma thread separada para não travar a UI
            new Thread(() -> {
                for (int t = 0; t < duracao; t++) {
                    simulador.executarPasso(t); // Modifique Simulador para ter um método que executa um passo
                    final double progresso = (t + 1) / (double) duracao;
                    Platform.runLater(() -> progressoSimulacao.setProgress(progresso));
                }

                Platform.runLater(() -> statusLabel.setText("Status: Simulação Finalizada!"));
            }).start();

        } catch (NumberFormatException e) {
            statusLabel.setText("Erro: Duração inválida!");
        } catch (Exception e) {
            statusLabel.setText("Erro: Simulação interrompida!");
            e.printStackTrace();
        }
    }
}
