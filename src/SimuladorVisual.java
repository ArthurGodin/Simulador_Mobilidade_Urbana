import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import estruturas.*;
import cidade.*;
import json.*;
import trafego.*;
import semaforo.*;

import java.util.*;

public class SimuladorVisual extends Application {

    private Map<Long, Circle> intersecaoCirculos = new HashMap<>();
    private Map<Long, Circle> semaforoCirculos = new HashMap<>();
    private List<Line> ruasLinhas = new ArrayList<>();
    private List<VeiculoVisual> veiculosVisuais = new ArrayList<>();

    private Pane mapaPane;
    private Label infoLabel = new Label("Simulando...");

    private Grafo grafo;
    private Lista<Intersecao> intersecoes;
    private List<Rua> ruasCidade;

    private Simulador simulador;

    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private double minLat, maxLat, minLon, maxLon;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Se simuladorGlobal não estiver inicializado, inicializa aqui
        if (Main.simuladorGlobal == null) {
            System.out.println("Simulador não encontrado, inicializando...");
            Main.iniciarSimulador(new String[]{});
        }

        simulador = Main.simuladorGlobal;

        if (simulador == null) {
            System.err.println("Simulador não inicializado! Execute a classe Main primeiro.");
            Platform.exit();
            return;
        }

        intersecoes = simulador.getIntersecoes();
        grafo = simulador.getGrafo();
        ruasCidade = coletarRuas(intersecoes);

        mapaPane = new Pane();
        mapaPane.setPrefSize(WIDTH, HEIGHT);
        infoLabel.setTranslateY(HEIGHT + 10);

        Pane root = new Pane(mapaPane, infoLabel);
        Scene scene = new Scene(root, WIDTH, HEIGHT + 40);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulador Visual Integrado - Mobilidade Urbana");
        primaryStage.show();

