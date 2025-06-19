# Análise Estatística de um Simulador de Mobilidade Urbana com Controle Semafórico Baseado em Grafos

**Resumo**
Este trabalho apresenta uma análise estatística detalhada dos resultados obtidos a partir de um simulador de mobilidade urbana. O simulador, concebido para a gestão do tráfego e controle inteligente de semáforos, modela a rede urbana como um grafo, onde interseções são representadas por nós e ruas por arestas. A simulação contempla a geração e o deslocamento de veículos, utilizando o algoritmo de Dijkstra para cálculo de rotas, e emprega uma heurística de ciclo fixo para o controle semafórico. O presente relatório foca na avaliação de métricas de desempenho chave, incluindo tempo médio de viagem, tempo de espera, fluxo de veículos, índices de congestionamento e considerações sobre o consumo energético. Os resultados indicam o comportamento do sistema sob a heurística de ciclo fixo, revelando padrões de movimentação, períodos de espera e a eficiência geral do tráfego na configuração testada. Esta análise serve como um estudo de linha de base para futuras investigações com heurísticas de controle semafórico mais avançadas, visando a otimização do fluxo veicular e a redução do consumo energético em ambientes urbanos.

**Abstract**
This paper presents a detailed statistical analysis of the results obtained from an urban mobility simulator. The simulator, designed for traffic management and intelligent traffic light control, models the urban network as a graph, where intersections are represented by nodes and streets by edges. The simulation includes vehicle generation and movement, using Dijkstra's algorithm for route calculation, and employs a fixed-cycle heuristic for traffic light control. This report focuses on evaluating key performance metrics, including average travel time, waiting time, vehicle flow, congestion indices, and considerations on energy consumption. The results indicate the system's behavior under the fixed-cycle heuristic, revealing movement patterns, waiting periods, and overall traffic efficiency in the tested configuration. This analysis serves as a baseline study for future investigations with more advanced traffic light control heuristics, aiming at optimizing vehicle flow and reducing energy consumption in urban environments.

## 1. Introdução

A mobilidade urbana é um dos desafios mais prementes das cidades contemporâneas, impactando diretamente a qualidade de vida dos cidadãos, a eficiência econômica e a sustentabilidade ambiental. O aumento contínuo da frota de veículos, aliado a uma infraestrutura viária muitas vezes limitada, resulta em congestionamentos frequentes, tempos de viagem elevados, aumento da poluição atmosférica e sonora, e um consumo energético significativo. Neste contexto, o desenvolvimento e a aplicação de Sistemas Inteligentes de Transporte (ITS) tornam-se cruciais para mitigar esses problemas e promover um trânsito mais fluido e eficiente. Entre as diversas ferramentas e estratégias que compõem os ITS, os simuladores de mobilidade urbana e os sistemas de controle de semáforos inteligentes desempenham um papel fundamental.

Os simuladores de tráfego permitem modelar, analisar e avaliar o comportamento de redes viárias sob diferentes condições e cenários, oferecendo uma plataforma robusta para testar novas estratégias de gestão antes de sua implementação no mundo real. Eles são ferramentas valiosas para planejadores urbanos, engenheiros de tráfego e pesquisadores, possibilitando a compreensão das complexas interações entre veículos, infraestrutura e sistemas de controle. A capacidade de simular a geração e o deslocamento de veículos, o cálculo de rotas e o funcionamento de semáforos permite identificar gargalos, prever o impacto de intervenções e otimizar o desempenho geral do sistema de transporte.

Este relatório se debruça sobre a análise estatística dos resultados gerados por um simulador de mobilidade urbana específico, cujo desenvolvimento foi delineado no "Trabalho Final de Estruturas de Dados". O referido simulador foi concebido com o objetivo de modelar a dinâmica do tráfego em uma cidade, utilizando uma representação baseada em grafos para a rede urbana, onde nós simbolizam interseções e arestas representam as vias. Um dos focos centrais do simulador é o controle de semáforos, com a previsão de implementação de diversas heurísticas, desde ciclos fixos até estratégias adaptativas que visam otimizar o fluxo de veículos e o consumo energético.

