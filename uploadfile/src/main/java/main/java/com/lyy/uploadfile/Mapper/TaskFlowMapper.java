package main.java.com.lyy.uploadfile.Mapper;

import main.java.com.lyy.uploadfile.Entry.TaskFlow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskFlowMapper {

    @Select("select tf.id, tf.taskName, tf.taskStartTime, tf.taskEndTime, tf.status, tf.fileId, tf.reviseAccount, tf.userAccount, " +
            " u.name as reviseName, u2.name as userName, " +
            " f.fileName, f.fileSize " +
            " from task_flow tf " +
            " left join user_uf u on tf.reviseAccount = u.account " +
            " left join user_uf u2 on tf.userAccount = u2.account " +
            " left join file_uf f on f.id = tf.fileId " +
            " where tf.reviseAccount = #{account} and tf.status = #{status}")
    @ResultType(TaskFlow.class)
    List<TaskFlow> getByReviseAccountAndStatus(@Param("account") String reviseAccount, @Param("status") int status);

    @Update("update task_flow set status = #{status} where id = #{id}")
    TaskFlow changeStatus(@Param("id") long id, @Param("status")int status);

    @Insert("insert into task_flow(id, taskName, taskStartTime, taskEndTime, userAccount, reviseAccount, status, fileId) " +
            "values(#{id}, #{taskName}, #{taskStartTime}, #{taskEndTime}, #{userAccount}, #{reviseAccount}, #{status} #{fileId})")
    TaskFlow insert(TaskFlow taskFlow);

    @Select("select * from task_flow where id = #{id}")
    TaskFlow getOne(@Param("id") long id);

    @Update("update task_flow set taskEndTime = #{date} where id = #{id}")
    TaskFlow updateEndDate(@Param("id") long id, @Param("date") Date date);
}