        normalizarCoordenadas();
        desenharMapa();
        criarVeiculosVisuais();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> simularPasso()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void normalizarCoordenadas() {
        minLat = Double.MAX_VALUE;
        maxLat = -Double.MAX_VALUE;
        minLon = Double.MAX_VALUE;
        maxLon = -Double.MAX_VALUE;

        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            Vertice v = inter.getVertice();
            if (v.getLat() < minLat) minLat = v.getLat();
            if (v.getLat() > maxLat) maxLat = v.getLat();
            if (v.getLon() < minLon) minLon = v.getLon();
            if (v.getLon() > maxLon) maxLon = v.getLon();
        }
    }

    private Point2D converterLatLonParaXY(double lat, double lon) {
        double x = ((lon - minLon) / (maxLon - minLon)) * (WIDTH - 40) + 20;
        double y = ((maxLat - lat) / (maxLat - minLat)) * (HEIGHT - 40) + 20;
        return new Point2D(x, y);
    }

    private List<Rua> coletarRuas(Lista<Intersecao> intersecoes) {
        List<Rua> ruas = new ArrayList<>();
        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            for (int j = 0; j < inter.getRuasSaida().tamanho(); j++) {
                ruas.add(inter.getRuasSaida().obter(j));
            }
        }
        return ruas;
    }

    private void desenharMapa() {
        mapaPane.getChildren().clear();
        intersecaoCirculos.clear();
        semaforoCirculos.clear();
        ruasLinhas.clear();

        for (Rua rua : ruasCidade) {
            Point2D p1 = converterLatLonParaXY(rua.getOrigem().getVertice().getLat(), rua.getOrigem().getVertice().getLon());
            Point2D p2 = converterLatLonParaXY(rua.getDestino().getVertice().getLat(), rua.getDestino().getVertice().getLon());
            Line linha = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            linha.setStroke(Color.GRAY);
            linha.setStrokeWidth(2);
            mapaPane.getChildren().add(linha);
            ruasLinhas.add(linha);
        }

        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            Point2D pos = converterLatLonParaXY(inter.getVertice().getLat(), inter.getVertice().getLon());

            Circle circ = new Circle(pos.getX(), pos.getY(), 10, Color.LIGHTBLUE);
            circ.setStroke(Color.BLACK);
            mapaPane.getChildren().add(circ);
            intersecaoCirculos.put(inter.getVertice().getId(), circ);

            Circle semaforoCirc = new Circle(pos.getX() + 15, pos.getY() - 15, 6, Color.GREEN);
            mapaPane.getChildren().add(semaforoCirc);
            semaforoCirculos.put(inter.getVertice().getId(), semaforoCirc);
        }
    }

    private void criarVeiculosVisuais() {
        veiculosVisuais.clear();
        GeradorVeiculos gerador = simulador.getGeradorVeiculos();

        if (gerador == null) {
            System.err.println("Gerador de veículos não inicializado.");
            return;
        }

        Lista<Intersecao> intersecoesGerador = gerador.getIntersecoes();
        if (intersecoesGerador == null || intersecoesGerador.tamanho() < 2) {
            System.err.println("Interseções insuficientes para gerar veículos.");
            return;
        }

        // Gerar até 5 veículos, se possível
        for (int i = 0; i < 5; i++) {
            gerador.gerarVeiculo();
        }

        Fila<Veiculo> veiculosReais = gerador.getVeiculos();

        for (int i = 0; i < veiculosReais.tamanho(); i++) {
            Veiculo v = veiculosReais.obter(i);
            List<Point2D> caminhoPixels = new ArrayList<>();

            for (int j = 0; j < v.getCaminho().tamanho(); j++) {
                Intersecao inter = v.getCaminho().obter(j);

                if (inter == null) {
                    System.err.println("Intersecao nula no caminho do veiculo " + i + " na posicao " + j);
                    continue;
                }

                Vertice vertice = inter.getVertice();
                if (vertice == null) {
                    System.err.println("Vertice nulo em Intersecao " + inter.getId() + " no caminho do veiculo " + i);
                    continue;
                }

                Point2D p = converterLatLonParaXY(vertice.getLat(), vertice.getLon());
                caminhoPixels.add(p);
            }

            if (!caminhoPixels.isEmpty()) {
                VeiculoVisual vv = new VeiculoVisual(caminhoPixels);
                veiculosVisuais.add(vv);
                mapaPane.getChildren().add(vv.circulo);
            } else {
                System.err.println("Veiculo " + i + " não possui caminho válido para visualização.");
            }
        }
    }


    private void simularPasso() {
        for (int i = 0; i < intersecoes.tamanho(); i++) {
            Intersecao inter = intersecoes.obter(i);
            Semaforo sem = inter.getSemaforo();
            sem.atualizar();
            Circle c = semaforoCirculos.get(inter.getVertice().getId());
            Platform.runLater(() -> {
                if (sem.getEstadoAtual().equals("VERDE")) c.setFill(Color.GREEN);
                else if (sem.getEstadoAtual().equals("AMARELO")) c.setFill(Color.YELLOW);
                else c.setFill(Color.RED);
            });
        }

        for (VeiculoVisual vv : veiculosVisuais) {
            vv.mover();
        }
    }

    static class VeiculoVisual {
        List<Point2D> caminho;
        int indiceAtual;
        Circle circulo;

        VeiculoVisual(List<Point2D> caminho) {
            this.caminho = caminho;
            this.indiceAtual = 0;
            Point2D p = caminho.get(0);
            this.circulo = new Circle(p.getX(), p.getY(), 6, Color.RED);
        }

        void mover() {
            if (indiceAtual < caminho.size() - 1) {
                Point2D atual = caminho.get(indiceAtual);
                Point2D proximo = caminho.get(indiceAtual + 1);

                double dx = proximo.getX() - circulo.getCenterX();
                double dy = proximo.getY() - circulo.getCenterY();

                double dist = Math.sqrt(dx * dx + dy * dy);
                double passo = 4;

                if (dist < passo) {
                    circulo.setCenterX(proximo.getX());
                    circulo.setCenterY(proximo.getY());
                    indiceAtual++;
                } else {
                    circulo.setCenterX(circulo.getCenterX() + passo * dx / dist);
                    circulo.setCenterY(circulo.getCenterY() + passo * dy / dist);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
