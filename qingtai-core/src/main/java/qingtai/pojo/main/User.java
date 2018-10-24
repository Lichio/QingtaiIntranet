package qingtai.pojo.main;

import qingtai.base.entity.BaseEntity;

public class User extends BaseEntity {
    private Long id;

    private String username;

    private String password;

    private Integer age;

    private Byte gender;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Byte getGender() {
        return gender;
    }

    public User setGender(Byte gender) {
        this.gender = gender;
        return this;
    }
}