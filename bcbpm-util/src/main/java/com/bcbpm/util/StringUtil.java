package com.bcbpm.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

/**
 * @title :字符串处理工具类
 * @author: jacky
 * @date: 2015-08-02
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class StringUtil{
    private static final char[] zeroArray = "000000000000000000000".toCharArray();

    private static final char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 将字符串转换为java.sql.date类型,str的格式必须匹配给定的格式formatStr
     */
    public static java.sql.Date str2SqlDate(String str, String formatStr){
        java.sql.Date sqlDate = new java.sql.Date(0);// 默认获得当前时间
        try{
            sqlDate = new java.sql.Date(new java.text.SimpleDateFormat(formatStr).parse(str).getTime());
        }catch(ParseException e){
            e.printStackTrace();
        }
        return sqlDate;
    }

    /**
     * @description: 补充字符的方法,1、direction的取值为r(在原字符串右边补充)，l(在原字符串左边补充)
     * @param oldStr：原字符串
     * @param strLen：返回字符串长度
     * @param padChar：插入字符串
     * @param direction：插入方向
     * @return
     */
    public static String padString(String oldStr, int strLen, char padChar, char direction){
        String newStr = oldStr;
        try{
            if(oldStr.length() >= strLen){
                newStr = oldStr;
            }else{
                if(direction == 'r'){
                    while (newStr.length() < strLen){
                        newStr = newStr + padChar;
                    }
                }else{
                    while (newStr.length() < strLen){
                        newStr = padChar + newStr;
                    }
                }
            }
            return newStr;
        }catch(Exception e){
            return oldStr;
        }
    }

    /** 
     * 提供字符串到Vector的转变 
     **/
    public static Vector Str2Vect(String tStr, String sStr){
        Vector vector = new Vector();
        StringTokenizer st = new StringTokenizer(tStr, sStr);
        while (st.hasMoreTokens()){
            vector.add(st.nextToken());
        }
        return vector;
    }

    /** 
     * 提供Vector到字符串的转变，转变后的字符串以sStr作为分割符 
     * */
    public static String Vect2Str(Vector tVect, String sStr){
        String reStr = "";
        if(tVect.size() > 0)
            reStr = (String) tVect.get(0);
        for(int i = 1; i < tVect.size(); i++){
            reStr += sStr + (String) tVect.get(i);
        }
        return reStr;
    }

    /** 
     * 将字符串转换成Utf-8编码格式 
     **/
    public static String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c >= 0 && c <= 255){
                sb.append(c);
            }else{
                byte[] b;
                try{
                    b = Character.toString(c).getBytes("utf-8");
                }catch(Exception ex){
                    b = new byte[0];
                }
                for(int j = 0; j < b.length; j++){
                    int k = b[j];
                    if(k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /** 
     * 字符串数组到字符串的转变，转变后的字符串没有分割符 
     **/
    public static String Strs2Str(String[] tStrs){
        String reStr = "";
        int len = tStrs.length;
        for(int i = 0; i < len; i++){
            if(tStrs[i] != null){
                if(tStrs[i].length() > 0)
                    reStr += tStrs[i];
            }
        }
        return reStr;
    }

    /**
     * 根据日期获得日期(不含秒的任何情况),严格要求日期正确性,格式:yyyy-MM-dd HH:mm
     * @param sourceDate
     * @return
     */
    public static Date getDate(String sourceDate) throws DataFormatException{
        if(sourceDate == null){
            throw new DataFormatException("源数据为null");
        }
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormat.setLenient(false);
            return dateFormat.parse(sourceDate);
        }catch(Exception e){
            throw new DataFormatException("源数据格式错误");
        }
    }

    /**
     * 不够位数的在前面补0
     * 
     * @param string
     * @param length
     * @return
     */
    public static final String zeroPadString(String string, int length){
        if(string == null || string.length() > length){
            return string;
        }
        StringBuffer buf = new StringBuffer(length);
        buf.append(zeroArray, 0, length - string.length()).append(string);
        return buf.toString();
    }

    /**
     * 抓取字符串中数据
     * @param data 数据
     * @param flag 标识
     * @return 
     */
    public static String watchData(String data, String flag){
        //<flag>(.{1,})</flag>
        StringBuilder sb = new StringBuilder();
        sb.append("(?s)<");
        sb.append(flag);
        sb.append(">(.{1,})</");
        sb.append(flag);
        sb.append(">");
        Matcher m = Pattern.compile(sb.toString()).matcher(data);
        if(m.find()){
            return m.group(1);
        }
        return null;
    }

    /**
     * 生成校验位
     * @param data
     * @return byte
     */
    public static byte getCrcCode(byte[] data){
        byte crc = 0;

        for(int i = 0; i < data.length; i++){

            crc = (byte) ((crc + data[i]) % 256);
        }
        return crc;
    }

    /**
     * @description: 后台操作员密码加密
     * @param password
     * @return：
     */
    public static String encodePassword(String password){
        String s = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte tmp[] = md.digest();
            int j = tmp.length;
            char str[] = new char[j * 2];
            int k = 0;
            for(int i = 0; i < j; i++){
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        }catch(Exception e){
            return password;
        }
        return s;
    }

    /**
     * @description: 对传入的字符串按照指定的长度分割
     * @param: String source 被分割对象,int len 分割长度
     * @return：String[]
     */
    public static String[] splitByLen(String source, int len){
        if(source == null){
            return null;
        }
        if(len < 1){
            return new String[] { source };
        }
        int slen = source.length();// 字符串长度
        int aryLen = slen % len == 0 ? slen / len : slen / len + 1;// 分割后的数组长度
        String[] strAry = new String[aryLen];
        for(int i = 0; i < strAry.length; i++){
            if(slen % len != 0 && i == strAry.length - 1)
                strAry[i] = source.substring(i * len, i * len + slen % len);
            else
                strAry[i] = source.substring(i * len, (i + 1) * len);
        }
        return strAry;
    }

    /**
     * @title:对订单金额进行票据上金额书写格式处理
     * @description:例如：1.02 --> 00000000102（十二位 ....万千百拾元角分）
     * */
    public static String money2String(double money){
        String[] amounts = (String.format("%.2f", money)).split("\\.");
        String amount = "";
        for(String a : amounts){
            amount += a;
        }
        return String.format("%012d", Long.valueOf(amount));
    }

    /**
     * @title: 对订单金额进行票据上金额书写格式处理
     * @description: 例如：000000000102（十二位 ....万千百拾元角分） -->1.02 
     * */
    public static String string2Money(String money){
        if(money.matches("[0-9]{12,}")){
            money = money.substring(0, money.length() - 2) + "." + money.substring(money.length() - 2);

            return money.replaceAll("^0+(?!$)", "");
        }else{
            return null;
        }
    }

    /**
     * @description: 钱数转为中文
     * @param double d
     */
    public static String moneyToRMB(double d){
        String[] digit = { "无", "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟" };
        String[] capi = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        DecimalFormat df = new DecimalFormat("###0.00");
        char[] c = df.format(d).toCharArray();
        StringBuffer buf = new StringBuffer();
        String reStr = null;
        int cLength = c.length;

        for(int i = 0; i < c.length; i++){
            String s = "" + c[i];
            if(s.equals(".")){
                cLength++;
                continue;
            }

            int cp = Integer.parseInt(s);
            buf.append(capi[cp]);
            buf.append(digit[cLength - i - 1]);
        }

        reStr = buf.toString();
        for(int j = 0; j < 2; j++){
            reStr = reStr.replaceAll("零零", "零"); // replaceAll:jdk1.4才有
            reStr = reStr.replaceAll("零亿", "亿");
            reStr = reStr.replaceAll("零万", "万");
            reStr = reStr.replaceAll("零仟", "零");
            reStr = reStr.replaceAll("零佰", "零");
            reStr = reStr.replaceAll("零拾", "零");
            reStr = reStr.replaceAll("零角", "零");
            reStr = reStr.replaceAll("亿万", "亿零");
            reStr = reStr.replaceAll("零分", "元整");
            reStr = reStr.replaceAll("零元", "元");
            reStr = reStr.replaceAll("零零", "零");
            reStr = reStr.replaceAll("角元整", "角整");
            reStr = reStr.replaceAll("元元", "元");
        }
        return reStr;
    }

    /**
     * @description: #,##0.00               比如1234.1578格式后为1234.16 
     *  比如1234.1格式后为1234.10 
     *  #,##00.00             比如4.1578格式后为04.16 
     *  0表示该位有值则用该值表示，如果没有值则用0表示， 
     *  ＃表示0表示该位有值则用该值表示，如果没有值则不表示， 
     * @param dd
     * @return：
     */
    public static double numberTodouble(double dd){
        DecimalFormat df = new DecimalFormat("#,##0.00 ");
        String str = df.format(dd);
        Number sss = 0.00;
        try{
            sss = df.parse(str);
        }catch(ParseException e){
            //            throw new Exception("数据转换异常");
        }
        return sss.doubleValue();
    }

    /**
     * 
     * @description: 让double在数字大时不使用科学计数法
     * @param dd
     * @return：
     */
    public static String numFormat(double dd){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(dd);
    }

    /**
     * 根据数据中的标志位替换参数
     * "?：?缴费?元。"
     * */
    public static String replace(String str, String flag, String[] replaceAry){
        if(str != null && replaceAry != null){
            for(int i = 0; i < replaceAry.length; i++){
                str = str.replaceFirst("\\" + flag, replaceAry[i]);
            }
        }
        return str;
    }

    /**
     * 串行化
     * 将list中的元素以指定的标志分割
     * */
    public static String join(List list, String flag){
        String res = "";
        if(list != null){
            StringBuilder sb = new StringBuilder();
            int len = list.size();
            for(int i = 0; i < len; i++){
                sb.append(flag);
                sb.append(list.get(i));
            }
            res = sb.replace(0, 1, "").toString();
        }
        return res;
    }

    /**
     * @title : 从给定的字符串中根据标签名获得标签的值
     * @param labelName
     * @param xmlPara
     * @return
     */
    public static String getLabelValueByName(String labelName, String xmlPara){
        if(labelName == null || xmlPara == null){
            return "";
        }
        String prefix = "<" + labelName + ">";
        String suffix = "</" + labelName + ">";
        int startIndex = xmlPara.indexOf(prefix);
        int endIndex = xmlPara.lastIndexOf(suffix);
        if(startIndex < 0 || endIndex < 0 || startIndex == endIndex){
            return "";
        }else{
            return xmlPara.substring(startIndex + prefix.length(), endIndex);
        }
    }

    /**
     * @description：替换传入字符串中的空格、回车符、换行符、Tab符号
     * @param str
     * @return
     */
    public static String replace(String str){
        if(str != null){
            return str.trim().replaceAll("\n|\b|\r|\t|  |   |    ", "").replace("> <", "><");
        }else{
            return null;
        }

    }

    /**
     * @description: 判断字符串是否为空或空串
     * @param str
     * @return：
     */
    public static boolean stringIsEmpty(String str){
        if(str == null || "".equals(str) || str.trim().length() < 1){
            return true;
        }
        return false;
    }

    /**
     * @title：实现null值转为""
     * @description:实现null值转为""
     * @param srcStr：源字符串
     * @author: jacky
     * @date 2013-07-29
     * @return String 处理后的字符串
     */
    public static String trimNull(Object srcStr){
        String result = "";
        if(!(null == srcStr || srcStr.toString().equalsIgnoreCase("null"))){
            result = srcStr.toString();
        }
        return result;
    }

    /**
     * @description: 二进制转字符串
     * @param b
     * @return：
     */
    public static String byte2hex(byte[] b){ // 二进制转字符串
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for(int n = 0; n < b.length; n++){
            stmp = Integer.toHexString(b[n] & 0XFF);
            if(stmp.length() == 1){
                sb.append("0" + stmp);
            }else{
                sb.append(stmp);
            }

        }
        return sb.toString();
    }

    /**
     * @description: 字符串转二进制
     * @param 字符串
     * @return：byte数组
     */
    public static byte[] hex2byte(String str){ // 字符串转二进制
        if(str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if(len == 0 || len % 2 == 1)
            return null;
        byte[] b = new byte[len / 2];
        try{
            for(int i = 0; i < str.length(); i += 2){
                b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
            }
            return b;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * @description: 16进制逆向转换成字符串
     * @param s
     * @throws Exception：
     */
    public static String hexToString(String s) throws Exception{
        byte[] b = new byte[s.length() / 2];
        for(int i = 0, j = 0; i < s.length(); i++, i++, j++){
            byte b1 = Integer.valueOf(s.charAt(i) + "", 16).byteValue();
            byte b2 = Integer.valueOf(s.charAt(i + 1) + "", 16).byteValue();
            b[j] = (byte) ((b1 << 4) + b2);
        }
        return new String(b);
    }

    /**
     * @description:按位截取字符串
     * @param 字符串 String str
     * @param 位的长度 int bitLength
     * @return：String
     */
    public static String splitByBit(String str, int bitLength){
        String ret = "";
        int i = 0;
        for(int temp = 0; temp < bitLength;){
            char ch = str.charAt(i);
            byte[] by = String.valueOf(ch).getBytes();
            if(by.length > 1){
                temp = temp + 2;
            }else{
                temp++;
            }
            if(temp > bitLength){// 位大于截取长度-1
                break;
            }
            i++;
            if(i >= str.length()){
                break;
            }
        }
        ret = str.substring(0, i);
        return ret;
    }

    /**
     * @title：实现字符任意分割
     * @description:实现字符任意分割
     * @param srcStr:分割字符串 writchie.splite.test
     * @param pattern：分割符 ，如，#  _等
     * @param indexOrLast: 取分割符的第一个还是最后一个，index取indexOf,否则取lastIndexOf
     * @param frontOrBack: 取分割符前部分还 是后部分：front取分割符前部分，否则取分割符后部分
     * @author: jacky
     * @date: 2013-08-06 
     * @return String 分割完成的结果字符串
     */
    public static String spliteString(String srcStr, String pattern, String indexOrLast, String frontOrBack){
        String result = "";
        int loc = -1;
        if(indexOrLast.equalsIgnoreCase("index")){
            loc = srcStr.indexOf(pattern);
        }else{
            loc = srcStr.lastIndexOf(pattern);
        }
        if(frontOrBack.equalsIgnoreCase("front")){
            if(loc != -1)
                result = srcStr.substring(0, loc);
        }else{
            if(loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length());
        }
        return result;
    }

    /**
     * @description: 获取字符串的位数
     * @param String str
     * @return：int
     */
    public static int getBitLength(String str){
        int ret = 0;
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            byte[] by = String.valueOf(ch).getBytes();
            if(by.length > 1){
                ret = ret + 2;
            }else{
                ret++;
            }
        }
        return ret;
    }

    /**
     * @description: 获取文件类型
     * @param String filePath
     * @return：String fileType
     */
    public static String getFileType(String filePath){
        return filePath.replaceAll("^.*\\.", "");// 文件后缀名
        //replaceAll("^.*\\.(.*)$", "$1");//js|css
    }

    /**
     * @description: 获取文件类型
     * @param String filePath
     * @return：String fileType
     */
    public static String getFileType(File file){
        return file.getName().replaceAll("^.*\\.", "");// 文件后缀名
    }

    /**  
    * 从ip的字符串形式得到字节数组形式  
    * @param ip 字符串形式的ip  
    * @return 字节数组形式的ip  
    */
    public static byte[] getIpByteArrayFromString(String ip){
        byte[] ret = new byte[4];
        java.util.StringTokenizer st = new java.util.StringTokenizer(ip, ".");
        try{
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        }catch(Exception e){
        }
        return ret;
    }

    public static void main(String args[]){
        //        byte[] a=getIpByteArrayFromString("58.20.297.50");   
        //         for(int i=0;i< a.length;i++)   
        //              logger.info(a[i]);
        //             logger.info(getIpStringFromBytes(a)) ;
    }

    /**  
     * 对原始字符串进行编码转换，如果失败，返回原始的字符串  
     * @param s 原始字符串  
     * @param srcEncoding 源编码方式  
     * @param destEncoding 目标编码方式  
     * @return 转换编码后的字符串，失败返回原始字符串  
     */
    public static String getString(String s, String srcEncoding, String destEncoding){
        try{
            return new String(s.getBytes(srcEncoding), destEncoding);
        }catch(UnsupportedEncodingException e){
            return s;
        }
    }

    /**  
     * 根据某种编码方式将字节数组转换成字符串  
     * @param b 字节数组  
     * @param encoding 编码方式  
     * @return 如果encoding不支持，返回一个缺省编码的字符串  
     */
    public static String getString(byte[] b, String encoding){
        try{
            return new String(b, encoding);
        }catch(UnsupportedEncodingException e){
            return new String(b);
        }
    }

    /**  
     * 根据某种编码方式将字节数组转换成字符串  
     * @param b 字节数组  
     * @param offset 要转换的起始位置  
     * @param len 要转换的长度  
     * @param encoding 编码方式  
     * @return 如果encoding不支持，返回一个缺省编码的字符串  
     */
    public static String getString(byte[] b, int offset, int len, String encoding){
        try{
            return new String(b, offset, len, encoding);
        }catch(UnsupportedEncodingException e){
            return new String(b, offset, len);
        }
    }

    /**  
     * @param ip ip的字节数组形式  
     * @return 字符串形式的ip  
     */
    public static String getIpStringFromBytes(byte[] ip){
        StringBuffer sb = new StringBuffer();
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

    /**
     * @description：拼接返回结果字符串
     * @param buffer
     * @param key：标签名称
     * @param value：标签中间值
     * @return
     */
    @SuppressWarnings("finally")
    public static StringBuffer resultStrPatch(StringBuffer buffer, String key, Object value){
        key = key == null ? "" : key;
        try{
            buffer.append("<").append(key).append(">");
            if(value != null){
                buffer.append(value);
            }else{
                buffer.append("");
            }
            buffer.append("</").append(key).append(">");
        }catch(Exception e){
        }finally{
            return buffer;
        }
    }

    /**
     * 清空XML节点信息
     * @param value
     * @return
     */
    public static String dataReset(String str, String value){
        String value1 = "<" + value + ">";
        String value2 = "</" + value + ">";
        while (true){
            if(str.indexOf(value1) != -1 && str.indexOf(value2) != -1){
                str = str.replaceAll(str.substring((str.indexOf(value1)), str.indexOf(value2) + value2.length()), "");
            }else{
                break;
            }
        }
        return str;
    }

    // 静态公用方法，判断参数是否为null或""
    public static boolean isNullOrEmpty(Object param){
        if(param == null || "".equals(param)){
            return true;
        }else{
            return false;
        }
    }

    // 静态公用方法，判断参数是否为null或""
    public static boolean isNullOrEmpty(String param){
        if(param == null || "".equals(param)){
            return true;
        }else{
            return false;
        }
    }

    // 将null值转空字符串
    public static String dealNullOrEmpty(Object param){
        if(param == null){
            return "";
        }else{
            return param.toString();
        }
    }

    public static String padhead(String str, String pad, int len){
        if(pad.length() == 0){
            return str;
        }
        int plen = len - str.length();
        StringBuffer buffer = new StringBuffer();
        while (buffer.length() < plen){
            buffer.append(pad);
        }
        return buffer.append(str).toString();
    }

    public static String padtail(String str, String pad, int len){
        if(pad.length() == 0){
            return str;
        }
        StringBuffer buffer = new StringBuffer(str);
        while (buffer.length() < len){
            buffer.append(pad);
        }
        return buffer.toString();
    }

    public static String objToString(Object o){
        return StringUtil.replace(String.valueOf(o));
    }
}
