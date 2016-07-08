package problem4;

public class Acquisition implements Runnable {
	private Cashpoint[] cashpointArray;
	private Thread[] cashpointThreadArray;
	
	public Acquisition() {
		this.cashpointArray = new Cashpoint[6];
		this.cashpointThreadArray = new Thread[6];
	}
	
	public void initArrays() {
		for(int i = 0; i < 6; i++) {
			cashpointArray[i] = new Cashpoint(i);
			cashpointThreadArray[i] = new Thread(cashpointArray[i]);
		}	
	}
	
	/*
	 * Do all 6 cashpoints have 6 customers in the queue? 
	 */
	public boolean isAllCashpointsLimited() {
		for(Cashpoint cp: cashpointArray) {
			if (!cp.isLimited()) {
				return false;
			}
		}
		
		return true;
	}
	
	/* 
	 * return the first available cashpoint. If all 6 cashpoints are limited,
	 * i.e. 6 customers in them, return cashpoint 0.
	 */
	public Cashpoint currentAvailableCashpoint() {
		int customerCount = 6;
		Cashpoint cashpoint = null;
		for(Cashpoint cp: cashpointArray) {
			if (!cp.isLimited()) {
				if ((cp.size() < customerCount) && (cp.size() > 0)) {
					customerCount = cp.size();
					cashpoint = cp; 
				}
			}
		}
		
		if (cashpoint == null) {
			for(Cashpoint cp: cashpointArray) {
				if (!cp.isLimited()) {
					System.out.println("+++++++++ New Kasse +++++++++");
					System.out.println("All opened Kassen limited, opening Kasse " + (cp.getId() + 1));
					System.out.println("+++++++++ End Kasse +++++++++");
					return cp;
				}
			}
		} else {
			System.out.println("Current available Kasse " + (cashpoint.getId() + 1));
			return cashpoint;
		}
		
		if(cashpointArray[0].isFull()) {
			return null;
		} else {
			return cashpointArray[0];
		}
	}
	
	@Override
	public void run() {
		initArrays();
		int customerCount = 1;
		Cashpoint currCashpoint = currentAvailableCashpoint();
		Customer customer;
		while(true) {
			long sleepTime = Math.round(Math.random()*2) * 1000; //[0,2]
			try {
				if (currCashpoint.isFull()) {
					System.out.println("Kasse " + (currCashpoint.getId() + 1) + " is full. exit and no more customers.");
					return ;
				} else if (currCashpoint.isLimited()) {
					System.out.println("[Kasse " + (currCashpoint.getId() + 1) + "] has 6 customers, to choose a available Kasse.");
					currCashpoint = currentAvailableCashpoint();
				}
				
				Thread.sleep(sleepTime);
				customer = new Customer("Customer" + customerCount, Math.round(Math.random()*90) + 1);
				currCashpoint.enqueue(customer);
				System.out.println("Acquisition spent " + sleepTime/1000 + " seconds. Puted customer in Kasse " + (currCashpoint.getId() + 1));
				customerCount++;
				
				if (!cashpointThreadArray[currCashpoint.getId()].isAlive()) {
					cashpointThreadArray[currCashpoint.getId()].start();
				}		
				
			} catch (InterruptedException e) {
				System.out.println("A interrupt came.");
			}
			
		}
	}
}
