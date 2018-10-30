package com.bcbpm.framework.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**<p>Title: 定位用户日志过滤</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午6:19:21
 * @version :
 * @description: 根据设定的用户，定位用户单独输出的日志目录
 */
public class PositionUserLogFilter extends Filter<ILoggingEvent>{
    //    @Autowired
    //    HttpServletRequest request;

    @Override
    public FilterReply decide(ILoggingEvent event){
        //        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //        Map user = (Map) SessionDeal.getNowUserFront(request);
        //        String mobile = user.get("mobile");
        //        String positionUserMobile = MainContainer.getInstance().getLogMobile();
        //        if(positionUserMobile != null && mobile.equals(positionUserMobile)){
        return FilterReply.ACCEPT;
        //        }else{
        //            return FilterReply.DENY;
        //        }
    }
}