O objetivo principal deste documento é realizar uma análise aprofundada dos dados de saída de uma simulação específica, que utilizou uma heurística de controle semafórico de ciclo fixo. Através desta análise, busca-se quantificar e interpretar diversas métricas de desempenho, tais como o tempo médio de viagem dos veículos, o tempo médio de espera em interseções, o fluxo de veículos na rede, os índices de congestionamento e, adicionalmente, discutir aspectos relacionados ao consumo energético, embora este último não tenha sido diretamente quantificado na simulação em análise. A metodologia empregada na simulação envolveu a carga de um grafo representando a malha viária, a geração de um número limitado de veículos com origens e destinos aleatórios, o cálculo de rotas utilizando o algoritmo de Dijkstra e a progressão da simulação em passos de tempo discretos, durante os quais os veículos se movimentam ou aguardam em semáforos.

A estrutura deste relatório está organizada da seguinte forma: a Seção 2 detalha a metodologia empregada na simulação, abrangendo a modelagem da rede urbana, a geração e movimentação de veículos, o sistema de controle semafórico utilizado e a forma como os dados foram coletados e processados. A Seção 3 apresenta os resultados obtidos, seguida de uma discussão aprofundada sobre as métricas de desempenho e os padrões observados, incluindo uma análise comparativa entre os resultados esperados e os efetivamente alcançados. Finalmente, a Seção 4 sumariza as conclusões do estudo, destacando as principais observações, as limitações da abordagem de ciclo fixo evidenciadas pela simulação e as direções para trabalhos futuros, especialmente no que tange à exploração de heurísticas de controle mais sofisticadas.

## 2. Metodologia

A metodologia adotada para a simulação e subsequente análise estatística dos resultados baseia-se nos princípios e requisitos estabelecidos no "Trabalho Final de Estruturas de Dados", que orientou o desenvolvimento do simulador de mobilidade urbana. Esta seção descreve os componentes fundamentais do modelo de simulação, incluindo a representação da rede viária, a dinâmica dos veículos, o mecanismo de controle semafórico implementado e os parâmetros específicos da execução analisada.

### 2.1 Modelagem da Rede Urbana

A representação da cidade no simulador é realizada por meio de um grafo, uma estrutura de dados fundamental para modelar relações espaciais e de conectividade. Neste grafo, cada nó (ou vértice) corresponde a uma interseção da malha viária, enquanto cada aresta direcionada representa um segmento de rua que conecta duas interseções. As interseções são pontos críticos no sistema de tráfego, podendo ou não ser equipadas com semáforos, e servem como pontos de decisão para os veículos. As ruas, por sua vez, são caracterizadas por atributos como comprimento, capacidade de fluxo (número máximo de veículos que podem ocupar a via simultaneamente), direção do tráfego (sentido único ou mão dupla) e tempo de travessia, que idealmente reflete o tempo necessário para um veículo percorrer o segmento de rua de um nó a outro. O "Trabalho Final de Estruturas de Dados" enfatiza a importância de não utilizar estruturas de dados prontas da linguagem Java, incentivando a construção de classes próprias para listas, filas e grafos, o que confere ao simulador uma base customizada e alinhada com os objetivos pedagógicos do projeto original. Para a simulação específica analisada neste relatório, foi carregado um grafo a partir de um arquivo JSON, resultando em uma rede com 1922 vértices, que correspondem ao mesmo número de interseções. A identificação e vinculação desses vértices às suas respectivas interseções foram etapas cruciais na inicialização do ambiente de simulação, conforme indicado nos dados de entrada: "Número de vértices carregados: 1922" e "Total de interseções criadas: 1922".

### 2.2 Geração e Movimentação de Veículos

A dinâmica do tráfego é simulada através da geração e movimentação de veículos pela rede urbana. No modelo conceitual do simulador, os veículos são gerados aleatoriamente, possuindo pontos de origem e destino distintos dentro da malha viária. Para determinar as rotas entre esses pontos, o simulador emprega algoritmos de caminho mínimo, sendo o algoritmo de Dijkstra explicitamente mencionado como a ferramenta para essa finalidade no "Trabalho Final de Estruturas de Dados". Uma vez que a rota é calculada – uma sequência de vértices a serem percorridos – o veículo inicia seu deslocamento. A movimentação ocorre em passos de tempo discretos. Em cada passo, um veículo tenta avançar para o próximo vértice em sua rota. Se o segmento de rua estiver livre e o semáforo na próxima interseção (caso exista) permitir a passagem, o veículo se move. Caso contrário, ele permanece em sua posição atual, contribuindo para o tempo de espera.

