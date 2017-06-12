package com.xmomen.generator.jdbc;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanxinzheng on 17/6/11.
 */
public class SQLKeywords {

    public static Map<DatabaseType, List<String>> getKeywords(){
        InputStream inputStream = SQLKeywords.class.getResourceAsStream("/database/mysql_keyword.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> keywords = new ArrayList<>();
        String line = null;
        try {
            while ((line = StringUtils.trimToNull(bufferedReader.readLine())) != null){
                keywords.add(line);
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
        Map<DatabaseType, List<String>> databaseTypeMap = new HashMap<>();
        databaseTypeMap.put(DatabaseType.MYSQL, keywords);
        return databaseTypeMap;
    }
}
