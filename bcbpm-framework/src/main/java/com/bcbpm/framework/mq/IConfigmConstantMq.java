/**
 * 
 */
package com.bcbpm.framework.mq;

/**<p>Title: IConfigmConstantMq</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年10月18日 下午5:36:26
 * @version :
 * @description: 消息队列的 topic tag等相关配置常量
 */
public interface IConfigmConstantMq{
    // 系统公共主题标识
    public final static String TOPIC_COMMON = "topic_common";
    // 消息类型tag: 系统消息
    public final static String TAG_SYSTEM = "tag_system";
    // 消息类型tag: 短信
    public final static String TAG_SMS = "tag_sms";
    // 消息类型tag: 定时任务消息
    public final static String TAG_TIME = "tag_time";
}
