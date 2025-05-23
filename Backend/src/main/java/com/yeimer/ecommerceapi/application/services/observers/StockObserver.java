package com.yeimer.ecommerceapi.application.services.observers;

import com.yeimer.ecommerceapi.domain.pojos.Product;

public interface StockObserver {
    void notifyLowStock(Product product);
}
