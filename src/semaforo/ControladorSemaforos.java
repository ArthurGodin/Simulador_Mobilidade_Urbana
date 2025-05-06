package semaforo;

public class ControladorSemaforos {
    private Semaforo semaforo;
    private ModoOperacao modoOperacao;

    public ControladorSemaforos(Semaforo semaforo, ModoOperacao modoOperacao){
        this.semaforo = semaforo;
        this.modoOperacao = modoOperacao;
    }

    public void ajustarSemaforo(Intersecao intersecao){
        switch (modoOperacao){
            case CICLO_FIXO:
                //ciclo fixo: tempos definidos na classe semaforo
                System.out.println("Ciclo fixo: " + semaforo.getTempoVerde() + " s de verde, " +
                        semaforo.getTempoAmarelo() + "s de tempo amarelo" +
                        semaforo.getTempoVermelho() + "s de tempo vermelho");
                break;
        }
    }

}
