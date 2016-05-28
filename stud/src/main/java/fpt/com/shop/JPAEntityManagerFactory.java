package fpt.com.shop;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.openjpa.persistence.OpenJPAPersistence;

public class JPAEntityManagerFactory {
	
	public static EntityManagerFactory getWithoutConfig() {

		Map<String, String> map = new HashMap<String, String>();

		map.put("openjpa.ConnectionURL",
				"jdbc:postgresql://java.is.uni-due.de/ws1011");
		map.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");
		map.put("openjpa.ConnectionUserName", "ws1011");
		map.put("openjpa.ConnectionPassword", "ftpw10");
		map.put("openjpa.RuntimeUnenhancedClasses", "supported");
		map.put("openjpa.jdbc.SynchronizeMappings", "false");

		// find all classes to registrate them
		List<Class<?>> types = new ArrayList<Class<?>>();
		types.add(Product.class);

		if (!types.isEmpty()) {
			StringBuffer buf = new StringBuffer();
			for (Class<?> c : types) {
				if (buf.length() > 0)
					buf.append(";");
				buf.append(c.getName());
			}
			// <class>Producer</class>
			map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()
					+ ")");
		}

		return OpenJPAPersistence.getEntityManagerFactory(map);

	}
	
	
	public static void JPAPersistenceProduct(Product p) {
		EntityManagerFactory fac = getWithoutConfig();
		//EntityManagerFactory fac = Persistence.createEntityManagerFactory(
		//		"openjpa", System.getProperties());

		EntityManager e = fac.createEntityManager();

		EntityTransaction t = e.getTransaction();
		 t.begin();
		 e.persist(p);
		 t.commit(); // all ok commit
		// all Data is saved in database now
		t.begin();
		// QBE
		Query query = e.createQuery("SELECT c FROM Product c WHERE c.id=" + p.getId());
		System.out.println("1111");
		List resultList = query.getResultList();
		System.out.println("2222");
		for (Object o : resultList) {
			System.out.println(o);
			Product c = (Product) o;
			System.out.println("3333" + c.getName());
		}
		t.commit(); // all ok commit
		
		e.close();
		fac.close();
		System.out.println("44444");
	}

}
