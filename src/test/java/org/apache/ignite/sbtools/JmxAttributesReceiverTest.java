package org.apache.ignite.sbtools;

import org.junit.Test;

public class JmxAttributesReceiverTest {

    @Test
    public void test() throws Exception {
        JmxAttributesReceiver.printToConsole(JmxAttributesReceiver.fetchObjects("localhost", "50000"));
    }
}
