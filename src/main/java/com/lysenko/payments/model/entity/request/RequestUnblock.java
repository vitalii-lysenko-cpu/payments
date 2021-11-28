package com.lysenko.payments.model.entity.request;

import java.io.Serializable;

public class RequestUnblock implements Serializable {
   private int id;
   private StatusRequest statusRequest;
   private int accountId;

    public RequestUnblock(int id, StatusRequest statusRequest, int accountId) {
        this.id = id;
        this.statusRequest = statusRequest;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public StatusRequest getStatusRequest() {
        return statusRequest;
    }

    public int getAccountId() {
        return accountId;
    }
}
