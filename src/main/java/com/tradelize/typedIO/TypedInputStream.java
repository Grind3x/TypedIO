package com.tradelize.typedIO;

import java.io.DataInputStream;
import java.io.IOException;

import static com.tradelize.typedIO.Type.BOOLEAN_TYPE;
import static com.tradelize.typedIO.Type.DOUBLE_TYPE;
import static com.tradelize.typedIO.Type.INT_TYPE;
import static com.tradelize.typedIO.Type.LONG_TYPE;
import static com.tradelize.typedIO.Type.STRING_TYPE;

public class TypedInputStream {
    private final DataInputStream is;

    public TypedInputStream(DataInputStream is) {
        this.is = is;
    }

    public String readString() throws IOException {
        if (is.readByte() == STRING_TYPE) {
            if (is.readBoolean()) {
                return null;
            }
            int length = is.readInt();
            byte[] bytes = new byte[length];
            is.readFully(bytes, 0, length);
            return new String(bytes);
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public int readInt() throws IOException {
        if (is.readByte() == INT_TYPE) {
            return is.readInt();
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public long readLong() throws IOException {
        if (is.readByte() == LONG_TYPE) {
            return is.readLong();
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public double readDouble() throws IOException {
        if (is.readByte() == DOUBLE_TYPE) {
            return is.readDouble();
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public boolean readBoolean() throws IOException {
        if (is.readByte() == BOOLEAN_TYPE) {
            return is.readBoolean();
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public byte readByte() throws IOException {
        return is.readByte();
    }
}
