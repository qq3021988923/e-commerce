package com.yang.utils;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil { // 自动把图片上传到阿里云网盘，并自动生成分享链接！

    private String endpoint; // 阿里云OSS地区地址
    private String accessKeyId;  // 你的账号ID
    private String accessKeySecret; // 你的账号密钥
    private String bucketName; // 你的存储空间名字

    /**
     * 文件上传
     *前端传过来的图片 / 文件，上传到阿里云 OSS 服务器，并返回图片的访问网址
     * @param bytes
     * @param objectName 文件名
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        } catch (OSSException oe) {

//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
            System.out.println("捕获到OSSException：请求已经成功到达阿里云OSS服务端，但是操作被拒绝！");
            System.out.println("错误信息：" + oe.getErrorMessage());
            System.out.println("错误代码：" + oe.getErrorCode());
            System.out.println("请求ID：" + oe.getRequestId());
            System.out.println("Host ID：" + oe.getHostId());
        } catch (ClientException ce) {

//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
            System.out.println("捕获到ClientException：客户端与阿里云OSS通信时发生严重异常（如网络不通）！");
            System.out.println("错误信息：" + ce.getMessage());

        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //拼接图片访问地址  https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName) //你的存储空间名字
                .append(".")
                .append(endpoint) // 阿里云OSS地区地址
                .append("/")
                .append(objectName); // 文件名

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();
    }
}
