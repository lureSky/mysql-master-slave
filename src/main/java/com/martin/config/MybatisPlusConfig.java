package com.martin.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.martin.util.DynamicDataSource;
import com.martin.enums.DBTypeEnum;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author martin
 * @email necaofeng@foxmail.com
 * @Date 2020/4/9 0009
 */
@Configuration
public class MybatisPlusConfig {
    //分页拦截器
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    //定义master
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource master(){
        return DruidDataSourceBuilder.create().build();
    }

    //定义slave
    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slave(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @Description:    动态数据源配置
     *  为什么要用@qualifier 因为DataSource有很多实现，利用@Autowired不能根据类型找到指定的对象，
     *  对于多实现的地方我们使用@Qualifier
     * @return:
     * @author: martin
     * @date: 2020-04-09 18:06
    */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("master") DataSource master, @Qualifier("slave") DataSource slave){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(master);
        //设置指定数据源
        Map<Object,Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DBTypeEnum.master.getValue(), master);
        targetDataSources.put(DBTypeEnum.slave.getValue(), slave);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    /**
     * @Description:    引入sqlSessionFactory
     * @return:
     * @author: martin
     * @date: 2020-04-11 11:20
    */
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        //引入mybatis的sqlSessionFactory
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        //指明数据源
        sqlSessionFactoryBean.setDataSource(multipleDataSource(master(),slave()));

        //指明mapper.xml的位置
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapping/**Mapper.xml"));
        //指明实体类扫描
        sqlSessionFactoryBean.setTypeAliasesPackage("com.martin.entity");

        //导入mybatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{paginationInterceptor()});

        //导入全局变量
//        sqlSessionFactoryBean.setGlobalConfig(globalConfiguration());
        return sqlSessionFactoryBean.getObject();
    }


    /**
     * @Description: 代码中配置mybatisPlus代替application.yml的配置
     * @return: com.baomidou.mybatisplus.core.config.GlobalConfig
     * @author: martin
     * @date: 2020-04-11 11:31
    */
    /*@Bean
    public GlobalConfig globalConfiguration(){
        GlobalConfig conf = new GlobalConfig();
        conf.setSqlInjector(new ISqlInjector());
    }*/
}
