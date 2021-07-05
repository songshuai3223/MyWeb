package com.cn.my.mybatis.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

/**
 * Created by gdxieyue
 *
 * @gmail.com 2018-03-19 11:29
 */
@SpringBootApplication
public class DigitalgdDmpGenerator {
    /**
     * 是否覆盖原有的文件
     */
    private final static Boolean fileOverride = true;

    private final static String projectPath = "F:\\code\\myweb\\";
    private final static String moduleName = "catalog";
    private final static String[] generateTables = new String[]{"user"};
    private final static String tablePrefix = "sys_";

    private final static String driver = "com.mysql.cj.jdbc.Driver";
    private final static String url = "jdbc:mysql://127.0.0.1:3306/mydb?characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false";
    private final static String username = "root";
    private final static String password = "123456";

    private final static String author = "songshuai3223@163.com";
    /**
     * 是否打开文件夹
     */
    private final static Boolean openFile = true;

    protected static GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                .setOutputDir(getJavaPath())//输出目录
                .setFileOverride(fileOverride)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(true)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setKotlin(false) //是否生成 kotlin 代码
                .setOpen(openFile)
                .setAuthor(author) //作者
                .setIdType(IdType.UUID)
                //自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName("%s")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("I%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sRestController");
    }

    protected static DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig()
                .setDbType(DbType.MYSQL)// 数据库类型
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        if (fieldType.toLowerCase().equals("bit")) {
                            return DbColumnType.BOOLEAN;
                        }
                        if (fieldType.toLowerCase().equals("tinyint")) {
                            return DbColumnType.BOOLEAN;
                        }
                        if (fieldType.toLowerCase().equals("date")) {
                            return DbColumnType.LOCAL_DATE;
                        }
                        if (fieldType.toLowerCase().equals("time")) {
                            return DbColumnType.LOCAL_TIME;
                        }
                        if (fieldType.toLowerCase().equals("datetime")) {
                            return DbColumnType.LOCAL_DATE_TIME;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setDriverName(driver)
                .setUsername(username)
                .setPassword(password)
                .setUrl(url);
    }

    /**
     * 获取TableFill策略
     *
     * @return
     */
    protected static List<TableFill> getTableFills() {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("createTime", FieldFill.INSERT));
        tableFillList.add(new TableFill("updateTime", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("createUid", FieldFill.INSERT));
        tableFillList.add(new TableFill("updateUid", FieldFill.INSERT_UPDATE));
        return tableFillList;
    }

    protected static StrategyConfig getStrategyConfig() {

        List<TableFill> tableFillList = getTableFills();
        return new StrategyConfig()
                .setCapitalMode(false)// 全局大写命名
                .setTablePrefix(tablePrefix)// 去除前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                // 自定义实体，公共字段
                .setSuperEntityColumns("id")
                .setTableFillList(tableFillList)
                /* // 自定义 mapper 父类
                 .setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper")
                 // 自定义 controller 父类
                 .setSuperControllerClass("com.digitalgd.dmp.framework.controller.SuperController")
                 // 自定义 service 实现类父类
                 .setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl")
                 // 自定义 service 接口父类
                 .setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService")*/
                // 【实体】是否生成字段常量（默认 false）
                .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                .setEntityBuilderModel(true)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setRestControllerStyle(false)
                .setRestControllerStyle(true)
                .setInclude(generateTables);// 需要生成的表

    }

    protected static PackageConfig getPackageConfig() {
        return new PackageConfig().setParent("com.digitalgd.cloud.code.generator." + moduleName)
                .setController("controller")
                .setEntity("model.entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl");
    }

    protected static InjectionConfig getInjectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
        }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(
                "/templates/mapper.xml.vm") {
            // 自定义输出文件目录
            @Override
            public String outputFile(TableInfo tableInfo) {
//                System.out.println("getResourcePath():" + getResourcePath());
                return getResourcePath() + tableInfo.getEntityName() + "Mapper.xml";
            }
        }));
    }

    /**
     * 获取根目录
     *
     * @return
     */
    protected static String getProjectPath() {
        return projectPath;
    }

    /**
     * 获取JAVA目录
     *
     * @return
     */
    protected static String getJavaPath() {
        return getProjectPath() + "/src/main/java";
    }

    /**
     * 获取Resource目录
     *
     * @return
     */
    protected static String getResourcePath() {
        return getProjectPath() + "/src/main/resources/modules/" + moduleName + "/";
    }

    public void generate() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(getGlobalConfig())
                .setDataSource(getDataSourceConfig())
                .setStrategy(getStrategyConfig())
                .setPackageInfo(getPackageConfig())
                .setCfg(getInjectionConfig())
                .setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                        new TemplateConfig().setXml(getResourcePath())
                        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                        // .setController("...");
                        // .setEntity("...");
                        // .setMapper("...");
                        // .setXml("...");
                        // .setService("...");
                        // .setServiceImpl("...");
                );
        // 执行生成
        mpg.execute();
    }

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(getGlobalConfig())
                .setDataSource(getDataSourceConfig())
                .setStrategy(getStrategyConfig())
                .setPackageInfo(getPackageConfig())
                .setCfg(getInjectionConfig())
                .setTemplate(
                        // 关闭默认 xml 生成，调整生成 至 根目录
                        new TemplateConfig().setXml(null)
                        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                        // .setController("...");
                        // .setEntity("...");
                        // .setMapper("...");
                        // .setXml("...");
                        // .setService("...");
                        // .setServiceImpl("...");
                );
        // 执行生成
        mpg.execute();
    }
}