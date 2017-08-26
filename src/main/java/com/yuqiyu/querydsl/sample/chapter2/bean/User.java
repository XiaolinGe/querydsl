package com.yuqiyu.querydsl.sample.chapter2.bean;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(exclude = "role")
@Entity
public class User {
    @Id
    @GeneratedValue
    Long id;
    String name;
    Long age;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

}
