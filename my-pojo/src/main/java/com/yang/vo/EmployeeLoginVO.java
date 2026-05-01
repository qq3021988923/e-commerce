package com.yang.vo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeLoginVO implements Serializable {


    private Long id;
    private String userName;
    private String name;
    private String token;








}
