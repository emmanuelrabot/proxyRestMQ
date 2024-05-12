package com.proxyrestmq.services;

import lombok.Data;

import java.io.Serializable;

@Data
public class Request implements Serializable {

    private String msg;
    private String req1;

}