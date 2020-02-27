package com.metropolis.common.encrypt;


import com.metropolis.common.encrypt.exception.TokenTimeParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token将会使用的超时单位
 */
public enum TokenTime {
    SECOND("#S"),MINUTE("#M"),HOUR("#H");
    private String unit;
    public String getUnit() { return unit; }
    public String timestamp(int value){ return unit+value; }
    private static final String MARK = "#";
    public static Date getTime(String token){
        try {
            String originalToken = Aec.getDecoder().decode(token,AECProcessor.TIME_KEY);
            int markIndex =originalToken.indexOf(MARK);
            if(markIndex==-1){throw new TokenTimeParseException(" 不合法的 token 时间戳 "); }
            String unit = originalToken.substring(markIndex,markIndex+2);
            if(!timeMap.containsKey(unit)){throw new TokenTimeParseException(" 找不到指定的token超时单位 ");}
            calendar.setTime(new Date(Long.valueOf(originalToken.substring(0,markIndex))));
            calendar.add(timeMap.get(unit),Integer.parseInt(originalToken.substring(markIndex+2,originalToken.length())));
            return calendar.getTime();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    private static Calendar calendar = Calendar.getInstance();
    private static Map<String,Integer> timeMap = new HashMap<>();
    static {
        timeMap.put("#S",Calendar.SECOND);
        timeMap.put("#M",Calendar.MINUTE);
        timeMap.put("#H",Calendar.HOUR);
    }

    TokenTime(String unit) {
        this.unit = unit;
    }
}
