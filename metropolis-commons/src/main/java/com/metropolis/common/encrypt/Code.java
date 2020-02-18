package com.metropolis.common.encrypt;




/**
 *
 * @description: 记录码信息
 * @author: Pop
 * @create: 2019-09-19 15:40
 **/
public class Code {
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String appId;
    private String username;
    private String password;

    public Code() {
    }

    public Code(String appId, String username, String password) {
        this.appId = appId;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return appId + username + password ;
    }
    //    private char crcCode=0xF;//标志位
//    private ByteBuffer buffer;//具体的值
//
//    public byte[] getData(){
//        ByteBuffer b = ByteBuffer.allocate(buffer.capacity()+2);
//        b.putChar(crcCode);
//        b.put(buffer);
//        return b.array();
//    }
}