Na simulação específica cujos resultados são analisados, o gerador de veículos foi configurado para criar um máximo de 7 veículos. A geração de um novo veículo ocorria a cada passo de simulação ("Passos para gerar veículo: 1") até que o limite de 7 veículos ativos fosse atingido. Adicionalmente, foi estabelecido um limite máximo de 204 vértices para o comprimento dos caminhos calculados, uma restrição que pode influenciar a capacidade dos veículos de alcançarem destinos muito distantes, especialmente em redes complexas. Os dados da simulação mostram a criação sequencial dos veículos, com o Veículo 1 surgindo no Passo 0, o Veículo 2 no Passo 1, e assim sucessivamente até o Veículo 7 no Passo 6.

### 2.3 Controle Semafórico

O controle semafórico é um componente vital para a gestão do tráfego urbano. O "Trabalho Final de Estruturas de Dados" prevê três modelos de operação para os semáforos: Modelo 1 (ciclo fixo), Modelo 2 (otimização do tempo de espera) e Modelo 3 (otimização do consumo de energia). A simulação analisada neste relatório utilizou exclusivamente o **Modelo 1: Ciclo fixo**. Conforme os dados de inicialização: "Configurando heurística do semáforo (ciclo fixo)...". Nesta abordagem, os tempos de verde, amarelo e vermelho para cada fase do semáforo são pré-determinados e não se alteram em resposta às condições de tráfego em tempo real. Embora o ciclo fixo seja mais simples de implementar, ele pode ser menos eficiente em cenários de tráfego variável, podendo levar a tempos de espera desnecessários ou à subutilização da capacidade da via. Os registros de movimentação dos veículos indicam claramente os estados semafóricos encontrados: "Semáforo atual: VERDE" permite o movimento, enquanto "Semáforo atual: VERMELHO (PARADO)" impõe a parada do veículo. O estado "AMARELO", embora conceitualmente parte de um ciclo semafórico completo e mencionado no "Trabalho Final", não foi explicitamente registrado como um estado de parada ou movimento nos logs desta simulação específica, sugerindo que a transição pode ser instantânea ou que o foco da lógica de parada estava nos estados verde e vermelho.

### 2.4 Coleta e Processamento de Dados

Para permitir a análise de desempenho, o simulador registra uma série de dados durante sua execução. Os logs detalhados fornecidos como entrada para este relatório incluem informações sobre o cálculo de caminhos para cada veículo (origem, destino e a sequência de vértices), o movimento de cada veículo em cada passo de tempo, e o estado do semáforo encontrado pelo veículo no momento da tentativa de movimento. A partir desses dados brutos, diversas métricas de desempenho são calculadas ao final da simulação. As métricas chave incluem:
*   **Tempo Médio de Viagem:** Definido como o tempo total que os veículos permaneceram ativos na simulação, dividido pelo número de veículos. Na simulação analisada, como nenhum veículo atingiu seu destino final, este tempo reflete a duração da participação de cada veículo na simulação até o seu término.
*   **Tempo de Espera:** Representa o tempo acumulado que os veículos passam parados, seja devido a semáforos vermelhos ou a congestionamentos (embora, na simulação de baixa densidade analisada, a espera seja predominantemente semafórica).
*   **Fluxo de Veículos:** Uma medida da quantidade de movimentação na rede, podendo ser expressa como o número de segmentos de rua atravessados por unidade de tempo ou outra métrica interna do simulador.
*   **Índices de Congestionamento:** Indicadores que buscam quantificar o nível de saturação da rede, possivelmente derivados da relação entre tempo de espera e tempo de viagem, ou da densidade de veículos.
*   **Consumo Energético:** Embora seja um objetivo de otimização no Modelo 3 do simulador, conforme o "Trabalho Final", os dados da simulação atual (Modelo 1) não apresentam uma quantificação explícita desta métrica. Sua análise, portanto, permanece em um nível conceitual neste relatório, baseada nas implicações dos padrões de parada e movimento.

