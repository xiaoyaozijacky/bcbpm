/**
 * 
 */
package com.bcbpm.framework.data.db;

import com.bcbpm.framework.data.enums.DatabaseType;

/**<p>Title: DatabaseContextHolder</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月14日 上午11:13:34
 * @version :
 * @description: 在切换多数据源设置的时候用ThreadLocal来保证是线程安全的
 */
public class DatabaseContextHolder{
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type){
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType(){
        DatabaseType dt = contextHolder.get();
        return dt;
    }
}
