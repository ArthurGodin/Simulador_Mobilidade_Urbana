# Simulador de Mobilidade Urbana para Controle de Tráfego e Semáforos

## Resumo

Este projeto apresenta um simulador de mobilidade urbana que modela a rede viária de uma cidade como um grafo, onde os nós representam interseções e as arestas representam ruas. O sistema gera veículos de forma aleatória com origem e destino definidos, calculando rotas mínimas por meio do algoritmo de Dijkstra (fornecido). O controle dos semáforos é realizado por três modelos de heurísticas adaptativas, que otimizam o fluxo de veículos e o consumo energético. A simulação gera estatísticas como tempos médios de viagem, espera e índices de congestionamento, auxiliando no estudo e na melhoria do tráfego urbano.

---

## Objetivos do Simulador

- Modelar a rede urbana por meio de um grafo dirigido, representando interseções e ruas com atributos configuráveis.
- Simular a geração, deslocamento e rota dos veículos, calculando caminhos mínimos para seus trajetos.
- Implementar e testar três heurísticas adaptativas para o controle de semáforos visando otimização do fluxo e redução do consumo energético.
- Utilizar estruturas de dados próprias para filas, listas e grafos, sem uso de bibliotecas prontas do Java.
- Fornecer relatórios e logs da simulação para análise estatística do desempenho do sistema de tráfego.

---

## Modelagem da Rede Urbana como Grafo

A cidade é representada por um grafo dirigido, onde:

- **Nó (Interseção):** representa uma interseção viária, que pode possuir semáforos controlados.
- **Aresta (Rua):** representa um trecho viário que liga duas interseções, com atributos como comprimento, capacidade máxima de veículos, sentido (único ou mão dupla) e tempo de travessia.

Cada aresta possui propriedades configuráveis que influenciam a simulação, como variações de tempo de travessia para horários de pico.

---

## Estruturas de Dados Implementadas

Para garantir controle e personalização, todas as estruturas de dados utilizadas foram implementadas do zero, sem uso das coleções padrão do Java:

- **Lista Ligada (Lista):** utilizada para armazenar os nós e arestas do grafo, bem como listas internas como filas de espera dos semáforos.
- **Fila (Queue):** usada para gerenciar a ordem de passagem dos veículos nos semáforos e para o processamento dos eventos de simulação.
- **Grafo:** estrutura personalizada que contém os nós e suas arestas, com métodos para inserção, busca e manipulação dos elementos.

Exemplo simples de implementação da fila:

```java
public class Fila<T> {
    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    public void enqueue(T elemento) {
        No<T> novoNo = new No<>(elemento);
        if (fim == null) {
            inicio = fim = novoNo;
        } else {
            fim.proximo = novoNo;
            fim = novoNo;
        }
        tamanho++;
    }

    public T dequeue() {
        if (inicio == null) return null;
        T valor = inicio.valor;
        inicio = inicio.proximo;
        if (inicio == null) fim = null;
        tamanho--;
        return valor;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
}
```

---

## Principais Algoritmos Implementados

### Geração de Veículos

Veículos são gerados aleatoriamente em intervalos configuráveis, com origem e destino definidos dentro da rede urbana. Cada veículo utiliza o algoritmo de Dijkstra (fornecido) para calcular sua rota mínima até o destino.

### Controle dos Semáforos

Cada interseção com semáforo controla o ciclo de luzes (verde, amarelo, vermelho) e as filas de veículos que aguardam passagem, conforme o modelo heurístico selecionado.

---

## Modelos de Heurísticas para Controle dos Semáforos

1. **Modelo 1 - Ciclo Fixo:**
   - Os tempos de verde, amarelo e vermelho são fixos e não se adaptam ao fluxo de veículos.
   - Exemplo: Verde = 30s, Amarelo = 5s, Vermelho = 30s, independentemente da fila.

2. **Modelo 2 - Otimização do Tempo de Espera:**
   - Ajusta dinamicamente os tempos de verde com base no tamanho das filas de veículos.
   - Semáforo com maior fila ganha tempo de verde maior para reduzir espera.

3. **Modelo 3 - Otimização do Consumo de Energia:**
   - Prioriza minimizar paradas e deslocamentos desnecessários.
   - Ajusta os ciclos conforme horários de pico e períodos de menor movimentação para reduzir consumo energético global.

---

## Parâmetros Configuráveis

O sistema permite configuração dos seguintes parâmetros, que podem ser alterados no arquivo de configuração ou via interface (se implementada):

- Número de interseções (nós) e ruas (arestas).
- Atributos das ruas: comprimento, capacidade, tempo de travessia (pode variar para horário de pico).
- Taxa e distribuição da geração de veículos.
- Tempos fixos ou adaptativos do ciclo dos semáforos (verde, amarelo, vermelho).
- Escolha do modelo de heurística (1, 2 ou 3).
- Parâmetros para consumo energético (custo por deslocamento, parada, etc.).
- Horários de pico para variação dinâmica.

---

## Instruções para Execução

### Requisitos

- Java JDK 11 ou superior instalado.
- Sistema operacional: Windows ou Linux.

### Passos para Compilar e Executar

1. **No Windows:**

Abra o Prompt de Comando na pasta do projeto:

```bash
javac -d bin src/**/*.java
java -cp bin simulador.SimuladorMain
```

2. **No Linux:**

Abra o terminal na pasta do projeto:

```bash
javac -d bin src/**/*.java
java -cp bin simulador.SimuladorMain
```

*Nota:* Ajuste o caminho do pacote e nomes conforme sua organização do projeto.

---

## Exemplo de Saída da Simulação

```
[12:00:01] Gerando veículo ID=101: Origem=Interseção 3, Destino=Interseção 10
[12:00:05] Veículo ID=101 avançou para Interseção 4
[12:00:07] Semáforo Interseção 4: Verde para rua A, tempo restante 15s
[12:00:10] Veículo ID=101 liberado pela fila no semáforo da Interseção 4
[12:05:30] Estatísticas:
- Tempo médio de viagem: 420 segundos
- Tempo médio de espera no semáforo: 35 segundos
- Índice de congestionamento: 0.75
- Consumo energético estimado: 1200 unidades
```

---

## Sugestões para Melhorias Futuras

- Implementação de interface gráfica para visualização em tempo real do tráfego e semáforos.
- Inclusão de múltiplos tipos de veículos com diferentes velocidades e prioridades (ônibus, emergências).
- Algoritmos de roteamento alternativos e aprendizado de máquina para controle adaptativo mais eficiente.
- Simulação de eventos inesperados (acidentes, obras) para análise de impacto no tráfego.
- Exportação dos dados da simulação em formatos padrão para análise externa (CSV, JSON).
- Otimização do desempenho para simulações em larga escala.
