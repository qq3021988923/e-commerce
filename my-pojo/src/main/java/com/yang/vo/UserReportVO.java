package com.yang.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReportVO implements Serializable {

    private String dateList;       // 日期，逗号分隔
    private String totalUserList;  // 用户总量，逗号分隔
    private String newUserList;    // 新增用户，逗号分隔


}