package com.vertx.poc;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.vertx.poc")
public class VertxApplication {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        ApplicationContext context = new AnnotationConfigApplicationContext(VertxApplication.class);

        VerticleFactory verticleFactory = context.getBean(SpringVerticleFactory.class);

        vertx.registerVerticleFactory(verticleFactory);
        
        DeploymentOptions options = new DeploymentOptions().setInstances(4);
        vertx.deployVerticle(verticleFactory.prefix() + ":" + DemoVerticle.class.getName(), options);
    }
}
