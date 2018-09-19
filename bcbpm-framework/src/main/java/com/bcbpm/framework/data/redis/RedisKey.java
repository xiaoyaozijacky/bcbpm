package com.bcbpm.framework.data.redis;

/**
 * redis的key值配置
 * */
public final class RedisKey{
    public static final String SMS_QUEUE = "S_Q_0419_PRO";//进驻redis队列的短信键值
    public static final String SMS_MAPPING_CODE = "SMS_MAPPING_CODE_S_Q_0703_PRO";//短信的映射配置
    public static final String SESSION_IP = "S_IP";//session里装的用户map信息
    public static final String SESSION_USER_MP = "S_U_M";//sessionid
    public static final String SESSION_FUNCTION_STATISTICS_MAP = "S_F_S_M";//访问功能时间统计MAP
    public static final String SESSION_FUNCTION_STATISTICS_QUEUE = "S_F_S_Q";//访问功能时间统计队列

    public static final String ORDER_QUEUE = "O_QS_Q_0419";//进驻redis队列的订单键值
    public static final String ORDER_SEQUENCE = "O_S";//订单缓存值
    public static final String ORDER_PAY_TIMEOUT_QUEUE = "O_P_T_Q";//订单超时支付队列

    //    public static final String USER_ROLE_MENU_PREFIX = "U_R_M";//用戶左侧菜单前缀
    //    public static final String USER_ROLE_MENU_SCROLLTOP = "U_R_M_S";//用戶左侧菜单定位
    //    public static final String USER_ROLE_HAVE_URL_PREFIX = "U_R_H_U";//用户拥有哪些url前缀
    public static final String USER_ROLE_PREFIX = "U_R_";
    public static final String USER_ROLE_EXPIRE = USER_ROLE_PREFIX + "E_0703";//当前角色是否过期，key:U_R_E前缀+rid+(若存在第二层时需要 + MID)     VALUE: 1(没过期)  0或null(过期)
    public static final String USER_ROLE_MENU = USER_ROLE_PREFIX + "M_0703";//角色对应功能菜单项  KEY: 第一层(前缀+rid:  第二层(前缀+rid+MID)                 VALUE:LIST<MenuBo>集合
    //    public static final String USER_ROLE_AUTHORITY = "USE_R_A";//进驻redis队列的角色键值
    public static final String USER_MOBILE_CODE = "USER_MOBILE_CODES_Q_0703_PRO_512";//短信验证码key

    public static final String FILE_PDFVIEW_KEY = "F_P_K";//进驻redis的已经经过pdf转换的文件key
    //分布式集群session，session id由tomcat生成，存取通过redis
    public static final String CLUSTER_JSESSIONID_FR = "-FR_SID_";//前台用户
    public static final String CLUSTER_JSESSIONID_BK = "-BK_SID";//后台用户

    // 登陆缓存用户username的后缀
    public static final String CLUSTER_JSESSIONID_FR_USERNAME = "USERNAME_SUFFIX";//缓存登陆时的用户名
    public static final String CLUSTER_JSESSIONID_FR_USERLOGIN = "USERLOGIN";//缓存登陆后的用户对象
    //缓存收支
    public static final String FINANCE_EXPEND_ACCOUNT = "FINANCE_EXPEND_ACCOUNT";//支出账户后缀
    public static final String FINANCE_EXPEND_ACCOUNT_NAME = "FINANCE_EXPEND_ACCOUNT_NAME";//支出账户名称

    private RedisKey(){
    }
}
