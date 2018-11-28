package com.ten.air.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Schaffer Xu
 * @version 1.0
 */
@SuppressWarnings("unused")
public class CommonUtils {


    /**
     * 获取当前时间-格式化
     *
     * @param format format
     * @return String
     */
    public static String getTimeNow(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }

    /**
     * 进行和校验
     *
     * @param datagram datagram
     * @return boolean
     */
    public static boolean checkDataGram(byte[] datagram) {
        int hundred = 100;
        if (datagram.length != hundred) {
            return false;
        }
        String strdatagram = CodeConvertUtil.convertASCIIToString(datagram);
        return makeChecksum(strdatagram.substring(4, 96)).equals(strdatagram.substring(98, 100));
    }

    /**
     * 校验和算法
     *
     * @param data data
     * @return String
     */
    private static String makeChecksum(String data) {
        if (data == null || "".equals(data)) {
            return "";
        }
        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
            // s为每两位
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        // 用256求余最大是255，即16进制的FF   
        int mod = total % 256;
        String hex = Integer.toHexString(mod);
        len = hex.length();
        // 如果不够校验位的长度，补0,这里用的是两位校验
        int four = 4;
        if (len < four) {
            hex = "0" + hex;
        }
        return hex.toUpperCase();
    }

}
