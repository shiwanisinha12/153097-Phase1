package com.capgemini.mypayment.service;

import java.math.BigDecimal;
import java.sql.Savepoint;
import java.util.Map;
import java.util.Scanner;

import com.capgemini.mypayment.bean.Customer;
import com.capgemini.mypayment.bean.Wallet;
import com.capgemini.mypayment.exception.InsufficientBalanceException;
import com.capgemini.mypayment.exception.InvalidInputException;
import com.capgemini.mypayment.repo.WalletRepo;
import com.capgemini.mypayment.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{


WalletRepo repo =new WalletRepoImpl();	
Customer customer;
Customer customer1;
Wallet wallet,wallet1;
	
	public WalletServiceImpl() {
		super();
	}
	public WalletServiceImpl(Map<String, Customer> data){
		repo= new WalletRepoImpl(data);
	}
	public boolean isValid(Customer customer) throws InvalidInputException
	{
		Scanner sc=new Scanner(System.in);
		if(customer.getName().matches("[A-Z][a-z]*")){
			if(String.valueOf(customer.getMobileNo()).matches("[6-9][0-9]{9}") && customer.getMobileNo()!=null)
			{
			return true;
			}
			else
			{
			System.err.println("Invalid phone number");
			System.out.println("Enter the phone number again");
			customer.setMobileNo(sc.next());
			return false;
			}
		}
		else
		{
			System.err.println("Name should contain alphabets");
			System.out.println("Enter correct name :");
			customer.setName(sc.next());
			return false;
		}
		}
	
			
	

	
	@Override
	public Customer createAccount(String name, String mobileno,
			BigDecimal amount) throws InvalidInputException {
	
		Customer customer=new Customer(name,mobileno,new Wallet(amount));
		boolean b;
		do
			{ 
			b=isValid(customer);
			}while(!b);
		if(b)
		{
		boolean res=repo.save(customer);
			System.out.println("Account created");
			return customer;
		}
		else
		{
			throw new InvalidInputException("Incorrect Input exception");
		}
	
		
		
	
	}
	@Override
	public Customer showBalance(String mobileno) throws InvalidInputException  {
		
		
		customer=repo.findOne(mobileno);
		
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}
	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo,
			BigDecimal amount) throws InvalidInputException {
		customer=repo.findOne(sourceMobileNo);
		String srcMobNo=customer.getMobileNo();
		wallet=customer.getWallet();
		BigDecimal bal1=wallet.getBalance();
		//System.out.println(bal1);
		customer1=repo.findOne(targetMobileNo);
		String targetMob=customer1.getMobileNo();
		wallet1=customer1.getWallet();
		BigDecimal bal2=wallet1.getBalance();
		
		if(amount.compareTo(bal1)>=0)
		{
			System.out.println("Balance is low.You cant transfer");
		}
		else {
		bal1=bal1.subtract(amount);
		bal2=bal2.add(amount);
		wallet.setBalance(bal1);
		customer.setWallet(wallet);
		wallet1.setBalance(bal2);
		customer1.setWallet(wallet1);
		}
		return customer ;
		}
	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount)throws InvalidInputException  {
		
		customer=repo.findOne(mobileNo);
		if(customer==null)
			throw new InvalidInputException("Account does not exist");
		
		else
		{
			wallet=customer.getWallet();
			BigDecimal bal=wallet.getBalance();
			bal=bal.add(amount);
			wallet.setBalance(bal);
			customer.setWallet(wallet);
		}
		return customer;
	}
	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount)throws InsufficientBalanceException, InvalidInputException{
		BigDecimal minBal=new BigDecimal("1000");
		customer=repo.findOne(mobileNo);
		 wallet=customer.getWallet();
			BigDecimal bal=wallet.getBalance();
			if(amount.compareTo(bal)>=0)
			{
				//System.out.println("Insufficient Balance.You cant withdraw!");
				throw new InsufficientBalanceException("Insufficient Balance.You cant withdraw!");
			}
			else {
				if(bal.compareTo(minBal)<=0)
				{
					throw new InsufficientBalanceException("Insufficient Balance.You cant withdraw!");
				}
				else
				{
				bal=bal.subtract(amount);
				wallet.setBalance(bal);
				customer.setWallet(wallet);
			}
			
		}
		
		return customer;
	
	}

}
