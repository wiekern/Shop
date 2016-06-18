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
		while(true) {
			long sleepTime = Math.round(Math.random()*4 + 6) * 1000;
			synchronized(Balance.class) {
				try {
					if (isEmpty()) {
						System.out.println("[Kasse " + (this.id + 1) + "] could be closed.\n");
						//Balance.getInstance().resetKasseBalance(this.id);
						return ;
					} else {
						
							Thread.sleep(sleepTime);
							customer = dequeue();
							Balance.getInstance().addBalance(this.id, customer.getSum());
					}
				} catch (InterruptedException e) {
					System.out.println("Kasse is sleeping but interrupted.");
				}
			}
		}
	}
	

}
