import pandas as pd
from tabulate import tabulate
pd.options.display.max_columns = None
pd.options.display.width=None
data = [[]]

with open("input.txt", "r", encoding="utf-8") as file:
    for line in file:
        if line.isspace():
            data.append([])
        else:
            data[-1].append(line.strip().split())
    # print(line, end='')
dichotomy_list = data[0]
golden_list = data[1]
fibonacci_list = data[2]
parabol_list = data[3]
brents_list = data[4]

dichotomy_columns = ['Левая граница', 'Правая граница', 'Отношение интервалов', 'x1', 'fx1', 'x2', 'fx2']
golden_columns = ['Левая граница', 'Правая граница', 'Отношение интервалов', 'x1', 'fx1', 'x2', 'fx2']
fibonacci_columns = ['Левая граница', 'Правая граница', 'Отношение интервалов', 'x1', 'fx1', 'x2', 'fx2']
parabol_columns = ['Левая граница', 'Правая граница', 'Отношение интервалов', 'currentApproximation',
                   'currentApproximationValue']
brents_columns = ['Левая граница', 'Правая граница', 'Отношение интервалов', 'x1', 'fx2']

colums = [dichotomy_columns, golden_columns, fibonacci_columns, parabol_columns, brents_columns]


def print_method_data(lst, names):
    print(' '.join(lst[0]))
    end = ' '.join(lst[-1])
    lst = [tuple(i) for i in lst[1:-1]]
    df = pd.DataFrame(lst, columns=names)
    pdtabulate = lambda df: tabulate(df, headers='keys', tablefmt='psql')
    print(pdtabulate(df))
    print(end)
    print()


# print(dichotomy_list)
# print_method_data(dichotomy_list, dichotomy_columns)
for i in range(5):
    print_method_data(data[i], colums[i])
