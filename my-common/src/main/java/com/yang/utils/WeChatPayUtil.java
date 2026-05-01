package com.yang.utils;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WeChatPayUtil {

    /**
     * 模拟退款（不真实调用微信）
     */
    public  String refund( String outRefundNo) throws Exception {
        // 直接返回模拟的成功结果
        JSONObject result = new JSONObject();
        result.put("code", "SUCCESS");
        result.put("message", "退款成功");
        result.put("out_refund_no", outRefundNo);
        result.put("orderNumber", outRefundNo);
        return result.toString();
    }

    //  参数，目前模拟支付
    public static JSONObject pay(String orderNum, BigDecimal total, String description, String openid) throws Exception {
        // 模拟微信支付返回
        JSONObject jo = new JSONObject();
        jo.put("order_number",orderNum);
        jo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        jo.put("nonceStr", "mock_nonce_str");
        jo.put("package", "prepay_id=mock_prepay_id_123456");
        jo.put("signType", "MD5");
        jo.put("paySign", "mock_pay_sign_abcdef");
        return jo;
    }

}
