import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

df = pd.read_csv("resultados.csv")

# Define o estilo dos gráficos
sns.set(style="whitegrid")

# Função para gerar gráfico
def plot_metric(metric, ylabel):
    plt.figure(figsize=(10, 6))
    sns.lineplot(data=df, x="Tamanho", y=metric, hue="Algoritmo", marker="o")
    plt.title(f"{ylabel} por Algoritmo")
    plt.ylabel(ylabel)
    plt.xlabel("Tamanho do vetor")
    plt.legend(title="Algoritmo")
    plt.tight_layout()
    plt.savefig(f"grafico_{metric}.png")
    plt.show()
    plt.clf()  # Limpa a figura atual para garantir que o próximo gráfico seja desenhado

# Gera os três gráficos
plot_metric("Tempo", "Tempo de Execução (ms)")
plot_metric("Comparacoes", "Número de Comparações")
plot_metric("Movimentacoes", "Número de Movimentações")

