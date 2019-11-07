package com.tradelize.typedIO;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.tradelize.typedIO.Type.BOOLEAN_TYPE;
import static com.tradelize.typedIO.Type.DOUBLE_TYPE;
import static com.tradelize.typedIO.Type.INT_TYPE;
import static com.tradelize.typedIO.Type.LONG_TYPE;
import static com.tradelize.typedIO.Type.STRING_TYPE;

public class TypedOutputStream {
    private final DataOutputStream os;

    public TypedOutputStream(DataOutputStream os) {
        this.os = os;
    }

    public void writeString(String source) throws IOException {
        os.writeByte(STRING_TYPE);
        os.writeBoolean(source == null);
        if (source != null) {
            byte[] bytes = source.getBytes();
            os.writeInt(bytes.length);
            os.write(bytes);
        }
    }

    public void writeInt(int number) throws IOException {
        os.writeByte(INT_TYPE);
        os.writeInt(number);
    }

    public void writeBoolean(boolean value) throws IOException {
        os.writeByte(BOOLEAN_TYPE);
        os.writeBoolean(value);
    }

    public void writeDouble(double value) throws IOException {
        os.writeByte(DOUBLE_TYPE);
        os.writeDouble(value);
    }

    public void writeLong(long value) throws IOException {
        os.writeByte(LONG_TYPE);
        os.writeLong(value);
    }

    public void writeByte(int value) throws IOException {
        os.writeByte(value);
    }
}
