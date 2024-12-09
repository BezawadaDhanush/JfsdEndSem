package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.criterion.*;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        // Insert records
        Transaction tx = session.beginTransaction();
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setEmail("alice@example.com");
        customer1.setAge(30);
        customer1.setLocation("New York");

        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setEmail("bob@example.com");
        customer2.setAge(40);
        customer2.setLocation("California");

        session.save(customer1);
        session.save(customer2);
        tx.commit();

        // Criteria Queries
        Criteria criteria = session.createCriteria(Customer.class);

        // Equal
        System.out.println("\n=== Customers in California ===");
        criteria.add(Restrictions.eq("location", "California"));
        List<Customer> californiaCustomers = criteria.list();
        californiaCustomers.forEach(c -> System.out.println(c.getName()));

        // Between
        System.out.println("\n=== Customers aged between 25 and 35 ===");
        criteria = session.createCriteria(Customer.class);
        criteria.add(Restrictions.between("age", 25, 35));
        List<Customer> agedCustomers = criteria.list();
        agedCustomers.forEach(c -> System.out.println(c.getName()));

        // Like
        System.out.println("\n=== Customers with email ending in '@example.com' ===");
        criteria = session.createCriteria(Customer.class);
        criteria.add(Restrictions.like("email", "%@example.com"));
        List<Customer> emailCustomers = criteria.list();
        emailCustomers.forEach(c -> System.out.println(c.getName()));

        session.close();
        factory.close();
    }
}
