package com.greatlearning.crmapp;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.greatlearning.crmapp.entity.Customer;
import com.greatlearning.crmapp.service.CustomerService;
import com.greatlearning.crmapp.service.CustomerServiceImpl;

public class App {
	public static void main(String[] args) {
		Configuration con = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Customer.class);
		SessionFactory sessionFactory = con.buildSessionFactory();

		CustomerService ss = new CustomerServiceImpl(sessionFactory);

		Customer cust = new Customer();

		cust.setFirstName("Rahul");
		cust.setLastName("Sarkar");
		cust.setEmailId("scjuee@gmail.com");

		ss.save(cust);

		List<Customer> custList = ss.findAll();

		for (Customer c : custList) {
			System.out.println(c);
		}
	}
}
