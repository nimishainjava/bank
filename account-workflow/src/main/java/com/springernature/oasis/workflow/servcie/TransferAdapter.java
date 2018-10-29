package com.springernature.oasis.workflow.servcie;

import com.springernature.oasis.model.TransactionDetails;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties
public class TransferAdapter implements JavaDelegate {

    @Autowired
    RestTemplate restTemplate;

    @Value("${account.service.baseurl}")
    String baseurl;

    @Override
    public void execute(DelegateExecution ctx) throws Exception {
        TransactionDetails transactionDetails = (TransactionDetails) ctx.getVariable("transactionDetails");
        ResponseEntity<String> response = restTemplate.postForEntity(baseurl+"transfer", transactionDetails, String.class);
        System.out.println("Amount has been transferred successfully.");
        }
}
