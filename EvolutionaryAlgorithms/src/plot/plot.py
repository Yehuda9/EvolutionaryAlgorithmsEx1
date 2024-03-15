import matplotlib.pyplot as plt
import pandas as pd
import sys


def plot_graph(csv_path):
    try:
        df = pd.read_csv(csv_path,
                         names=['Time', 'Generation', 'Best Fitness',
                                'Average Fitness', 'Generation Size',
                                'Chromosome Size', 'Mutation Rate',
                                'Crossover Rate', 'Elitism', 'Max Generations'],
                         skiprows=2)
    except Exception as e:
        return

    generation = df['Generation']
    best_fitness = df['Best Fitness']
    average_fitness = df['Average Fitness']

    plt.xticks(range(0, int(max(generation)) + 1,
                     len(generation) // 10 if len(generation) > 10 else 1))
    plt.yticks(range(0, int(max(best_fitness)) + 1, 5000))
    plt.plot(generation, best_fitness, label='Best Fitness')
    plt.plot(generation, average_fitness, label='Average Fitness')
    plt.xlabel('Generation')
    plt.ylabel('Fitness')
    plt.title('Fitness vs Generation')
    plt.legend()
    plt.savefig(csv_path.replace('.csv', '.png'), dpi=300)
    plt.clf()


if __name__ == '__main__':
    [plot_graph(path.strip(',')) for path in sys.argv[1].split(',')]
