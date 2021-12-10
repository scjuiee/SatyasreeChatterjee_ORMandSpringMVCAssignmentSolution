package com.greatlearning.crmapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.greatlearning.crmapp.entity.Customer;

@Repository
public class CustomerServiceImpl implements CustomerService {
	private SessionFactory sessionFactory;
	private Session session;

	// Using Autowired in constructor-based DI is optional in latest Spring
	@Autowired
	public CustomerServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

		try {
			this.session = this.sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			this.session = this.sessionFactory.openSession();
		}
	}

	@Transactional
	public List<Customer> findAll() {
		// Note: If Entity name is specified then use it instead of table name - eg.
		// "from Pustak"
		// Transaction tx = session.beginTransaction();
		List<Customer> customers = session.createQuery("from Customer", Customer.class).list(); // find all the records
																								// from the database
																								// table
		// tx.commit();

		return customers;
	}

	@Transactional
	public Customer findById(int id) {
		Customer cust = new Customer();

		Transaction tx = session.beginTransaction();
		cust = session.get(Customer.class, id);
		tx.commit();

		return cust;
	}

	@Transactional
	public void save(Customer cust) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(cust);
		tx.commit();
	}

	@Transactional
	public void deleteById(int id) {
		Transaction tx = session.beginTransaction();
		Customer cust = session.get(Customer.class, id);
		session.delete(cust);
		tx.commit();
	}

	@Transactional
	public void print(List<Customer> custList) {
		for (Customer cust : custList) {
			System.out.println(cust);
		}
	}
}
