package problem4;

public class Customer {
	private String name;
	private double sum;
	
	public Customer() {
		this("", 0);
	}
	
	public Customer(String name, double sum) {
		this.name = name;
		this.sum = sum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}
}
