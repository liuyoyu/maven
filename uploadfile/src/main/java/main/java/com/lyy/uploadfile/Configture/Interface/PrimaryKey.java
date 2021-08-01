package main.java.com.lyy.uploadfile.Configture.Interface;

import main.java.com.lyy.uploadfile.Entry.TablePrimaryKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
    //用于标注类中的主键的增长方式，默认时自动增长
    TablePrimaryKey.IncreaseStrategy strategy() default TablePrimaryKey.IncreaseStrategy.AUTO;
}
