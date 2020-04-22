package com.metropolis.common.constants;

/**
 * @author Pop
 * @date 2020/2/16 18:16
 *
 * 系统状态常量
 */

public enum  SysCodeConstants{

    // 系统公用
    SUCCESS                             ("000000", "成功"),
    FAILURE                             ("200000", "失败"),
    LOGIN_VALIDATE                      ("300000", "登陆失效"),

    USERORPASSWORD_ERRROR               ("003001","用户名或密码不正确"),
    SESSION_TIMEOUT_FAILED              ("003008","session过期，请重新登陆"),
    TOKEN_VALID_FAILED                  ("003002","token校验失败"),
    USERNAME_ALREADY_EXISTS             ("003003","用户名已存在"),
    USER_REGISTER_FAILED                ("003004","注册失败，请联系管理员"),
    KAPTCHA_CODE_ERROR                  ("003005","验证码不正确"),
    USER_ISVERFIED_ERROR                ("003006","用户名尚未激活"),
    USER_REGISTER_VERIFY_FAILED         ("003007","用户注册失败插入验证数据失败"),
    USER_INFOR_INVALID                  ("003004","用户信息不合法"),

    REQUEST_FORMAT_ILLEGAL              ("003060", "请求数据格式非法"),
    REQUEST_IP_ILLEGAL                  ("003061", "请求IP非法"),
    REQUEST_CHECK_FAILURE               ("003062", "请求数据校验失败"),
    DATA_NOT_EXIST                      ("003070", "数据不存在"),
    DATA_REPEATED                       ("003071", "数据重复"),
    REQUEST_DATA_NOT_EXIST              ("003072", "传入对象不能为空"),
    REQUEST_DATA_ERROR                  ("003074", "必要的参数不正确"),
    REQUISITE_PARAMETER_NOT_EXIST       ("003073", "必要的参数不能为空"),
    PERMISSION_DENIED                   ("003091", "权限不足"),
    DB_EXCEPTION                        ("003097", "数据库异常"),
    SYSTEM_TIMEOUT                      ("003098", "系统超时"),
    SYSTEM_ERROR                        ("003099", "系统错误"),

    USERVERIFY_INFOR_INVALID            ("003200", "用户注册验证验证信息不合法");

    SysCodeConstants(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(String code) {
        for (SysCodeConstants s : SysCodeConstants.values()) {
            if (null == code)
                break;
            if (s.code.equals(code)) {
                return s.message;
            }
        }
        return null;
    }
}
