FROM docker.io/adoptopenjdk/openjdk11:jre-11.0.11_9

RUN mkdir app/results; \ 
 mkdir -p app/de.hub.mse.ttc2020.solution.atl; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario1/instances/expout; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario1/instances/input; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario1/models; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario2/instances/expout; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario2/instances/input; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario2/models; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario3/instances/expout; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario3/instances/input; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario3/models; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario4/instances/expout; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario4/instances/input; \
 mkdir -p app/de.hub.mse.ttc2020.benchmark/data/scenario4/models

COPY de.hub.mse.ttc2020.benchmark/data/scenario1/instances/expout/* app/de.hub.mse.ttc2020.benchmark/data/scenario1/instances/expout/
COPY de.hub.mse.ttc2020.benchmark/data/scenario1/instances/input/*  app/de.hub.mse.ttc2020.benchmark/data/scenario1/instances/input/
COPY de.hub.mse.ttc2020.benchmark/data/scenario1/models/*           app/de.hub.mse.ttc2020.benchmark/data/scenario1/models/
COPY de.hub.mse.ttc2020.benchmark/data/scenario2/instances/expout/* app/de.hub.mse.ttc2020.benchmark/data/scenario2/instances/expout/
COPY de.hub.mse.ttc2020.benchmark/data/scenario2/instances/input/*  app/de.hub.mse.ttc2020.benchmark/data/scenario2/instances/input/
COPY de.hub.mse.ttc2020.benchmark/data/scenario2/models/*           app/de.hub.mse.ttc2020.benchmark/data/scenario2/models/
COPY de.hub.mse.ttc2020.benchmark/data/scenario3/instances/expout/* app/de.hub.mse.ttc2020.benchmark/data/scenario3/instances/expout/
COPY de.hub.mse.ttc2020.benchmark/data/scenario3/instances/input/*  app/de.hub.mse.ttc2020.benchmark/data/scenario3/instances/input/
COPY de.hub.mse.ttc2020.benchmark/data/scenario3/models/*           app/de.hub.mse.ttc2020.benchmark/data/scenario3/models/
COPY de.hub.mse.ttc2020.benchmark/data/scenario4/instances/expout/* app/de.hub.mse.ttc2020.benchmark/data/scenario4/instances/expout/
COPY de.hub.mse.ttc2020.benchmark/data/scenario4/instances/input/*  app/de.hub.mse.ttc2020.benchmark/data/scenario4/instances/input/
COPY de.hub.mse.ttc2020.benchmark/data/scenario4/models/*           app/de.hub.mse.ttc2020.benchmark/data/scenario4/models/
 
COPY de.hub.mse.ttc2020.benchmark/target/*.jar    app/de.hub.mse.ttc2020.solution.atl/
COPY de.hub.mse.ttc2020.solution/target/*.jar     app/de.hub.mse.ttc2020.solution.atl/
COPY de.hub.mse.ttc2020.solution.atl/target/*.jar app/de.hub.mse.ttc2020.solution.atl/

WORKDIR app/results

CMD java -cp `find ../ -name '*.jar' -printf '%p:' | sed 's/:$//'` \
 org.junit.runner.JUnitCore de.hub.mse.ttc2020.solution.atl.AllATLPerformanceTests \
 && cat results.csv
