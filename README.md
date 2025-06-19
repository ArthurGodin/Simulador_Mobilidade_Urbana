# 🚦 Simulador de Mobilidade Urbana - Documentação Completa

## 📋 Visão Geral

Este projeto apresenta um **simulador completo de mobilidade urbana** que modela a rede viária do bairro **Morada do Sol** em **Teresina, Piauí** como um grafo. O sistema inclui:

- **Simulador Java**: Motor principal de simulação com algoritmos próprios
- **Ferramentas Python**: Scripts de análise estatística e visualização
- **Dados Reais**: Rede viária baseada em dados do OpenStreetMap
- **Análise Avançada**: Métricas de desempenho e relatórios detalhados

---

## 🎯 Características Principais

### Simulador Java
- ✅ **Rede Real**: 1922 vértices do bairro Morada do Sol
- ✅ **Algoritmo de Dijkstra**: Cálculo de rotas mínimas
- ✅ **Controle de Semáforos**: Heurísticas adaptativas
- ✅ **Estruturas Próprias**: Implementações personalizadas (filas, listas, grafos)
- ✅ **Geração Dinâmica**: Veículos com origem/destino aleatórios

### Ferramentas Python
- ✅ **Análise Estatística**: Métricas de desempenho detalhadas
- ✅ **Visualizações**: Gráficos e relatórios visuais
- ✅ **Análise Comparativa**: Múltiplas simulações automatizadas
- ✅ **Exportação**: CSV, PNG e relatórios estruturados

---

## 🖥️ Requisitos do Sistema

### Software Obrigatório
```bash
# Java Development Kit
java -version  # Versão 8 ou superior

# Python (para análise)
python3 --version  # Versão 3.7 ou superior
```

### Dependências Python
```bash
pip install pandas matplotlib numpy seaborn
```

### Dependências Java
- ✅ `json-20240303.jar` (já incluída no projeto)
- ✅ Estruturas de dados personalizadas (implementadas no projeto)

---

## 📁 Estrutura Completa do Projeto

```
workspace/
├── Simulador_Mobilidade_Urbana-main/     # 🎯 SIMULADOR JAVA
│   ├── src/                              # Código fonte Java
│   │   ├── Main.java                     # ⭐ Classe principal
│   │   ├── Simulador.java                # Motor de simulação
│   │   ├── cidade/                       # Classes da cidade e grafo
│   │   │   ├── Grafo.java
│   │   │   ├── Vertice.java
│   │   │   ├── Aresta.java
│   │   │   └── Rua.java
│   │   ├── estruturas/                   # Estruturas de dados próprias
│   │   │   ├── ArrayList1.java
│   │   │   ├── Fila.java
│   │   │   ├── Dijkstra.java
│   │   │   └── HashMap1.java
│   │   ├── heuristica/                   # Controle de semáforos
│   │   ├── json/                         # Dados da cidade
│   │   │   └── Morada_do_Sol.json        # 🗺️ Dados reais das ruas
│   │   ├── semaforo/                     # Sistema de semáforos
│   │   └── trafego/                      # Geração de veículos
│   ├── lib/                              # Bibliotecas externas
│   │   └── json-20240303.jar
│   ├── bin/                              # Arquivos compilados
│   └── README.md                         # Esta documentação
│
├── analyze_simulation.py                 # 📊 ANÁLISE ESTATÍSTICA
├── run_multiple_simulations.py           # 🔄 SIMULAÇÕES MÚLTIPLAS
├── results/                              # 📈 Resultados e gráficos
└── simulation_results.txt                # 📝 Logs de simulação
```

---

# 🚀 PARTE 1: EXECUTANDO O SIMULADOR JAVA

## 1.1 Preparação do Ambiente

### Navegue para o diretório do projeto
```bash
cd ~/workspace/Simulador_Mobilidade_Urbana-main
```

### Verifique os arquivos necessários
```bash
# Verificar estrutura
ls -la src/
ls -la lib/
ls -la src/json/Morada_do_Sol.json
```

## 1.2 Compilação do Projeto

### Método Recomendado (Linha de Comando)
```bash
# Limpar compilações anteriores (opcional)
rm -rf bin/*

# Compilar todo o projeto
javac -cp "lib/*:src" -d bin $(find src -name "*.java")

# Verificar compilação
ls -la bin/
```

