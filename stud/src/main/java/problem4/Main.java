package problem4;

public class Main {

	public static void main(String[] args) {
		// bring customers in.
		Thread acquisition = new Thread(new Acquisition());
		acquisition.start();
	}
}
