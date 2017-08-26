package com.yuqiyu.querydsl.sample.chapter2.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuqiyu.querydsl.sample.chapter2.bean.QRole;
import com.yuqiyu.querydsl.sample.chapter2.bean.QUser;
import com.yuqiyu.querydsl.sample.chapter2.bean.Role;
import com.yuqiyu.querydsl.sample.chapter2.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.List;
import java.util.Queue;

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

    public String count() {
        List<Long> userCount = queryFactory.select(QUser.user.count()).from(QUser.user).fetch();
        List<Double> ageAvg = queryFactory.select(QUser.user.age.avg()).from(QUser.user).fetch();
        List<Long> ageMin = queryFactory.select(QUser.user.age.min()).from(QUser.user).fetch();
        List<Long> ageMax = queryFactory.select(QUser.user.age.max()).from(QUser.user).fetch();
        List<Long> ageSum = queryFactory.select(QUser.user.age.sum()).from(QUser.user).fetch();
        List<Long> ageSum1 = queryFactory.select(QUser.user.age.sum()).from(QUser.user).where(QUser.user.age.gt(22)).fetch();
        List<Long> ageSum2 = queryFactory.select(QUser.user.age.sum()).from(QUser.user).where(QUser.user.age.eq(22L)).fetch();

        return  "user count:" + userCount + "\n" +
                "age avg: " + ageAvg + "\n" +
                "age min: " + ageMin + "\n" +
                "age max: " + ageMax + "\n" +
                "age sum: " + ageSum + "\n" +
                "age gl sum : " + ageSum1 + "\n" +
                "age lm sum : " + ageSum2;
    }

    //select u.name uese_name, u.age, r.name role_name from user u left join role r on (u.role_id=r.id) where u.age > 20 and r.name in ("admin","staff");
    // select u.name, r.name from user u left join role r on (r.id=u.role_id) where u.role_id is not null;

    public String findUser() {
        List<com.querydsl.core.Tuple> user =queryFactory
                .select(QUser.user.age, QUser.user.name, QRole.role.name )
                .from(QUser.user).leftJoin(QUser.user.role, QRole.role)
                .where(QRole.role.name.in("admin","stuff"))
                .fetch();

        List<com.querydsl.core.Tuple> userHasRole = queryFactory
                .select(QUser.user.age, QUser.user.name, QRole.role.name )
                .from(QUser.user).leftJoin(QUser.user.role, QRole.role)
                .where(QUser.user.role.id.isNotNull())
                .fetch();

        return "user info: " + user + "\n" +
                "user(has role): " + userHasRole;
    }

    //select  distinct(r.name) from user u left join role r on (r.id=u.role_id) where u.role_id != "";
    //select  count(distinct(r.name)) `user totle`  from user u left join role r on (r.id=u.role_id) where u.role_id != "";
    public String findRole() {
        List<Role> roleInfo = queryFactory.select(QRole.role).from(QRole.role).fetch();
        List<String> roleDistinct = queryFactory.selectDistinct(QRole.role.name)
                .from(QUser.user).leftJoin(QUser.user.role, QRole.role)
                .where(QUser.user.role.isNotNull())
                .fetch();

        List<Long> roleDistinctCount = queryFactory.selectDistinct(QRole.role.name.count())
                .from(QUser.user).leftJoin(QUser.user.role, QRole.role)
                .where(QUser.user.role.isNotNull())
                .fetch();

        return "role: info: " + roleInfo + "\n" +
                "role(distinct): " + roleDistinct + "\n" +
                "role(distinct)Count: " + roleDistinctCount;
    }

    //mysql> select u.name, r.name, length(r.name) name_length from user u left join role r on(u.role_id=r.id) where length(r.name) = 5;


}
