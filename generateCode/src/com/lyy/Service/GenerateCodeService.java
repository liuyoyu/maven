package com.lyy.Service;

import com.lyy.Api.GenerateCodeApi;
import com.lyy.Configer.GenerateCodeConfiger;
import com.lyy.Entity.TableEntity;
import com.lyy.Util.PropertiesUtil;
import com.lyy.Util.StringUtil;
import com.sun.tools.corba.se.idl.StringGen;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author liuyongyu
 * date 2021/8/1
 */
public class GenerateCodeService extends BaseService implements GenerateCodeApi {
    private static final Logger logger = LoggerFactory.getLogger(GenerateCodeService.class);

    private Configuration initTemplate(){
        Configuration cfg = null;
        try{
            cfg = new Configuration(Configuration.VERSION_2_3_0);
            cfg.setDirectoryForTemplateLoading(new File(PropertiesUtil.get(GenerateCodeConfiger.FTLVOFILEPATH)));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
        }catch (IOException e) {
            logger.error("初始化模板出现异常", e);
        }
        return cfg;
    }
    @Override
    public void generateVO() {
        //获取模板
        Configuration cfg = initTemplate();
        if (tableList == null) {
            try {
                tableList = getTablesInfo(PropertiesUtil.get(GenerateCodeConfiger.DBCAT),
                        PropertiesUtil.get(GenerateCodeConfiger.DBSCHEM));
            } catch (SQLException e) {
                logger.info("===========获取表信息时出现异常==========", e);
                return ;
            }
        }

        logger.info("==========开始处理==========");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (TableEntity entity : tableList) {
            logger.info("==========表名:{}==========", entity.getName());
            logger.info("==========表主键:{}==========", entity.getPrimaryKeyInfo());
            logger.info("==========表目录:{}==========", entity.getCatalog());
            logger.info("==========表模式:{}==========", entity.getSchema());
            logger.info("==========表列数:{}==========", entity.getColumns().size());
            Map<String, Object> root = new HashMap<>();
            root.put("packageName", PropertiesUtil.get(GenerateCodeConfiger.FTLVOPACKAGENAME));
            root.put("date", sdf.format(new Date()));
            root.put("tableName", entity.getName());
//            root.put("import", transformType(entity.getColumns()));
            root.put("className", StringUtil.upperCaseHeader(StringUtil.underScoreCaseToCamelCase(entity.getName()))+"VO");
            root.put("fields", transformField(entity.getColumns(), "private"));
            try {
                Template template = cfg.getTemplate(PropertiesUtil.get(GenerateCodeConfiger.FTLVOFILENAME));
                String packagePath = ((String)root.get("packageName")).replace(".", "/"); //生成文件放在包路径下
                String filePath = StringUtil.geneJavaFilePath(StringUtil.upperCaseHeader((String) root.get("className")),
                        PropertiesUtil.get(GenerateCodeConfiger.FILEOUTPUT)+"/"+packagePath);
                Writer writer = getWriter(filePath);
                template.process(root, writer);
                writer.close();
            } catch (IOException e) {
                logger.error("==========获取模板出现问题==========", e);
            } catch (TemplateException e) {
                logger.error("==========生成模板出现问题==========", e);
            }
        }
    }
}
