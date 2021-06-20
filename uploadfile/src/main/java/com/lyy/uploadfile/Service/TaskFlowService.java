package com.lyy.uploadfile.Service;

import com.lyy.uploadfile.Entry.TaskFlow;
import com.lyy.uploadfile.Utils.Message;

import java.util.List;

public interface TaskFlowService {

    List<TaskFlow> getMyTaskList(String account);

    List<TaskFlow> getHistoryList(String account);

    void pass(long id);

    void refuse(long id);

    void cancel(long id);

    Message apply(String userAccount, String reviseAccount, long fileId, String taskName);
}
