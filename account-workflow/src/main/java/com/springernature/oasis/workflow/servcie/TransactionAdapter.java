package com.springernature.oasis.workflow.servcie;

import com.springernature.oasis.model.TransactionDetails;
import com.springernature.oasis.model.TransactionType;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static io.vavr.API.*;

@Component
@ConfigurationProperties
public class TransactionAdapter implements JavaDelegate {

    @Override
    public void execute(DelegateExecution ctx) throws Exception {
        TransactionDetails transactionDetails = (TransactionDetails) ctx.getVariable("transactionDetails");
        String type = Match(transactionDetails.getType()).of(
                Case($(TransactionType.CREDIT), "deposit"),
                Case($(TransactionType.DEBIT), "withdraw"),
                Case($(),"transfer"));
        ctx.setVariable("transactionType", type);
        ctx.setVariable("transactionDetails", transactionDetails);
    }
}
