package org.beyene.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ZmqPubRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Note: camel-zeromq component is not yet compatible with Camel 4.x
        // This route is temporarily disabled until a compatible version is available
        // from("timer://timer1?fixedRate=true&period={{timer.publishPeriod:5000}}")
        //         .routeId("zmq-pub")
        //         .process(exchange ->  exchange.getIn().setBody("MBE TEST"))
        //         .to("zeromq:tcp://127.0.0.1:5556?socketType=PUBLISH&topics=info");

        // Temporary test route to verify Camel 4.x is working
        from("timer://timer1?fixedRate=true&period={{timer.publishPeriod:5000}}")
                .routeId("zmq-pub")
                .process(exchange ->  exchange.getIn().setBody("MBE TEST"))
                .log("${body}");
    }
}
