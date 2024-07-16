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
				.route(p -> p.path("/api/books/**")
						.uri("lb://book-service"))
				.route(p -> p.path("/api/cart/**")
						.uri("lb://cart-service"))
				.route(p -> p.path("/api/orders/**")
						.uri("lb://order-service"))
				.route(p -> p.path("/api/users/**")
						.uri("lb://user-service"))
//				.route(p -> p.path("/currency-conversion-new/**")
//						.filters(f -> f.rewritePath(
//								"/currency-conversion-new/(?<segment>.*)", 
//								"/currency-conversion-feign/${segment}"))
//						.uri("lb://currency-conversion"))
				.build();
	}

}
