package com.movix.router.processor;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.movix.router.dto.MvxBean;
import com.movix.router.services.MvxServices;

@Component
public class MvxProcessorQueue implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		MvxBean bodyIn = (MvxBean) exchange.getIn().getBody();

		// manipulates request as you want
		bodyIn.setName("Hello, " + bodyIn.getName());
		bodyIn.setId(bodyIn.getId() * 10);

		// bla bla bla
		// ...

		// calls external service
		MvxServices.example(bodyIn);

		exchange.getIn().setHeader("queue-transaction-id", UUID.randomUUID().toString());
	}
}
