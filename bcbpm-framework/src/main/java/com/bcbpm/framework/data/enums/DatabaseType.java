/**
 * 
 */
package com.bcbpm.framework.data.enums;

/**<p>Title: DatabaseType</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月14日 上午11:12:04
 * @version :
 * @description: 多数据源的类型设置
 */
public enum DatabaseType {
    // main为主库 back为从库，一些读操作走从库
    // statistic 为统计库，一般存储一些像日志之类为了统计的数据
    main , back , statistic;
}
