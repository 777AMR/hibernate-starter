package com.example;

import com.example.converter.BirthdayConverter;
import com.example.entity.Birthday;
import com.example.entity.Role;
import com.example.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {

        Configuration configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
//        configuration.addAnnotatedClass(User.class);

        configuration.addAttributeConverter(new BirthdayConverter());
        //configuration.registerTypeOverride(new JsonBinaryType());

        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

//            User user = User.builder()
//                    .username("ivan9@gmail.com")
//                    .firstname("Ivan")
//                    .lastname("Ivanov")
//                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
//                    .role(Role.ADMIN)
//                    .build();
//
//            session.delete(user);
            User user = session.get(User.class, "ivan9@gmail.com");

            session.getTransaction().commit();
        }
    }
}