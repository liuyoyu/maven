package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.EmailStore;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailStoreMapper {

    @Insert("insert into email_store(id, account, code, createDate) values(#{id}, #{account}, #{code}, #{createDate})")
    int insert(EmailStore emailStore);
}
