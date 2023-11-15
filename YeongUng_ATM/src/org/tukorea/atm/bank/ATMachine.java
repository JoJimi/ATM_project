package org.tukorea.atm.bank;

import java.util.*;

import org.tukorea.atm.util.Statistics;

public class ATMachine {
	private Account [] accountArray; // 고객계좌배열 참조변수
	private int machineBalance; // ATM 잔고
	private int maxAccountNum; // 고객계좌 참조변수 배열크기
	private int currentAccountNum = 0; // 개설된 고객계좌 수
	private String managerPassword; // 관리자 비밀번호
	public static final int BASE_ACCOUNT_ID = 100; // 고객계좌 발급 시 최소 번호
	
	// 랜덤으로 계좌 번호 발급 – Math.random()
	// 범위 : 100 부터 (계좌 발급시 개설가능한 최대 계좌 수 * 2) 번호 까지 부여
	public ATMachine(int size, int balance, String password) { // 생성자
		accountArray = new Account[size];
		maxAccountNum = size;
		machineBalance = balance;
		managerPassword = password;
	}
	
	public void createAccount() { // 계좌 개설
		Scanner sc = new Scanner(System.in);
		System.out.print("이름 입력: ");
		String name = sc.nextLine();
		System.out.print("암호 입력: ");
		String pwd = sc.next();
		int AccountNumber = (int) (Math.random() * maxAccountNum * 2) + BASE_ACCOUNT_ID;
		
		accountArray[currentAccountNum] = new Account(AccountNumber, 0, name, pwd);
		currentAccountNum++;
		System.out.println(name + "님 " + AccountNumber + "번 계좌번호가 정상적으로 개설되었습니다. 감사합니다.");
		System.out.println();
	}
	
	public void checkMoney() { // 계좌 조회
		Scanner sc = new Scanner(System.in);
		System.out.print("계좌번호 입력: ");
		int Number = sc.nextInt();
		System.out.print("비밀번호 입력: ");
		String pwd = sc.next();
		
		for(int i = 0; i < currentAccountNum; i++) {
			if(accountArray[i].authenticate(Number, pwd)) {
				System.out.println("계좌 잔액 : " + accountArray[i].getBalance());
				break;
			}
		}
		System.out.println();
	}
	
	public void depositMoney() { //계좌 입금
		Scanner sc = new Scanner(System.in);
		System.out.print("계좌번호 입력: ");
		int Number = sc.nextInt();
		System.out.print("비밀번호 입력: ");
		String pwd = sc.next();
		for(int i = 0; i < currentAccountNum; i++) {
			if(accountArray[i].authenticate(Number, pwd)) {
				System.out.print("입금액 입력: ");
				int money = sc.nextInt();
				System.out.println("입금 후 잔액: " + accountArray[i].deposit(money));
				machineBalance += money;
				break;
			}
		}
	}
	
	public void widrawMoney() { //계좌 출금
		Scanner sc = new Scanner(System.in);
		System.out.print("계좌번호 입력: ");
		int Number = sc.nextInt();
		System.out.print("비밀번호 입력: ");
		String pwd = sc.next();
		for(int i = 0; i < currentAccountNum; i++) {
			if(accountArray[i].authenticate(Number, pwd)) {
				System.out.print("출금액 입력: ");
				int money = sc.nextInt();
				if(money < accountArray[i].getBalance()) {
					System.out.println("출금 후 잔액: " + accountArray[i].widraw(money));
					machineBalance -= money;
				}
				else
					System.out.println("출금하고자 하는 금액이 잔여 금액보다 많습니다. 다시 입력해주세요.");
				break;
			}
		}
	}
	
	public void transfer() {
		Scanner sc = new Scanner(System.in);
		System.out.print("계좌번호 입력: ");
		int Number = sc.nextInt();
		System.out.print("비밀번호 입력: ");
		String pwd = sc.next();
		System.out.print("이체계좌 입력: ");
		int transNumber = sc.nextInt();
		System.out.print("이체금액 입력: ");
		int transMoney = sc.nextInt();
		
		loopOut:
		for(int i = 0; i < currentAccountNum; i++) {
			if(accountArray[i].authenticate(Number, pwd)) {			
				for(int j = 0; j < currentAccountNum; j++) {
					if(accountArray[j].getAccountId() == transNumber) {
						accountArray[i].widraw(transMoney);
						accountArray[j].deposit(transMoney);
						System.out.println("현재 잔액: " + accountArray[i].getBalance());
						System.out.println("계좌 이체를 완료하였습니다.");
						break loopOut;
					}
				}
			}
			else {
				System.out.println("계좌번호나 비밀번호가 틀렸습니다. 다시 입력해주세요.");
				break loopOut;
			}
		}		
	}
	
	public void managerMode() {
		Account[] arr = new Account[currentAccountNum];
		Scanner sc = new Scanner(System.in);
		System.out.println("--------고객관리--------");
		System.out.print("관리자 비밀번호 입력: ");
		String pwd = sc.next();
		if(managerPassword.equals(pwd)) {
			System.out.println("ATM 현금 잔고:\t" + machineBalance);
			System.out.println("고객 잔고 총액:\t" + Statistics.sum(accountArray, currentAccountNum)+ "원(" + currentAccountNum + "명)");
			System.out.println("고객 잔고 평균:\t" + (int)Statistics.average(accountArray, currentAccountNum) + "원");
			System.out.println("고객 잔고 최고:\t" + Statistics.max(accountArray, currentAccountNum) + "원");
			System.out.println("고객 계좌 현황(고객 잔고 내림 차순 정렬)");
			arr = Statistics.sort(accountArray, currentAccountNum);
			for(int i = 0; i < currentAccountNum; i++) {
				System.out.println(arr[i].getAccountName() + "\t\t"
								+ arr[i].getAccountId() + "\t"
								+ arr[i].getBalance() + "원");
			}
		}
	}
	
	public void displayMenu() { // 메인 메뉴 표시
		System.out.println("----------------------");
		System.out.println("-    TUKOREA BANK    -");
		System.out.println("----------------------");
		System.out.println("1. 계좌 개설");
		System.out.println("2. 계좌 조회");
		System.out.println("3. 계좌 입금");
		System.out.println("4. 계좌 출금");
		System.out.println("5. 계좌 이체");
		System.out.println("8. 고객 관리");
		System.out.println("9. 업무 종료");
	}
	
}
