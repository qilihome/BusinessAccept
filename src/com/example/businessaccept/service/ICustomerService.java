package com.example.businessaccept.service;

import java.util.List;

import com.example.businessaccept.entity.Customer;

public interface ICustomerService
{
	List<Customer> query(Customer customer) throws Exception;
}
