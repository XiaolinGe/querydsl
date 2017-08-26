package com.yuqiyu.querydsl.sample.chapter2.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuqiyu.querydsl.sample.chapter2.bean.QUser;
import com.yuqiyu.querydsl.sample.chapter2.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private EntityManager entityManager;

    //查询工厂实体
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory() {
        System.out.println("开始实例化JPAQueryFactory");
        queryFactory = new JPAQueryFactory(entityManager);
    }

    public List findAll() {
        List<User> list = queryFactory.select(QUser.user).from(QUser.user).fetch();
        return list;
    }



}
