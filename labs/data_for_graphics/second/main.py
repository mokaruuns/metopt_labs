# %matplotlib inline
import matplotlib.pyplot as plt
import statistics


def get_data(dirty_data, name):
    data_by_name = dict()
    n_k = dirty_data[name][0::3]
    iterations = dirty_data[name][1::3]
    # print(d_c)
    # print(iter)
    for i, k in enumerate(n_k):
        d = int(k[0].split()[-1])
        c = int(k[1].split()[-1])
        p = statistics.mean([int(i) for i in iterations[i][0].split()])
        if d in data_by_name:
            data_by_name[d].update({c: p})
        else:
            data_by_name.update({d: {c: p}})
    return data_by_name


def print_plot(dirty_data, name):
    colors = {10: 'green', 100: 'red', 1000: 'black', 10000: 'blue'}
    data_by_name = get_data(dirty_data, name)
    # get_data(data, files[0]),
    for key, value in data_by_name.items():
        # print(key)
        list_one = value.keys()
        # print(list_one)
        list_two = value.values()
        # print(list_two)
        plt.plot(list_one, list_two, color=colors[key])
    plt.xlabel("Число обусловленности")
    plt.ylabel("Количество итераций функции")
    plt.savefig(name + '.png')
    plt.show()


def read_data_from_files(files):
    data = dict()
    for f in files:
        data[f] = []
        with open(f, "r") as file:
            for line in file:
                data[f].append(line.strip().split(','))
    return data


def print_all_plots():
    files = ['gradientDescent', 'steepestDescent', 'FletcherReeves']
    data = read_data_from_files(files)
    print_plot(data, files[0])
    print_plot(data, files[1])
    print_plot(data, files[2])


if __name__ == '__main__':
    print_all_plots()
