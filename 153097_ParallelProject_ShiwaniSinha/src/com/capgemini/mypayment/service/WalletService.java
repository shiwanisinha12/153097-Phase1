package com.capgemini.mypayment.service;

import java.math.BigDecimal;

import com.capgemini.mypayment.bean.Customer;
import com.capgemini.mypayment.exception.InsufficientBalanceException;
import com.capgemini.mypayment.exception.InvalidInputException;

public interface WalletService {
public Customer createAccount(String name ,String mobileno, BigDecimal amount)throws InvalidInputException;
public Customer showBalance (String mobileno)throws InvalidInputException;
public Customer fundTransfer (String sourceMobileNo,String targetMobileNo, BigDecimal amount)throws InvalidInputException;
public Customer depositAmount (String mobileNo,BigDecimal amount )throws InvalidInputException,InsufficientBalanceException;
public Customer withdrawAmount(String mobileNo, BigDecimal amount)throws InvalidInputException,InsufficientBalanceException;
public boolean isValid(Customer customer)throws InvalidInputException;

}