### Verificação da Compilação
```bash
# Deve mostrar arquivos .class
find bin -name "*.class" | head -10
```

## 1.3 Execução do Simulador

### Execução Básica (Duração Padrão: 20 unidades)
```bash
java -cp "lib/*:bin:src" Main
```

### Execução com Duração Personalizada
```bash
# Simulação de 30 unidades de tempo
java -cp "lib/*:bin:src" Main 30

# Simulação de 50 unidades de tempo
java -cp "lib/*:bin:src" Main 50

# Simulação longa (100 unidades)
java -cp "lib/*:bin:src" Main 100
```

### Execução com Redirecionamento de Saída
```bash
# Salvar resultado em arquivo
java -cp "lib/*:bin:src" Main 30 > simulation_output.txt

# Salvar resultado com timestamp
java -cp "lib/*:bin:src" Main 30 > "simulation_$(date +%Y%m%d_%H%M%S).txt"
```

## 1.4 Parâmetros Disponíveis

### Parâmetros do Simulador
| Parâmetro | Tipo | Padrão | Descrição |
|-----------|------|--------|-----------|
| `duração` | int | 20 | Duração da simulação em unidades de tempo |

### Configurações Internas (Main.java)
```java
// Configurações modificáveis no código
int duracaoSimulacao = 20;                    // Duração padrão
int maxVeiculos = 7;                          // Máximo de veículos simultâneos
int passosParaGerarVeiculo = 1;               // Frequência de geração
int limiteMaximoVerticesCaminho = 204;        // Limite de vértices no caminho
```

## 1.5 Interpretação da Saída

### Inicialização
```
Carregando grafo do arquivo JSON...
Número de vértices carregados: 1922

Configurando simulador...
- Duração: 30 unidades de tempo
- Máximo de veículos: 7
- Heurística: Ciclo Fixo

Iniciando simulação...
```

### Durante a Simulação
```
== Passo 0 ==
Veículo #1: Iniciou trajeto (Origem: Rua Crisântemos -> Destino: Rua Leonardo Castelo Branco)
Veículo #1: Avançou para Rua Aristides Saraiva de Almeida
Semáforo Rua Leôncio Ferraz: VERDE (5s)

== Passo 1 ==
Veículo #2: Iniciou trajeto (Origem: Rua Carlos Fortes -> Destino: Rua Treze)
Veículo #1: Avançou para Rua Assis Veloso
Veículo #2: Parado no semáforo (VERMELHO)
```

### Estatísticas Finais
```
Simulação finalizada.
Total de veículos gerados: 7
Veículos que completaram o trajeto: 3
Tempo médio de viagem: 15.2 unidades
Tempo médio de espera: 4.8 unidades
Índice de congestionamento: 0.57
```

### Significado das Métricas
- **Total de veículos gerados**: Quantidade total criada durante a simulação
- **Veículos que completaram**: Veículos que chegaram ao destino
- **Tempo médio de viagem**: Tempo total do trajeto (origem → destino)
- **Tempo médio de espera**: Tempo parado em semáforos
- **Índice de congestionamento**: 0.0 (sem congestionamento) a 1.0 (máximo)

---

# 📊 PARTE 2: FERRAMENTAS DE ANÁLISE PYTHON

## 2.1 Instalação de Dependências Python

### Instalar Pacotes Necessários
```bash
# Instalar dependências principais
pip install pandas matplotlib numpy seaborn

# Verificar instalação
python3 -c "import pandas, matplotlib, numpy, seaborn; print('✅ Todas as dependências instaladas')"
```

### Dependências Detalhadas
```bash
# Versões recomendadas
pip install pandas>=1.3.0 matplotlib>=3.5.0 numpy>=1.21.0 seaborn>=0.11.0
```

## 2.2 Script de Análise Estatística (analyze_simulation.py)

### Funcionalidades
- ✅ **Parsing de Logs**: Extrai dados dos arquivos de simulação
- ✅ **Métricas Avançadas**: Calcula estatísticas de desempenho
- ✅ **Visualizações**: Gera gráficos e charts
- ✅ **Relatórios**: Exporta CSV e tabelas resumo

### Execução Básica
```bash
# Navegar para o diretório de trabalho
cd ~/workspace

# Executar análise (usa simulation_results.txt por padrão)
python3 analyze_simulation.py
```

