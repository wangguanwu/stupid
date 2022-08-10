package com.gw.stupid.consistency.serializer;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import com.gw.stupid.consistency.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author guanwu
 * @created on 2022-08-09 15:59:55
 **/
public class HessianSerializer implements Serializer {
    public static final String NAME = "Hessian";

    private final SerializerFactory serializerFactory = new SerializerFactory();


    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output hop = new Hessian2Output(bos);
        hop.setSerializerFactory(serializerFactory);
        try {
            hop.writeObject(obj);
            hop.close();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred when hessian serialize data", e);
        }
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data) {
        return doDeserialize(data);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> objClazz) {
        return doDeserialize(data);
    }

    private<T> T doDeserialize(byte[] data) {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        input.setSerializerFactory(serializerFactory);
        Object object = null;
        try {
            object = input.readObject();
            input.close();
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred when hessian deserialize data", e);
        }
        return (T)object;
    }

    @Override
    public String name() {
        return NAME;
    }
}
