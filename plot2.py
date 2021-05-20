import matplotlib.pyplot as plt
import pandas as pd
import sys

if len(sys.argv) < 3:
    print("USAGE: python plot.py <RESULTS_CSV_FILE> <LABEL> <RESULTS_CSV_FILE2> <LABEL2>")
    sys.exit(0)

filename = sys.argv[1]
label = sys.argv[2]
filename2 = sys.argv[3]
label2 = sys.argv[4]

df = pd.read_csv(filename, sep=";")
df2 = pd.read_csv(filename2, sep=";")

fig, ax = plt.subplots(figsize=(8, 4))

ax.set_ylabel('Runtime (ms)')
ax.set_xlabel('No. of Repetitions')
ax.set_title('Total Transformation Runtime')

plt.plot (df.values[:, 0], df.values[:, 1] / 1000000.0, '', label=label)
plt.plot (df2.values[:, 0], df2.values[:, 1] / 1000000.0, '', label=label2)

plt.legend()

plt.savefig("runtime.pdf")
