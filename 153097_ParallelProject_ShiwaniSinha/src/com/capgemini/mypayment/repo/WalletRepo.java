package com.capgemini.mypayment.repo;

import com.capgemini.mypayment.bean.Customer;
import com.capgemini.mypayment.exception.InvalidInputException;


public interface WalletRepo {

	 public boolean save(Customer customer)throws InvalidInputException;
			
			public Customer findOne(String mobileNo)throws InvalidInputException ;
		}

