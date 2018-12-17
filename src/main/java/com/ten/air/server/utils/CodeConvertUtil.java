package com.ten.air.server.utils;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 转码工具类
 */
public class CodeConvertUtil {

    /**
     * (byte)ASCII----->16进制
     *
     * @param src src
     * @return String
     */
    private static String convertASCIIToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        asciiToHex(src, stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * (byte)ASCII----->10进制
     *
     * @param src src
     * @return String
     */
    static String convertASCIIToString(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        String hexString = convertASCIIToHexString(src);
        return convertHexStringToString(hexString);
    }


    private static void asciiToHex(byte[] src, StringBuilder stringBuilder) {
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
    }

    /**
     * 16进制----->STRING
     *
     * @param hex hex
     * @return String
     */
    private static String convertHexStringToString(String hex) {
        StringBuilder sb = new StringBuilder();
        AtomicReference<StringBuilder> temp = new AtomicReference<>(new StringBuilder());
        int two = 2;
        for (int i = 0; i < hex.length() - 1; i += two) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
            temp.get().append(decimal);
        }
        return sb.toString();
    }


    /**
     * STRING[EF]----->16进制[4546]
     *
     * @param str str
     * @return String
     */
    public static String convertStringToHexString(String str) {
        char[] chars = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char aChar : chars) {
            hex.append(Integer.toHexString((int) aChar));
        }
        return hex.toString().toUpperCase();
    }

    /**
     * 16进制[4546]----->ASCII[6970]
     *
     * @param hexString hexString
     * @return byte[]
     */
    private static byte[] convertHexStringToASCII(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte[] convertStringToASCII(String src) {
        String hexString = convertStringToHexString(src);
        return convertHexStringToASCII(hexString);
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * @param str 需要发送的字符串
     * @return buffer
     */
    public static ByteBuffer getByteBuffer(String str) {
        return ByteBuffer.wrap(str.getBytes());
    }

    /**
     * 回送指令解析
     *
     * @param src src
     * @return String
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        asciiToHex(src, stringBuilder);
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * 十进制----->十六进制
     *
     * @param decimalism 十进制数
     * @return
     */
    public static String decimalismToHexString(int decimalism) {
        String hexStr = Integer.toHexString(decimalism).toUpperCase();
        hexStr = hexStr.length() % 2 == 0 ? hexStr : "0" + hexStr;
        return hexStr;
    }

    /**
     * 当前时间----->十六进制
     *
     * @return
     */
    public static String getNowTimeToHexString() {
        String result = "";
        String timeNow = CommonUtils.getTimeNow("yyMMddHHmmss");
        String year = Integer.toHexString(Integer.valueOf(timeNow.substring(0, 2))).toUpperCase();//年
        String mouth = Integer.toHexString(Integer.valueOf(timeNow.substring(2, 4))).toUpperCase();//月
        String day = Integer.toHexString(Integer.valueOf(timeNow.substring(4, 6))).toUpperCase();//日
        String hour = Integer.toHexString(Integer.valueOf(timeNow.substring(6, 8))).toUpperCase();//时
        String min = Integer.toHexString(Integer.valueOf(timeNow.substring(8, 10))).toUpperCase();//分
        String second = Integer.toHexString(Integer.valueOf(timeNow.substring(10))).toUpperCase();//秒
        return result + (year.length() == 2 ? year : "0" + year)
                + (mouth.length() == 2 ? mouth : "0" + mouth)
                + (day.length() == 2 ? day : "0" + day)
                + (hour.length() == 2 ? hour : "0" + hour)
                + (min.length() == 2 ? min : "0" + min)
                + (second.length() == 2 ? second : "0" + second);
    }


}
