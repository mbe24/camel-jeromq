package org.beyene.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ZmqSubRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Note: camel-zeromq component is not yet compatible with Camel 4.x
        // This route is temporarily disabled until a compatible version is available
        // from("zeromq:tcp://127.0.0.1:5556?socketType=SUBSCRIBE&topics=info")
        //         .routeId("zmq-sub")
        //         .log("${body}");

        // Temporary test route to verify Camel 4.x is working
        from("direct:zmq-sub-test")
                .routeId("zmq-sub")
                .log("${body}");
    }
}
