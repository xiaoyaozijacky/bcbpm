/**
 * 
 */
package com.bcbpm.controller.mq;

import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bcbpm.framework.mq.IConfigmConstantMq;
import com.bcbpm.service.mq.IBaseTransactionListener;

/**<p>Title: MqProduceController</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月17日 下午6:58:23
 * @version :
 * @description: 消息队列生产者例子
 */
@RestController
public class MqProduceController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DefaultMQProducer defaultProducer;
    @Autowired
    private TransactionMQProducer transactionProducer;
    @Autowired
    private IBaseTransactionListener smsTransactionListener;
    @Autowired
    private IBaseTransactionListener systemTransactionListener;

    private int i = 0;

    @RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
    public void sendMsg(){
        String sendBody = "hello RocketMQ!" + i;
        try{
            // topic , tag , key , body 
            // 系统消息
            Message sys = new Message(IConfigmConstantMq.TOPIC_COMMON, IConfigmConstantMq.TAG_SYSTEM, "OrderID00" + i, sendBody.getBytes());
            defaultProducer.send(sys, new SendCallback(){
                @Override
                public void onSuccess(SendResult sendResult){
                    logger.info("[系统消息]：发送成功,正在进行回调处理,消息：" + sendResult);
                }

                @Override
                public void onException(Throwable e){
                    logger.info("[系统消息]：发送失败,正在进行回调处理,异常：" + e.getMessage());
                }
            });
            // 短信
            Message sms = new Message(IConfigmConstantMq.TOPIC_COMMON, IConfigmConstantMq.TAG_SMS, "OrderID00" + i, sendBody.getBytes());
            defaultProducer.send(sms, new SendCallback(){
                @Override
                public void onSuccess(SendResult sendResult){
                    logger.info("[短信消息]：发送成功,正在进行回调处理,消息：" + sendResult);
                }

                @Override
                public void onException(Throwable e){
                    logger.info("[短信消息]：发送失败,正在进行回调处理,异常：" + e.getMessage());
                }
            });

            // 定时任务
            Message job = new Message(IConfigmConstantMq.TOPIC_COMMON, IConfigmConstantMq.TAG_TIME, "OrderID00" + i, sendBody.getBytes());
            defaultProducer.send(job, new SendCallback(){
                @Override
                public void onSuccess(SendResult sendResult){
                    logger.info("[定时任务消息]：发送成功,正在进行回调处理,消息：" + sendResult);
                }

                @Override
                public void onException(Throwable e){
                    logger.info("[定时任务消息]：发送失败,正在进行回调处理,异常：" + e.getMessage());
                }
            });
            i++;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/sendTransactionMsg", method = RequestMethod.GET)
    public String sendTransactionMsg(){
        SendResult sendResult = null;
        try{
            transactionProducer.setTransactionListener(systemTransactionListener);//使用系统消息处理器
            Message msg = new Message(IConfigmConstantMq.TOPIC_COMMON, IConfigmConstantMq.TAG_SYSTEM, "OrderID001", ("Hello zebra mq").getBytes()); // topic , tag , key , body 
            sendResult = transactionProducer.sendMessageInTransaction(msg, 1);
            System.out.println(sendResult);
        }catch(Exception e){
            e.printStackTrace();
        }
        return sendResult.toString();
    }

    @RequestMapping(value = "/sendTransactionSms", method = RequestMethod.GET)
    public String sendTransactionSms(){
        SendResult sendResult = null;
        try{
            transactionProducer.setTransactionListener(smsTransactionListener);//使用短信消息处理器
            Message msg = new Message(IConfigmConstantMq.TOPIC_COMMON, IConfigmConstantMq.TAG_SMS, "OrderID001", ("Hello zebra mq").getBytes()); // topic , tag , key , body 
            sendResult = transactionProducer.sendMessageInTransaction(msg, 1);
            System.out.println(sendResult);
        }catch(Exception e){
            e.printStackTrace();
        }
        return sendResult.toString();
    }

    @RequestMapping(value = "/sendMsgOrder", method = RequestMethod.GET)
    public void sendMsgOrder(){
        try{
            Message msg = new Message(IConfigmConstantMq.TOPIC_COMMON, IConfigmConstantMq.TAG_SYSTEM, "OrderID00" + i, ("Hello zebra mq" + i).getBytes()); // topic , tag , key , body 
            defaultProducer.send(msg, new MessageQueueSelector(){
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg){
                    System.out.println("MessageQueue" + arg);
                    int index = ((Integer) arg) % mqs.size();
                    return mqs.get(index);
                }
            }, i);// i==arg
            //            defaultProducer.send(msg, (List<MessageQueue> mqs, Message msgs, Object arg) -> {
            //                System.out.println("MessageQueue" + arg);
            //                int index = ((Integer) arg) % mqs.size();
            //                return mqs.get(index);
            //            }, i);// i==arg
            i++;
        }catch(

        Exception e){
            e.printStackTrace();
        }
    }
}
