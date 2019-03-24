/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.beyene.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private CamelContext camelContext;

    @Test
    public void test() {
        // Wait for maximum 5s until the first order gets inserted and processed
        NotifyBuilder notify = new NotifyBuilder(camelContext)
                .fromRoute("zmq-pub")
                .whenDone(1)
                .and()
                .fromRoute("zmq-sub")
                .whenDone(1)
                .create();

        org.junit.Assert.assertTrue(notify.matches(3, TimeUnit.SECONDS));

        //ShutdownStrategy strategy = camelContext.getShutdownStrategy();
        //strategy.setTimeout(10);
        //strategy.setTimeUnit(TimeUnit.SECONDS);
        //strategy.setShutdownNowOnTimeout(true);
    }
}
