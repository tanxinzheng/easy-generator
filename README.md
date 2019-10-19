# Easy-Generator
基于freemarker模板引擎开发的代码生成器Maven插件工具，可根据各类数据库中的表结构一键生成controller,service,dao,entity等各类型代码文件，并可以自定义文件模板，是一种轻量级的代码生成工具。

## 支持配置文件类型
- yaml(推荐)
- json

## 支持数据库类型
- MySQL
- Oracle
- PostgreSQL
- Mongodb

## maven插件配置
``` pom.xml
...
<plugin>
    <groupId>com.xmomen.maven.plugins</groupId>
    <artifactId>xmomen-generator-maven-plugin</artifactId>
    <version>1.1.0-SNAPSHOT</version>
    <configuration>
        <configurationFile>src/test/resources/help/generator-config.json</configurationFile>
    </configuration>
</plugin>
...
```

- 常用命令
```maven
xmomen-generator:generate
  Base on freemarker template create java code.

xmomen-generator:help
  Display help information on maven-xmomen-generator-plugin.

xmomen-generator:generate-config
  Generate a simple generator-config.json configuration file in the test resources help directory.

xmomen-generator:generate-config-oracle
Generate a simple mysql database generator-config.json configuration file in the test resources help directory.

xmomen-generator:generate-config-mysql
  Generate a simple oracle database generator-config.json configuration file in the test resources help directory.

xmomen-generator:generate-templates
  Generate all default template files in the test resources help directory.

xmomen-generator:generate-help
  Generate a simple generator-help.json configuration file in the test resources help directory.
  Or you can view com.xmomen.generator.configuration.GeneratorConfiguration.java file know all properties.
```