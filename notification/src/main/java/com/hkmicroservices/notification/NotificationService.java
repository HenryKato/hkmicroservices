package com.hkmicroservices.notification;

import com.hkmicroservices.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public void send(NotificationRequest request){
        repository.save(
                Notification.builder()
                        .toCustomerId(request.toCustomerId())
                        .toCustomerEmail(request.toCustomerEmail())
                        .sender("henry")
                        .message(request.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
