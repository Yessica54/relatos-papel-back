package com.unir.grupo_12.api_gateway.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MethodChangeFilter implements GatewayFilterFactory<MethodChangeFilter.Config>{

    private final List<String> methods = Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH");
    
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("method");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> extracted(config, exchange, chain);
    }

    private Mono<Void> extracted(Config config, ServerWebExchange exchange, GatewayFilterChain chain) {
        if(config.getMethod().isBlank() || !methods.contains(config.getMethod())){
            return chain.filter(exchange);
        }
        var request = exchange.getRequest();
        var mutatedExchange = exchange.mutate().request(request.mutate().method(HttpMethod.valueOf(config.getMethod())).build())
                .build();
        return chain.filter(mutatedExchange);
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Override
    public Config newConfig() {
        return new Config("MethodChangeFilter");
    }

    public static class Config {

        public Config(String method) {
            this.method = method;
        }

        private String method;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
}