### Execução com Arquivo Específico
```bash
# Modificar o script para usar arquivo específico
# Editar linha: data = parse_simulation_data('/caminho/para/arquivo.txt')

# Exemplo de modificação
sed -i "s|simulation_results.txt|simulation_output.txt|g" analyze_simulation.py
python3 analyze_simulation.py
```

### Arquivos Gerados
```bash
# Verificar resultados
ls -la results/
# Saída esperada:
# - metricas_resumo.csv
# - metricas_por_veiculo.csv
# - tempo_viagem_vs_espera.png
# - metricas_por_veiculo.png
# - fluxo_temporal.png
# - distribuicao_semaforos.png
```

## 2.3 Script de Simulações Múltiplas (run_multiple_simulations.py)

### Funcionalidades
- ✅ **Execução Automatizada**: Roda múltiplas simulações
- ✅ **Análise Comparativa**: Compara diferentes configurações
- ✅ **Relatórios Comparativos**: Gera análises agregadas
- ✅ **Simulação Energética**: Calcula consumo energético estimado

### Execução
```bash
# Navegar para o diretório
cd ~/workspace

# Executar análise comparativa
python3 run_multiple_simulations.py
```

### Configurações Padrão
```python
# Durações testadas automaticamente
durações = [20, 30, 40, 50]

# Arquivos de saída gerados
# - sim_output_20.txt
# - sim_output_30.txt
# - sim_output_40.txt
# - sim_output_50.txt
```

### Personalizar Configurações
```bash
# Editar durações no script
nano run_multiple_simulations.py

# Modificar linha:
# durações = [20, 30, 40, 50]
# Para:
# durações = [10, 25, 35, 60, 100]
```

---

# 🔄 PARTE 3: FLUXO DE TRABALHO COMPLETO

## 3.1 Execução Passo a Passo Completa

### Passo 1: Preparar Ambiente
```bash
# 1. Navegar para o projeto
cd ~/workspace/Simulador_Mobilidade_Urbana-main

# 2. Verificar Java
java -version

# 3. Verificar Python
python3 --version

# 4. Instalar dependências Python
pip install pandas matplotlib numpy seaborn
```

### Passo 2: Compilar Simulador
```bash
# 1. Limpar compilações anteriores
rm -rf bin/*

# 2. Compilar projeto
javac -cp "lib/*:src" -d bin $(find src -name "*.java")

# 3. Verificar compilação
echo "✅ Compilação concluída" && ls bin/ | head -5
```

### Passo 3: Executar Simulação Simples
```bash
# 1. Executar simulação básica
java -cp "lib/*:bin:src" Main 30 > ../simulation_output.txt

# 2. Verificar saída
echo "✅ Simulação concluída" && tail -10 ../simulation_output.txt
```

### Passo 4: Análise Estatística
```bash
# 1. Voltar ao diretório de trabalho
cd ~/workspace

# 2. Executar análise
python3 analyze_simulation.py

# 3. Verificar resultados
echo "✅ Análise concluída" && ls results/
```

### Passo 5: Análise Comparativa (Opcional)
```bash
# 1. Executar múltiplas simulações
python3 run_multiple_simulations.py

# 2. Verificar relatórios
echo "✅ Análise comparativa concluída" && ls results/analise_comparativa.csv
```

## 3.2 Ordem Recomendada de Execução

### Para Análise Simples
```bash
# 1. Compilar → 2. Simular → 3. Analisar
javac -cp "lib/*:src" -d bin $(find src -name "*.java")
java -cp "lib/*:bin:src" Main 30 > simulation_output.txt
python3 analyze_simulation.py
```

### Para Análise Completa
```bash
# 1. Compilar → 2. Múltiplas Simulações → 3. Análise Comparativa
javac -cp "lib/*:src" -d bin $(find src -name "*.java")
python3 run_multiple_simulations.py
# (Análise automática incluída no script)
```

## 3.3 Interpretação de Resultados

### Métricas Principais
| Métrica | Faixa Ideal | Interpretação |
|---------|-------------|---------------|
| Taxa de Conclusão | > 80% | Eficiência do sistema |
| Tempo Médio de Viagem | < 20 unidades | Performance de rotas |
| Índice de Congestionamento | < 30% | Fluidez do tráfego |
| Eficiência Média | > 70% | Otimização geral |

