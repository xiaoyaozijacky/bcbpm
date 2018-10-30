package com.bcbpm.service.mq.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bcbpm.framework.mq.IConfigmConstantMq;
import com.bcbpm.framework.mq.RocketmqEvent;
import com.bcbpm.model.log.AccessLog;
import com.bcbpm.service.log.IRecordUserAccessLog;
import com.bcbpm.service.mq.IBaseConsumer;
import com.bcbpm.util.UuidUtil;

/**<p>Title: BaseConsumerImpl</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月18日 下午2:12:46
 * @version :
 * @description:消息消费者组件
 */
@Service("baseConsumer")
public class BaseConsumerImpl implements IBaseConsumer{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<AccessLog> accessLoglist = Collections.synchronizedList(new ArrayList<AccessLog>());//记录操作日志
    private static AtomicLong logCnt = new AtomicLong();
    @Autowired
    private IRecordUserAccessLog recordUserAccessLog = null;

    /**
     * @Title: 消费日志记录 
     * @author jacky
     * @date 2018年10月19日 下午3:55:27
     * @param event
     */
    @EventListener(condition = "#event.msgs[0].topic=='" + IConfigmConstantMq.TOPIC_COMMON + "' && #event.msgs[0].tags=='" + IConfigmConstantMq.TAG_LOG + "'")
    public void consumerForLog(RocketmqEvent event){
        //      DefaultMQPushConsumer consumer = event.getConsumer();
        AccessLog accessLog = JSON.parseObject(event.getBody(0), AccessLog.class);
        try{
            accessLog.setId(UuidUtil.newGeneratId());
            //            accessLog.setParamNamesView(Arrays.toString(accessLog.getParamNames()));
            //            accessLog.setParamValuesView(JSON.toJSONString(accessLog.getParamValues()));
            //            accessLog.setReturnValueView(JSON.toJSONString(accessLog.getReturnValue()));
            accessLog.setCreated(Calendar.getInstance().getTime());

            accessLog.setParamValuesBytes(JSON.toJSONBytes(accessLog.getParamValues()));
            accessLog.setReturnValueBytes(JSON.toJSONBytes(accessLog.getReturnValue()));
            accessLog.setExceptionBytes(JSON.toJSONBytes(accessLog.getException()));
            //            if(accessLog.getParamValuesBytes().length > 512){
            //                accessLog.setParamValuesBytes(new byte[0]);
            //            }
            logger.info("请求记录：" + accessLog);
            if(accessLog != null){
                accessLoglist.add(accessLog);
                addDB();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private synchronized void addDB(){
        long cnt = logCnt.addAndGet(1);
        //        int batchAddLogNum = environment.getProperty("batchAddLogNum") == null ? 200 : Integer.parseInt(environment.getProperty("batchAddLogNum"));
        int batchAddLogNum = 2;
        try{
            if(cnt % batchAddLogNum == 0){//每次累计一定条数的记录时做一次入库操作
                logger.info("目前使用日志库进行处理");
                recordUserAccessLog.addBatchAccessLogs(accessLoglist);
                accessLoglist.clear();//清空日志重新累积
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Title: 消费常规系统消息 
     * @author jacky
     * @date 2018年10月18日 下午3:46:43
     * @param event
     */
    @EventListener(condition = "#event.msgs[0].topic=='" + IConfigmConstantMq.TOPIC_COMMON + "' && #event.msgs[0].tags=='" + IConfigmConstantMq.TAG_SYSTEM + "'")
    public void consumerForSys(RocketmqEvent event){
        //      DefaultMQPushConsumer consumer = event.getConsumer();
        try{
            //            logger.info("消费者监听到一个消息达到：" + event.getMsgs().get(0).getMsgId());
            // TODO 进行业务处理
            logger.info("当前进行系统消息的处理" + event.getMsg(0));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Title:  消费短信类型的消息
     * @author jacky
     * @date 2018年10月18日 下午3:45:55
     * @param event
     */
    @EventListener(condition = "#event.msgs[0].topic=='" + IConfigmConstantMq.TOPIC_COMMON + "' && #event.msgs[0].tags=='" + IConfigmConstantMq.TAG_SMS + "'")
    public void consumerForSMS(RocketmqEvent event){
        try{
            // TODO 进行业务处理
            logger.info("当前进行短信消息的处理" + event.getMsg(0));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @Title:  消费定时任务的消息
     * @author jacky
     * @date 2018年10月18日 下午3:45:55
     * @param event
     */
    @EventListener(condition = "#event.msgs[0].topic=='" + IConfigmConstantMq.TOPIC_COMMON + "' && #event.msgs[0].tags=='" + IConfigmConstantMq.TAG_TIME + "'")
    public void consumerForJob(RocketmqEvent event){
        try{
            // TODO 进行业务处理
            logger.info("当前进行定时任务的处理" + event.getMsg(0));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
