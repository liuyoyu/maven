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

    //动态语句
    @UpdateProvider(type = Provider.class, method = "update")
    int updateProvider(@Param("list") List<${className}VO> list, @Param("${className?uncap_first}VO") ${className}VO ${className?uncap_first}VO);

    <#--@DeleteProvider(type = Provider.class, method = "batchDelete")-->
    <#--int deleteBatch(@Param("list") List<${className}VO> list);-->

    class Provider{
        //动态更新
        public int update(@Param("list") List<${className}VO> list, @Param("${className?uncap_first}VO") ${className}VO ${className?uncap_first}VO){
            return new SQL(){
                UPDATE("${tableName}");
                <#list fields as field>
                    if(${className?uncap_first}VO.get${field.name?cap_first}() != null && !"".equals(${className?uncap_first}VO.get${field.name?cap_first}())){
                        SET("${field.name} = ${r"#{"}${field.name}${r"}"}");
                    }
                </#list>
                <#list primaryKey as pk>
                    WHERE("${pk.columnsName?lower_case} = ${r"#{"}${pk.columnsName?lower_case}${r"}"}");
                </#list>
            };
        }
        //批量删除
        <#--public String batchDelete(@Param("list") List<${className}VO> list){-->
            <#--StringBuilder sb = new StringBuilder();-->
            <#--for (int i = 0; i < list.size(); i++) {-->
                <#--if (i != 0) {-->
                    <#--sb.append(",");-->
                <#--}-->
                <#--sb.append(idList.get(i));-->
            <#--}-->
            <#--return new SQL(){{-->
                <#--DELETE_FROM("menu");-->
                <#--WHERE("id in (" + sb.toString() + ")");-->
                <#--}}.toString();-->
        <#--}-->
}