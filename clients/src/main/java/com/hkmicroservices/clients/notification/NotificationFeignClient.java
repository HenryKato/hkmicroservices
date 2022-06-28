package com.hkmicroservices.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION")
public interface NotificationFeignClient {

    @PostMapping("api/v1/notification")
    void sendNotification(@RequestBody NotificationRequest request);
}
