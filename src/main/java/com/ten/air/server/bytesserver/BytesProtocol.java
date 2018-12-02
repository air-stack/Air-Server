package com.ten.air.server.bytesserver;

import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioSession;

import java.nio.ByteBuffer;

/**
 * byte protocol
 */
public class BytesProtocol implements Protocol<byte[]> {

    private static final int BYTE_LENGTH = 1;

    @Override
    public byte[] decode(ByteBuffer data, AioSession<byte[]> aioSession) {
        if (data.remaining() < BYTE_LENGTH) {
            return null;
        }
        byte[] originBts = new byte[data.limit()];
        for (int i = 0; data.hasRemaining(); i++) {
            originBts[i] = data.get();
        }
        return originBts;
    }

    @Override
    public ByteBuffer encode(byte[] s, AioSession<byte[]> session) {
        ByteBuffer b = ByteBuffer.allocate(s.length);
        b.put(s);
        return b;
    }
}
