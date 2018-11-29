package com.ten.air.tcp.bean;

import org.smartboot.socket.transport.AioSession;

import java.util.Arrays;

/**
 * Client连接对象 data transfer object
 */
public class BytesConnection {
    private AioSession<byte[]> session;
    private byte[] data;

    private BytesConnection() {
    }

    public static BytesConnection newInstance(AioSession<byte[]> session, byte[] data) {
        BytesConnection instance = new BytesConnection();
        instance.session = session;
        instance.data = data;
        return instance;
    }

    @Override
    public String toString() {
        return "BytesConnection{" +
                "session=" + session +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public AioSession<byte[]> getSession() {
        return session;
    }

    public void setSession(AioSession<byte[]> session) {
        this.session = session;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
