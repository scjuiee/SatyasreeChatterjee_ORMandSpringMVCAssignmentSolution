package com.greatlearning.crmapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.crmapp.entity.Customer;
import com.greatlearning.crmapp.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	// use @Qualifier if you happen to have more than 1 candidate bean that can be
	// injected
	private CustomerService customerService;

	@RequestMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> custs = customerService.findAll();
		model.addAttribute("customers", custs);

		return "list-customers";
	}

	@RequestMapping("/new")
	public String showFormForAdd(Model model) {
		Customer cust = new Customer();

		model.addAttribute("newCustomer", true);
		model.addAttribute("customer", cust);

		return "edit-customer";
	}

	@RequestMapping("/edit")
	public String showFormForEdit(@RequestParam("id") int id, Model model) {
		Customer cust = customerService.findById(id);

		model.addAttribute("newCustomer", false);
		model.addAttribute("customer", cust);

		return "edit-customer";
	}

	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("emailId") String emailId) {
		Customer cust = null;

		if (id == 0) {
			cust = new Customer(firstName, lastName, emailId);
		} else {
			cust = customerService.findById(id);
			cust.setFirstName(firstName);
			cust.setLastName(lastName);
			cust.setEmailId(emailId);
		}

		customerService.save(cust);

		return "redirect:/customer/list";
	}

	@RequestMapping("/delete")
	public String deleteCustomer(@RequestParam("id") int id) {
		customerService.deleteById(id);

		return "redirect:/customer/list";
	}
}
