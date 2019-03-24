package org.beyene.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ZmqPubRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://timer1?fixedRate=true&period={{timer.publishPeriod:5000}}")
                .routeId("zmq-pub")
                .process(exchange ->  exchange.getIn().setBody("MBE TEST"))
                .to("zeromq:tcp://127.0.0.1:5556?socketType=PUBLISH&topics=info");

    }
}
