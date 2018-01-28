package com.techgarden.payu.model;

import java.util.List;

public class OrderCreateRequest {
    private String notifyUrl;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private String totalAmount;
    private List<Product> products;

    public OrderCreateRequest(String notifyUrl, String customerIp, String merchantPosId, String description, String currencyCode, List<Product> products) {
        this.notifyUrl = notifyUrl;
        this.customerIp = customerIp;
        this.merchantPosId = merchantPosId;
        this.description = description;
        this.currencyCode = currencyCode;
        this.products = products;
        recalculateTotalAmount();
    }

    private void recalculateTotalAmount() {
        int total = 0;
        for (Product product : products) {
            int prize = Integer.parseInt(product.getUnitPrice()) * Integer.parseInt(product.getQuantity());
            total += prize;
        }
        this.totalAmount = String.valueOf(total);
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public void setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
    }

    public String getMerchantPosId() {
        return merchantPosId;
    }

    public void setMerchantPosId(String merchantPosId) {
        this.merchantPosId = merchantPosId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        recalculateTotalAmount();
    }
}
