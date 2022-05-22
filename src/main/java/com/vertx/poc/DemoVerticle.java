package com.vertx.poc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.vertx.ext.web.Router;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class DemoVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(DemoVerticle.class);

    @Autowired
    Demo greeter;

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);
        router.route("/").handler(this::getDefault);
        router.route("/health").handler(this::getHealthCheck);

        // 启动服务最核心的代码, 类似的可参考: https://silentbalanceyh.gitbooks.io/vert-x/content/chapter03/03-2-http-server.html
        vertx.createHttpServer().requestHandler(router).listen(8080, ar -> {
            if (ar.succeeded()) {
                LOG.info("GreetingVerticle started: @" + this.hashCode());
                startPromise.complete();
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }

    private void getHealthCheck(RoutingContext ctx) {
        ctx.response().end("HealthCheck");
    }

    private void getDefault(RoutingContext ctx) {
        ctx.response().end("default");
    }
}
