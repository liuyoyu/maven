package main.java.com.lyy.uploadfile.Entry;

import main.java.com.lyy.uploadfile.Configture.Interface.PrimaryKey;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUF {

    @PrimaryKey
    private Long id;

    @NotEmpty(message = "用户账号不能为空")
    private String account;

    @NotEmpty(message = "用户密码不能为空")
    private String password;

    @NotEmpty(message = "用户名不能为空")
    private String name;

    private String sex;

    private String telephone;

    @Email(message = "邮箱格式不正确")
    @NotEmpty(message = "邮箱不能为空")
    private String email;

    private Date createDate;

    public UserUF() {
    }

    public UserUF(String name, String account) {
        this.account = account;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateFormat(){
        if (this.createDate == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        return sdf.format(this.createDate);
    }

    public void setDefaultPwd(){
        this.password = "123";
    }
}
