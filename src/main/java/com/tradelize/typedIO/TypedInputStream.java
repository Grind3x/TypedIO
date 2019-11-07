package com.tradelize.typedIO;

import java.io.IOException;
import java.io.InputStream;

import static com.tradelize.typedIO.Type.BOOLEAN_TYPE;
import static com.tradelize.typedIO.Type.INT_TYPE;
import static com.tradelize.typedIO.Type.LONG_TYPE;
import static com.tradelize.typedIO.Type.STRING_TYPE;

public class TypedInputStream extends InputStream {
    private static final int INT_LENGTH = 4;
    private InputStream is;

    public TypedInputStream(InputStream is) {
        this.is = is;
    }

    public String readString() throws IOException {
        int type = is.read();
        if (type == STRING_TYPE) {
            //check is source object is null
            if (readBoolean()) {
                return null;
            }
            int length = readLength();
            byte[] bytes = readBytes(length);
            return new String(bytes);
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public int readInt() throws IOException {
        int type = is.read();
        if (type == INT_TYPE) {
            int length = readLength();
            byte[] bytes = readBytes(length);
            return bytesToInt(bytes);
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public long readLong() throws IOException {
        int type = is.read();
        if (type == LONG_TYPE) {
            int length = readLength();
            byte[] bytes = readBytes(length);
            return bytesToLong(bytes);
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    public boolean readBoolean() throws IOException {
        int type = is.read();
        if (type == BOOLEAN_TYPE) {
            return is.read() == 1;
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    private byte[] readBytes(int length) throws IOException {
        byte[] bytes = new byte[length];
        int read = is.read(bytes, 0, length);
        checkReadBytes(read, length);
        return bytes;
    }

    private void checkReadBytes(int read, int intLength) throws IOException {
        if (read != intLength) {
            throw new IOException("Incorrect length of bytes");
        }
    }

    private int readLength() throws IOException {
        byte[] lengthBytes = new byte[INT_LENGTH];
        int read = is.read(lengthBytes, 0, INT_LENGTH);
        checkReadBytes(read, INT_LENGTH);
        return bytesToInt(lengthBytes);
    }

    private static int bytesToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF));
    }

    private long bytesToLong(byte[] bytes) {
        return ((long) bytes[0] << 56) +
                ((long) (bytes[1] & 255) << 48) +
                ((long) (bytes[2] & 255) << 40) +
                ((long) (bytes[3] & 255) << 32) +
                ((long) (bytes[4] & 255) << 24) +
                (long) ((bytes[5] & 255) << 16) +
                (long) ((bytes[6] & 255) << 8) +
                (long) ((bytes[7] & 255));
    }

    @Override
    public int read() throws IOException {
        return this.is.read();
    }
}
