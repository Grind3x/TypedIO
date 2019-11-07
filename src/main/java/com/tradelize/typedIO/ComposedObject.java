package com.tradelize.typedIO;

import java.io.IOException;
import java.util.Objects;

public class ComposedObject {
    private int id;
    private String name;
    private SimpleObject object;
    private boolean valid;
    private long amount;
    private double rate;

    public ComposedObject() {
    }

    public ComposedObject(int id, String name, SimpleObject object, boolean valid, long amount, double rate) {
        this.id = id;
        this.name = name;
        this.object = object;
        this.valid = valid;
        this.amount = amount;
        this.rate = rate;
    }

    public static void writeObj(TypedOutputStream os, ComposedObject source) throws IOException {
        if (source != null) {
            os.write(Type.COMPOSED_OBJECT);
            os.writeBoolean(false);
            os.writeInt(source.getId());
            os.writeString(source.getName());
            SimpleObject.writeObj(os, source.getObject());
            os.writeBoolean(source.isValid());
            os.writeLong(source.getAmount());
            os.writeDouble(source.getRate());
            os.writeInt(source.hashCode());
        } else {
            //mark target object as null
            os.writeBoolean(true);
        }
    }

    public static ComposedObject readObj(TypedInputStream is) throws IOException {
        int type = is.read();
        if (type == Type.COMPOSED_OBJECT) {
            //check is source object null
            if (is.readBoolean()) {
                return null;
            }
            ComposedObject result = new ComposedObject();
            result.setId(is.readInt());
            result.setName(is.readString());
            result.setObject(SimpleObject.readObj(is));
            result.setValid(is.readBoolean());
            result.setAmount(is.readLong());
            result.setRate(is.readDouble());
            int hash = is.readInt();
            if (hash != result.hashCode()) {
                throw new IOException("Hash code doesn't match");
            }
            return result;
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimpleObject getObject() {
        return object;
    }

    public void setObject(SimpleObject object) {
        this.object = object;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComposedObject that = (ComposedObject) o;
        return id == that.id &&
                valid == that.valid &&
                amount == that.amount &&
                Double.compare(that.rate, rate) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, object, valid, amount, rate);
    }

    @Override
    public String toString() {
        return "ComposedObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", object=" + object +
                ", valid=" + valid +
                ", amount=" + amount +
                ", rate=" + rate +
                '}';
    }
}
