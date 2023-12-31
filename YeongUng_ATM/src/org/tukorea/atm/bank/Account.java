package org.tukorea.atm.bank;

public class Account {
	private int accountId; // 계좌 번호
	private int balance; // 계좌 잔고
	private String accountName; // 고객 명
	private String password; // 계좌 비밀번호
	
	public Account(int id, int money, String name, String pwd) {
		accountId = id;
		balance = money;
		accountName = name;
		password = pwd;
	}
	boolean authenticate(int id, String passwd) { // 계정 확인
		boolean auth = false;
		if ((accountId == id) && password.equals(passwd)) 
			auth = true;
		
		return auth;
	}
	public int getAccountId() {
		return accountId;
	}
	public int getBalance() {
		return balance;
	}
	public int deposit(int money) {
		balance += money;
		return balance;
	}
	public int widraw(int money) {
		balance -= money;
		return balance;
	}
	public String getAccountName() {
		return accountName;
	}
}
