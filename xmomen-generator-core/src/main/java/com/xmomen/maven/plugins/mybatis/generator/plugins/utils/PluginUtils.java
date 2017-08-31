package com.xmomen.maven.plugins.mybatis.generator.plugins.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jengt_000 on 2014/12/27.
 */
public class PluginUtils {

    public static final char UNDERLINE='_';

    /**
     * 字符串转大写
     * @param inputString
     * @return
     */
    public static String getUpperCaseString(String inputString) {
        StringBuilder sb = new StringBuilder(inputString);

        if (inputString != null && inputString.length() > 0) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    /**
     * 字符串转小写
     * @param inputString
     * @return
     */
    public static String getLowerCaseString(String inputString) {
        StringBuilder sb = new StringBuilder(inputString);

        if (inputString != null && inputString.length() > 0) {
            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    /**
     * 驼峰转下划线
     * @param param
     * @return
     */
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                if(i != 0){
                    sb.append(UNDERLINE);
                }
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * @param param
     * @return
     */
    public static String underlineToCamel2(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        StringBuilder sb=new StringBuilder(param);
        Matcher mc= Pattern.compile("_").matcher(param);
        int i=0;
        while (mc.find()){
            int position=mc.end()-(i++);
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
        }
        return sb.toString();
    }

}
