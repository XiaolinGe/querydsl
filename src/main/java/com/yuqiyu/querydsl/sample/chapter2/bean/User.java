package com.yuqiyu.querydsl.sample.chapter2.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String age;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

}
