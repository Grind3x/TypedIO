import java.io.IOException;
import java.io.OutputStream;

public class TypedOutputStream extends OutputStream {
    private OutputStream os;

    public TypedOutputStream(OutputStream os) {
        this.os = os;
    }

    public void writeString(String source) throws IOException {
        os.write(Type.STRING_TYPE);
        //send true if source string is null
        writeBoolean(source == null);
        if (source != null) {
            byte[] bytes = source.getBytes();
            os.write(intToByte(bytes.length));
            os.write(bytes);
        }
    }

    public void writeInt(int number) throws IOException {
        os.write(Type.INT_TYPE);
        byte[] bytes = intToByte(number);
        os.write(intToByte(bytes.length));
        os.write(bytes);
    }

    public void writeBoolean(boolean value) throws IOException {
        os.write(Type.BOOLEAN_TYPE);
        os.write(value ? 1 : 0);
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
