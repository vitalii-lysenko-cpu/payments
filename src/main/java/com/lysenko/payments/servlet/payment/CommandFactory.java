package com.lysenko.payments.servlet.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public abstract class CommandFactory {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected CommandFactory(HttpServletRequest request, HttpServletResponse response) {
        this.request = new HttpServletRequestWrapper(request);
        this.response = new HttpServletResponseWrapper(response);
    }

    public abstract Command defineCommand();
}
