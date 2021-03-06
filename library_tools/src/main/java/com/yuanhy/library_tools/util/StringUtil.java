package com.yuanhy.library_tools.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.yuanhy.library_tools.app.AppFramentUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static String host;
    private static String face;

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";
    public static String delHTMLTag(String htmlStr) {

        htmlStr = htmlStr.replace("/<img.*\\/>/ig", "");
        htmlStr = htmlStr.replace("/<\\/?[^>]*>/g",""); //去除HTML tag
        htmlStr = htmlStr.replace("/[ | ]*\\n/g",""); //去除行尾空白
        htmlStr = htmlStr.replace("/\\n[\\s| | ]*\\r/g",""); //去除多余空行
        htmlStr=htmlStr.replace("/ /ig","");//去掉

        htmlStr=htmlStr.replace("&quot;","\"");//去掉
        htmlStr=htmlStr.replace("&amp;","&");//去掉
        htmlStr=htmlStr.replace("&gt;",">");//去掉
        htmlStr=htmlStr.replace("&lt;","<");//去掉
        htmlStr=htmlStr.replace("&nbsp;"," ");//去掉
        htmlStr=htmlStr.replace("&amp;","");//去掉
        htmlStr=htmlStr.replace("lt;","");//去掉
        htmlStr=htmlStr.replace("&p&amp;","");//去掉
        htmlStr=htmlStr.replace("gt;","");//去掉

        AppFramentUtil.logCatUtil.i("--------------","1:--->:"+htmlStr);
        // 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        AppFramentUtil.logCatUtil.i("--------------","2:--->:"+htmlStr.trim());
        return htmlStr.trim(); // 返回文本字符串
    }
    public static String setFontColor(String string,String  color){
        string = "<font color=\""+color+"\">" + string + "</font>";

        return string;
    }
    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否是手机号
     *
     * @param mobileNums
     * @return
     */
    public static boolean isPhone(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * 【全为英文】
     *
     * @param str
     * @return返回true 否则false
     */
    public static boolean isEnglish(String str) {
        return str.matches("[a-zA-Z]+");
    }

    /**
     * 【全为数字】
     *
     * @param str
     * @return返回true 否则false
     */
    public static boolean isNubm(String str) {
        return str.matches("[0-9]+");
    }

    /**
     * 【含有英文】
     *
     * @param str
     * @return返回true 否则false
     */
    public static boolean isContainEnglish(String str) {
        return str.matches(".*[a-zA-z].*");
    }

    /**
     * 【含有数字】
     *
     * @param str
     * @return返回true 否则false
     */
    public static boolean isContainNubm(String str) {
        return str.matches(".*[0-9].*");
    }

    /**
     * 是否包含汉字
     *
     * @param str
     * @return返回true 否则false
     */
    public static boolean isChina(String str) {
        boolean bl= false;
        for (int i = 0; i < str.length(); i++) {
            String  s=str.substring(i,i+1);
            if ( s .matches("[\\u4e00-\\u9fa5]+")){
                bl=true;
                break;
            }
        }
        return bl;
    }

    /**
     * 【除英文和数字外无其他字符(只有英文数字的字符串)】
     *
     * @param str
     * @return返回true 否则false
     */
    public static boolean isNubmAndEnglish(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 隐藏手机号
     *
     * @param phone
     * @return
     */
    public static String getHidePhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() < 11)
            return phone;
        String phones = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        return phones;
    }
    /**
     *隐藏姓名后的名字
     *
     * @param name
     * @return
     */
    public static String hideName(String name) {
        if (TextUtils.isEmpty(name) )
            return "";
        int nameLeth =name.length();
        if (nameLeth==1){
            return name;
        }
        String namehide=name.substring(0, 1);
        for (int i = 1; i <nameLeth ; i++) {
            namehide=namehide+"*";
        }
        return namehide;
    }
    /**
     * 隐藏字符串中的文字用*代替
     * @param stase 开始几位不隐藏
     * @param end 最后几位不隐藏
     * @param hideStr 字符串
     * @return
     */
    public static String hideStr(int stase,int end,String hideStr) {
        if (TextUtils.isEmpty(hideStr) )
            return "";
        int nameLeth =hideStr.length();
        if (nameLeth<= stase+end){
            return hideStr;
        }

        String namehide=hideStr.substring(0, stase);
        for (int i = stase; i <nameLeth-end ; i++) {
            namehide=namehide+"*";
        }
        namehide=namehide+hideStr.substring(nameLeth-end, nameLeth);
        return namehide;
    }

    /**
     * @param host
     * @param face
     * @return
     */
    public static String formatURL(String host, String face) {
        StringBuffer _buf = new StringBuffer();
        if (TextUtils.isEmpty(host) || TextUtils.isEmpty(face)) {
            return "";
        }
        if (host.endsWith("/") || face.startsWith("/")) {
            _buf.append(host).append(face);
        } else {
            _buf.append(host).append("/").append(face);
        }
        return _buf.toString();
    }


    public static String getMapKey() {
        return "IHozpctWChRmsza8Olm7GWR6";
    }

    public static String convert(String str) {
        String tmp;
        StringBuffer sb = new StringBuffer(1024);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c > 255) {
                sb.append("\\u");
                j = (c >>> 8);
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1)
                    sb.append("0");
                sb.append(tmp);
                j = (c & 0xFF);
                tmp = Integer.toHexString(j);
                if (tmp.length() == 1)
                    sb.append("0");
                sb.append(tmp);
            } else {
                sb.append(c);
            }
        }
        return (new String(sb));
    }

    /**
     * @param content
     */
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public static String getJidByName(String userName, String jidFor) {
        if (empty(jidFor) || empty(jidFor)) {
            return null;
        }
        return userName + "@" + jidFor;
    }

    public static boolean notEmpty(Object o) {
        return o != null && !"".equals(o.toString().trim())
                && !"null".equalsIgnoreCase(o.toString().trim())
                && !"undefined".equalsIgnoreCase(o.toString().trim());
    }

    public static boolean empty(Object o) {
        return o == null || "".equals(o.toString().trim())
                || "null".equalsIgnoreCase(o.toString().trim())
                || "undefined".equalsIgnoreCase(o.toString().trim());
    }

    public static boolean num(Object o) {
        int n = 0;
        try {
            n = Integer.parseInt(o.toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (n > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param str
     * @param defaultValue
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
        if (str == null || str.equalsIgnoreCase("null")
                || str.trim().equals("") || str.trim().equals("����ѡ��")) {
            str = defaultValue;
        } else if (str.startsWith("null")) {
            str = str.substring(4, str.length());
        }
        return str.trim();
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String setSoundViewWidth(int length) {
        if (length < 0) {
            return "\t\t\t\t\t\t\t\t\t";
        } else if (length < 1) {
            return "\t\t" + length + "s'";
        } else if (length < 2) {
            return "\t\t" + length + "s'";
        } else if (length < 3) {
            return "\t\t\t" + length + "s'";
        } else if (length < 5) {
            return "\t\t\t\t" + length + "s'";
        } else if (length < 7) {
            return "\t\t\t\t\t" + length + "s'";
        } else if (length < 10) {
            return "\t\t\t\t\t\t" + length + "s'";
        } else if (length < 13) {
            return "\t\t\t\t\t\t\t" + length + "s'";
        } else if (length < 16) {
            return "\t\t\t\t\t\t\t\t" + length + "s'";
        } else if (length < 18) {
            return "\t\t\t\t\t\t\t\t\t" + length + "s'";
        } else if (length < 20) {
            return "\t\t\t\t\t\t\t\t\t\t" + length + "s'";
        } else {
            return "\t\t\t\t\t\t\t\t\t\t\t" + length + "s'";
        }
    }


    public static String formatURL2(String host, String face, String name) {
        if (TextUtils.isEmpty(host) || TextUtils.isEmpty(face)) {
            return "";
        }
        StringBuffer _buf = new StringBuffer();
        String _http = formatURL(host, face);
        _buf.append(formatURL(_http, name));
        return _buf.toString();
    }

    public static String formatMinImage(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        int endIndex = url.lastIndexOf(".");
        StringBuffer _buf = new StringBuffer();
        _buf.append(url.substring(0, endIndex)).append("_s").append(url.substring(endIndex, url.length()));
        return _buf.toString();
    }

    public static String formatMaxImage(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String _newURL = url.replaceAll("_s.", ".");
        return _newURL;
    }

    /**
     * 检测字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        boolean result = false;
        if (str != null && !str.equals("") && !"null".equals(str)
                && str.trim().length() > 0) {
            result = true;
        }
        return result;
    }


    public static String trim(String str) {
        if (str != null) {
            str = str.trim().replaceAll("\\s", "");
        }
        return str;
    }


    public static String merge(String... args) {
        StringBuffer _buf = new StringBuffer();
        if (args != null) {
            for (String _s : args) {
                if (_buf.indexOf(_s) == -1) {
                    _buf.append(_s);
                }
            }
        }
        return _buf.toString();
    }


    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将换行符替换成空格
     * 由于数据经过处理不能直接使用
     * str.replaceAll("\\\n"," ");
     *
     * @param str
     * @return yhy
     */
    public static String getStringNoLins(String str) {
        str = str.replaceAll("\\\\", "");
        str = str.replaceAll("n", "\n");
        str = str.replaceAll("p", "");
        return str;
    }

    //==========================================================
    //==========================================================
    @SuppressWarnings("unused")
    public static boolean judgeHasChinese(String content) {
        int chinese = 0;
        int english = 0;
        boolean isChinese = false;

        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < content.length(); i++) {
            String retContent = content.substring(i, i + 1);
            // 生成一个Pattern,同时编译一个正则表达式
            boolean isChina = retContent.matches("[\u4E00-\u9FA5]");
            if (isChina) {
                chinese++;
            }

            boolean isCapital = retContent.matches("[A-Z]");
            if (isCapital) {
                english++;
            }
            boolean isNum = retContent.matches("[0-9]");
            if (isNum) {
                english++;
            }
        }
        if (chinese > 0) {
            isChinese = true;
        } else {
            isChinese = false;
        }

        return isChinese;
    }

    /**
     * @param e
     * @return
     */
    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }

    public static String toString(String... var) {
        StringBuffer _buf = new StringBuffer();
        if (var != null) {
            for (String _str : var) {
                _buf.append(_str);
            }
        }
        return _buf.toString();
    }


    //判断字符串是否为纯数字
    public static boolean isnum(String str) {
        boolean result = str.matches("[0-9]+");
        return result;
    }

    /**
     * 判断是否包含特殊字符
     *
     * @param string
     * @return
     */
    public static boolean isConSpeCharacters(String string) {
        if (string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
            //不包含特殊字符
            return false;
        }
        return true;
    }

    //验证身份证号码
    public static boolean idCardNumber(String number) {
        boolean result = number.matches("^\\d{15}|^\\d{17}([0-9]|X|x)$");
        return result;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param value 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int String_length(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 根据系统编码获取字节长度
     * //此处判断的 字节数 是根据系统编码有关   GBK 站三个字符，手机默认的GBK，
     * // UTF-8是2个   如果想固定汉字是两个可以用  StringUtil.String_length()
     * @param value
     * @return
     */
    public static int getStringlength(String value) {

        return value.getBytes(Charset.defaultCharset()).length;
    }


    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断单个字符串是否是英文字母
     *
     * @param s
     * @return
     */
    public static boolean letter(String s) {
        if (TextUtils.isEmpty(s)){
            return false;
        }
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    /**
     * 获取日期
     *
     * @return
     */
    public static long getDateLong2(String dateString) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format1.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断服务器返回的字符串是否正确
     *
     * @param dataString
     * @return true 错误的格式
     */
    public static boolean isServerExcepInfo(String dataString) {
        if (!TextUtils.isEmpty(dataString)) {
            if (dataString.contains("404 Not Found") || dataString.contains("404!") || (dataString.contains("502 Bad Gateway"))
                    || dataString.contains("<html><head><title>") || (dataString.contains("HTTP Status 404"))) {
                return true;
            } else
                return false;
        } else
            return false;
    }

    /**
     * 判断字符串是否是汉字或者是英文
     * @param str
     * @return true 是
     */
    public static boolean isChineseCharacterEnglish(String str){
    Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
    Matcher m = p.matcher(str);
    if (!m.matches()){//
        return false;
    }
    return true;
}

    /**
     * 保留n位小数正则
     * @param number
     * @param n
     * @return
     */
    public static boolean isOnlyPointNumber(String number,int n) {//保留n位小数正则
        String  regex   = "^\\d+\\.?\\d{0,"+n+"}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return  string
     */
    public  static String replaceDoubleZero(String s){
        if(null != s && s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
    /**
     * 截取小数点后几位
     *
     * @param s
     * @param scale
     * @return
     */
    public static String subString(String s, int scale) {
        if (s == null) {
            return "";
        }

        if (s.indexOf(".") > 0) {

            if (scale == 0) {
                s = s.substring(0, s.indexOf("."));
            } else {
                int index = (s.indexOf(".") + scale + 1);
                if (index >= s.length()) {
                    s = s.substring(0, s.length());
                } else {
                    s = s.substring(0, index);
                }

            }
        }
        return s;
    }

    /**
     * 把字符串小数点后添加几位
     *
     * @param s
     * @param scale
     * @return
     */
    public static String addString(String s, int scale) {
        if (s == null) {
            return "";
        }

        if (scale == 0) {
            return s;
        }

        if (s.indexOf(".") > 0) {//有小数点

            String str = s.substring(s.indexOf(".") + 1, s.length());

            if (str.length() > scale) {
                s = s.substring(0, s.indexOf(".") + scale + 1);
            } else if (str.length() < scale) {
                int count = scale - str.length();

                String zero = "";

                for (int i = 0; i < count; i++) {
                    zero += "0";
                }

                s = s + zero;
            }
        } else {//没有小数点

            String zero = "";

            for (int i = 0; i < scale; i++) {
                zero += "0";
            }

            s = s + "." + zero;
        }
        return s;
    }

}
