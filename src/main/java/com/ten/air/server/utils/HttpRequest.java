package com.ten.air.server.utils;

import com.sun.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * http远程调用
 */
public class HttpRequest {

    private static final String ERROR = "{code:0,msg:\"error\",data:\"\"}";
    private static final String USER_AGENT = "Mozilla/5.0";

    /**
     * UPDATE
     */
    public String sendUpdate(String url, String params) {
        StringBuilder result = new StringBuilder();
        BufferedReader in;
        String urlNameString = url + "?" + params;

        try {
            // 创建连接对象
            URL realUrl = new URL(urlNameString);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置请求头
            connection.setRequestMethod("UPDATE");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", USER_AGENT);
            // 建立实际的连接
            connection.connect();
            // 设置相应请求时间
            connection.setConnectTimeout(30000);
            // 设置读取超时时间
            connection.setReadTimeout(30000);
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            // 循环读取响应结果
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            // 返回结果
            return String.valueOf(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * POST
     */
    public String sendPost(String url, String params) {
        StringBuilder result = new StringBuilder();
        BufferedReader in;
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // 发送Post请求
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            // 返回结果
            return String.valueOf(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

}
