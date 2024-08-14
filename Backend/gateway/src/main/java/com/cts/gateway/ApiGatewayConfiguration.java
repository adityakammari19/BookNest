package com.cts.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/api/users/**")
						.uri("lb://user-service"))
				.route(p -> p.path("/api/auth/**")
						.uri("lb://user-service"))
//				.route(p -> p.path("/api/books/**")
////						.filters(f -> f
////								.filter((GatewayFilter) new AuthenticationFilter())
////								.rewritePath("/api/books/(?<segment>.*)", "/api/books/${segment}"))
////						 .filters(f -> f.filter(new GatewayFilterAdapter(new AuthenticationFilter())))
//						.filters(f -> f.filter((exchange, chain) -> new AuthenticationFilter().apply(exchange, chain)))
//						.uri("lb://book-service"))
//				.route(p -> p.path("/api/cart/**")
//						.uri("lb://cart-service"))
//				.route(p -> p.path("/api/orders/**")
//						.uri("lb://order-service"))
//				.route(p -> p.path("/api/books/new/**")
//						.filters(f -> f.rewritePath(
//								"/api/books/new/(?<segment>.*)", 
//								"/api/books/${segment}"))
//						.uri("lb://book-service"))
				.build();
	}

}
