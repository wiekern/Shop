package problem4;

public class Cashpoint extends WaitingQueue<Customer> implements Runnable {
	private int id;
	private Balance balance = Balance.getInstance();
	private boolean running = false;
	
	public Cashpoint() {
		
	}
	
	public Cashpoint(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Balance getBalance() {
		return this.balance;
	}
	
	public boolean isRunning() {
		return running;
	}

	@Override
	public void run() {
		Customer customer;
		running = true;
		while(true) {
			long sleepTime = Math.round(Math.random()*4 + 6) * 1000;
			//long sleepTime = Math.round(Math.random()*4) * 1000;
			synchronized(balance) {
				try {
					if (isEmpty()) {
						running = false;
						System.out.println("[Cashpoint " + this.id + "] could be closed.\n");
						return ;
					} else {
						balance.setCashpointId(this.id);
						balance.setNetIncome(200);
						System.out.println("------------- Waitqueue " + this.id + " start -------------");
						System.out.println("[" + this.size() + " customers] are in the waitqueue [" +  this.id + "].");
						System.out.println("------------- Waitqueue " + this.id + " end   -------------");
						System.out.println("Cashpoint " + balance.getCashpointId() + ": " + balance.getNetIncome() + ".");
						Thread.sleep(sleepTime);
						customer = dequeue();
						System.out.println("------------- Finished " + this.id + " start -------------");
						System.out.println("[" + customer.getName() + "] finished at Cashpoint " + this.id + " for [" + sleepTime/1000 + "] seconds.");
						System.out.println("------------- Finished " + this.id + " start -------------");
					}
				} catch (InterruptedException e) {
					System.out.println("Cashpoint is sleeping but interrupted.");
				}
				
				//System.out.println("------------- Cashpoint " + this.id + "-------------");
			}
		}
	}
	

}
