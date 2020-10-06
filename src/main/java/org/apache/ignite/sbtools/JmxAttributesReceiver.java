package org.apache.ignite.sbtools;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Set;

public class JmxAttributesReceiver {

    private static MBeanServerConnection serverConnection;

    public static void main(String[] args) throws Exception {
        if (args.length == 3) {
            saveToFile(fetchObjects(args[0], args[1]), args[2]);
        } else if (args.length == 2) {
            printToConsole(fetchObjects(args[0], args[1]));
        } else {
            printHelp();
        }
    }

    private static Set<ObjectName> fetchObjects(String hostname, String portNumber) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + hostname + ":" + portNumber + "/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        jmxc.connect();

        serverConnection = jmxc.getMBeanServerConnection();
        return serverConnection.queryNames(new ObjectName("org.apache" + ":*"), null);
    }

    private static void printToConsole(Set<ObjectName> objects) throws Exception {
        for (ObjectName object : objects) {
            MBeanInfo beanInfo = serverConnection.getMBeanInfo(object);
            MBeanAttributeInfo[] attrInfo = beanInfo.getAttributes();

            for (MBeanAttributeInfo attr : attrInfo) {
                System.out.println(
                        attr.getName() + ";" + attr.getType() + ";"
                                + attr.getDescription() + ";" + object.getKeyProperty("name")
                                + ";" + object.getKeyProperty("name") + ";" + object.getKeyProperty("group")
                );
            }
        }
    }

    private static void saveToFile(Set<ObjectName> objects, String fileName) throws Exception {
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (ObjectName object : objects) {
            MBeanInfo beanInfo = serverConnection.getMBeanInfo(object);
            MBeanAttributeInfo[] attrInfo = beanInfo.getAttributes();

            for (MBeanAttributeInfo attr : attrInfo) {
                printWriter.println(
                        attr.getName() + ";" + attr.getType() + ";"
                                + attr.getDescription() + ";" + object.getKeyProperty("name")
                                + ";" + object.getKeyProperty("name") + ";" + object.getKeyProperty("group")
                );
            }

        }

        printWriter.close();
    }

    private static void printHelp() {
        System.out.println("Usage: java -jar apache-ignite-metrics-viewer-0.1.jar <hostname> <port> [output-file]");
        System.out.println("  <hostname> <port> are required parameters");
        System.out.println("  [output-file] is optional parameter");
    }
}
