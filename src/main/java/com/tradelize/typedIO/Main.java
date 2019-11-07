package com.tradelize.typedIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        TypedOutputStream tos = new TypedOutputStream(bos);
        SimpleObject simpleObject = new SimpleObject("simple", 22, "description");
        ComposedObject composedObject = new ComposedObject(42, "composed", simpleObject, true, Long.MAX_VALUE, Double.MAX_VALUE);
        System.out.println("source: " + composedObject);
        ComposedObject.writeObj(tos, composedObject);

        byte[] bytes = bos.toByteArray();

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        TypedInputStream tis = new TypedInputStream(bis);

        ComposedObject result = ComposedObject.readObj(tis);
        System.out.println("target: " + result);
    }
}
