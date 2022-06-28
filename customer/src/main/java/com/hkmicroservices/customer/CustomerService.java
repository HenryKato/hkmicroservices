package com.hkmicroservices.customer;

import com.hkmicroservices.amqp.RabbitMQMessageProducer;
import com.hkmicroservices.clients.fraud.FraudCheckResponse;
import com.hkmicroservices.clients.fraud.FraudClient;
import com.hkmicroservices.clients.notification.NotificationFeignClient;
import com.hkmicroservices.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    // private final NotificationFeignClient notificationFeignClient; REQUIRED FOR SYNCHRONOUS DIRECT CALL
    private final RabbitMQMessageProducer rabbitMQMessageProducer; // Injected here since CustomerService is a producer

    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse response = fraudClient.isFraudulentCustomerCheck(customer.getId());

        if(response.isFraudStar()){
            throw new IllegalStateException("Fraud-star");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome...", customer.getFirstName()),
                customer.getEmail()
        );

        // PUBLISH TO RABBITMQ MESSAGE QUEUE - ASYNCHRONOUS CALL
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

        // SEND DIRECTLY TO NOTIFICATION SERVICE - SYNCHRONOUS CALL
        /* notificationFeignClient.sendNotification(
                notificationRequest
        );*/

    }
}
