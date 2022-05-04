package com.vertx.poc;

import org.springframework.stereotype.Component;

@Component
public class Demo {

    public String healthCheck() {
        return "Health Check";
    }

}
