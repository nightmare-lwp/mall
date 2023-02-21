package com.macro.mall.tiny.mbg;


import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于生产MBG的代码
 *
 * @author lwp
 * @date 2023/2/21 18:20
 */
public class Generator {
    public static void main(String[] args){
        //警告信息
        List<String> warnings=new ArrayList<String>();
        boolean overwrite=true;
        //读取MBG配置文件
        InputStream is=Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp=new ConfigurationParser(warnings);
        Configuration config= null;
        try {
            config = cp.parseConfiguration(is);
            is.close();
            DefaultShellCallback callback=new DefaultShellCallback(overwrite);
            //创建MBG
            MyBatisGenerator myBatisGenerator=new MyBatisGenerator(config,callback,warnings);
            //执行生成代码
            myBatisGenerator.generate(null);
            //输出警告信息
            for(String warning:warnings){
                System.out.println(warning);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
