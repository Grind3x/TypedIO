import java.io.IOException;
import java.io.InputStream;

public class TypedInputStream extends InputStream {
    private static final int INT_LENGTH = 4;
    private InputStream is;

    public TypedInputStream(InputStream is) {
        this.is = is;
    }

    public String readString() throws IOException {
        int type = is.read();
        if (type == Type.STRING_TYPE) {
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
        if (type == Type.INT_TYPE) {
            int length = readLength();
            byte[] bytes = readBytes(length);
            return byteToInt(bytes);
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public boolean readBoolean() throws IOException {
        int type = is.read();
        if (type == Type.BOOLEAN_TYPE) {
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
        int read = is.read(lengthBytes);
        checkReadBytes(read, INT_LENGTH);
        return byteToInt(lengthBytes);
    }

    private static int byteToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF));
    }

    @Override
    public int read() throws IOException {
        return this.is.read();
    }
}
