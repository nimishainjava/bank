package com.springernature.oasis.workflow.servcie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountWorkflowService {

    final Logger logger = LoggerFactory.getLogger(AccountWorkflowService.class);

      public boolean accountTransaction() {
          logger.info("Account Transaction Completed...");
          return true;
      }

      public boolean accountBalance() {
          logger.info("Account Balance updated....");
          return true;
      }

      public boolean accountStatus() {
          logger.info("Account Status is updated...");
          return true;
      }
}
