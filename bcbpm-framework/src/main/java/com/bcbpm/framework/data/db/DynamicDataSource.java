/**
 * 
 */
package com.bcbpm.framework.data.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**<p>Title: DynamicDataSource</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月14日 上午11:15:00
 * @version :
 * @description: 动态数据源使用DatabaseContextHolder获取当前线程的DatabaseType
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

    protected Object determineCurrentLookupKey(){
        return DatabaseContextHolder.getDatabaseType();
    }
}
