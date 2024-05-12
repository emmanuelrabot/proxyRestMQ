package com.proxyrestmq.services;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Response implements Serializable {

    private String res1;
    private String res2;

}