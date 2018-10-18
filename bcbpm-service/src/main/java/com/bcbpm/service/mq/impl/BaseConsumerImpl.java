package com.bcbpm.service.mq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.bcbpm.framework.mq.IConfigmConstantMq;
import com.bcbpm.framework.mq.RocketmqEvent;
import com.bcbpm.service.mq.IBaseConsumer;

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
