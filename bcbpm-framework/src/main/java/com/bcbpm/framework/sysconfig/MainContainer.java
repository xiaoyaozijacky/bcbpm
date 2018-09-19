package com.bcbpm.framework.sysconfig;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p>Title: 服务容器组件</p>
* <p>Company: bcbpm</p> 
 * @author jacky
 * @date 2018年9月13日 下午7:19:49
 * @version :
 * @description:
 */
public final class MainContainer{
    private static MainContainer instance = null;

    //	访问计数器
    //    private static final String SYS_VISIT_CNT = "SYS_VISIT_CNT";
    // 前端功能对应映射关系 key是command  value 第一个参数是类名 第二个参数是method
    public static Map<String, Object[]> functionMap = new HashMap<String, Object[]>();
    // 后端客户系统页面访问用户  key是页面url  value为可以使用的手机号码
    public static Map<String, Object[]> systemPageMap = new HashMap<String, Object[]>();
    // 处理类对应映射关系 key是类名 value 第一个参数是class 第二个参数是instance
    public static Map<String, Object[]> classMap = new HashMap<String, Object[]>();
    // 消息管理容器(处理未读消息)key1:value1(key2:value2)   key1用户编码     key2:消息类型 value2:具体数量
    // isNotRead_need_msg   需求中心
    // isNotRead_need_dyn    需求动态

    // isNotRead_project_msg        项目中心
    // isNotRead_project_project    我的项目
    // isNotRead_project_task        我的任务
    // isNotRead_project_shenpi    项目审批
    // isNotRead_project_dyn        项目动态

    //	public static Map<String,Map<String,Integer>> messageMap = new ConcurrentHashMap<String, Map<String,Integer>>();

    // 前端页面不需要登录的map
    public static Map<String, String> pageMap = new HashMap<String, String>();
    private static String superpassword = "superpassword112233";//前台超级密码
    private static String smsSwitch = "on";//前台短信网关
    private static String ylykver = "1.0";//前台系统版本，影响静态资源文件的缓存获取
    private static String isUseCache = "off";//控制是否使用缓存：针对组织机构缓存和人员-角色，角色-资源的缓存。

    private static String contextRootPath = "";//根目录地址
    private static String jsAccessPath = "build";//js接入路径
    private static String logMobile = null;//手机号，输入手机号锁定当前需要监控的人员日志

    private MainContainer(){
    }

    public synchronized static MainContainer getInstance(){
        if(instance == null){
            instance = new MainContainer();
        }
        return instance;
    }

    //    /**
    //     *  增加访问次数
    //     * */
    //    public  void addWebCount(){
    //        RedisManager.getInstance().getRedisService().incr(SYS_VISIT_CNT);
    //    }
    //
    //    /**
    //     *  获得当前系统访问次数
    //     * */
    //    public  long fecthWebCount(){
    //        String key = RedisManager.getInstance().getRedisService().get(SYS_VISIT_CNT);
    //        return Long.parseLong(key != null && !key.equals("") ? key : "0");
    //    }

    public void putFunctionMap(String key, Object[] value){
        functionMap.put(key, value);
    }

    public void putSystemPageMap(String key, Object[] value){
        systemPageMap.put(key, value);
    }

    public void putPageMap(String key, String value){
        pageMap.put(key, value);
    }

    /**
     * 设置根目录地址
     * */
    public synchronized void refreshContextRootPath(String param){
        contextRootPath = param;
    }

    /**
     * 刷新前台超级用户登录密码
     * */
    public synchronized void refreshSuperpassword(String newPwd){
        superpassword = newPwd;
    }

    /**
     * 刷新短信网关设置 on 开  off 关
     * */
    public synchronized void refreshSmsSwitch(String newSmsSwitch){
        smsSwitch = newSmsSwitch;
    }

    /**
     * 刷新缓存使用开关 on 使用  off 走DB 针对组织机构缓存和人员-角色，角色-资源的缓存。
     * */
    public synchronized void refreshIsUseCache(String v){
        isUseCache = v;
    }

    /**
     * 刷新需要监控的日志手机号
     * */
    public synchronized void refreshLogMobileSwitch(String v){
        logMobile = v;
    }

