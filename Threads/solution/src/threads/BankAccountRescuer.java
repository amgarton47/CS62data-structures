package threads;

import java.util.List;

public class BankAccountRescuer extends BankAccountUser {
	
	BankAccountUser[] users;

	public BankAccountRescuer(String name, BankAccount account, List<Integer> transactions) {
		super(name, account, transactions);
	}	
	
	public BankAccountRescuer(String name, BankAccount account, BankAccountUser[] users) {
		super(name, account, null);
		this.users = users;
		
	}

	public void run() {
		while(!allFinished()) {
			if(allWaiting()) {
				for (BankAccountUser user: users) {
					if(!user.isFinished())
						user.getAccount().deposit(100, user);
				}
			}
	        try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		this.setOneMore(true);
		//????
		
		
	}
	
	public boolean allFinished() {
		for (BankAccountUser user: users) {
			if(!user.isFinished())
				return false;
		}
		return true;
	}
	public boolean allWaiting() {
		for (BankAccountUser user: users) {
			if(!user.isFinished())
				if(!user.getWaiting())
					return false;
		}
		return true;
	}
}
