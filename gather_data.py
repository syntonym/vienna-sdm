import subprocess
import json

DATAFILE = "datafile.json"

def read_in():
    with open(DATAFILE) as f:
        inp = f.read()
    i = json.loads(inp)
    return i

def write_out(data):
    with open(DATAFILE, mode="w", encoding="UTF-8") as f:
        f.write(json.dumps(data))



INITIALISATION = ["RANDOM_PARTITION", "RANDOM_CLUSTER_CENTERS"]
STRATEGIES = ["LLOYD", "MACQUEEN"]

def call_main(n, dim, k, init, strat):
    sb = subprocess.run(["java", "Main", str(n), str(dim), str(k), str(strat), str(init)], stdout=subprocess.PIPE, encoding="UTF-8")
    print(sb)
    return sb.stdout


if __name__ == "__main__":

    tries = 5

    try:
        data = read_in()
    except Exception as e:
        print(e)
        data = []


    try:
        for k in [2, 4, 8, 16, 32]:
            for dim in [2, 3, 20, 100]:
                for init in ["RANDOM_PARTITION"]: #INITIALISATION:
                    for strat in ["MACQUEEN"]: #STRATEGIES:
                        for n in [1000, 2000, 4000, 8000]:
                            datapoint = []
                            for _ in range(tries):
                                iterations = call_main(n, dim, k, init, strat)
                                data.append((k, dim, init, strat, n, iterations))
    except Exception as e:
        print(e)
    finally:
        write_out(data)
