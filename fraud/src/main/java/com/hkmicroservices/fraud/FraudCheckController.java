package com.hkmicroservices.fraud;

import com.hkmicroservices.clients.fraud.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fraud-check")
@Slf4j
public class FraudCheckController {

    private final FraudCheckService service;

    //Constructor Injection. @Autowired is optional starting with Spring 4.3
    // We could also have used the lombok @AllArgsConstructor annotation
    public FraudCheckController(FraudCheckService service){
        this.service = service;
    }

    @GetMapping("/{customerId}")
    public FraudCheckResponse isFraudulentCustomerCheck(@PathVariable Integer customerId){
        boolean isFraudulentCustomer = service.isFraudulentCustomer(customerId);
        log.info("Fraud check request for customer {}", customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }

}
