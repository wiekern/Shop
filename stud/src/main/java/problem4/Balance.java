package problem4;

public class Balance {
	private long cashpointId;
	private double netIncome;	//Umsatz
	
	private static Balance blanceInstance = null;
	protected Balance() {
	   
   }
   public static Balance getInstance() {
      if(blanceInstance == null) {
    	  blanceInstance = new Balance();
      }
      return blanceInstance;
   }
   
	public long getCashpointId() {
		return cashpointId;
	}
	public void setCashpointId(long cashpointId) {
		this.cashpointId = cashpointId;
	}
	public double getNetIncome() {
		return netIncome;
	}
	public void setNetIncome(double netIncome) {
		this.netIncome = netIncome;
	}
}