### Análise de Gráficos
- **tempo_viagem_vs_espera.png**: Correlação entre tempo de viagem e espera
- **fluxo_temporal.png**: Distribuição de veículos ao longo do tempo
- **distribuicao_semaforos.png**: Eficiência do controle de semáforos
- **metricas_por_veiculo.png**: Performance individual dos veículos

---

# 💡 PARTE 4: EXEMPLOS PRÁTICOS

## 4.1 Simulação Simples Rápida

### Comando Único
```bash
cd ~/workspace/Simulador_Mobilidade_Urbana-main && \
javac -cp "lib/*:src" -d bin $(find src -name "*.java") && \
java -cp "lib/*:bin:src" Main 20
```

### Com Análise Automática
```bash
cd ~/workspace/Simulador_Mobilidade_Urbana-main && \
javac -cp "lib/*:src" -d bin $(find src -name "*.java") && \
java -cp "lib/*:bin:src" Main 30 > ../simulation_results.txt && \
cd .. && python3 analyze_simulation.py
```

## 4.2 Análise Completa Automatizada

### Script Completo
```bash
#!/bin/bash
echo "🚀 Iniciando análise completa do simulador..."

# Navegar e compilar
cd ~/workspace/Simulador_Mobilidade_Urbana-main
echo "📦 Compilando simulador..."
javac -cp "lib/*:src" -d bin $(find src -name "*.java")

# Voltar e executar análise comparativa
cd ~/workspace
echo "🔄 Executando simulações múltiplas..."
python3 run_multiple_simulations.py

echo "✅ Análise completa finalizada!"
echo "📊 Resultados disponíveis em: ~/workspace/results/"
ls -la results/
```

### Salvar como script
```bash
# Criar script
cat > ~/workspace/run_complete_analysis.sh << 'EOF'
#!/bin/bash
echo "🚀 Iniciando análise completa do simulador..."
cd ~/workspace/Simulador_Mobilidade_Urbana-main
echo "📦 Compilando simulador..."
javac -cp "lib/*:src" -d bin $(find src -name "*.java")
cd ~/workspace
echo "🔄 Executando simulações múltiplas..."
python3 run_multiple_simulations.py
echo "✅ Análise completa finalizada!"
echo "📊 Resultados disponíveis em: ~/workspace/results/"
ls -la results/
EOF

# Dar permissão de execução
chmod +x ~/workspace/run_complete_analysis.sh

# Executar
~/workspace/run_complete_analysis.sh
```

## 4.3 Simulação com Configurações Específicas

### Simulação Longa (100 unidades)
```bash
cd ~/workspace/Simulador_Mobilidade_Urbana-main
java -cp "lib/*:bin:src" Main 100 > ../simulation_long.txt
cd ..
# Modificar analyze_simulation.py para usar simulation_long.txt
sed -i "s|simulation_results.txt|simulation_long.txt|g" analyze_simulation.py
python3 analyze_simulation.py
```

### Múltiplas Simulações Personalizadas
```bash
# Criar script para diferentes durações
for duration in 15 25 35 45 55; do
    echo "Executando simulação de $duration unidades..."
    cd ~/workspace/Simulador_Mobilidade_Urbana-main
    java -cp "lib/*:bin:src" Main $duration > ../sim_custom_$duration.txt
    cd ..
done
echo "✅ Simulações personalizadas concluídas"
```

## 4.4 Geração de Relatórios Específicos

### Relatório Executivo
```bash
# Executar análise e gerar resumo
cd ~/workspace
python3 analyze_simulation.py > relatorio_executivo.txt
echo "📋 Relatório executivo salvo em: relatorio_executivo.txt"
```

### Exportar Apenas Gráficos
```python
# Modificar analyze_simulation.py para focar em visualizações
# Adicionar no final do main():
print("📈 Gráficos salvos em:")
print("- results/tempo_viagem_vs_espera.png")
print("- results/fluxo_temporal.png")
print("- results/distribuicao_semaforos.png")
print("- results/metricas_por_veiculo.png")
```

---

# 🛠️ PARTE 5: TROUBLESHOOTING E SOLUÇÕES

## 5.1 Problemas de Compilação Java

### Erro: "class JSONException not found"
```bash
# Verificar biblioteca JSON
ls -la lib/json-20240303.jar
ls -la src/libs/json-20240303.jar

# Solução: Usar classpath correto
javac -cp "lib/*:src:src/libs/*" -d bin $(find src -name "*.java")
```

