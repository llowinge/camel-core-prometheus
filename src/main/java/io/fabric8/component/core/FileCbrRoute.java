package io.fabric8.component.core;

import org.apache.camel.builder.RouteBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class FileCbrRoute extends RouteBuilder {

	// must have a main method spring-boot can run
	public static void main(String[] args) {
		SpringApplication.run(FileCbrRoute.class, args);
	}

	@Override
	public void configure() throws Exception {
		// take file and route it based on filename extension to appropriate output directory
		from("file:{{env:JAVA_DATA_DIR}}/in?noop=true")
				.log("Logging headers: ${headers}")
				.choice()
				.when(header("CamelFileNameOnly").endsWith(".txt")).to("file:{{env:JAVA_DATA_DIR}}/out/txt?autoCreate=true")
				.when(header("CamelFileNameOnly").endsWith(".xml")).to("file:{{env:JAVA_DATA_DIR}}/out/xml?autoCreate=true");
	}
}
