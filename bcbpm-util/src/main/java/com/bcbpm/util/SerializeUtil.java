package com.bcbpm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtil{
    static Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    public static byte[] serialize(Object object){
        if(object != null){
            ObjectOutputStream oos = null;
            ByteArrayOutputStream baos = null;
            try{
                //序列化
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                byte[] bytes = baos.toByteArray();
                return bytes;
            }catch(Exception e){
                logger.info("serialize 失败:" + e.getMessage());
            }
        }
        return null;
    }

    public static Object unserialize(byte[] bytes){
        if(bytes != null){
            ByteArrayInputStream bais = null;
            try{
                //反序列化
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            }catch(Exception e){
                logger.info("unserialize 失败:" + e.getMessage());
            }
        }
        return null;
    }
}