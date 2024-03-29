# TTC2020

[![Java CI with Maven](https://github.com/dwagelaar/ttc2020/actions/workflows/maven.yml/badge.svg)](https://github.com/dwagelaar/ttc2020/actions/workflows/maven.yml)
[![Docker Pulls](https://img.shields.io/docker/pulls/dwagelaar/ttc2020-atl?label=docker%20pulls)](https://hub.docker.com/r/dwagelaar/ttc2020-atl)

Evaluation framework and reference solution for the TTC 2020 "Round-Trip Migration of Object-Oriented Data Model Instances" case.

## Building

The project can be built using maven:

```
mvn verify
```

## Running

The project can be run with docker:

```
docker image build . -t dwagelaar/ttc2020-atl:latest
docker run dwagelaar/ttc2020-atl:latest
```

## Performance Evaluation

To use the provided plotting scripts, make sure your Python environment provides the dependencies listed in `requirements.txt`.

To plot the runtime results of the reference solution using the plotting script, execute the following command. Make sure to execute the corresponding `AllJavaPerformanceTests` JUnit test beforehand, which creates the `results.csv` file.

```python
python plot.py de.hub.mse.ttc2020.solution.atl/results.csv
```

You can also plot two result sets as follows:

```python
python plot2.py de.hub.mse.ttc2020.solution.atl/results.csv ATL de.hub.mse.ttc2020.solution/results.csv Java
```

The resulting plot will be saved to the file `./runtime.pdf`.
