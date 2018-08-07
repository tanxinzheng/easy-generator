# 代码生成器
基于freemarker模板引擎开发的代码生成器Maven插件

## 架构设计
- easy-generator-core
- easy-generator-maven-plugin

## maven使用
``` pom.xml
...
<plugin>
    <groupId>com.xmomen.maven.plugins</groupId>
    <artifactId>xmomen-generator-maven-plugin</artifactId>
    <version>1.1.0-SNAPSHOT</version>
    <configuration>
        <configurationFile>src/main/resources/tools/generator-config.json</configurationFile>
    </configuration>
</plugin>
...
```