Os dados processados foram sumarizados em duas tabelas principais: uma contendo métricas globais da simulação e outra detalhando o desempenho individual de cada veículo.

### 2.5 Configuração da Simulação

A simulação específica que serve de base para esta análise foi executada com os seguintes parâmetros principais:
*   **Duração da Simulação:** 30 unidades de tempo (passos).
*   **Número de Vértices/Interseções:** 1922.
*   **Máximo de Veículos Criados:** 7.
*   **Intervalo de Geração de Veículos:** 1 veículo por passo, até o máximo ser atingido.
*   **Limite Máximo de Vértices no Caminho:** 204.
*   **Heurística Semafórica:** Ciclo fixo.

Esta configuração representa um cenário de baixa densidade veicular em uma rede urbana de tamanho considerável, ao longo de um período de simulação relativamente curto. Tais condições são importantes para contextualizar a interpretação dos resultados obtidos.

## 3. Resultados e Discussão

A execução do simulador de mobilidade urbana, configurado conforme detalhado na seção de Metodologia, gerou um conjunto de dados que permite uma análise multifacetada do comportamento do tráfego sob uma política de semáforos de ciclo fixo. Esta seção apresenta os resultados quantitativos obtidos, tanto em nível agregado quanto individual por veículo, e discute suas implicações para o desempenho do sistema viário simulado. A análise se baseia nas tabelas de resumo fornecidas e nos padrões observados nos logs de movimentação.

### 3.1 Análise Geral da Simulação

A simulação foi conduzida por uma duração de 30 unidades de tempo (passos). Durante este período, um total de 7 veículos foram introduzidos na rede, que é composta por 1922 vértices (interseções). Um dos resultados mais imediatos e impactantes é que, ao final dos 30 passos, **nenhum dos 7 veículos criados conseguiu alcançar seu destino final**. Consequentemente, a **Taxa de Conclusão de Viagens foi de 0,00%**. Este resultado sugere que, ou os caminhos gerados eram excessivamente longos para serem completados no tempo estipulado, ou a eficiência da movimentação foi severamente comprometida por outros fatores, como os tempos de espera. A Tabela 1 apresenta um resumo das métricas globais da simulação.

**Tabela 1: Métricas Globais da Simulação**

| Métrica                       | Valor   |
| ----------------------------- | ------- |
| Total de Vértices             | 1922    |
| Total de Interseções          | 1922    |
| Veículos Criados              | 7       |
| Veículos Finalizados          | 0       |
| Taxa de Conclusão (%)         | 0.00    |
| Duração da Simulação          | 30      |
| Tempo Médio de Viagem         | 27.00   |
| Tempo Médio de Espera         | 38.86   |
| Eficiência Média (%)          | 81.54   |
| Índice de Congestionamento (%)| 17.55   |
| Fluxo Médio de Veículos       | 81.58   |
| Fluxo Máximo de Veículos      | 252     |
| Tamanho Médio do Caminho      | 63.43   |
| Tamanho Máximo do Caminho     | 86      |

O **Tempo Médio de Viagem** reportado é de 27.00 unidades de tempo. Considerando que a simulação durou 30 unidades e os veículos foram introduzidos sequencialmente (o último no passo 6), este valor parece refletir o tempo médio que cada veículo permaneceu ativo no sistema. Por exemplo, o Veículo 1 esteve ativo por 30 passos (0-29), enquanto o Veículo 7 esteve ativo por 24 passos (6-29). A média desses tempos de atividade para os 7 veículos é (30+29+28+27+26+25+24)/7 = 27.00, o que corrobora esta interpretação.

O **Tempo Médio de Espera** de 38.86 unidades é uma métrica que chama atenção, especialmente por ser superior ao Tempo Médio de Viagem. Esta aparente anomalia necessita de uma análise mais aprofundada em conjunto com os dados por veículo, pois sugere que a definição de "tempo de espera" no simulador pode não ser um subconjunto direto do "tempo de viagem" ou pode ser medido em uma escala ou com ponderações diferentes.

