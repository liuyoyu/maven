package main.java.com.lyy.uploadfile.Mapper;

import main.java.com.lyy.uploadfile.Entry.EmailStore;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailStoreMapper {

    @Insert("insert into email_store(id, account, toEmailAddr, froEmailAddr, contextHtml, context, createDate) " +
            "values(#{id}, #{account}, #{toEmailAddr}, #{froEmailAddr}, #{contextHtml}, " +
            "#{context}, #{createDate})")
    int insert(EmailStore emailStore);
}
