package com.dapeng.springboot.entity;

import com.fasterxml.jackson.core.type.TypeReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * ==================================================
 * <p>
 * FileName: UserEntity
 *
 * @author : lipeng
 * @create 2019/9/24
 * @since 1.0.0
 * 〈功能〉：TODO
 * ==================================================
 */

@Entity
@Table(name = "user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增设置
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Long age;

    @Column(name = "address")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void main(String[] args) {
        UserEntity entity = new UserEntity();
        new TypeReference<Map<String,String>>(){};
    }

}