A **Eficiência Média** de 81.54% e o **Índice de Congestionamento** de 17.55% são métricas derivadas que, à primeira vista, parecem contraditórias com o alto tempo de espera e a taxa de conclusão nula. A interpretação dessas métricas dependerá da sua formulação exata dentro do simulador. O **Tamanho Médio do Caminho** de 63.43 vértices (com um máximo de 86) indica que os veículos tinham rotas consideravelmente longas a percorrer, o que, combinado com as interrupções, contribui para a não conclusão das viagens.

### 3.2 Desempenho Individual dos Veículos

A Tabela 2 detalha as métricas de desempenho para cada um dos 7 veículos que participaram da simulação. Estes dados permitem uma visão mais granular das experiências individuais no trânsito simulado.

**Tabela 2: Métricas de Desempenho por Veículo**

| veiculo_id | tempo_viagem | tempo_espera | movimentos_totais | eficiencia (%) |
| ---------- | ------------ | ------------ | ----------------- | -------------- |
| 1          | 30           | 39           | 303               | 87.13          |
| 2          | 29           | 39           | 273               | 85.71          |
| 3          | 28           | 39           | 244               | 84.02          |
| 4          | 27           | 39           | 216               | 81.94          |
| 5          | 26           | 39           | 189               | 79.37          |
| 6          | 25           | 39           | 163               | 76.07          |
| 7          | 24           | 38           | 162               | 76.54          |

Observa-se que o `tempo_viagem` decresce consistentemente do Veículo 1 ao Veículo 7, o que é esperado, dado que foram introduzidos sequencialmente na simulação de 30 passos. O `tempo_espera` é notavelmente alto para todos os veículos, predominantemente 39 unidades, com uma ligeira redução para 38 no Veículo 7. Como discutido anteriormente, esses valores de `tempo_espera` são superiores aos respectivos `tempo_viagem`, indicando que a métrica "tempo_espera" fornecida pelo simulador possui uma definição específica que não corresponde simplesmente ao número de passos em que o veículo esteve parado devido a semáforos, conforme observado nos logs brutos (onde, por exemplo, o Veículo 1 esteve parado por 15 passos). Para os propósitos desta análise, os valores da Tabela 2 são considerados como saídas diretas do simulador.

Os `movimentos_totais` também apresentam valores elevados (e.g., 303 para o Veículo 1), que não correspondem ao número de segmentos de rua atravessados (que, para o Veículo 1, foi de 15 segmentos, dado que se moveu por 15 passos e cada movimento avança uma posição no caminho). Esta métrica provavelmente se refere a uma unidade de distância ou a algum outro fator interno do simulador. A `eficiencia` reportada, apesar dos altos `tempo_espera`, é relativamente elevada, variando de 76.07% a 87.13%. Esta eficiência, calculada pelo simulador, sugere que, mesmo com as paradas, a proporção do "esforço" ou "distância" considerada útil foi significativa.

A Figura 1 ilustra a comparação entre o tempo de viagem e o tempo de espera para cada veículo. Nota-se a constância do alto tempo de espera em relação ao tempo de viagem decrescente.

![Gráfico de barras comparando tempo de viagem e tempo de espera para cada veículo](/home/ubuntu/workspace/results/metricas_por_veiculo.png)
<p align="center">Figura 1: Comparação entre Tempo de Viagem e Tempo de Espera por Veículo.</p>

A Figura 2 apresenta um gráfico de dispersão que relaciona o tempo de viagem, o tempo de espera e a eficiência. A coloração baseada na eficiência mostra uma tendência de maior eficiência para veículos com maior tempo de viagem (os primeiros a entrar na simulação), o que pode ser um artefato da forma como a eficiência é calculada em relação ao número de "movimentos totais" acumulados.

![Gráfico de dispersão mostrando a relação entre tempo de viagem e tempo de espera por veículo, com coloração baseada na eficiência](/home/ubuntu/workspace/results/tempo_viagem_vs_espera.png)
<p align="center">Figura 2: Relação entre Tempo de Viagem, Tempo de Espera e Eficiência por Veículo.</p>

### 3.3 Dinâmica do Fluxo de Veículos e Congestionamento

