package com.yang.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetmealOverViewVO implements Serializable {

    // 已启售数量
    private Integer sold;

    // 已停售数量
    private Integer discontinued;
}
