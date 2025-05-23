# Simulador de Mobilidade Urbana para Controle de Tráfego e Semáforos
#Caso ocorra erros de   
symbol:   class JSONException
  location: class json.LeitorOSMJson


 configurar a dependência no IntelliJ via Project Structure > libraries > clique em "+" e depois adicione a pasta Libs que está em src/libs que já está no código
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

## Como Executar no IntelliJ IDEA

1. **Abrir o Projeto:**
   - Abra o IntelliJ IDEA
   - Selecione "Open" e navegue até a pasta `Simulador_Mobilidade_Urbana`
   - Aguarde o IntelliJ indexar o projeto

2. **Configurar as Bibliotecas:**
   - Clique com botão direito na pasta `src/libs`
   - Selecione "Add as Library..."
   - Marque "Global Library" se quiser que fique disponível para outros projetos
   - Clique em "OK"

3. **Executar o Programa:**
   - Navegue até `src/Main.java`
   - Clique no botão "Run" (▶️) ao lado da classe `Main`
   - Ou use o atalho Shift + F10 (Windows/Linux) ou Control + R (Mac)

### Observações:
- Certifique-se de que o arquivo `Morada_do_Sol.json` está na pasta `src/json/`
- Se aparecer erro de `JSONException`, verifique se as bibliotecas foram adicionadas corretamente no passo 2
- O Java Development Kit (JDK) deve estar instalado e configurado no IntelliJ

---

## Exemplo de Saída

```
Carregando grafo do arquivo JSON...
Número de vértices carregados: 204

Configurando simulador...
- Duração: 20 unidades de tempo
- Máximo de veículos: 7
- Heurística: Ciclo Fixo

Iniciando simulação...

== Passo 0 ==
Veículo #1: Iniciou trajeto (Origem: 10 -> Destino: 50)
Semáforo 10: Verde (5s)

== Passo 1 ==
Veículo #1: Avançou para 11
Veículo #2: Iniciou trajeto (Origem: 20 -> Destino: 60)
Semáforo 11: Vermelho (2s)

```

### Explicação da Saída:

1. **Inicialização:**
   - Carrega o grafo do arquivo JSON
   - Cria as interseções
   - Configura a heurística dos semáforos
   - Define parâmetros do gerador de veículos

2. **Durante a Simulação:**
   - Mostra cada passo da simulação
   - Indica geração de novos veículos
   - Mostra movimentação dos veículos
   - Exibe estado dos semáforos

3. **Estatísticas Finais:**
   - Total de veículos gerados
   - Veículos que completaram o trajeto
   - Métricas de desempenho (tempos médios, congestionamento)

### Observações:
- Os números exatos podem variar entre execuções devido à natureza aleatória da simulação
- O tempo de simulação padrão é de 20 unidades, mas pode ser alterado
- O número máximo de veículos e outras configurações podem ser ajustadas no código

---

## Sugestões para Melhorias Futuras

- Implementação de interface gráfica para visualização em tempo real do tráfego e semáforos.
- Inclusão de múltiplos tipos de veículos com diferentes velocidades e prioridades (ônibus, emergências).
- Algoritmos de roteamento alternativos e aprendizado de máquina para controle adaptativo mais eficiente.
- Simulação de eventos inesperados (acidentes, obras) para análise de impacto no tráfego.
- Exportação dos dados da simulação em formatos padrão para análise externa (CSV, JSON).
- Otimização do desempenho para simulações em larga escala.
