package com.example;

import com.example.entity.PersonalInfo;
import com.example.entity.User;
import com.example.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;


@Slf4j
public class HibernateRunner {
//    public static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);
    public static void main(String[] args) throws SQLException {

        User user = User.builder()
                .username("Petr@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Petrov")
                        .firstname("Petr")
                        .build())
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