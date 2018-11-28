package com.ten.air.server.utils;

import com.tencent.aioserver.controller.ServerCtrl;

/**
 * 进行AIO相关的格式校验
 *
 * @author wshten
 * @date 10/12/2018
 */
public class FormatUtil {
    private static volatile FormatUtil INSTANCE = null;

    private FormatUtil() {
    }

    public static FormatUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (FormatUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FormatUtil();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 校验闸机02指令集下开关门操作指令合法性
     */
    public boolean isTwoOperationLegal(String operation) {
        return ServerCtrl.OPEN_DOOR.equals(operation)
                || ServerCtrl.REJECT_DOOR.equals(operation);
    }

    /**
     * 校验闸机04指令集下闸机清除正在显示的二维码功能指令合法性
     */
    public boolean isFourOperationLegal(String operation) {
        return ServerCtrl.CLEAR_FUN.equals(operation);
    }

    /**
     * 校验闸机指令集合法性
     */
    public boolean isOperationLegal(String operation) {
        return ServerCtrl.HEART_BEAT.equals(operation)
                || ServerCtrl.DISTINGUISH.equals(operation)
                || ServerCtrl.DOOR_RESULT.equals(operation)
                || ServerCtrl.QR_CODE_CLEAR.equals(operation)
                || ServerCtrl.QR_CODE_LINK.equals(operation);
    }

}

