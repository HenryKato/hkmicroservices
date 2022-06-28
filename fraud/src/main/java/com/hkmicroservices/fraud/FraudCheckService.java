package com.hkmicroservices.fraud;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FraudCheckService {

    private final FraudCheckHistoryRepository repository;

    //Constructor Injection. @Autowired is optional starting with Spring 4.3
    // We could also have used the lombok @AllArgsConstructor annotation
    public FraudCheckService(FraudCheckHistoryRepository repository){
        this.repository = repository;
    }

    public boolean isFraudulentCustomer(Integer customerId){
        repository.save(FraudCheckHistory.builder()
                .fraudStar(false)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build());
        return false;
    }
}
