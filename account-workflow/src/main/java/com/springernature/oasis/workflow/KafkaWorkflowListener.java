package com.springernature.oasis.workflow;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public  class KafkaWorkflowListener {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaWorkflowListener.class);

	@Value("${workflow.process.name}")
	private String workflow;

	@Autowired
	private RuntimeService runtime;

	@PostConstruct
	public void setup() {
		LOG.info("process name = {}", workflow);
	}

	/**
	 * Listen for Kafka messages on the specified topic.
	 * @param message kafka record
	 */
	@KafkaListener(topics = "${workflow.messaging.topic}", groupId = "oasis-subscriber")
	public void listen(String message) {
		LOG.info("message ----- "+message);
		this.runtime.startProcessInstanceByKey(this.workflow);
	}
}
