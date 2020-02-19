package com.metropolis.authorization.validate;

import com.metropolis.authorization.properties.ValidateCodeProperties;
import com.metropolis.authorization.validate.exception.ValidateCodeException;
import com.metropolis.common.string.StringUtils;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.wf.captcha.base.Captcha.*;

/**
 * @author Pop
 * @date 2020/2/19 22:06
 * 验证码服务
 */
@Service
public class ValidateCodeService {

    @Autowired
    private ValidateCodeProperties properties;

    public static final String CODE_HOLDER = "captcha";
    private final String noCache = "No-cache";
    private static final String PNG = "png";
    private static final String GIF = "gif";

    public void generateCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Captcha captcha = createCode();
        HttpSession session=request.getSession();
        session.setAttribute(CODE_HOLDER,captcha.text());
        captcha.out(response.getOutputStream());
    }

    public Captcha createCode(){
        Captcha captcha = null;
        String type = properties.getType();
        if(type.equalsIgnoreCase(GIF)){
            captcha = new GifCaptcha(properties.getWidth(),properties.getHeight(),properties.getDigit());
        }else{
            captcha = new SpecCaptcha(properties.getWidth(),properties.getHeight(),properties.getDigit());
        }
        setCharType(captcha,properties.getCodeType());
        return captcha;
    }

    private void setCharType(Captcha captcha,int charType){
        switch (charType){
            case TYPE_DEFAULT: captcha.setCharType(TYPE_DEFAULT);break;
            case TYPE_ONLY_NUMBER:captcha.setCharType(TYPE_ONLY_NUMBER);break;
            case TYPE_ONLY_CHAR:captcha.setCharType(TYPE_ONLY_CHAR);break;
            case TYPE_ONLY_UPPER:captcha.setCharType(TYPE_ONLY_CHAR);break;
            case TYPE_ONLY_LOWER:captcha.setCharType(TYPE_ONLY_LOWER);break;
            case TYPE_NUM_AND_UPPER:captcha.setCharType(TYPE_NUM_AND_UPPER);break;
            default:captcha.setCharType(TYPE_DEFAULT);break;
        }
    }

    private void setHttpHeader(HttpServletResponse response,String type){
        if(type.equals(MediaType.IMAGE_GIF_VALUE)){
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        }else{
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA,noCache);
        response.setHeader(HttpHeaders.CACHE_CONTROL,noCache);
        response.setDateHeader(HttpHeaders.EXPIRES,0);
    }

    public void checkCode(HttpServletRequest request,String code){
        HttpSession session=request.getSession();
        String trueCode = (String) session.getAttribute(CODE_HOLDER);
        if(StringUtils.isEmpty(code)){
            throw new ValidateCodeException("请输入验证码。");
        }else if(StringUtils.nonEqualsIgnore(trueCode,code)){
            throw new ValidateCodeException("验证码不正确。");
        }
    }

}
