package com.yang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSalesDTO implements Serializable {

    private String name;     // 商品名称
    private Integer number;  // 销量

}