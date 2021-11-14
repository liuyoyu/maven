package ${packageName};

/**
*  该类由自动代码生成
*  生成时间：${date}
*  表名: ${tableName}
*/
public class ${className}{
<#list fields as field>
    /**
    * ${field.remark}
    */
    ${field.qualifier} ${field.type} ${field.name};
</#list>
<#list fields as field>

    public ${field.type} get${field.upperCaseHeader}(){
        return this.${field.name};
    }

    public void set${field.upperCaseHeader}(${field.type} ${field.name}){
        this.${field.name} = ${field.name};
    }
</#list>
}