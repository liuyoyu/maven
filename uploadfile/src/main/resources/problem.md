```text
20210505 15:31
>>RA-12505, TNS:listener does not currently know of SID given in connect descriptor
>>ORA-12514, TNS:listener does not currently know of service requested in connect descriptor
配置文件中 spring.datasource.url=jdbc:oracle:thin:@localhost:1521添加了后缀--:orcl或/orcl，导致出现那个错误，因为我在oracle中的实例名字为XE
改了之后又出现表不存在的问题，了解后知道mybatis没有自动创建实体类对应的表的功能，而hibernate可以，这个功能相当方便，事实上也有人针对这个问题开发了相关的自动构建表格的插件ACTable，但这个目前只支持mysql，所以还是老老实实地去数据库创建表
```
[进入docker中的oracle方法](https://blog.csdn.net/weixin_36001063/article/details/91598532)


