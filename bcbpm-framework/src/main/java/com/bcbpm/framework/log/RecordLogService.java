/**
 * 
 */
package com.bcbpm.framework.log;

import javax.servlet.http.HttpServletRequest;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.bcbpm.framework.mq.IConfigmConstantMq;
import com.bcbpm.framework.session.SessionDeal;
import com.bcbpm.model.log.AccessLog;
import com.bcbpm.model.user.User;
import com.bcbpm.util.TimeUtil;

/**<p>Title: RecordLog</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月19日 下午5:30:44
 * @version :
 * @description:
 */
@Component
public class RecordLogService implements IRecordLogService{
    @Autowired
    private SessionDeal sessionDeal = null;
    @Autowired
    private DefaultMQProducer defaultProducer = null;

    /**
     * @Title: 后台逻辑执行完后处理日志 
     * @author jacky
     * @date 2018年10月19日 下午5:33:15
     */
    public void recordLog(Object ret){
        try{
            AccessLog accessLog = writeLogInfo();
            accessLog.setReturnName("void");
            if(ret != null){
                accessLog.setReturnName(ret.getClass().getName());
                accessLog.setReturnValue(ret);
            }
            // 日志记录
            sendLog2Mq(accessLog);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Title: 后台逻辑执行完后处理日志 
     * @author jacky
     * @date 2018年10月19日 下午5:33:15
     */
    public void recordLogException(String exception){
        try{
            AccessLog accessLog = writeLogInfo();
            accessLog.setException(exception);
            sendLog2Mq(accessLog);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**@Title: 发送日志到消息队列 
     * @author jacky
     * @date 2018年10月19日 下午5:44:28
     * @param accessLog
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
    */
    private void sendLog2Mq(AccessLog accessLog){
        // 日志记录
        try{
            Message logMsg = new Message(IConfigmConstantMq.TOPIC_COMMON, IConfigmConstantMq.TAG_LOG, "", JSON.toJSONBytes(accessLog));
            defaultProducer.send(logMsg, new SendCallback(){
                @Override
                public void onSuccess(SendResult sendResult){
                }

                @Override
                public void onException(Throwable e){
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**@Title: 记录基础数据 
     * @author jacky
     * @date 2018年10月19日 下午5:42:59
     * @param exception
     * @return
    */
    private AccessLog writeLogInfo(){
        long endTimeMs = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        AccessLog accessLog = (AccessLog) request.getAttribute("recordAccessLog");
        accessLog.setExecuteTime((int) (endTimeMs - accessLog.getStartTimeMs()));
        accessLog.setEndTimeMs(endTimeMs);
        accessLog.setStartTime(TimeUtil.toDate(accessLog.getStartTimeMs()));
        accessLog.setEndTime(TimeUtil.toDate(endTimeMs));
        User user = (User) sessionDeal.getNowUserFront(request);
        if(user != null){
            accessLog.setTenantId(user.getTenantId());
            accessLog.setUserId(user.getUserId());
            accessLog.setUserName(user.getTrueName());
        }
        return accessLog;
    }

}
