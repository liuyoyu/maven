package com.lyy.Service;

import com.lyy.Api.GenerateCodeApi;
import com.lyy.Configer.GenerateCodeConfiger;
import com.lyy.Entity.PrimaryKey;
import com.lyy.Entity.TableEntity;
import com.lyy.Util.JDBCUtil;
import com.lyy.Util.PropertiesUtil;
import com.lyy.Util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  生成代码
 * author liuyongyu
 * date 2021/8/1
 */
public class GenerateCodeService implements GenerateCodeApi {
    private static final Logger logger = LoggerFactory.getLogger(GenerateCodeService.class);
    private static final DataBaseService dataBaseService = new DataBaseService();;

    private Configuration initTemplate() {
        Configuration cfg = null;
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_0);
            cfg.setDirectoryForTemplateLoading(new File(PropertiesUtil.get(GenerateCodeConfiger.FTLVOFILEPATH)));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
        } catch (IOException e) {
            logger.error("初始化模板出现异常", e);
        }
        return cfg;
    }

    private void outputTemplate(Configuration cfg, Map<String, Object> root, String templatePath, String fileType) {
        try {
            Template template = cfg.getTemplate(templatePath);
            String packagePath = ((String) root.get("packageName")).replace(".", "/"); //生成文件放在包路径下
            String filePath = StringUtil.geneJavaFilePath(StringUtil.upperCaseHeader((String) root.get("className")+fileType),
                    PropertiesUtil.get(GenerateCodeConfiger.FILEOUTPUT) + "/" + packagePath);
            Writer writer = getWriter(filePath);
            template.process(root, writer);
            writer.close();
        } catch (IOException e) {
            logger.error("获取模板出现问题", e);
        } catch (TemplateException e) {
            logger.error("生成模板出现问题", e);
        }
    }

    /**
     * 写入文件
     *
     * @param filePath 文件名称
     * @return
     */
    private Writer getWriter(String filePath) {
        FileWriter fileWriter = null;
        File file = null;
        logger.info("输出文件：{}", filePath);
        try {
            file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, false);
        } catch (IOException e) {
            logger.error("==========设置输出时出现异常==========");
            e.printStackTrace();
        }
        return fileWriter;
    }

    @Override
    public void generateVO() {
        //获取模板
        Configuration cfg = initTemplate();
        logger.info("==========开始处理GenerateCodeService.generateVO==========");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (TableEntity entity : dataBaseService.tableList) {
            logger.info("==========表名:{}，表主键:{}，表目录:{}，表模式:{}，表列数:{}==========", entity.getName(),
                    entity.getPrimaryKeyInfo(), entity.getCatalog(), entity.getSchema(), entity.getColumns().size());
            Map<String, Object> root = new HashMap<>();
            root.put("packageName", PropertiesUtil.get(GenerateCodeConfiger.FTLVOPACKAGENAME));
            root.put("date", sdf.format(new Date()));
            root.put("tableName", entity.getName());
            root.put("className", StringUtil.upperCaseHeader(StringUtil.underScoreCaseToCamelCase(entity.getName())));
            root.put("fields", dataBaseService.transformField(entity.getColumns(), "private"));
            outputTemplate(cfg, root, PropertiesUtil.get(GenerateCodeConfiger.FTLVOFILENAME), "VO");
        }
    }

    @Override
    public void generateMapper() {
        Configuration cfg = initTemplate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        logger.info("==========开始处理GenerateCodeService.generateMapper==========");
        for (TableEntity entity : dataBaseService.tableList) {
            logger.info("==========表名:{}，表主键:{}，表目录:{}，表模式:{}，表列数:{}==========", entity.getName(),
                    entity.getPrimaryKeyInfo(), entity.getCatalog(), entity.getSchema(), entity.getColumns().size());
            Map<String, Object> root = new HashMap<>();
            root.put("packageName", PropertiesUtil.get(GenerateCodeConfiger.FTLDAOPCKAGENAME));
            root.put("importVO", PropertiesUtil.get(GenerateCodeConfiger.FTLVOPACKAGENAME));
            root.put("date", sdf.format(new Date()));
            root.put("className", StringUtil.upperCaseHeader(StringUtil.underScoreCaseToCamelCase(entity.getName())));
            root.put("tableName", entity.getName());
            root.put("fields", dataBaseService.transformField(entity.getColumns(), "private"));
            //处理主键数据类型，db类型->Java类型
            List<PrimaryKey> pks = new ArrayList<>(entity.getPk());
            for (int i = 0; i < pks.size(); i ++) {
                PrimaryKey primaryKey = pks.get(i);
                primaryKey.setFieldType(JDBCUtil.tranformType(primaryKey.getFieldType()));
            }
            root.put("primaryKey", pks);
            outputTemplate(cfg, root, PropertiesUtil.get(GenerateCodeConfiger.FTLDAOFILENAME), "Mapper");
        }
    }
}
