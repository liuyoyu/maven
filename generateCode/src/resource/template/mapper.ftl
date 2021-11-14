package ${packageName};

import ${importVO}.${className}VO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
*  mybatis映射器
*  createDate：${date}
*/
@Repository
public interface ${className}Mapper{

    @Insert("insert into ${tableName} (<#list fields as field>${field.name}<#if field_has_next>, </#if></#list>) values (<#list fields as field>${r"#{"}${field.name}${r"}"}<#if field_has_next>, </#if></#list>)")
    int insert(${className} ${className?uncap_first}VO);

    //全量更新
    @Update("update ${tableName} set<#list fields as field>${field.name} = ${r"#{"}${field.name}${r"}"}<#if field_has_next>, </#if></#list> where<#list primaryKey as pk>${pk.columnsName?lower_case} = ${r"#{"}${pk.columnsName?lower_case}${r"}"}</#list>")
    int update(${className} ${className?uncap_first}VO);

    @Select("select <#list fields as field>${field.name}<#if field_has_next>, </#if></#list> from ${tableName} where <#list primaryKey as pk>${pk.columnsName?lower_case} = ${r"#{"}${pk.columnsName?lower_case}${r"}"}<#if pk_has_next>, </#if></#list>")
    ${className}VO getById(<#list primaryKey as pk>@Param("${pk.columnsName?lower_case}") ${pk.fieldType} ${pk.columnsName?lower_case}<#if pk_has_next>, </#if></#list>);
}