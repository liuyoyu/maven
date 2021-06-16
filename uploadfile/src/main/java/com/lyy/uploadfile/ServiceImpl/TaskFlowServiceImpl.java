package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Entry.TaskFlow;
import com.lyy.uploadfile.Mapper.TaskFlowMapper;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Service.TaskFlowService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TaskFlowServiceImpl implements TaskFlowService {

    TaskFlowMapper taskFlowMapper;

    TablePrimaryKeyService tablePrimaryService;

    @Autowired
    public TaskFlowServiceImpl(TaskFlowMapper taskFlowMapper, TablePrimaryKeyService tablePrimaryService) {
        this.taskFlowMapper = taskFlowMapper;
        this.tablePrimaryService = tablePrimaryService;
    }

    @Override
    public List<TaskFlow> getMyTaskList(String account) {
        List<TaskFlow> byReviseAccountAndStatus = taskFlowMapper.getByReviseAccountAndStatus(account, TaskFlow.STATUS.WAITING.getVal());
        return byReviseAccountAndStatus;
    }

    /**
     * 获取历史任务，流程状态为拒绝或通过的工作流
     * @param reviseAccount
     * @return
     */
    @Override
    public List<TaskFlow> getHistoryList(String reviseAccount) {
        List<TaskFlow> byReviseAccountAndStatus = taskFlowMapper.getByReviseAccountAndStatus(reviseAccount, TaskFlow.STATUS.PASS.getVal());
        byReviseAccountAndStatus.addAll(taskFlowMapper.getByReviseAccountAndStatus(reviseAccount, TaskFlow.STATUS.REFUSE.getVal()));
        return byReviseAccountAndStatus;
    }

    @Override
    public void pass(long id) {
        taskFlowMapper.changeStatus(id, TaskFlow.STATUS.PASS.getVal());
    }

    @Override
    public void refuse(long id) {
        taskFlowMapper.changeStatus(id, TaskFlow.STATUS.REFUSE.getVal());
    }

    @Override
    public void cancel(long id) {
        taskFlowMapper.changeStatus(id, TaskFlow.STATUS.CANCEL.getVal());
    }
}
