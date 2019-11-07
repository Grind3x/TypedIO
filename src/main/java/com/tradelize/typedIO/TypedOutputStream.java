package com.tradelize.typedIO;

import java.io.IOException;
import java.io.OutputStream;

import static com.tradelize.typedIO.Type.BOOLEAN_TYPE;
import static com.tradelize.typedIO.Type.INT_TYPE;
import static com.tradelize.typedIO.Type.LONG_TYPE;
import static com.tradelize.typedIO.Type.STRING_TYPE;

public class TypedOutputStream extends OutputStream {
    private OutputStream os;

    public TypedOutputStream(OutputStream os) {
        this.os = os;
    }

    public void writeString(String source) throws IOException {
        os.write(STRING_TYPE);
        //send true if source string is null
        writeBoolean(source == null);
        if (source != null) {
            byte[] bytes = source.getBytes();
            os.write(intToByte(bytes.length));
            os.write(bytes);
        }
    }

    public void writeInt(int number) throws IOException {
        os.write(INT_TYPE);
        byte[] bytes = intToByte(number);
        os.write(intToByte(bytes.length));
        os.write(bytes);
    }

    public void writeBoolean(boolean value) throws IOException {
        os.write(BOOLEAN_TYPE);
        os.write(value ? 1 : 0);
    }

    public void writeDouble(double value) throws IOException {
        long longValue = Double.doubleToRawLongBits(value);
        writeLong(longValue);
    }

    public void writeLong(long value) throws IOException {
        os.write(LONG_TYPE);
        byte[] bytes = new byte[]{
                (byte) ((int) (value >>> 56)),
                (byte) ((int) (value >>> 48)),
                (byte) ((int) (value >>> 40)),
                (byte) ((int) (value >>> 32)),
                (byte) ((int) (value >>> 24)),
                (byte) ((int) (value >>> 16)),
                (byte) ((int) (value >>> 8)),
                (byte) ((int) (value))
        };
        os.write(intToByte(bytes.length));
        os.write(bytes);
    }

    private static byte[] intToByte(int number) {
        return new byte[]{
                (byte) (number >>> 24),
                (byte) (number >>> 16),
                (byte) (number >>> 8),
                (byte) (number)
        };
    }

    @Override
    public void write(int i) throws IOException {
        this.os.write(i);
    }
}
