###文件传输系统 V0.1

用于上传存储文件的系统。

使用springboot和thymeleaf，界面ui使用layui

已有功能：
- 用户登录
- 文件上传和下载过的文件可以从
- 下载历史记录
- 上传的文件支持mp4在线播放和pd预览。

后续计划：实现后台用户界面，包括用户管理、角色管理、菜单管理、*申请审核流程*，启用redis缓存实现LRU，其他待定。

后台用户界面跳转规则：后台统一使用"/uploadFile/admin/###"格式，其中###表示要跳转的界面，注意，该###也是前
台展示的界面名，例如，用户访问/uploadFile/admin/menuList界面，该界面的前台代码在/template/
menuList.html中。所以，"/uploadFile/admin/###"中###即为视图名字。

