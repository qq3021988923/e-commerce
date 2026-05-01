package com.yang.controller.admin;


import com.yang.result.Result;
import com.yang.service.ReportService;
import com.yang.vo.OrderReportVO;
import com.yang.vo.SalesTop10ReportVO;
import com.yang.vo.TurnoverReportVO;
import com.yang.vo.UserReportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

// 数据报表统计
@RestController
@RequestMapping("/admin/report")
@Tag( name="数据统计相关接口")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/turnoverStatistics")
    @Operation(summary = "区间营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){

        log.info("营业额数据统计：{},{}",begin,end);
        return Result.success(reportService.getTurnoverStatistics(begin,end));
    }

    @GetMapping("/user") // 统计新增和总数
    @Operation(summary = "统计区间用户")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){

        UserReportVO userStatistics = reportService.getUserStatistics(begin, end);
        return Result.success(userStatistics);
    }

    // 区间订单统计
    @GetMapping("/order")
    @Operation(summary = "区间订单率统计")
    public Result<OrderReportVO> getOrderStatistics(
                @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){

        OrderReportVO orderStatistics = reportService.getOrderStatistics(begin, end);
        return Result.success(orderStatistics);
        }

    @GetMapping("/sales")
    @Operation(summary = "区间热门订单排名销售")
    public Result<SalesTop10ReportVO> getSalesTop10(
            @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){

        SalesTop10ReportVO salesTop10 = reportService.getSalesTop10(begin, end);
        return Result.success(salesTop10);
    }

    // http://localhost:1234/admin/report/export
    @GetMapping("/export")
    @Operation(summary = "导出运营数据报表")
    public void export(HttpServletResponse response) throws IOException {

        reportService.exportBusinessData(response);

    }


}
