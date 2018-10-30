package com.bcbpm.framework.data.access;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.bcbpm.framework.data.enums.ResultEnum;
import com.bcbpm.framework.log.RecordLogService;
import com.bcbpm.model.IBusinessResult;

/**
 * 
* <p>Title: 全局异常捕获处理器</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月12日 下午6:43:13
 * @version :
 * @description:
 */
@ControllerAdvice
@ResponseBody
public class GlobalControllerAccessHandler implements ResponseBodyAdvice<Object>{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RecordLogService recordLogService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType){
        return true;
    }

    /**
     * 在返回前端前进行数据统一处理
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response){
        String requestPath = request.getURI().getPath();// 对特定的请求不进行过滤
        if(requestPath.startsWith("/app") || requestPath.startsWith("/bcbpm/app")){
            return body;
        }
        if(body instanceof BaseResponse){//已经设置过返回类型的
            return body;
        }else if(body instanceof IBusinessResult){
            IBusinessResult result = (IBusinessResult) body;
            return new BaseResponse(result.getResultCode(), result.getResultMsg());
        }
        //对基础数据类型进行转换
        if(isBaseType(returnType.getMethod().getReturnType())){
            logger.info("对基础数据类型进行BaseResponse类型转换");
            return JSON.toJSONString(new BaseResponse(body));
        }
        return new BaseResponse(body);
    }

    //声明要捕获的异常
    @ExceptionHandler(Exception.class)
    public Object defultExcepitonHandler(HttpServletRequest request, Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        recordLogService.recordLogException(sw.toString().substring(0, 1000));
        if(e instanceof BusinessException){
            logger.error("业务异常：" + e.getMessage());
            BusinessException businessException = (BusinessException) e;
            return new BaseResponse(businessException.getCode(), businessException.getMessage());
        }else{
            logger.error("系统未知异常：" + e.getMessage());
            return new BaseResponse(ResultEnum.UNKONW_ERROR.getResultCode(), ResultEnum.UNKONW_ERROR.getResultMsg());
        }

    }

    /**
    * 判断object是否为基本类型
    * @param object
    * @return
    */
    public static boolean isBaseType(Object object){
        String className = object.toString().replaceFirst("class ", "");
        if(className.equals("java.lang.String") || className.equals("java.lang.Integer") || className.equals("java.lang.Byte") || className.equals("java.lang.Long")
                || className.equals("java.lang.Double") || className.equals("java.lang.Float") || className.equals("java.lang.Character") || className.equals("java.lang.Short")
                || className.equals("java.lang.Boolean")){
            return true;
        }
        return false;
    }
}