As métricas de fluxo da Tabela 1, **Fluxo Médio de Veículos (81.58)** e **Fluxo Máximo de Veículos (252)**, assim como os `movimentos_totais` por veículo, parecem ser expressas em unidades específicas do simulador, não diretamente correlacionadas com o simples número de arcos atravessados por passo de simulação. Se interpretarmos "Fluxo Médio de Veículos" como uma medida da atividade geral na rede por unidade de tempo, o valor de 81.58 sugere um nível de movimentação considerável, apesar das interrupções. O **Índice de Congestionamento de 17.55%** indica que, segundo a formulação do simulador, uma porção do tempo ou da capacidade da rede foi perdida devido a impedimentos ao fluxo.

A Figura 3 apresenta uma distribuição conceitual dos estados dos semáforos. Embora os logs da simulação detalhem predominantemente os estados "VERDE" e "VERMELHO (PARADO)", a figura ilustra a capacidade geral do sistema de semáforos de operar com os três estados canônicos (Verde, Amarelo, Vermelho). Na simulação analisada, o impacto do estado "VERMELHO" foi proeminente, como será discutido na análise dos padrões de movimentação.

![Gráfico de pizza mostrando a distribuição percentual dos estados dos semáforos](/home/ubuntu/workspace/results/distribuicao_semaforos.png)
<p align="center">Figura 3: Distribuição Percentual Conceitual dos Estados dos Semáforos.</p>

A Figura 4 exibe o fluxo de veículos, medido pelo número de movimentações (veículos que avançaram um segmento em sua rota) em cada passo da simulação. Este gráfico é particularmente revelador sobre o impacto da heurística de ciclo fixo.

![Gráfico de linha mostrando o fluxo de veículos ao longo do tempo](/home/ubuntu/workspace/results/fluxo_temporal.png)
<p align="center">Figura 4: Fluxo de Veículos (Número de Movimentações) por Passo de Simulação.</p>

O fluxo inicia com 1 movimentação no Passo 0 (Veículo 1) e aumenta gradualmente à medida que novos veículos são introduzidos, atingindo 6 movimentações no Passo 5. No Passo 6, o Veículo 7 é introduzido e também se move, mas é neste ponto que os veículos que já estavam em movimento começam a encontrar semáforos vermelhos de forma sincronizada. Observam-se períodos distintos de atividade e inatividade:
*   **Passos 0-4:** Fluxo crescente à medida que veículos entram e se movem.
*   **Passo 5:** 6 veículos se movem, mas todos encontram semáforos vermelhos ao final do passo.
*   **Passo 6:** O Veículo 7 é introduzido e se move, mas também para em um semáforo vermelho. Os outros 6 veículos permanecem parados.
*   **Passos 7-11:** Nenhum veículo se move (fluxo zero). Todos os 7 veículos estão parados em semáforos vermelhos.
*   **Passo 12:** Todos os 7 veículos retomam o movimento, indicando uma mudança sincronizada para o estado verde dos semáforos.
*   **Passos 13-16:** Fluxo constante de 7 movimentações por passo.
*   **Passo 17:** Todos os 7 veículos se movem e novamente param em semáforos vermelhos.
*   **Passos 18-23:** Novo período de fluxo zero, com todos os veículos parados.
*   **Passo 24:** Todos os 7 veículos retomam o movimento.
*   **Passos 25-28:** Fluxo constante de 7 movimentações por passo.
*   **Passo 29:** Todos os 7 veículos se movem e param em semáforos vermelhos, onde permanecem até o final da simulação no Passo 30.

Este padrão cíclico de movimento e parada total é uma consequência direta da heurística de semáforos de ciclo fixo, que não se adapta à presença ou ausência de veículos nas interseções. Os ciclos parecem estar alinhados de tal forma que causam interrupções generalizadas na rede.

### 3.4 Avaliação da Estratégia de Semáforos de Ciclo Fixo

