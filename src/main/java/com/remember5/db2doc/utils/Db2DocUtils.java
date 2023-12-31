package com.remember5.db2doc.utils;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.remember5.db2doc.entity.DBEnum;
import com.remember5.db2doc.entity.DbDTO;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Db2DocUtils {

    public static List<String> GENERATE_TYPE_LIST = Arrays.asList("Word", "HTML", "Markdown");

    /**
     * 生成doc文档
     *
     * @param dbDto /
     */
    public static void generatedDoc(DbDTO dbDto) {
        HikariConfig hikariConfig = generateHikariConfig(dbDto);
        //数据源
        final String filename = dbDto.getDatabase();
        DataSource dataSource = new HikariDataSource(hikariConfig);
        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
                //生成文件路径
                .fileOutputDir(dbDto.getFileOutputDir())
                //打开目录
                .openOutputDir(true)
                //文件类型 支持word md html
                .fileType(EngineFileType.WORD)
                //生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //自定义文件名称
                .fileName(filename).build();

        switch (dbDto.getGenerateType()) {
            case "HTML":
                engineConfig.setFileType(EngineFileType.HTML);
                break;
            case "Markdown":
                engineConfig.setFileType(EngineFileType.MD);
                break;
            default:
                break;
        }

        //忽略表
        ArrayList<String> ignoreTableName = new ArrayList<>();
        ignoreTableName.add("test_user");
        ignoreTableName.add("test_group");
        //忽略表前缀
        ArrayList<String> ignorePrefix = new ArrayList<>();
        ignorePrefix.add("test_");
        //忽略表后缀
        ArrayList<String> ignoreSuffix = new ArrayList<>();
        ignoreSuffix.add("_test");
        ProcessConfig processConfig = ProcessConfig.builder()
                //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableName)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffix)
                .build();
        //配置
        Configuration config = Configuration.builder()
                //版本
                .version("1.0.0")
                //描述
                .description("数据库设计文档生成")
                //数据源
                .dataSource(dataSource)
                //生成配置
                .engineConfig(engineConfig)
                //生成配置
                .produceConfig(processConfig)
                .build();
        //执行生成
        new DocumentationExecute(config).execute();
    }

    /**
     * 生成连接配置
     *
     * @param dbDto dto
     * @return 需要的连接池配置
     */
    public static HikariConfig generateHikariConfig(DbDTO dbDto) {
        final String driverClassName = DBEnum.getDriverClassNameByKey(dbDto.getDbType());
        String jdbcUrl = null;
        if ("Mysql".equals(dbDto.getDbType())) {
            jdbcUrl = "jdbc:mysql://" + dbDto.getIp() + ":" + dbDto.getPort() + "/" + dbDto.getDatabase();
        }
        if ("MariaDB".equals(dbDto.getDbType())) {
            jdbcUrl = "jdbc:mariadb://" + dbDto.getIp() + ":" + dbDto.getPort() + "/" + dbDto.getDatabase();
        }
        if ("Oracle".equals(dbDto.getDbType())) {
            jdbcUrl = "jdbc:oracle:thin:@//" + dbDto.getIp() + ":" + dbDto.getPort() + "/" + dbDto.getDatabase();
        }
        if ("PostgreSQL".equals(dbDto.getDbType())) {
            jdbcUrl = "jdbc:postgresql://" + dbDto.getIp() + ":" + dbDto.getPort() + "/" + dbDto.getDatabase();
        }
        if ("TIDB".equals(dbDto.getDbType())) {
            jdbcUrl = "jdbc:mysql://" + dbDto.getIp() + ":" + dbDto.getPort() + "/" + dbDto.getDatabase();
        }
        if ("SqlServer".equals(dbDto.getDbType())) {
            jdbcUrl = "jdbc:sqlserver://" + dbDto.getIp() + ":" + dbDto.getPort() + ";databaseName=" + dbDto.getDatabase();
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(dbDto.getUsername());
        hikariConfig.setPassword(dbDto.getPassword());
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        return hikariConfig;
    }

}
