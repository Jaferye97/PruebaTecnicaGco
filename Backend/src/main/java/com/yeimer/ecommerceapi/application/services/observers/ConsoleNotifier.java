package com.yeimer.ecommerceapi.application.services.observers;

import com.yeimer.ecommerceapi.domain.pojos.Product;
import org.springframework.stereotype.Component;

@Component
public class ConsoleNotifier implements StockObserver {
    @Override
    public void notifyLowStock(Product product) {
        System.out.printf("⚠️ Alert: The product %s %s has low stock (%d units)%n",
                product.getCode(),
                product.getName(),
                product.getStock());
    }
}