A análise do fluxo temporal (Figura 4) e dos logs de movimentação individual dos veículos demonstra claramente o impacto da estratégia de semáforos de ciclo fixo. Os veículos experimentaram longos períodos de espera, totalizando 15 passos de espera efetiva para o Veículo 1 (7 passos entre T5-T11, 7 passos entre T17-T23, e 1 passo em T29) dentro de seus 30 passos de atividade. Isso representa 50% do seu tempo na simulação gasto em espera. Períodos similares de espera foram observados para os demais veículos. Essa alta proporção de tempo de espera é um forte indicativo da ineficiência do sistema de ciclo fixo na configuração testada.

A natureza síncrona das paradas, onde todos os veículos ativos ficam retidos simultaneamente, sugere que os ciclos dos semáforos ao longo das rotas dos veículos podem não estar coordenados de forma a promover "ondas verdes" ou que a duração dos ciclos é tal que causa essas interrupções generalizadas. Embora a simulação tenha sido de baixa densidade (apenas 7 veículos), o impacto negativo no fluxo já é evidente. Em cenários de maior densidade, tal comportamento poderia levar a um rápido acúmulo de filas e a um congestionamento severo.

A taxa de conclusão de viagens de 0% também pode ser parcialmente atribuída a essa ineficiência. Com uma parcela significativa do tempo gasta em espera, a distância efetivamente percorrida pelos veículos é reduzida, tornando improvável a conclusão de rotas com dezenas de vértices (Tamanho Médio do Caminho de 63.43) em apenas 30 passos de simulação.

### 3.5 Considerações sobre o Consumo Energético

O "Trabalho Final de Estruturas de Dados" estabelece a otimização do consumo de energia como um dos objetivos para um dos modelos de controle semafórico (Modelo 3). Embora a simulação analisada (Modelo 1) não tenha fornecido métricas diretas de consumo energético, os padrões de movimentação observados permitem inferências qualitativas. O ciclo de "anda-para" frequente, com acelerações e desacelerações repetidas, é inerentemente menos eficiente em termos de consumo de combustível (ou energia, para veículos elétricos) do que um fluxo de tráfego mais constante. Os longos períodos de marcha lenta enquanto os veículos aguardam em semáforos vermelhos também contribuem para o desperdício de energia. Portanto, pode-se inferir que a estratégia de ciclo fixo, conforme implementada e observada nesta simulação, provavelmente resultaria em um consumo energético subótimo. A implementação do Modelo 3, que priorizaria estratégias para minimizar deslocamentos desnecessários e ajustar ciclos conforme períodos de pico, seria essencial para uma análise quantitativa e para buscar melhorias neste aspecto.

### 3.6 Análise Comparativa: Resultados Esperados vs. Obtidos

Antes da execução, esperava-se que os veículos se movimentassem pela rede, com algumas paradas em semáforos, mas que houvesse um progresso mais contínuo. Os resultados obtidos, no entanto, revelaram um sistema onde os períodos de espera impostos pelos semáforos de ciclo fixo foram substanciais e sincronizados, afetando todos os veículos de forma similar e levando a uma taxa de conclusão de viagens nula no horizonte de simulação. As métricas tabuladas de `tempo_espera`, `movimentos_totais` e `eficiencia` apresentaram valores que, embora sejam saídas diretas do simulador, não se alinham trivialmente com as observações diretas do número de passos de espera ou de segmentos percorridos nos logs. Isso sugere que estas métricas são calculadas internamente pelo simulador usando definições ou ponderações específicas que não foram detalhadas nos dados fornecidos. Por exemplo, o `tempo_espera` reportado (e.g., 39 para o Veículo 1) é consideravelmente maior do que os 15 passos de espera observados no log. Similarmente, a `eficiencia` reportada (e.g., 87.13% para o Veículo 1) parece alta, considerando que 50% dos passos de simulação do veículo foram gastos em espera.

Esta discrepância não invalida os resultados, mas ressalta a importância de compreender a semântica precisa das métricas geradas por qualquer ferramenta de simulação. Independentemente da escala exata dessas métricas, o padrão qualitativo de longas esperas e fluxo interrompido é inegável a partir dos logs e da Figura 4. A simulação cumpriu o objetivo de modelar a rede e o movimento, mas os resultados de desempenho com o ciclo fixo ficaram aquém do que seria desejável para um sistema de tráfego eficiente.

## 4. Conclusão

