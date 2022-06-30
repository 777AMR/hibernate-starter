package com.example;

import com.example.entity.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class HibernateRunner {
    public static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);
    public static void main(String[] args) throws SQLException {

        User user = User.builder()
                .username("Ivan@gmail.com")
                .lastname("Ivanov")
                .firstname("Ivan")
                .build();

        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                Transaction transaction = session1.beginTransaction();
                log.trace("transaction is created,{}", transaction);

                session1.saveOrUpdate(user);
                log.trace("user: {}, session {}", user, session1);

                session1.getTransaction().commit();
            }
            log.trace("user: {}", user);
        } catch (Exception exception){
            log.error("exception", exception);
            throw exception;
        }
    }
}