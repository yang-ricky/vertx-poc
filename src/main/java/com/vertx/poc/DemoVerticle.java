package com.vertx.poc;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component

@Scope(SCOPE_PROTOTYPE)
public class DemoVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(DemoVerticle.class);

    @Autowired
    Demo greeter;

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createHttpServer().requestHandler(request -> {
            request.response().end(greeter.healthCheck());
        }).listen(8080, ar -> {
            if (ar.succeeded()) {
                LOG.info("GreetingVerticle started: @" + this.hashCode());
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }
}