### Erro: "package does not exist"
```bash
# Verificar estrutura de pacotes
find src -name "*.java" | head -10

# Recompilar com estrutura correta
rm -rf bin/*
javac -cp "lib/*:src" -d bin $(find src -name "*.java")
```

### Erro: "JAVA_HOME not set"
```bash
# Linux/Mac
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
export PATH=$JAVA_HOME/bin:$PATH

# Verificar
echo $JAVA_HOME
java -version
```

## 5.2 Problemas de Execução Java

### Erro: "Could not find or load main class Main"
```bash
# Verificar classpath e executar corretamente
java -cp "lib/*:bin:src" Main

# Alternativa: especificar classe completa
java -cp "lib/*:bin:src" Main
```

### Erro: "Arquivo JSON não encontrado"
```bash
# Verificar arquivo de dados
ls -la src/json/Morada_do_Sol.json

# Executar do diretório correto
cd ~/workspace/Simulador_Mobilidade_Urbana-main
java -cp "lib/*:bin:src" Main
```

### Erro: "OutOfMemoryError"
```bash
# Executar com mais memória
java -Xmx2048m -cp "lib/*:bin:src" Main 50

# Para simulações muito longas
java -Xmx4096m -cp "lib/*:bin:src" Main 100
```

## 5.3 Problemas Python

### Erro: "ModuleNotFoundError: No module named 'pandas'"
```bash
# Instalar dependências
pip install pandas matplotlib numpy seaborn

# Verificar instalação
python3 -c "import pandas; print('✅ Pandas instalado')"
```

### Erro: "FileNotFoundError: simulation_results.txt"
```bash
# Verificar arquivo de entrada
ls -la simulation_results.txt

# Criar arquivo de teste se necessário
cd ~/workspace/Simulador_Mobilidade_Urbana-main
java -cp "lib/*:bin:src" Main 20 > ../simulation_results.txt
cd ..
```

### Erro: "Permission denied" ao salvar gráficos
```bash
# Criar diretório de resultados
mkdir -p ~/workspace/results
chmod 755 ~/workspace/results

# Verificar permissões
ls -la ~/workspace/results/
```

## 5.4 Problemas de Performance

### Simulação Muito Lenta
```bash
# Reduzir duração para testes
java -cp "lib/*:bin:src" Main 10

# Verificar recursos do sistema
free -h
top -p $(pgrep java)
```

### Análise Python Lenta
```bash
# Usar arquivo menor para teste
head -100 simulation_results.txt > simulation_test.txt

# Modificar script para usar arquivo menor
sed -i "s|simulation_results.txt|simulation_test.txt|g" analyze_simulation.py
```

## 5.5 Verificação de Integridade

### Verificar Instalação Completa
```bash
#!/bin/bash
echo "🔍 Verificando integridade do sistema..."

# Verificar Java
if java -version &> /dev/null; then
    echo "✅ Java instalado"
else
    echo "❌ Java não encontrado"
fi

# Verificar Python
if python3 --version &> /dev/null; then
    echo "✅ Python instalado"
else
    echo "❌ Python não encontrado"
fi

# Verificar dependências Python
for pkg in pandas matplotlib numpy seaborn; do
    if python3 -c "import $pkg" &> /dev/null; then
        echo "✅ $pkg instalado"
    else
        echo "❌ $pkg não encontrado"
    fi
done

# Verificar arquivos do projeto
if [ -f "Simulador_Mobilidade_Urbana-main/src/Main.java" ]; then
    echo "✅ Simulador Java encontrado"
else
    echo "❌ Simulador Java não encontrado"
fi

if [ -f "analyze_simulation.py" ]; then
    echo "✅ Script de análise encontrado"
else
    echo "❌ Script de análise não encontrado"
fi

echo "🔍 Verificação concluída"
```

---

# 📈 PARTE 6: CONFIGURAÇÕES AVANÇADAS

## 6.1 Modificações no Simulador Java

### Alterar Parâmetros de Veículos
```java
// Em Main.java, modificar:
simuladorGlobal.getGeradorVeiculos().setMaxVeiculosParaCriar(10);  // Mais veículos
simuladorGlobal.getGeradorVeiculos().setPassosParaGerarVeiculo(2); // Geração mais lenta
```

