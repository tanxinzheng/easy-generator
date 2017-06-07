package com.xmomen.generator.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.*;

/**
 * Created by tanxinzheng on 17/6/7.
 */
public class ConfigurationParser {

    /**
     * 解析json格式configuration
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static GeneratorConfiguration parserJsonConfig(File file) throws FileNotFoundException, JSONException {
        InputStream inputStream = new FileInputStream(file);
        String stringBuffer = reader(inputStream);
        return JSON.parseObject(stringBuffer, GeneratorConfiguration.class);
    }


    /**
     *
     * @param inputStream
     * @return
     */
    public static String reader(InputStream inputStream) {
         /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
