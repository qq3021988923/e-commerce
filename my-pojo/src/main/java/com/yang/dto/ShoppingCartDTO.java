package com.yang.dto;

import lombok.Data;

@Data
public class ShoppingCartDTO {

        private Long userId;
        private Long dishId;
        private Long setmealId;
        private String dishFlavor;

    }
