    import pylab
import numpy


def makeData():
    x = numpy.arange(-0.1, 0.2, 0.01)
    y = numpy.arange(-0.4, 0.1, 0.01)
    xgrid, ygrid = numpy.meshgrid(x, y)
    # zgrid = (xgrid * xgrid * 64) + (64 * ygrid * ygrid) + (126 * xgrid * ygrid) + (-10 * xgrid) + (30 * ygrid) + 13
    zgrid = (xgrid * xgrid * 150) + (40 * ygrid * ygrid) + (0 * xgrid * ygrid) + (-24 * xgrid) + (10 * ygrid) + 10
    # zgrid = (xgrid * xgrid * 16) + (20 * ygrid * ygrid) + (0 * xgrid * ygrid) + (-4 * xgrid) + (-8 * ygrid) + 5
    return xgrid, ygrid, zgrid


def count_levels(x, y):
    z = []
    for i in range(len(x)):
        # temp = (x[i] * x[i] * 64) + (64 * y[i] * y[i]) + (126 * x[i] * y[i]) + (-10 * x[i]) + (30 * y[i]) + 13
        temp = (x[i] * x[i] * 150) + (40 * y[i] * y[i]) + (0 * x[i] * y[i]) + (-24 * x[i]) + (10 * y[i]) + 10
        # temp = (x[i] * x[i] * 16) + (20 * y[i] * y[i]) + (0 * x[i] * y[i]) + (-4 * x[i]) + (-8 * y[i]) + 5
        z.append(temp)
    return z


l = []
with open('text.txt') as f:
    l = f.read().splitlines()
x1 = []
y1 = []
for i in range(len(l)):
    temp = l[i].split(' ')
    x1.append(float(temp[0]))
    y1.append(float(temp[1]))
x, y, z = makeData()

fig, ax = pylab.plt.subplots()
lvl = sorted(count_levels(x1, y1))
ax.contour(x, y, z, levels=lvl, colors='black', linestyles='solid', linewidths=1)
ax.spines['left'].set_position('zero')
ax.spines['bottom'].set_position('zero')
ax.spines['top'].set_visible(False)
ax.spines['right'].set_visible(False)
pylab.plt.plot(x1, y1, color='r', marker='o')
fig.set_figwidth(12)  # ширина и
fig.set_figheight(12)  # высота "Figure"
# pylab.plt.savefig('f1m1.png')
# pylab.plt.savefig('f1m2.png')
# pylab.plt.savefig('f1m3.png')
# pylab.plt.savefig('f2m1.png')
# pylab.plt.savefig('f2m2.png')
pylab.plt.savefig('f2m3.png')
# pylab.plt.savefig('f3m1.png')
# pylab.plt.savefig('f3m2.png')
# pylab.plt.savefig('f3m3.png')
pylab.plt.show()
