package problem4;

import java.util.ArrayList;

public class Balance {
	private double gewinn[] = new double[6];
	
	private static Balance blanceInstance = null;
	protected Balance() {
	   
   }
   public static Balance getInstance() {
      if(blanceInstance == null) {
    	  blanceInstance = new Balance();
      }
      return blanceInstance;
   }
   
   public void resetKasseBalance(int id) {
	   if ((id >=0) && (id < 6)) {
		   gewinn[id] = 0;
		   System.out.println(gewinn[id]);
	   }
   }
	
	public void addBalance(int id, double Umsatz){
		gewinn[id] += Umsatz;
		ArrayList<Double> g = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			g.add(gewinn[i]);
		}
		
		//sortiert ausgeben
		int max = 0;
		int k = g.size();
		while (k-- > 0) {
			for (int i = 0; i < g.size(); i++) {
				if (g.get(i) >= 0) {
					if (g.get(i) > g.get(max)) {
						max = i;
					}
				}
			}
			
			if (g.get(max) >= 0) {
				System.out.print("Kasse " + (max + 1) + ": " + g.get(max) + "Euro");
				if (k > 0) {
					System.out.print(", ");
				}
				g.set(max, -1.0);
			}
			
			max = 0;
		}
		System.out.println();
	}
	
}