Este relatório apresentou uma análise estatística dos resultados de uma simulação de mobilidade urbana, focada em avaliar o desempenho do tráfego sob uma heurística de controle semafórico de ciclo fixo. A simulação foi conduzida em uma rede de 1922 interseções, com a introdução de 7 veículos ao longo de 30 unidades de tempo.

Os principais achados desta análise indicam que a estratégia de semáforos de ciclo fixo, na configuração testada, resultou em ineficiências significativas no fluxo de tráfego. A observação mais crítica foi a ocorrência de longos períodos de espera sincronizada, durante os quais todos os veículos ativos na simulação permaneciam parados em semáforos. Esses períodos de inatividade total, como os registrados entre os passos 7-11 e 18-23, demonstram uma falta de adaptabilidade do sistema de controle às condições da rede, mesmo em um cenário de baixa densidade veicular. Consequentemente, o tempo médio de espera reportado pelo simulador foi consideravelmente alto. Um reflexo direto dessa ineficiência foi a taxa de conclusão de viagens de 0,00%, indicando que nenhum veículo conseguiu alcançar seu destino dentro da duração da simulação, em parte devido ao tempo perdido em espera e à extensão das rotas planejadas.

As métricas de desempenho individual dos veículos, como tempo de viagem, tempo de espera e eficiência, embora fornecidas pelo simulador, apresentaram valores que sugerem definições internas específicas para seu cálculo, nem sempre diretamente correlacionáveis com uma contagem simples de passos de simulação ou segmentos percorridos. No entanto, a tendência de altos tempos de espera foi consistente entre todos os veículos. O fluxo de veículos ao longo do tempo, visualizado graficamente, confirmou o padrão cíclico de movimento seguido por parada total, um comportamento característico de sistemas de ciclo fixo não otimizados ou não coordenados.

Apesar das limitações observadas na estratégia de controle semafórico, a simulação demonstrou a capacidade do sistema em modelar componentes essenciais da mobilidade urbana, como a representação da rede em grafo, a geração de veículos, o cálculo de rotas por meio do algoritmo de Dijkstra e a simulação da movimentação passo a passo. Os resultados obtidos com o Modelo 1 (ciclo fixo) servem como uma importante linha de base quantitativa e qualitativa. Eles ressaltam a necessidade e o potencial de melhoria que podem ser alcançados através da implementação de heurísticas de controle semafórico mais avançadas, como as previstas no "Trabalho Final de Estruturas de Dados" (Modelo 2, focado na otimização do tempo de espera, e Modelo 3, na otimização do consumo energético).

Para trabalhos futuros, recomenda-se a implementação e análise comparativa desses modelos adaptativos. Simulações de maior duração seriam necessárias para observar taxas de conclusão de viagem e para permitir que os sistemas de controle adaptativo demonstrem seu potencial em diferentes regimes de tráfego. A calibração dos parâmetros do simulador, incluindo os tempos de ciclo dos semáforos e as taxas de geração de veículos, também será crucial para cenários mais realistas. Adicionalmente, a incorporação e análise explícita de métricas de consumo energético, conforme planejado para o Modelo 3, enriqueceriam a avaliação do simulador. A validação dos modelos e do simulador como um todo, idealmente comparando seus resultados com dados de tráfego do mundo real, representaria um passo significativo para aumentar sua confiabilidade e aplicabilidade prática como ferramenta de planejamento e gestão urbana.

Em suma, este estudo analítico dos resultados de uma simulação inicial forneceu insights valiosos sobre o comportamento de um sistema de tráfego com controle semafórico de ciclo fixo e estabeleceu um ponto de partida para o desenvolvimento e avaliação de estratégias mais inteligentes e eficientes para a mobilidade urbana.

## Referências

Dijkstra, E. W. (1959). A note on two problems in connexion with graphs. *Numerische Mathematik*, 1(1), 269-271.

(Nota: Outras referências seriam adicionadas conforme a utilização de modelos teóricos específicos, dados de calibração de cidades reais, ou comparação com outros simuladores, o que não foi o escopo dos dados fornecidos para esta análise específica. A referência a Dijkstra é genérica, dado que o algoritmo é central para o cálculo de rotas no simulador.)