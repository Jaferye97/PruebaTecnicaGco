package com.yeimer.ecommerceapi.application.services.observers;

import com.yeimer.ecommerceapi.domain.pojos.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockSubject {
    private final List<StockObserver> observers = new ArrayList<>();

    public void addObserver(StockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    public void notifyLowStock(Product product) {
        for (StockObserver observer : observers) {
            observer.notifyLowStock(product);
        }
    }
}
