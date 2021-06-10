package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Entry.UserRole;
import com.lyy.uploadfile.Mapper.UserRoleMapper;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Service.UserRoleService;
import com.lyy.uploadfile.Utils.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    TablePrimaryKeyService tablePrimaryKeyService;

    UserRoleMapper userRoleMapper;

    @Autowired
    public UserRoleServiceImpl(TablePrimaryKeyService tablePrimaryKeyService, UserRoleMapper userRoleMapper) {
        this.tablePrimaryKeyService = tablePrimaryKeyService;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Message insert(UserRole userRole) {
        int i = userRoleMapper.countByAccountAndRoleId(userRole.getAccount(), userRole.getRoleId());
        if (i != 0) {
            return Message.fail("用户已有该角色");
        }
        if (userRole.getStatus() == UserRole.STATUS.USING.val()) {
            //更改其他使用状态，改为不使用
            int n = userRoleMapper.updateStatusByAccount(userRole.getAccount(), UserRole.STATUS.UNUSED.val());
            if (n == 0) {
                log.error("更改用户角色使用状态(UserRole.status)参数失败");
            }
        }
        Long id = tablePrimaryKeyService.get(UserRole.class);
        userRole.setId(id);
        int insert = userRoleMapper.insert(userRole);
        return insert == 1 ? Message.success("用户获得新的角色！") : Message.fail("用户获取角色失败");
    }

    @Override
    public Message getAllByPage(int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        List<UserRoleDTO> allByPage = userRoleMapper.getAllByPage(start, end);
        int n = userRoleMapper.countAll();
        return Message.Page.setMsg(allByPage, n);
    }

    @Override
    public Message delete(long id) {
        userRoleMapper.deleteOne(id);
        return Message.success("删除成功");
    }

    @Override
    public Message update(long id, long roleId) {
        UserRoleDTO one = userRoleMapper.getOne(id);
        if (one == null) {
            return Message.fail("记录不存在");
        }
        int n = userRoleMapper.countByAccountAndRoleId(one.getAccount(), roleId);
        if (n != 1) {
            return Message.fail("用户有重叠角色，请联系管理员进行清理");
        }
        int update = userRoleMapper.update(id, roleId);
        return update == 1 ? Message.success("更新成功") : Message.fail("更新失败");
    }

    @Override
    public Message update(long id, int status) {
        int i = userRoleMapper.updateStatus(id, status);
        return i == 1 ? Message.success("成功") : Message.fail("失败");
    }

    @Override
    public Message update(UserRole userRole) {
        UserRoleDTO one = userRoleMapper.getOne(userRole.getId());
        if (one == null) {
            return Message.fail("用户角色不存在");
        }
        if (userRole.getStatus() == UserRole.STATUS.USING.val()) {
            int n = userRoleMapper.updateStatusByAccount(userRole.getAccount(), UserRole.STATUS.UNUSED.val());
            if (n == 0) {
                log.error("更改用户角色使用状态(UserRole.status)参数失败");
            }
        }
        int i = userRoleMapper.updateUserRole(userRole);
        return i == 1 ? Message.success("更新成功") : Message.fail("更新失败");
    }

    @Override
    public Message getByAccount(String account) {
        return null;
    }

    @Override
    public Message search(String account, String roleId, String status, int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        List<UserRoleDTO> search = userRoleMapper.search(account, roleId, status, start, end);
        int n = userRoleMapper.countSearch(account, status, roleId);
        return Message.Page.setMsg(search, n);
    }

    @Override
    public int deleteList(List<Long> id) {
        int i = userRoleMapper.deleteBatch(id);
        return i;
    }
}
