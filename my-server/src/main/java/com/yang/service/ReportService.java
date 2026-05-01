package com.yang.service;

import com.yang.vo.OrderReportVO;
import com.yang.vo.SalesTop10ReportVO;
import com.yang.vo.TurnoverReportVO;
import com.yang.vo.UserReportVO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {

    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

    void exportBusinessData(HttpServletResponse response) throws IOException;
}
