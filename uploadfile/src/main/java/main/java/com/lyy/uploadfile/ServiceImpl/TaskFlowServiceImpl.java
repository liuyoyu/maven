package main.java.com.lyy.uploadfile.ServiceImpl;

import main.java.com.lyy.uploadfile.Mapper.TaskFlowMapper;
import main.java.com.lyy.uploadfile.Entry.TaskFlow;
import main.java.com.lyy.uploadfile.Service.FileService;
import main.java.com.lyy.uploadfile.Service.TablePrimaryKeyService;
import main.java.com.lyy.uploadfile.Service.TaskFlowService;
import main.java.com.lyy.uploadfile.Utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskFlowServiceImpl implements TaskFlowService {

    final Logger logger = LoggerFactory.getLogger(TaskFlowServiceImpl.class);

    TaskFlowMapper taskFlowMapper;

    TablePrimaryKeyService tablePrimaryService;

    FileService fileService;

    @Autowired
    public TaskFlowServiceImpl(TaskFlowMapper taskFlowMapper,
                               TablePrimaryKeyService tablePrimaryService,
                               FileService fileService) {
        this.taskFlowMapper = taskFlowMapper;
        this.tablePrimaryService = tablePrimaryService;
        this.fileService = fileService;
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
        logger.info("工作流（" + id + "）完成");
        TaskFlow one = taskFlowMapper.getOne(id);
        if (one == null) {
            return ;
        }
        taskFlowMapper.updateEndDate(id, new Date());
        taskFlowMapper.changeStatus(id, TaskFlow.STATUS.PASS.getVal());
    }

    @Override
    public void refuse(long id) {
        logger.info("工作流（" + id + "）被拒绝");
        TaskFlow one = taskFlowMapper.getOne(id);
        if (one == null) {
            return ;
        }
        taskFlowMapper.updateEndDate(id, new Date());
        taskFlowMapper.changeStatus(id, TaskFlow.STATUS.REFUSE.getVal());
    }

    @Override
    public void cancel(long id) {
        logger.info("工作流（" + id + "）取消");
        TaskFlow one = taskFlowMapper.getOne(id);
        if (one == null) {
            return ;
        }
        taskFlowMapper.updateEndDate(id, new Date());
        taskFlowMapper.changeStatus(id, TaskFlow.STATUS.CANCEL.getVal());
    }

    @Override
    public Message apply(String userAccount, String reviseAccount, long fileId, String taskName) {
        Message one = fileService.getOne(fileId);
        if (one == null) {
            return Message.fail("申请文件不存在");
        }
        TaskFlow taskFlow = new TaskFlow();
        taskFlow.setFileId(fileId);
        taskFlow.setTaskName(taskName);
        taskFlow.setTaskStartTime(new Date());
        taskFlow.setTaskEndTime(new Date());
        taskFlow.setStatus(TaskFlow.STATUS.WAITING.getVal());
        taskFlow.setUserAccount(userAccount);
        taskFlow.setReviseAccount(reviseAccount);
        TaskFlow insert = taskFlowMapper.insert(taskFlow);
        return insert == null ? Message.fail("创建申请失败") : Message.success("申请成功");
    }
}
