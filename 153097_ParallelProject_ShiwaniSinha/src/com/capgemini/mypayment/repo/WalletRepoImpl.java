package com.capgemini.mypayment.repo;

import java.util.HashMap;
import java.util.Map;

import com.capgemini.mypayment.bean.Customer;
import com.capgemini.mypayment.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo{

	Customer customer;

	
	private Map<String, Customer> data; 
	{
		data=new HashMap<String, Customer>();
	}
	public WalletRepoImpl(Map<String, Customer> data) {
		//super();
		this.data = data;
		
	}
	public WalletRepoImpl() {
		
	}
	@Override
	public boolean save(Customer customer) {
		
		data.put(customer.getMobileNo(), customer);
		return true;
		
	}

	@Override
	public Customer findOne(String mobileNo) throws InvalidInputException {
	
		customer=data.get(mobileNo);
		
		if(customer==null)
			throw new InvalidInputException("Account not found!");
			//System.out.println("Account not found!");
		else
		{
			return customer;
		}
		
}
}