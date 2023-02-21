package com.macro.mall.tiny.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * 自定义注释生成器
 *
 * @author lwp
 * @date 2023/2/21 17:02
 */
public class CommentGenerator extends DefaultCommentGenerator {
    //是否用数据库表中字段的注释
    private boolean addRemarkComments=false;

    /**
     * 设置用户配置的参数
     * @param properties
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments= StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 给字段增加注释
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remarks=introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加备注信息
        if(addRemarkComments && StringUtility.stringHasValue(remarks)){
            addFieldJavaDoc(field,remarks);
        }
    }

    /**
     * 给model的字段添加注释
     *
     * @param field
     * @param remarks
     */
    public void addFieldJavaDoc(Field field,String remarks){
        //文档注释开始
        field.addJavaDocLine("/**");
        //获取数据库字段的备注信息
        String[] remarkLines=remarks.split(System.getProperty("line.separator"));
        for(String remarkLine : remarkLines){
            field.addJavaDocLine(" * "+remarkLine);
        }
        addJavadocTag(field,false);
        field.addJavaDocLine("*/");
    }
}