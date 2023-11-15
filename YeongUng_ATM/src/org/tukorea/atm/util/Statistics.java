package org.tukorea.atm.util;

import org.tukorea.atm.bank.Account;

public class Statistics {
	public static int sum(Account [] account, int size) {		
		int res = 0;
		for(int i = 0; i < size; i++) {
			res += account[i].getBalance();
		}
		return res;
	}
	public static double average(Account [] account, int size) {
		int res = 0;
		for(int i = 0; i < size; i++) {
			res += account[i].getBalance();
		}
		return res/size;
	}
	public static int max(Account [] account, int size) {
		int res = account[0].getBalance();
		for(int i = 0; i < size; i++) {
			if(res <= account[i].getBalance()) {
				res = account[i].getBalance();
			}
		}
		return res;
	}
	public static Account [] sort(Account [] account, int size) {
		Account[] arr = account;
		Account temp;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(arr[i].getBalance() > arr[j].getBalance()) {
					temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		
		return arr;
	}
}
