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
		try {
			Thread.sleep(6000);
			System.out.println("[Kasse " + (this.getId() + 1) + "] opened.");
		} catch (InterruptedException e1) {
			System.out.println("Sleep 6 seconds to simulate opening a Kasse, but failed.");
			e1.printStackTrace();
		}
		Customer customer;
		synchronized(this) {
			while(true) {
				long sleepTime = Math.round(Math.random()*4 + 6) * 1000;
				
					try {
						if (isEmpty()) {
							System.out.println("[Kasse " + (this.id + 1) + "] could be closed.\n");
							//Balance.getInstance().resetKasseBalance(this.id);
							return ;
						} else {
							Thread.sleep(sleepTime);
							customer = dequeue();
							synchronized(Balance.class) {
								Balance.getInstance().addBalance(this.id, customer.getSum());
							}
							System.out.println("Kasse " + (this.id + 1) + ": " + this.size() + " customers are waiting.");
						}
					} catch (InterruptedException e) {
						System.out.println("Kasse is sleeping but interrupted.");
					}
			}
		}
	}
	

}
