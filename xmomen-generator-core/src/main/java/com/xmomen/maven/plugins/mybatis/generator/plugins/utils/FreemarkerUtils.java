package com.xmomen.maven.plugins.mybatis.generator.plugins.utils;

import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tanxinzheng on 16/8/27.
 */
public class FreemarkerUtils {

    private static String DEFAULT_TEMPLATES = "/templates";
    private static Configuration cfg = null;

    private static Configuration getConfiguration() {
        if (null == cfg) {
            cfg = new Configuration();
            // 这里有三种方式读取
            // （一个文件目录）
            // cfg.setDirectoryForTemplateLoading(new File("templates"));
            // classpath下的一个目录（读取jar文件）
            cfg.setClassForTemplateLoading(FreemarkerUtils.class, DEFAULT_TEMPLATES);
            // 相对web的根路径来说 根目录
            // cfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "templates");
            // setEncoding这个方法一定要设置国家及其编码，不然在flt中的中文在生成html后会变成乱码
            cfg.setEncoding(Locale.getDefault(), "UTF-8");
            cfg.setClassicCompatible(true);
            // 设置对象的包装器
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            // 设置异常处理器//这样的话就可以${a.b.c.d}即使没有属性也不会出错
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        }

        return cfg;
    }

    public static Template getTemplate(String name, String ftlDirectory) {
        if(name == null){
            return null;
        }
        try {
            // 通过Freemaker的Configuration读取相应的ftl
            cfg = getConfiguration();
            // 设定去哪里读取相应的ftl模板文件
            cfg.setClassForTemplateLoading(FreemarkerUtils.class, ftlDirectory);
            cfg.setClassicCompatible(true);
            // 在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(name);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据默认模板文件目录获取模板文件
     * @param name
     * @return
     */
    public static Template getTemplateByDefaultDir(String name) {
        return getTemplate(name, DEFAULT_TEMPLATES);
    }

    /**
     * 根据模板文件路径直接获取模板文件
     * @param path
     * @return
     * @throws IOException
     */
    public static Template getTemplate(String path) throws IOException {
        File ftl = new File(path);
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(ftl.getParentFile());
        return cfg.getTemplate(ftl.getName());
    }

    /**
     * 控制台输出
     *
     * @param name
     * @param root
     */
    public static void print(String name, Map<String, Object> root) {
        try {
            Template temp = getTemplate(name, DEFAULT_TEMPLATES);
            temp.process(root, new PrintWriter(System.out));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
