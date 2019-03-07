package com.movix.router.processor;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.movix.router.dto.MvxBean;
import com.movix.router.dto.MvxResponse;
import com.movix.router.services.MvxServices;

@Component
public class MvxProcessor2 implements Processor  {

	@Override
	public void process(Exchange exchange) throws Exception {
		Integer id = Integer.parseInt((String)exchange.getIn().getHeader("id"));
		String name = (String) exchange.getIn().getHeader("name");
		
		//manipulates request as you want
		MvxBean bodyIn = new MvxBean(id, name);
		bodyIn.setName("Hello, " + bodyIn.getName());
		bodyIn.setId(bodyIn.getId() * 10);
		
		//bla bla bla
		//...
		
		//calls external service
		MvxServices.example(bodyIn);
		
		//sets response
		exchange.getIn().setBody(new MvxResponse()); //now exchange has body
		exchange.getIn().setHeader("queue-transaction-id", UUID.randomUUID().toString());
	}

}
