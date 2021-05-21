package com.lyy.uploadfile.ServiceImpl;

import com.lyy.uploadfile.Configture.DTO.UserRoleDTO;
import com.lyy.uploadfile.Entry.UserRole;
import com.lyy.uploadfile.Mapper.UserRoleMapper;
import com.lyy.uploadfile.Service.TablePrimaryKeyService;
import com.lyy.uploadfile.Service.UserRoleService;
import com.lyy.uploadfile.Utils.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    TablePrimaryKeyService tablePrimaryKeyService;

    UserRoleMapper userRoleMapper;

    @Override
    public Message insert(UserRole userRole) {
        int i = userRoleMapper.countByAccountAndRoleId(userRole.getAccount(), userRole.getRoleId());
        if (i != 0) {
            return Message.fail("用户已有该角色");
        }
        Long id = tablePrimaryKeyService.get(UserRole.class);
        userRole.setId(id);
        int insert = userRoleMapper.insert(userRole);
        return insert == 1 ? Message.success("用户获得新的角色！") : Message.fail("用户获取角色失败");
    }

    @Override
    public List<UserRoleDTO> getAllByPage(int page, int limit) {
        int start = (page - 1) * limit, end = page * limit;
        return userRoleMapper.getAllByPage(start, end);
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
}