### Alterar Configurações de Semáforos
```java
// Modificar tempos de semáforo (verde, amarelo, vermelho)
HeuristicaControle heuristica = new HeuristicaCicloFixo(8, 3, 8);
```

### Adicionar Logging Detalhado
```java
// Adicionar no loop principal em Main.java
System.out.printf("Tempo %d: %d veículos ativos%n", 
    tempoAtual, simuladorGlobal.getVeiculosAtivos());
```

## 6.2 Personalização da Análise Python

### Modificar Métricas Calculadas
```python
# Em analyze_simulation.py, adicionar novas métricas
def calculate_custom_metrics(data):
    # Adicionar cálculos personalizados
    metrics['velocidade_media'] = total_movimentos / tempo_total
    metrics['densidade_trafego'] = veiculos_simultaneos / total_intersecoes
    return metrics
```

### Personalizar Visualizações
```python
# Adicionar novos gráficos
def create_custom_plots(metrics, output_dir):
    # Gráfico de densidade de tráfego
    plt.figure(figsize=(12, 6))
    # ... código do gráfico
    plt.savefig(f'{output_dir}/densidade_trafego.png')
```

## 6.3 Automação Avançada

### Script de Monitoramento Contínuo
```bash
#!/bin/bash
# monitor_simulation.sh

while true; do
    echo "$(date): Executando simulação..."
    cd ~/workspace/Simulador_Mobilidade_Urbana-main
    java -cp "lib/*:bin:src" Main 30 > ../simulation_$(date +%H%M%S).txt
    cd ..
    python3 analyze_simulation.py
    echo "Aguardando próxima execução..."
    sleep 300  # 5 minutos
done
```

### Análise em Lote
```bash
#!/bin/bash
# batch_analysis.sh

for config in 10 20 30 40 50 60 70 80 90 100; do
    echo "Processando configuração: $config unidades"
    cd ~/workspace/Simulador_Mobilidade_Urbana-main
    java -cp "lib/*:bin:src" Main $config > ../batch_sim_$config.txt
    cd ..
    
    # Modificar script para arquivo específico
    sed -i "s|simulation_results.txt|batch_sim_$config.txt|g" analyze_simulation.py
    python3 analyze_simulation.py
    
    # Renomear resultados
    mv results/ results_$config/
    mkdir results/
done
```

---

# 🎯 PARTE 7: CASOS DE USO ESPECÍFICOS

## 7.1 Pesquisa Acadêmica

### Coleta de Dados para Artigo
```bash
# Executar 50 simulações para análise estatística
for i in {1..50}; do
    echo "Simulação $i/50"
    cd ~/workspace/Simulador_Mobilidade_Urbana-main
    java -cp "lib/*:bin:src" Main 30 > ../research_sim_$i.txt
    cd ..
done

# Consolidar resultados
python3 -c "
import pandas as pd
import glob

# Processar todos os arquivos
results = []
for file in glob.glob('research_sim_*.txt'):
    # Processar cada arquivo
    # ... código de processamento
    pass

# Salvar dataset consolidado
df = pd.DataFrame(results)
df.to_csv('research_dataset.csv', index=False)
print('Dataset de pesquisa salvo: research_dataset.csv')
"
```

### Análise de Sensibilidade
```bash
# Testar diferentes configurações de semáforos
# Modificar HeuristicaCicloFixo com diferentes parâmetros
for verde in 5 8 10 12; do
    for vermelho in 5 8 10 12; do
        echo "Testando: Verde=$verde, Vermelho=$vermelho"
        # Modificar código e executar
        # ... implementação específica
    done
done
```

## 7.2 Demonstração e Ensino

### Simulação Didática
```bash
# Simulação curta para demonstração
cd ~/workspace/Simulador_Mobilidade_Urbana-main
java -cp "lib/*:bin:src" Main 10 | tee ../demo_output.txt

# Análise simplificada
cd ..
python3 -c "
import re
with open('demo_output.txt', 'r') as f:
    content = f.read()
    
# Extrair métricas principais
veiculos = re.search(r'Total de veículos gerados: (\d+)', content)
finalizados = re.search(r'Veículos que completaram: (\d+)', content)

print('=== DEMONSTRAÇÃO RÁPIDA ===')
print(f'Veículos gerados: {veiculos.group(1) if veiculos else \"N/A\"}')
print(f'Veículos finalizados: {finalizados.group(1) if finalizados else \"N/A\"}')
"
```

