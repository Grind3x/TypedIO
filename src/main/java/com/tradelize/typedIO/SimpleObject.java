package com.tradelize.typedIO;

import java.io.IOException;
import java.util.Objects;

public class SimpleObject {
    private String name;
    private int age;
    private String description;

    public SimpleObject() {
    }

    public SimpleObject(String name, int age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
    }

    public static void writeObj(TypedOutputStream os, SimpleObject source) throws IOException {
        os.write(Type.SIMPLE_OBJECT);
        if (source != null) {
            os.writeBoolean(false);
            os.writeString(source.getName());
            os.writeInt(source.getAge());
            os.writeString(source.getDescription());
            os.writeInt(source.hashCode());
        } else {
            //mark target object as null
            os.writeBoolean(true);
        }
    }

    public static SimpleObject readObj(TypedInputStream is) throws IOException {
        int type = is.read();
        if (type == Type.SIMPLE_OBJECT) {
            //check is source object null
            if (is.readBoolean()) {
                return null;
            }
            SimpleObject result = new SimpleObject();
            result.setName(is.readString());
            result.setAge(is.readInt());
            result.setDescription(is.readString());
            int hash = is.readInt();
            if (hash != result.hashCode()) {
                throw new IOException("Hash code doesn't match");
            }
            return result;
        } else {
            throw new IOException("Incorrect type of object is occurred");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleObject that = (SimpleObject) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, description);
    }

    @Override
    public String toString() {
        return "SimpleObjectTwo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                '}';
    }
}
