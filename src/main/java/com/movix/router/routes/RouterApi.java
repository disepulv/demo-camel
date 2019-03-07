package com.movix.router.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.movix.router.dto.MvxBean;
import com.movix.router.processor.MvxProcessor;
import com.movix.router.processor.MvxProcessor2;
import com.movix.router.processor.MvxProcessorQueue;

/**
 * 
 * Movix router demo
 * 
 * @author dsepulveda
 *
 */
@Component
class RouterApi extends RouteBuilder {

	@Autowired
	private MvxProcessor processor;
	
	@Override
	public void configure() {

		/**
		 * General configuration 
		 * http://localhost:8080/camel/api-doc
		 */
		restConfiguration() //
				.enableCORS(true).apiContextPath("/api-doc").apiProperty("api.title", "Test REST API") //
				.apiProperty("api.version", "v1").apiProperty("cors", "true") // cross-site //
				.apiContextRouteId("doc-api").component("servlet").bindingMode(RestBindingMode.json) //
				.dataFormatProperty("prettyPrint", "true");

		/**
		 * POST
		 * curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/camel/api/demo --data '{"id": 1,"name": "World"}'
		 */
		rest("/api/").description("Test REST Service").id("api-route-demo") //
				.post("/demo") //
				.produces(MediaType.APPLICATION_JSON_VALUE) //
				.consumes(MediaType.APPLICATION_JSON_VALUE) //
				.bindingMode(RestBindingMode.auto).type(MvxBean.class) //
				.enableCORS(true) //
				.to("direct:remoteServiceA"); //
		
		/**
		 * GET
		 * curl -X GET -i http://localhost:8080/camel/api/demo2/1/Diego
		 * curl -X GET -i http://localhost:8080/camel/api/demo3/1/Diego
		 */
		rest("/api/") //
				.get("/demo2/{id}/{name}").to("direct:remoteServiceB") //
				.get("/demo3/{id}/{name}").to("direct:remoteServiceB"); //
		
		/**
		 * Routes
		 */
		from("direct:remoteServiceA") //
				.process(processor) //
				.marshal().json(JsonLibrary.Jackson) //
				.to("stream:out") //
				.to("rabbitmq:camel-direct?routingKey=movix.camel.demo&queue=camel-queue&autoDelete=false&exchangePattern=InOnly") //
				.unmarshal().json(JsonLibrary.Jackson)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)); //
		
		from("direct:remoteServiceB") //
				.process(new MvxProcessor2()); //
		
		//read queue
		from("rabbitmq:camel-direct?routingKey=movix.camel.demo&queue=camel-queue&autoDelete=false&threadPoolSize=1&autoAck=false")
				.to("stream:out")
				.unmarshal().json(JsonLibrary.Jackson, MvxBean.class) //
				.process(new MvxProcessorQueue()); //
	}

}