# %matplotlib inline
import matplotlib.pyplot as plt
import statistics

files = ['first', 'second', 'third']
data = dict()
for f in files:
    data[f] = []
    with open(f, "r") as file:
        for line in file:
            data[f].append(line.strip().split(','))


def get_data(name):
    result = dict()
    d_c = data[name][0::3]
    iter = data[name][1::3]
    print(d_c)
    print(iter)
    for i, k in enumerate(d_c):
        d = int(k[0].split()[-1])
        c = int(k[1].split()[-1])
        p = statistics.median([int(i) for i in iter[i][0].split()])

        if d in result:
            result[d].update({c: p})
        else:
            result.update({d: {c: p}})
    return result


first = get_data('first')
second = get_data('second')
third = get_data('third')

colors = {10: 'green', 100: 'red', 1000: 'black', 10000: 'blue'}


def print_plot(data):
    for key, value in data.items():
        print(key)
        list_one = value.keys()
        # print(list_one)
        list_two = value.values()
        # print(list_two)
        plt.plot(list_one, list_two, color=colors[key])
    plt.xlabel("Число обусловленности")
    plt.ylabel("Количество итераций функции")
    plt.show()


print_plot(first)
print_plot(second)
print_plot(third)