## 7.3 Análise de Performance

### Benchmark do Sistema
```bash
#!/bin/bash
echo "🚀 Iniciando benchmark do simulador..."

# Testar diferentes durações e medir tempo
for duration in 10 20 30 50 100; do
    echo "Testando duração: $duration"
    
    start_time=$(date +%s)
    cd ~/workspace/Simulador_Mobilidade_Urbana-main
    java -cp "lib/*:bin:src" Main $duration > ../benchmark_$duration.txt
    cd ..
    end_time=$(date +%s)
    
    execution_time=$((end_time - start_time))
    echo "Duração $duration: ${execution_time}s de execução"
    
    # Extrair métricas de performance
    vehicles=$(grep "Total de veículos gerados" benchmark_$duration.txt | grep -o '[0-9]\+')
    echo "Veículos processados: $vehicles"
    echo "Performance: $(echo "scale=2; $vehicles / $execution_time" | bc) veículos/segundo"
    echo "---"
done
```

---

# 📚 PARTE 8: REFERÊNCIA RÁPIDA

## 8.1 Comandos Essenciais

### Compilação e Execução Java
```bash
# Compilar
javac -cp "lib/*:src" -d bin $(find src -name "*.java")

# Executar (duração padrão)
java -cp "lib/*:bin:src" Main

# Executar (duração personalizada)
java -cp "lib/*:bin:src" Main 30

# Executar com saída em arquivo
java -cp "lib/*:bin:src" Main 30 > output.txt
```

### Scripts Python
```bash
# Análise estatística
python3 analyze_simulation.py

# Simulações múltiplas
python3 run_multiple_simulations.py

# Instalar dependências
pip install pandas matplotlib numpy seaborn
```

## 8.2 Estrutura de Arquivos Importantes

### Arquivos de Configuração
- `src/Main.java` - Configurações principais
- `src/json/Morada_do_Sol.json` - Dados da cidade
- `lib/json-20240303.jar` - Biblioteca JSON

### Arquivos de Saída
- `simulation_results.txt` - Log da simulação
- `results/metricas_resumo.csv` - Resumo estatístico
- `results/*.png` - Gráficos e visualizações

## 8.3 Parâmetros de Configuração

### Simulador Java
| Parâmetro | Localização | Valor Padrão | Descrição |
|-----------|-------------|--------------|-----------|
| Duração | Linha de comando | 20 | Unidades de tempo |
| Max Veículos | Main.java | 7 | Veículos simultâneos |
| Freq. Geração | Main.java | 1 | Passos para gerar veículo |
| Limite Caminho | Main.java | 204 | Máx. vértices no caminho |

### Scripts Python
| Script | Entrada Padrão | Saída Principal |
|--------|----------------|-----------------|
| analyze_simulation.py | simulation_results.txt | results/ |
| run_multiple_simulations.py | Múltiplas simulações | analise_comparativa.csv |

---

# 🎉 CONCLUSÃO

Este sistema completo de simulação de mobilidade urbana oferece:

## ✅ Funcionalidades Implementadas
- **Simulador Java robusto** com dados reais do Morada do Sol
- **Análise estatística avançada** com Python
- **Visualizações profissionais** e relatórios detalhados
- **Automação completa** para análises comparativas
- **Documentação abrangente** com exemplos práticos

## 🚀 Próximos Passos Sugeridos
1. **Execute a análise completa** usando o script automatizado
2. **Experimente diferentes configurações** de duração e parâmetros
3. **Analise os gráficos gerados** para insights sobre o tráfego
4. **Customize as métricas** conforme suas necessidades específicas
5. **Integre com outros sistemas** usando os dados exportados

## 📞 Suporte e Manutenção
- Verifique sempre a seção de **Troubleshooting** para problemas comuns
- Use os **comandos de verificação** para validar a instalação
- Consulte os **exemplos práticos** para casos de uso específicos
- Mantenha as **dependências atualizadas** para melhor performance

---

**🏙️ Simulador de Mobilidade Urbana - Morada do Sol, Teresina, PI**  
*Sistema desenvolvido para pesquisa e análise de tráfego urbano com dados reais*

---

*Última atualização: Junho 2025*
