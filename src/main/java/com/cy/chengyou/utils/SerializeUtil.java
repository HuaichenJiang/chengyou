package com.cy.chengyou.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author HuaichenJiang
 * @Title
 * @Description
 * @date 2018/11/14  17:08
 */
public class SerializeUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SerializeUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * @description: 将对象序列化输出
     * @param paramUnSerializedObject
     * @return
     */
    public static String serializeToJson(final Object paramUnSerializedObject) {
        if(paramUnSerializedObject == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(paramUnSerializedObject);
        }catch (JsonProcessingException e) {
            LOG.error("writing json value as string happened error: ",e.fillInStackTrace());
            return "";
        }
    }

    /**
     * @description: 将Json String反序列化成对象
     * @param message
     * @param classType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T deserializeToJsonByObjectMapper(final String message, final Class<T> classType) throws IOException {
        if (message == null) {
            return null;
        }
        return mapper.readValue(message, classType);
    }
}