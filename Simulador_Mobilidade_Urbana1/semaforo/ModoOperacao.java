package semaforo;

public enum ModoOperacao {
    //semáforo alterna entre verde, amarelo e vermelho em tempos fixos
    CICLO_FIXO,
    //ajusta os tempos com base no número de veículos esperando.
    TEMPO_ESPERA,
    //tenta otimizar o consumo de energia ajustando os tempos conforme a quantidade de carros.
    CONSUMO;
}
