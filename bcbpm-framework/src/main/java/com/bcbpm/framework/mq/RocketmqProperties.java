package com.bcbpm.framework.mq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Title: RocketmqProperties</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月17日 下午6:20:38
 * @version :
 * @description:
 */
@ConfigurationProperties(RocketmqProperties.PREFIX)
public class RocketmqProperties{
    public static final String PREFIX = "bcbpm.rocketmq";
    private String namesrvAddr;//namesrvAddr地址
    private String producerGroupName;//生产者group名称
    private String transactionProducerGroupName;//事务生产者group名称
    private String consumerGroupName;//消费者group名称
    private String producerInstanceName;//生产者实例名称
    private String consumerInstanceName;//消费者实例名称
    private String producerTranInstanceName;//事务生产者实例名称
    private int consumerBatchMaxSize;//一次最大消费多少数量消息
    private boolean consumerBroadcasting;//是否广播消费
    private boolean enableHisConsumer;//启动的时候是否消费历史记录
    private boolean enableOrderConsumer;// 启动顺序消费
    private List<String> subscribe = new ArrayList<String>();//消费的topic：tag

    public String getNamesrvAddr(){
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr){
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroupName(){
        return producerGroupName;
    }

    public void setProducerGroupName(String producerGroupName){
        this.producerGroupName = producerGroupName;
    }

    public String getTransactionProducerGroupName(){
        return transactionProducerGroupName;
    }

    public void setTransactionProducerGroupName(String transactionProducerGroupName){
        this.transactionProducerGroupName = transactionProducerGroupName;
    }

    public String getConsumerGroupName(){
        return consumerGroupName;
    }

    public void setConsumerGroupName(String consumerGroupName){
        this.consumerGroupName = consumerGroupName;
    }

    public String getProducerInstanceName(){
        return producerInstanceName;
    }

    public void setProducerInstanceName(String producerInstanceName){
        this.producerInstanceName = producerInstanceName;
    }

    public String getConsumerInstanceName(){
        return consumerInstanceName;
    }

    public void setConsumerInstanceName(String consumerInstanceName){
        this.consumerInstanceName = consumerInstanceName;
    }

    public String getProducerTranInstanceName(){
        return producerTranInstanceName;
    }

    public void setProducerTranInstanceName(String producerTranInstanceName){
        this.producerTranInstanceName = producerTranInstanceName;
    }

    public int getConsumerBatchMaxSize(){
        return consumerBatchMaxSize;
    }

    public void setConsumerBatchMaxSize(int consumerBatchMaxSize){
        this.consumerBatchMaxSize = consumerBatchMaxSize;
    }

    public boolean isConsumerBroadcasting(){
        return consumerBroadcasting;
    }

    public void setConsumerBroadcasting(boolean consumerBroadcasting){
        this.consumerBroadcasting = consumerBroadcasting;
    }

    public boolean isEnableHisConsumer(){
        return enableHisConsumer;
    }

    public void setEnableHisConsumer(boolean enableHisConsumer){
        this.enableHisConsumer = enableHisConsumer;
    }

    public boolean isEnableOrderConsumer(){
        return enableOrderConsumer;
    }

    public void setEnableOrderConsumer(boolean enableOrderConsumer){
        this.enableOrderConsumer = enableOrderConsumer;
    }

    public List<String> getSubscribe(){
        return subscribe;
    }

    public void setSubscribe(List<String> subscribe){
        this.subscribe = subscribe;
    }
}
