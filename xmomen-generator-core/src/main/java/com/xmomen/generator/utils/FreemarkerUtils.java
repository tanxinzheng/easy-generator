package com.xmomen.generator.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by tanxinzheng on 16/8/27.
 */
public class FreemarkerUtils {

    private static String defaultTemplates = "/templates";
    private static Configuration cfg = null;

    public static Configuration getConfiguration() {
        if (null == cfg) {
            cfg = new Configuration();
            cfg.setClassForTemplateLoading(FreemarkerUtils.class, defaultTemplates);
            cfg.setEncoding(Locale.getDefault(), "UTF-8");
            cfg.setClassicCompatible(true);
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setNumberFormat("#");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        }

        return cfg;
    }

    public static Template getTemplate(String name, String ftlDirectory) {
        if(name == null){
            return null;
        }
        try {
            cfg = getConfiguration();
            cfg.setClassForTemplateLoading(FreemarkerUtils.class, ftlDirectory);
            cfg.setClassicCompatible(true);
            Template temp = cfg.getTemplate(name);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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


}
