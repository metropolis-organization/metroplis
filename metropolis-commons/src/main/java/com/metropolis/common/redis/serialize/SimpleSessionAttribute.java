package com.metropolis.common.redis.serialize;

import java.util.Date;
import java.util.Map;

/**
 * @author Pop
 * @date 2020/2/18 22:36
 */
public class SimpleSessionAttribute {
    private  Date startTimestamp;
    private  Date stopTimestamp;
    private  Date lastAccessTime;
    private  long timeout;
    private  boolean expired;
    private  String host;
    private  Map<Object, Object> attributes;
}
