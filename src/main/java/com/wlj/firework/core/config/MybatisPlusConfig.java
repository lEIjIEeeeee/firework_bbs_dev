package com.wlj.firework.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public EasySqlInjector easySqlInjector() {
        return new EasySqlInjector();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //添加分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //添加乐观锁拦截器
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    private class EasySqlInjector extends DefaultSqlInjector {
        @Override
        public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
            List<AbstractMethod> methodList = super.getMethodList(mapperClass);
            methodList.add(new LogicDeleteByIdWithFill());
            methodList.add(new LogicBatchDeleteWithFill());
            methodList.add(new InsertBatchSomeColumn());
            return methodList;
        }
    }

    private class LogicBatchDeleteWithFill extends AbstractMethod {

        private final static String MAPPER_METHOD = "deleteBatchWithFill";

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            String sql;
            SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE;
            if (tableInfo.isWithLogicDelete()) {
                List<TableFieldInfo> fieldInfos = tableInfo.getFieldList().stream()
                                                           .filter(i -> i.getFieldFill() == FieldFill.UPDATE || i.getFieldFill() == FieldFill.INSERT_UPDATE)
                                                           .collect(toList());
                if (CollectionUtils.isNotEmpty(fieldInfos)) {
                    String sqlSet = "SET " + fieldInfos.stream().map(i -> i.getSqlSet(ENTITY_DOT)).collect(joining(EMPTY))
                            + tableInfo.getLogicDeleteSql(false, false);
                    sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet,
                            sqlWhereEntityWrapper(true, tableInfo), "");
                } else {
                    sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
                            sqlWhereEntityWrapper(true, tableInfo), "");
                }
                SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
                return this.addUpdateMappedStatement(mapperClass, modelClass, MAPPER_METHOD, sqlSource);
            } else {
                sqlMethod = SqlMethod.DELETE;
                sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo), "");
                SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
                return this.addDeleteMappedStatement(mapperClass, MAPPER_METHOD, sqlSource);
            }
        }
    }
}
