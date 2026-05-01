package com.yang.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersConfirmDTO implements Serializable {
    private Long id;
    private Integer status;
}