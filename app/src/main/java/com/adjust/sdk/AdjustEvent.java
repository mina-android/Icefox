package com.adjust.sdk;

import java.util.LinkedHashMap;
import java.util.Map;

public class AdjustEvent {
    public AdjustEvent(String eventToken) {
    }

    public void setRevenue(double revenue, String currency) {
    }

    public void addCallbackParameter(String key, String value) {
    }

    public void addPartnerParameter(String key, String value) {
    }

    public void setOrderId(String orderId) {
    }

    public void setDeduplicationId(String deduplicationId) {
    }

    public void setCallbackId(String callbackId) {
    }

    public void setProductId(String productId) {
    }

    public void setPurchaseToken(String purchaseToken) {
    }

    public boolean isValid() {
        return true;
    }

    public String getEventToken() {
        return "";
    }

    public Double getRevenue() {
        return 0.0;
    }

    public String getCurrency() {
        return "";
    }

    public Map<String, String> getCallbackParameters() {
        return new LinkedHashMap<String, String>();
    }

    public Map<String, String> getPartnerParameters() {
        return new LinkedHashMap<String, String>();
    }

    public String getOrderId() {
        return "";
    }

    public String getDeduplicationId() {
        return "";
    }

    public String getCallbackId() {
        return "";
    }

    public String getProductId() {
        return "";
    }

    public String getPurchaseToken() {
        return "";
    }

    private static boolean checkEventToken(String eventToken, ILogger logger) {
        return true;
    }

    private boolean checkRevenue(Double revenue, String currency) {
        return true;
    }
}