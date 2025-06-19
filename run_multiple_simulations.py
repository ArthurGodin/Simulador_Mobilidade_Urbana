#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script para executar múltiplas simulações com diferentes configurações
"""

import subprocess
import os
import pandas as pd
import json
from analyze_simulation import parse_simulation_data, calculate_metrics

def run_simulation(duration=30, output_file=None):
    """Executa uma simulação e retorna o caminho do arquivo de saída"""
    if output_file is None:
        output_file = f"/home/ubuntu/workspace/sim_output_{duration}.txt"
    
    cmd = [
        "java", "-cp", ".:libs/json-20240303.jar", "Main", str(duration)
    ]
    
    try:
        with open(output_file, 'w') as f:
            result = subprocess.run(
                cmd, 
                cwd="/home/ubuntu/workspace/Simulador_Mobilidade_Urbana-main/src",
                stdout=f, 
                stderr=subprocess.STDOUT,
                timeout=60
            )
        return output_file if result.returncode == 0 else None
    except subprocess.TimeoutExpired:
        print(f"Simulação com duração {duration} expirou")
        return None
    except Exception as e:
        print(f"Erro na simulação: {e}")
        return None

def run_comparative_analysis():
    """Executa análise comparativa com diferentes durações"""
    
    durações = [20, 30, 40, 50]
    resultados = []
    
    print("Executando simulações comparativas...")
    
    for duracao in durações:
        print(f"Executando simulação com duração {duracao}...")
        output_file = run_simulation(duracao)
        
        if output_file and os.path.exists(output_file):
            try:
                data = parse_simulation_data(output_file)
                metrics = calculate_metrics(data)
                
                # Adiciona informações da configuração
                metrics['configuracao'] = f"Duração {duracao}"
                metrics['duracao_configurada'] = duracao
                
                resultados.append(metrics)
                print(f"✓ Simulação {duracao} concluída")
            except Exception as e:
                print(f"✗ Erro ao analisar simulação {duracao}: {e}")
        else:
            print(f"✗ Falha na simulação {duracao}")
    
    return resultados

def create_comparative_report(resultados):
    """Cria relatório comparativo"""
    
    if not resultados:
        print("Nenhum resultado para comparar")
        return
    
    # Criar DataFrame comparativo
    comparative_data = []
    
    for resultado in resultados:
        row = {
            'Configuração': resultado.get('configuracao', 'N/A'),
            'Duração Configurada': resultado.get('duracao_configurada', 0),
            'Duração Real': resultado.get('duracao_simulacao', 0),
            'Veículos Criados': resultado.get('veiculos_criados', 0),
            'Veículos Finalizados': resultado.get('veiculos_finalizados', 0),
            'Taxa Conclusão (%)': resultado.get('taxa_conclusao', 0),
            'Tempo Médio Viagem': resultado.get('tempo_medio_viagem', 0),
            'Tempo Médio Espera': resultado.get('tempo_medio_espera', 0),
            'Eficiência Média (%)': resultado.get('eficiencia_media', 0),
            'Índice Congestionamento (%)': resultado.get('indice_congestionamento', 0),
            'Fluxo Médio Veículos': resultado.get('fluxo_medio_veiculos', 0),
            'Tamanho Médio Caminho': resultado.get('tamanho_medio_caminho', 0)
        }
        comparative_data.append(row)
    
    df_comparativo = pd.DataFrame(comparative_data)
    
    # Salvar relatório
    results_dir = '/home/ubuntu/workspace/results'
    os.makedirs(results_dir, exist_ok=True)
    
    df_comparativo.to_csv(f'{results_dir}/analise_comparativa.csv', index=False)
    
    print("\n=== ANÁLISE COMPARATIVA ===")
    print(df_comparativo.to_string(index=False))
    
    # Calcular estatísticas agregadas
    print("\n=== ESTATÍSTICAS AGREGADAS ===")
    print(f"Média de Eficiência: {df_comparativo['Eficiência Média (%)'].mean():.2f}%")
    print(f"Média de Congestionamento: {df_comparativo['Índice Congestionamento (%)'].mean():.2f}%")
    print(f"Média de Tempo de Viagem: {df_comparativo['Tempo Médio Viagem'].mean():.2f}")
    print(f"Média de Tempo de Espera: {df_comparativo['Tempo Médio Espera'].mean():.2f}")
    
    return df_comparativo

def simulate_energy_consumption(metrics):
    """Simula cálculo de consumo energético baseado nas métricas"""
    
    # Parâmetros de consumo energético (simulados)
    CONSUMO_POR_MOVIMENTO = 0.5  # kWh por movimento
    CONSUMO_PARADO = 0.1  # kWh por unidade de tempo parado
    CONSUMO_BASE = 0.2  # kWh por unidade de tempo base
    
    consumo_total = 0
    
    if 'veiculos_stats' in metrics and not metrics['veiculos_stats'].empty:
        veiculos_df = metrics['veiculos_stats']
        
        for _, veiculo in veiculos_df.iterrows():
            # Consumo por movimentos
            consumo_movimento = veiculo['movimentos_totais'] * CONSUMO_POR_MOVIMENTO
            
            # Consumo por tempo parado
            consumo_parado = veiculo['tempo_espera'] * CONSUMO_PARADO
            
            # Consumo base por tempo de viagem
            consumo_base = veiculo['tempo_viagem'] * CONSUMO_BASE
            
            consumo_total += consumo_movimento + consumo_parado + consumo_base
    
    return {
        'consumo_total_kwh': consumo_total,
        'consumo_por_veiculo': consumo_total / metrics.get('veiculos_criados', 1),
        'eficiencia_energetica': (1 - metrics.get('indice_congestionamento', 0) / 100) * 100
    }

def main():
    print("Iniciando análise comparativa do simulador...")
    
    # Executar simulações comparativas
    resultados = run_comparative_analysis()
    
    if resultados:
        # Criar relatório comparativo
        df_comparativo = create_comparative_report(resultados)
        
        # Adicionar análise de consumo energético
        print("\n=== ANÁLISE DE CONSUMO ENERGÉTICO ===")
        for i, resultado in enumerate(resultados):
            consumo = simulate_energy_consumption(resultado)
            print(f"Configuração {resultado.get('configuracao', f'#{i+1}')}:")
            print(f"  Consumo Total: {consumo['consumo_total_kwh']:.2f} kWh")
            print(f"  Consumo por Veículo: {consumo['consumo_por_veiculo']:.2f} kWh")
            print(f"  Eficiência Energética: {consumo['eficiencia_energetica']:.2f}%")
            print()
        
        print("Análise comparativa concluída!")
        print("Arquivos gerados:")
        print("- analise_comparativa.csv")
        
    else:
        print("Nenhuma simulação foi executada com sucesso.")

if __name__ == "__main__":
    main()
