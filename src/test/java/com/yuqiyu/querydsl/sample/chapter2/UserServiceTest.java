package com.yuqiyu.querydsl.sample.chapter2;

import com.yuqiyu.querydsl.sample.chapter2.bean.User;
import com.yuqiyu.querydsl.sample.chapter2.bean.UserBean;
import com.yuqiyu.querydsl.sample.chapter2.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = UserService.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindAll(){
        User bean  = new User();
        bean.setName("test");
        entityManager.persist(bean);
        System.out.println(userService.findAll());
    }

    @Test
    public void testCount() {
        System.out.println(userService.count());
    }

    @Test
    public void testFindUser() {
        System.out.println(userService.findUser());
    }

    @Test
    public void testFindRole() {
        System.out.println(userService.findRole());
    }

}