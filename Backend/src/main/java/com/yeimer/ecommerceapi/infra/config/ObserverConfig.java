package com.yeimer.ecommerceapi.infra.config;

import com.yeimer.ecommerceapi.application.services.observers.ConsoleNotifier;
import com.yeimer.ecommerceapi.application.services.observers.StockSubject;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObserverConfig {
    private final StockSubject stockSubject;
    private final ConsoleNotifier consoleNotifier;

    public ObserverConfig(StockSubject stockSubject, ConsoleNotifier consoleNotifier) {
        this.stockSubject = stockSubject;
        this.consoleNotifier = consoleNotifier;
    }

    @PostConstruct
    public void init() {
        stockSubject.addObserver(consoleNotifier);
    }
}
