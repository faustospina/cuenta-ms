package com.cuenta.cuenta_ms.config;

import com.cuenta.cuenta_ms.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SqsMessageReceiver {

    @Autowired
    private MovimientoService service;


    @Scheduled(fixedRate = 30000)
    public void scheduledReceiveMessages() {
        service.receiveAndProcessAllMessages();
    }

}
