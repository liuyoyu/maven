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
    @Update("update ${tableName} set<#list fields as field>${field.name} = ${r"#{"}${field.name}${r"}"}<#if field_has_next>, </#if></#list> where<#list primaryKey as pk>${pk.columnsName} = ${r"#{"}${pk.columnsName}${r"}"}</#list>")
    int update(${className} ${className?uncap_first}VO);

    @Select("select <#list fields as field>${field.name}<#if field_has_next>, </#if></#list> from ${tableName} where <#list primaryKey as pk>${pk.columnsName} = ${r"#{"}${pk.columnsName}${r"}"}<#if pk_has_next>, </#if></#list>")
    ${className}VO getById(<#list primaryKey as pk>@Param("${pk.columnsName}") ${pk.fieldType} ${pk.columnsName}<#if pk_has_next>, </#if></#list>);

    //动态语句
    @UpdateProvider(type = Provider.class, method = "update")
    int updateProvider(@Param("list") List<${className}VO> list, @Param("${className?uncap_first}VO") ${className}VO ${className?uncap_first}VO);

    @SelectProvider(type = Provider.class, method = "select")
    List<${className}VO> selectProvider(@Param("${className?uncap_first}VO") ${className}VO ${className?uncap_first}VO);

    <#if (primaryKey?size=1) >
    //自动生成批量方法
    @DeleteProvider(type = Provider.class, method = "batchDelete")
    int deleteBatch(@Param("list") List<${primaryKey[0].fieldType}> list);
    <#else>
    //如有需要，手动添加批量方法
    // ... ...
    </#if>

    class Provider{
        //动态更新
        public int update(@Param("list") List<${className}VO> list, @Param("${className?uncap_first}VO") ${className}VO ${className?uncap_first}VO){
            return new SQL(){{
                UPDATE("${tableName}");
                <#list fields as field>
                if(${className?uncap_first}VO.get${field.name?cap_first}() != null && !"".equals(${className?uncap_first}VO.get${field.name?cap_first}())){
                    SET("${field.name} = ${r"#{"}${field.name}${r"}"}");
                }
                </#list>
                <#list primaryKey as pk>
                WHERE("${pk.columnsName} = ${r"#{"}${pk.columnsName}${r"}"}");
                </#list>
            }}.toString();
        }
        //条件选择
        public List<${className}VO> select(@Param("${className?uncap_first}VO") ${className}VO ${className?uncap_first}VO){
            return new SQL(){{
                SELECT("<#list fields as field>${field.name}<#if field_has_next>, </#if></#list>");
                FROM("${tableName}");
                <#list fields as field>
                if(${className?uncap_first}VO.get${field.name?cap_first}() != null && !"".equals(${className?uncap_first}VO.get${field.name?cap_first}())){
                    WHERE("${field.name} = ${r"#{"}${field.name}${r"}"}");
                }
                </#list>
            }};
        }

        <#if (primaryKey?size=1) >
        //批量删除
        public String batchDelete(@Param("list") List<${primaryKey[0].fieldType}> list){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i != 0) sb.append(",");
                sb.append(idList.get(i));
            }
            return new SQL(){{
                DELETE_FROM("${tableName}");
                WHERE("${primaryKey[0].name} in (" + sb.toString() + ")");
            }}.toString();
        }
        </#if>
}