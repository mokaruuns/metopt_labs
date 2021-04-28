# %matplotlib inline
import matplotlib.pyplot as plt

files = ['d.txt', 'g.txt', 'f.txt', 'p.txt', 'b.txt']
data = dict()
for f in files:
    data[f] = []
    with open(f, "r") as file:
        for line in file:
            data[f].append(line.split())
print(data)
for d in data:
    list_one = [int(x[0]) for x in data[d]]
    list_two = [int(float(x[1])) for x in data[d]]
    data[d] = [list_one, list_two]
    print(data[d])
    plt.plot(list_two, list_one)
plt.xlabel("Логарифм точности")
plt.ylabel("Количество вычислений функции")
plt.show()
