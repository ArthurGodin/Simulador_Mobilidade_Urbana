README - Simulador de Mobilidade Urbana
PONTO DE PARTIDA PRINCIPAL DO PROGRAMA:

O programa deve ser iniciado a partir do caminho:

src > trafego > main
Este é o ponto de entrada principal para rodar o simulador.

Simulador de Mobilidade Urbana
Para rodar este programa, o ponto de partida principal é src>trafego>main. Este projeto consiste em um simulador de mobilidade urbana desenvolvido em Java. Ele foi criado para simular o tráfego de veículos em uma cidade, permitindo a análise de diferentes cenários e configurações de semáforos.

Simulador Visual (Programa à Parte)
Além do simulador principal em Java, há um simulador visual que é um programa à parte. Este componente permite uma visualização gráfica da simulação, proporcionando uma compreensão mais intuitiva do fluxo do tráfego. Ele é executado através do script Bash executar.sh, que se encontra na própria pasta do simulador visual.

Ponto de Entrada Principal
Conforme solicitado, o caminho para o ponto de entrada do programa é:
src>trafego>main

Como Rodar o Programa
Para executar o simulador principal em Java, siga os passos abaixo:

Pré-requisitos
Java Development Kit (JDK): Versão 8 ou superior é necessária para compilar e executar o código.
Biblioteca Java JSON: O projeto já inclui o arquivo json-20240303.jar na pasta lib/. Certifique-se de que este arquivo esteja incluído no classpath ao compilar e executar o código Java.

Execução do Simulador Java
Compilar o código Java:
Primeiro, navegue até o diretório raiz do seu projeto (Simulador_Mobilidade_Urbana-main/). Em seguida, compile o código Java. Este comando tenta incluir todos os arquivos .java dentro de src/ e suas subpastas, o que é o mais abrangente para compilar todas as classes necessárias.

Executar o simulador Java:
Após a compilação bem-sucedida, você pode executar o programa Java a partir do diretório raiz do projeto, especificando a classe principal.

(Se estiver usando Windows, lembre-se de usar ponto e vírgula ; em vez de dois pontos : para separar o classpath: java -cp "bin;lib/*" Main)

O simulador gerará arquivos de log e resultados diretamente na pasta results/ e no arquivo simulation_results.txt.

Como Rodar o Simulador Visual
O simulador visual é executado por um script Bash chamado executar.sh, que está localizado em sua própria pasta.

Navegue até a pasta do simulador visual:

Substitua Caminho/Para/Simulador/Visual pelo caminho real da pasta onde o executar.sh está.

Torne o script executável (se necessário):

Execute o script:

Estrutura do Projeto
O projeto é organizado da seguinte forma:

Simulador_Mobilidade_Urbana-main/: Contém o simulador Java principal.
src/: Todo o código-fonte Java.
Main.java: A classe principal que geralmente inicia a simulação.
Simulador.java: O motor principal da simulação.
cidade/: Classes relacionadas à estrutura da cidade (como Grafo.java, Vertice.java, Aresta.java, Rua.java).
estruturas/: Implementações de estruturas de dados personalizadas (e.g., ArrayList1.java, Fila.java, Dijkstra.java, HashMap1.java).
heuristica/: Lógica para controle de semáforos.
json/: Contém dados da cidade, como Morada_do_Sol.json, que possui dados reais de ruas.
semaforo/: Sistema e lógica de semáforos.
trafego/: Lógica de geração de veículos e gerenciamento de tráfego.
lib/: Bibliotecas externas necessárias (e.g., json-20240303.jar).
bin/: Onde os arquivos Java compilados são armazenados.
results/: Diretório destinado a armazenar os resultados das simulações.
simulation_results.txt: Um arquivo de log que registra os resultados detalhados de cada simulação.
[NOME_DA_PASTA_DO_SIMULADOR_VISUAL]/: (Exemplo: visual_simulator/ ou gui/) Esta pasta conteria os arquivos do simulador visual, incluindo o executar.sh.

Tecnologias Utilizadas
Java: A linguagem principal usada para desenvolver todo o simulador.
Bash: Utilizado para o script de execução do simulador visual.

Você pode encontrar o repositório original em GitHub - ArthurGodin/Simulador_Mobilidade_Urbana.
