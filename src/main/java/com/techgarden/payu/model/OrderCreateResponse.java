package com.techgarden.payu.model;

public class OrderCreateResponse {
    private String orderId;
    private OrderCreateStatus status;
    private String redirectUri;

    public OrderCreateResponse() {
    }

    public OrderCreateResponse(String orderId, OrderCreateStatus status, String redirectUri) {
        this.orderId = orderId;
        this.status = status;
        this.redirectUri = redirectUri;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderCreateStatus getStatus() {
        return status;
    }

    public void setStatus(OrderCreateStatus status) {
        this.status = status;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderCreateResponse that = (OrderCreateResponse) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return redirectUri != null ? redirectUri.equals(that.redirectUri) : that.redirectUri == null;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (redirectUri != null ? redirectUri.hashCode() : 0);
        return result;
    }
}
