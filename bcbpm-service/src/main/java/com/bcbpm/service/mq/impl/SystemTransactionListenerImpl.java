/**
 * 
 */
package com.bcbpm.service.mq.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bcbpm.framework.data.access.BusinessException;
import com.bcbpm.service.mq.IBaseTransactionListener;

/**<p>Title: 系统消息处理监听器</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月18日 下午2:12:46
 * @version :
 * @description:
 */
@Service("systemTransactionListener")
public class SystemTransactionListenerImpl implements IBaseTransactionListener{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg){
        //        int value = transactionIndex.getAndIncrement();
        int status = 0;
        try{
            // TODO 执行本地事务，改变value的值
            logger.info("执行本地事务。。。完成");
            if(arg instanceof Integer){
                status = (Integer) arg;
            }
            localTrans.put(msg.getTransactionId(), status);
            if(status == 0){
                return LocalTransactionState.UNKNOW;
            }else if(status == 1){
                return LocalTransactionState.COMMIT_MESSAGE;
            }else if(status == 2){
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }else if(status == 3){
                throw new BusinessException("Could not find db");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg){
        Integer status = localTrans.get(msg.getTransactionId());
        logger.info("checkLocalTransaction, transactionId :" + msg.getTransactionId() + "," + "status:" + status);
        if(null != status){
            switch(status){
            case 0:
                return LocalTransactionState.UNKNOW;
            case 1:
                return LocalTransactionState.COMMIT_MESSAGE;
            case 2:
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
