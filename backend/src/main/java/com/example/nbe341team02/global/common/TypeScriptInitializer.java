package com.example.nbe341team02.global.common;

import com.example.nbe341team02.global.util.Ut;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TypeScriptInitializer {
    @Bean
    public ApplicationRunner devInitDataApplicationRunner() {
        return args -> {
            System.out.println("devInitDataApplicationRunner works!");
            Ut.file.downloadByHttp("http://localhost:8080/api/v1/v3/api-docs", ".");

            String cmd = "yes | npx --package typescript --package openapi-typescript openapi-typescript api-docs.json -o ../frontend/src/lib/backend/apiV1/schema.d.ts";
            Ut.cmd.runAsync(cmd);
        };
    }
}
