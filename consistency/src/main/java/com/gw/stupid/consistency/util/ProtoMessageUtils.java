package com.gw.stupid.consistency.util;

import com.google.protobuf.Message;
import com.gw.stupid.api.util.ExceptionUtils;
import com.gw.stupid.consistency.entity.auto.ReadRequest;
import com.gw.stupid.consistency.entity.auto.WriteRequest;
import com.gw.stupid.consistency.exception.ConsistencyException;

/**
 * @author guanwu
 * @created on 2022-09-05 14:27:19
 **/
public class ProtoMessageUtils {

    public static final int REQUEST_TYPE_FIELD_TAG = 7 << 3;

    public static final int REQUEST_TYPE_READ = 1;

    public static final int REQUEST_TYPE_WRITE = 2;

    public static Message parseFrom(byte[] bytes) {
        Message result;
        int firstByte = bytes[0];
        if (firstByte != REQUEST_TYPE_FIELD_TAG) {
            throw new ConsistencyException("Current array cannot be deserialized to the message.");
        }
        int secondByte = bytes[1];
        try {
            switch (secondByte) {
                case REQUEST_TYPE_READ:
                    result = ReadRequest.parseFrom(bytes);
                    break;
                case REQUEST_TYPE_WRITE:
                    result = WriteRequest.parseFrom(bytes);
                    break;
                default:
                    throw new RuntimeException("InCompatible message type, the second byte is " + secondByte);
            }
        } catch (Throwable ex) {
            throw new ConsistencyException("Current array cannot be deserialized to the message "
             + ExceptionUtils.getStackTrace(ex));
        }
        return result;
    }
}
