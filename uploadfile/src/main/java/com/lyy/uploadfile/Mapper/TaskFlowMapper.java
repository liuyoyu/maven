package com.lyy.uploadfile.Mapper;

import com.lyy.uploadfile.Entry.TaskFlow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskFlowMapper {

    @Select("select tf.*, u.name as reviseAccount, u2.name as userAccount, f.name as fileName, f.size as fileSize " +
            " from task_flow tf inner join user_uf u on tf.reviseAccount = u.account " +
            " inner join u u2 on tf.userAccount = u2.account " +
            " inner join file_uf f on f.id = tf.fileId " +
            " where tf.reviseAccount = #{account} and tf.status = #{status}")
    List<TaskFlow> getByReviseAccountAndStatus(@Param("account") String reviseAccount, @Param("status") int status);

    @Update("update task_flow set status = #{status} where id = #{id}")
    TaskFlow changeStatus(@Param("id") long id, @Param("status")int status);
}
