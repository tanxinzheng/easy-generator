package com.xmomen.generator.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.yaml.snakeyaml.Yaml;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.ValidationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Set;

/**
 * Created by tanxinzheng on 17/6/7.
 */
@Slf4j
public class ConfigurationParser {

    /**
     * 根据文件后缀名自动解析config文件
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws ParserConfigurationException
     * @throws ValidationException
     */
    public static GeneratorConfiguration parserConfig(File file) throws FileNotFoundException, ParserConfigurationException, ValidationException {
        if (file.getAbsolutePath().endsWith(".yml") || file.getAbsolutePath().endsWith(".YML")) {
            return parserYmlConfig(file);
        }else if(file.getAbsolutePath().endsWith(".json") || file.getAbsolutePath().endsWith(".json")){
            return parserJsonConfig(file);
        }else {
            throw new ParserConfigurationException("仅支持json，yml格式配置文件");
        }
    }

    /**
     * 解析json格式configuration
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static GeneratorConfiguration parserJsonConfig(File file) throws FileNotFoundException, JSONException, ValidationException {
        InputStream inputStream = new FileInputStream(file);
        String stringBuffer = reader(inputStream);
        return JSON.parseObject(stringBuffer, GeneratorConfiguration.class);
    }

    /**
     * 解析yml格式配置文件
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static GeneratorConfiguration parserYmlConfig(File file) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(file);
        Yaml yaml = new Yaml();
        return yaml.loadAs(inputStream, GeneratorConfiguration.class);
    }

    /**
     * 校验配置文件
     * @param configuration
     * @throws ValidationException
     */
    public static void validate(GeneratorConfiguration configuration) throws ValidationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GeneratorConfiguration>> constraintViolations = validator.validate(configuration);
        if(CollectionUtils.isEmpty(constraintViolations)){
            return;
        }
        Optional<ConstraintViolation<GeneratorConfiguration>> first = constraintViolations.stream().findFirst();
        if(!first.isPresent()){
            return;
        }
        ConstraintViolation<GeneratorConfiguration> constraintViolation = first.get();
        throw new ValidationException(MessageFormat.format("{0} ：{1}",
                constraintViolation.getPropertyPath(),
                constraintViolation.getMessage()));
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
            log.error(e.getMessage(), e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return sb.toString();
    }
}
