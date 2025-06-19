#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Análise Estatística do Simulador de Mobilidade Urbana
Extrai métricas de desempenho dos dados de simulação
"""

import re
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from collections import defaultdict
import seaborn as sns

def parse_simulation_data(filename):
    """Extrai dados da simulação do arquivo de log"""
    
    with open(filename, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Dados básicos da simulação
    vertices_match = re.search(r'Número de vértices carregados: (\d+)', content)
    intersecoes_match = re.search(r'Total de interseções criadas: (\d+)', content)
    max_veiculos_match = re.search(r'Max veículos para criar: (\d+)', content)
    veiculos_criados_match = re.search(r'Total de veículos criados: (\d+)', content)
    veiculos_finalizados_match = re.search(r'Veículos que chegaram ao destino: (\d+)', content)
    
    # Extrai movimentações dos veículos
    movimentacoes = []
    movimento_pattern = r'Tempo (\d+) - Veículo ID (\d+) movendo-se de (\w+) para (\w+) \| Semáforo atual: (\w+)(?:\s*\((\w+)\))?'
    
    for match in re.finditer(movimento_pattern, content):
        tempo = int(match.group(1))
        veiculo_id = int(match.group(2))
        origem = match.group(3)
        destino = match.group(4)
        semaforo_estado = match.group(5)
        status_adicional = match.group(6) if match.group(6) else None
        
        movimentacoes.append({
            'tempo': tempo,
            'veiculo_id': veiculo_id,
            'origem': origem,
            'destino': destino,
            'semaforo_estado': semaforo_estado,
            'parado': status_adicional == 'PARADO'
        })
    
    # Extrai caminhos calculados
    caminhos = []
    caminho_pattern = r'Caminho calculado \((\d+) vértices\): de (\w+) até (\w+)'
    
    for match in re.finditer(caminho_pattern, content):
        num_vertices = int(match.group(1))
        origem = match.group(2)
        destino = match.group(3)
        
        caminhos.append({
            'num_vertices': num_vertices,
            'origem': origem,
            'destino': destino
        })
    
    return {
        'vertices_carregados': int(vertices_match.group(1)) if vertices_match else 0,
        'intersecoes_criadas': int(intersecoes_match.group(1)) if intersecoes_match else 0,
        'max_veiculos': int(max_veiculos_match.group(1)) if max_veiculos_match else 0,
        'veiculos_criados': int(veiculos_criados_match.group(1)) if veiculos_criados_match else 0,
        'veiculos_finalizados': int(veiculos_finalizados_match.group(1)) if veiculos_finalizados_match else 0,
        'movimentacoes': movimentacoes,
        'caminhos': caminhos
    }

def calculate_metrics(data):
    """Calcula métricas de desempenho"""
    
    movimentacoes_df = pd.DataFrame(data['movimentacoes'])
    caminhos_df = pd.DataFrame(data['caminhos'])
    
    metrics = {}
    
    # Métricas básicas
    metrics['total_vertices'] = data['vertices_carregados']
    metrics['total_intersecoes'] = data['intersecoes_criadas']
    metrics['veiculos_criados'] = data['veiculos_criados']
    metrics['veiculos_finalizados'] = data['veiculos_finalizados']
    metrics['taxa_conclusao'] = (data['veiculos_finalizados'] / data['veiculos_criados'] * 100) if data['veiculos_criados'] > 0 else 0
    
    if not movimentacoes_df.empty:
        # Tempo de simulação
        tempo_max = movimentacoes_df['tempo'].max()
        metrics['duracao_simulacao'] = tempo_max + 1
        
        # Análise por veículo
        veiculos_stats = []
        for veiculo_id in movimentacoes_df['veiculo_id'].unique():
            veiculo_data = movimentacoes_df[movimentacoes_df['veiculo_id'] == veiculo_id]
            
            tempo_inicio = veiculo_data['tempo'].min()
            tempo_fim = veiculo_data['tempo'].max()
            tempo_viagem = tempo_fim - tempo_inicio + 1
            
            # Tempo de espera (quando parado)
            tempo_espera = len(veiculo_data[veiculo_data['parado'] == True])
            
            # Movimentos totais
            movimentos_totais = len(veiculo_data)
            
            veiculos_stats.append({
                'veiculo_id': veiculo_id,
                'tempo_viagem': tempo_viagem,
                'tempo_espera': tempo_espera,
                'movimentos_totais': movimentos_totais,
                'eficiencia': (movimentos_totais - tempo_espera) / movimentos_totais * 100 if movimentos_totais > 0 else 0
            })
        
        veiculos_df = pd.DataFrame(veiculos_stats)
        
        # Métricas agregadas
        metrics['tempo_medio_viagem'] = veiculos_df['tempo_viagem'].mean()
        metrics['tempo_medio_espera'] = veiculos_df['tempo_espera'].mean()
        metrics['eficiencia_media'] = veiculos_df['eficiencia'].mean()
        
        # Análise de semáforos
        semaforo_stats = movimentacoes_df['semaforo_estado'].value_counts()
        metrics['distribuicao_semaforos'] = semaforo_stats.to_dict()
        
        # Índice de congestionamento (% de tempo parado)
        total_movimentos = len(movimentacoes_df)
        movimentos_parados = len(movimentacoes_df[movimentacoes_df['parado'] == True])
        metrics['indice_congestionamento'] = (movimentos_parados / total_movimentos * 100) if total_movimentos > 0 else 0
        
        # Fluxo de veículos por tempo
        fluxo_por_tempo = movimentacoes_df.groupby('tempo').size()
        metrics['fluxo_medio_veiculos'] = fluxo_por_tempo.mean()
        metrics['fluxo_maximo_veiculos'] = fluxo_por_tempo.max()
        
        metrics['veiculos_stats'] = veiculos_df
        metrics['fluxo_por_tempo'] = fluxo_por_tempo
    
    if not caminhos_df.empty:
        # Análise de caminhos
        metrics['tamanho_medio_caminho'] = caminhos_df['num_vertices'].mean()
        metrics['tamanho_maximo_caminho'] = caminhos_df['num_vertices'].max()
        metrics['tamanho_minimo_caminho'] = caminhos_df['num_vertices'].min()
    
    return metrics

def create_visualizations(metrics, output_dir):
    """Cria gráficos e visualizações"""
    
    plt.style.use('seaborn-v0_8')
    fig_size = (12, 8)
    
    # 1. Gráfico de Tempo de Viagem vs Tempo de Espera
    if 'veiculos_stats' in metrics and not metrics['veiculos_stats'].empty:
        plt.figure(figsize=fig_size)
        veiculos_df = metrics['veiculos_stats']
        
        plt.scatter(veiculos_df['tempo_viagem'], veiculos_df['tempo_espera'], 
                   s=100, alpha=0.7, c=veiculos_df['eficiencia'], cmap='RdYlGn')
        plt.colorbar(label='Eficiência (%)')
        plt.xlabel('Tempo de Viagem (unidades)')
        plt.ylabel('Tempo de Espera (unidades)')
        plt.title('Relação entre Tempo de Viagem e Tempo de Espera por Veículo')
        plt.grid(True, alpha=0.3)
        plt.tight_layout()
        plt.savefig(f'{output_dir}/tempo_viagem_vs_espera.png', dpi=300, bbox_inches='tight')
        plt.close()
        
        # 2. Gráfico de barras - Métricas por veículo
        plt.figure(figsize=fig_size)
        x = range(len(veiculos_df))
        width = 0.35
        
        plt.bar([i - width/2 for i in x], veiculos_df['tempo_viagem'], 
               width, label='Tempo de Viagem', alpha=0.8)
        plt.bar([i + width/2 for i in x], veiculos_df['tempo_espera'], 
               width, label='Tempo de Espera', alpha=0.8)
        
        plt.xlabel('Veículo ID')
        plt.ylabel('Tempo (unidades)')
        plt.title('Tempo de Viagem e Espera por Veículo')
        plt.xticks(x, [f'V{int(id)}' for id in veiculos_df['veiculo_id']])
        plt.legend()
        plt.grid(True, alpha=0.3)
        plt.tight_layout()
        plt.savefig(f'{output_dir}/metricas_por_veiculo.png', dpi=300, bbox_inches='tight')
        plt.close()
    
    # 3. Gráfico de fluxo de veículos ao longo do tempo
    if 'fluxo_por_tempo' in metrics:
        plt.figure(figsize=fig_size)
        fluxo = metrics['fluxo_por_tempo']
        
        plt.plot(fluxo.index, fluxo.values, marker='o', linewidth=2, markersize=6)
        plt.xlabel('Tempo (unidades)')
        plt.ylabel('Número de Movimentações')
        plt.title('Fluxo de Veículos ao Longo do Tempo')
        plt.grid(True, alpha=0.3)
        plt.tight_layout()
        plt.savefig(f'{output_dir}/fluxo_temporal.png', dpi=300, bbox_inches='tight')
        plt.close()
    
    # 4. Gráfico de pizza - Distribuição de estados de semáforo
    if 'distribuicao_semaforos' in metrics:
        plt.figure(figsize=(10, 8))
        semaforos = metrics['distribuicao_semaforos']
        
        colors = {'VERDE': '#2ecc71', 'VERMELHO': '#e74c3c', 'AMARELO': '#f39c12'}
        plot_colors = [colors.get(estado, '#95a5a6') for estado in semaforos.keys()]
        
        plt.pie(semaforos.values(), labels=semaforos.keys(), autopct='%1.1f%%',
               colors=plot_colors, startangle=90)
        plt.title('Distribuição de Estados dos Semáforos')
        plt.axis('equal')
        plt.tight_layout()
        plt.savefig(f'{output_dir}/distribuicao_semaforos.png', dpi=300, bbox_inches='tight')
        plt.close()

def generate_summary_table(metrics):
    """Gera tabela resumo das métricas"""
    
    summary_data = {
        'Métrica': [
            'Total de Vértices',
            'Total de Interseções',
            'Veículos Criados',
            'Veículos Finalizados',
            'Taxa de Conclusão (%)',
            'Duração da Simulação',
            'Tempo Médio de Viagem',
            'Tempo Médio de Espera',
            'Eficiência Média (%)',
            'Índice de Congestionamento (%)',
            'Fluxo Médio de Veículos',
            'Fluxo Máximo de Veículos',
            'Tamanho Médio do Caminho',
            'Tamanho Máximo do Caminho'
        ],
        'Valor': [
            metrics.get('total_vertices', 'N/A'),
            metrics.get('total_intersecoes', 'N/A'),
            metrics.get('veiculos_criados', 'N/A'),
            metrics.get('veiculos_finalizados', 'N/A'),
            f"{metrics.get('taxa_conclusao', 0):.2f}",
            metrics.get('duracao_simulacao', 'N/A'),
            f"{metrics.get('tempo_medio_viagem', 0):.2f}",
            f"{metrics.get('tempo_medio_espera', 0):.2f}",
            f"{metrics.get('eficiencia_media', 0):.2f}",
            f"{metrics.get('indice_congestionamento', 0):.2f}",
            f"{metrics.get('fluxo_medio_veiculos', 0):.2f}",
            metrics.get('fluxo_maximo_veiculos', 'N/A'),
            f"{metrics.get('tamanho_medio_caminho', 0):.2f}",
            metrics.get('tamanho_maximo_caminho', 'N/A')
        ]
    }
    
    return pd.DataFrame(summary_data)

def main():
    # Análise dos dados
    print("Analisando dados da simulação...")
    data = parse_simulation_data('/home/ubuntu/workspace/simulation_results.txt')
    
    print("Calculando métricas...")
    metrics = calculate_metrics(data)
    
    # Criar diretório para resultados
    import os
    results_dir = '/home/ubuntu/workspace/results'
    os.makedirs(results_dir, exist_ok=True)
    
    print("Gerando visualizações...")
    create_visualizations(metrics, results_dir)
    
    print("Gerando tabela resumo...")
    summary_table = generate_summary_table(metrics)
    
    # Salvar tabela resumo
    summary_table.to_csv(f'{results_dir}/metricas_resumo.csv', index=False)
    
    # Salvar métricas detalhadas
    if 'veiculos_stats' in metrics:
        metrics['veiculos_stats'].to_csv(f'{results_dir}/metricas_por_veiculo.csv', index=False)
    
    # Exibir resumo
    print("\n=== RESUMO DA ANÁLISE ===")
    print(summary_table.to_string(index=False))
    
    print(f"\nResultados salvos em: {results_dir}")
    print("Arquivos gerados:")
    print("- metricas_resumo.csv")
    print("- metricas_por_veiculo.csv")
    print("- tempo_viagem_vs_espera.png")
    print("- metricas_por_veiculo.png")
    print("- fluxo_temporal.png")
    print("- distribuicao_semaforos.png")

if __name__ == "__main__":
    main()