    /**
     * 设置版本号
     * */
    public synchronized void refreshFrontPageVersion(String ver){
        ylykver = ver;
    }

    /**
     *  获取静态资源版本号
     * */
    public String getYlykver(){
        return ylykver;
    }

    /**
     * js访问路径
     * */
    public synchronized void refreshJsAccessPath(String jsAccessPath){
        MainContainer.jsAccessPath = jsAccessPath;
    }

    public String getJsAccessPath(){
        return jsAccessPath;
    }

    /**
     *  获取前台超级用户登录密码
     * */
    public String getSuperpassword(){
        return superpassword;
    }

    /**
     *  获取短信网关设置 on 开  off 关
     * */
    public String getSmsSwitch(){
        return smsSwitch;
    }

    /**
     *  获取是否使用缓存  true使用  false 不使用
     * */
    public Boolean getIsUseCache(){
        return isUseCache != null && isUseCache.equals("on") ? true : false;
    }

    /**
     *  获取当前设置的具体手机号
     * */
    public String getLogMobile(){
        return logMobile;
    }

    /**
     *  获取根目录地址
     * */
    public String getContextRootPath(){
        return contextRootPath;
    }

    /**
     * 更新当前消息类型对应的数值
     * 
     * @param userId 用户id
     * @param messageType 消息类型
     * @param command 刷新命令 0：清空数据  1：新增数据
     * */
    public void refreshMessage(String userId, String messageType, int command){
        //        Map<String, Integer> inner = null;
        //        Integer cnt = 0;
        //        if(existsUserIdMessage(userId)){
        //            inner = (HashMap<String, Integer>) RedisManager.getInstance().getRedisService().getObject(userId);
        //            if(inner != null && inner.containsKey(messageType)){
        //                cnt = inner.get(messageType);
        //            }else{
        //                inner = initMsgMap();
        //            }
        //        }else{
        //            inner = initMsgMap();
        //        }
        //        inner.put(messageType, command == 0 ? 0 : cnt + 1);
        //        RedisManager.getInstance().getRedisService().setObject(userId, inner);//刷新缓存
    }

    //    private Map<String, Integer> initMsgMap(){
    //        Map<String, Integer> inner;
    //        inner = new HashMap<String, Integer>();
    //        inner.put("isNotRead_need_msg", 0);
    //        inner.put("isNotRead_need_dyn", 0);
    //        inner.put("isNotRead_project_msg", 0);
    //        inner.put("isNotRead_project_project", 0);
    //        inner.put("isNotRead_project_task", 0);
    //        inner.put("isNotRead_project_shenpi", 0);
    //        inner.put("isNotRead_project_dyn", 0);
    //        return inner;
    //    }

    /**
     * 更新当前消息类型对应的数值
     * 
     * @param userId 用户id
     * @param map 所有消息类型数据
     * */
    public void refreshMessageMap(String userId, Map<String, Integer> map){
        //        RedisManager.getInstance().getRedisService().setObject(userId, map);
    }

    /**
     * 获取当前消息类型对应的数值
     * 
     * @param userId 用户id
     * @param messageType 消息类型
     * @return cnt 对应数值
     * */
    public Integer fetchMessageCnt(String userId, String messageType){
        //        if(existsUserIdMessage(userId)){
        //            Map<String, Integer> obj = (HashMap<String, Integer>) RedisManager.getInstance().getRedisService().getObject(userId);
        //            return obj.get(messageType);
        //        }else{
        return null;
        //        }
    }

    /**
     * 判断当前用户是否有缓存数据
     * */
    public Boolean existsUserIdMessage(String userId){
        //        return RedisManager.getInstance().getRedisService().exists(userId);
        return true;
    }

    /**
     * 获取当前消息所有类型消息
     * 
     * @param userId 用户名称
     * @return map 所有类型消息
     * */
    public Map<String, Integer> fetchMessageMap(String userId){
        //        Map<String, Integer> obj = null;
        //        try{
        //            obj = (HashMap<String, Integer>) RedisManager.getInstance().getRedisService().getObject(userId);
        //        }catch(Exception e){
        //            e.printStackTrace();
        //            BaseLog.e(getClass(), "从redis里取未读消息数据出错,userid为:" + userId);
        //        }
        //        return obj;
        return null;
    }

