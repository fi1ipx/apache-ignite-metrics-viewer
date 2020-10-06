# Apache Ignite Metrics Viewer

A utility outputs all JMX metrics attributes on the specified cluster in this csv format:
attributeName;attributeType;attributeDescription;jmxBeanName;jmxBeanGroup

A utility recieves hostname and port as parametres.

It needed for receiving metrics that added in newer versions.

##### Usage example:
export IGNITE_JMX_PORT=50000

start a cluster

mvn clean install

java -jar target/apache-ignite-metrics-viewer-0.1.jar localhost 50000 8.5.17-params.txt

shutdown the old version cluster

start a new cluster

java -jar target/apache-ignite-metrics-viewer-0.1.jar localhost 50000 8.7.27-params.txt

diff <(sort 8.7.27-params.txt) <(sort 8.5.17-params.txt)
