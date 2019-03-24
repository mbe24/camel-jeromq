package org.beyene.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ZmqSubRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("zeromq:tcp://127.0.0.1:5556?socketType=SUBSCRIBE&topics=info")
                .routeId("zmq-sub")
                .log("${body}");

    }
}