    /**
     * 是否第一次加载消息
     * */
    public boolean isFirstMessage(String userId, String messageType){
        //        if(existsUserIdMessage(userId) && ((HashMap<String, Integer>) RedisManager.getInstance().getRedisService().getObject(userId)).containsKey(messageType)){
        //            return false;
        //        }else{
        return true;
        //        }
    }

    /** 
     * 从包package中获取所有的Class 
     *  
     * @param pack 
     * @return 
     */
    public synchronized static Set<Class<?>> getClasses(String pack){
        // 第一个class类的集合  
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代  
        boolean recursive = true;
        // 获取包的名字 并进行替换  
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things  
        Enumeration<URL> dirs;
        try{
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去  
            while (dirs.hasMoreElements()){
                // 获取下一个元素  
                URL url = dirs.nextElement();
                // 得到协议的名称  
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上  
                if("file".equals(protocol)){
                    //                    System.err.println("file类型的扫描");  
                    // 获取包的物理路径  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中  
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }else if("jar".equals(protocol)){
                    // 如果是jar包文件  
                    // 定义一个JarFile  
                    //                    System.err.println("jar类型的扫描");  
                    JarFile jar;
                    try{
                        // 获取jar  
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类  
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代  
                        while (entries.hasMoreElements()){
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的  
                            if(name.charAt(0) == '/'){
                                // 获取后面的字符串  
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同  
                            if(name.startsWith(packageDirName)){
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包  
                                if(idx != -1){
                                    // 获取包名 把"/"替换成"."  
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包  
                                if((idx != -1) || recursive){
                                    // 如果是一个.class文件 而且不是目录  
                                    if(name.endsWith(".class") && !entry.isDirectory()){
                                        // 去掉后面的".class" 获取真正的类名  
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try{
                                            // 添加到classes  
                                            Class<?> clz = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                                            if(!Modifier.isAbstract(clz.getModifiers())){
                                                Object o = clz.newInstance();
                                                classes.add(clz);
                                                Object[] objs = new Object[2];
                                                objs[0] = clz;
                                                objs[1] = o;
                                                classMap.put(className, objs);
                                            }
                                        }catch(Exception e){
                                            // log  .error("添加用户自定义视图类错误 找不到此类的.class文件");  
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }catch(IOException e){
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");  
                        e.printStackTrace();
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return classes;
    }

    /** 
     * 以文件的形式来获取包下的所有Class 
     *  
     * @param packageName 
     * @param packagePath 
     * @param recursive 
     * @param classes 
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes){
        // 获取此包的目录 建立一个File  
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回  
        if(!dir.exists() || !dir.isDirectory()){
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");  
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录  
        File[] dirfiles = dir.listFiles(new FileFilter(){
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
            public boolean accept(File file){
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件  
        for(File file : dirfiles){
            // 如果是目录 则继续扫描  
            if(file.isDirectory()){
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            }else{
                // 如果是java类文件 去掉后面的.class 只留下类名  
                String className = file.getName().substring(0, file.getName().length() - 6);
                try{
                    // 添加到集合中去 ,这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净  
                    //classes.add(Class.forName(packageName + '.' + className));  
                    //logger.info(packageName + '.' + className);
                    Class<?> clz = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                    if(!Modifier.isAbstract(clz.getModifiers())){
                        Object o = clz.newInstance();
                        classes.add(clz);
                        Object[] objs = new Object[2];
                        objs[0] = clz;
                        objs[1] = o;
                        classMap.put(className, objs);
                        classes.add(clz);
                    }
                }catch(Exception e){
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");  
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        //    	MainContainer.getClasses("maindeal.know.table");
        //    	classMap.get("maindeal.know.table.front");
        //    	for(int i=0;i<1000;i++){
        //    		MainContainer.addWebCount();
        //    	}
        //	logger.info(MainContainer.fecthWebCount());
    }
}
