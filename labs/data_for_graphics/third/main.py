import pandas as pd
from tabulate import tabulate
pd.options.display.max_columns = None
pd.options.display.width=None

def print_method_data(data):
    names = ["n" , "k", "‖𝑥∗ − 𝑥_𝑘‖", "‖𝑥∗ − 𝑥_𝑘‖ / ‖𝑥∗‖"]
    lst = [tuple(i) for i in data]
    df = pd.DataFrame(lst, columns=names)
    pdtabulate = lambda df: tabulate(df, headers='keys', tablefmt='psql')
    print(pdtabulate(df))
    print()

def print_method_data3(data):
    names = ["n" , "‖𝑥∗ − 𝑥_𝑘‖ - Гаусс", "‖𝑥∗ − 𝑥_𝑘‖ / ‖𝑥∗‖ - Гаусс", "‖𝑥∗ − 𝑥_𝑘‖ - LU", "‖𝑥∗ − 𝑥_𝑘‖ / ‖𝑥∗‖ - LU"]
    lst = [tuple(i) for i in data]
    df = pd.DataFrame(lst, columns=names)
    pdtabulate = lambda df: tabulate(df, headers='keys', tablefmt='psql')
    print(pdtabulate(df))
    print()

data = []
with open("data3", "r", encoding="utf-8") as file:
    for line in file:
        data.append(line.strip().split())

print_method_data3(data)