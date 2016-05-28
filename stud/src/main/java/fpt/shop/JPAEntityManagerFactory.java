package fpt.shop;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.postgresql.fastpath.Fastpath;

public class JPAEntityManagerFactory {
	
	@PersistenceContext(unitName="openjpa")
	private EntityManagerFactory entityManagerFactory;
	
	public enum GetFacMethod {
		WithoutConfig,
		WithConfig
	}
	
	public EntityManagerFactory getWithoutConfig() {
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
			// <class>ftp.shop.Product</class>
			map.put("openjpa.MetaDataFactory", "jpa(Types=" + buf.toString()
					+ ")");
		}

		return OpenJPAPersistence.getEntityManagerFactory(map);
	}
	
	
	protected EntityManagerFactory getWithConfig() {
		  return Persistence.createEntityManagerFactory("openjpa");
	}
	
	public void updateProduct(fpt.com.Product p) {
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		
		Product pt = null;
		if ((pt = em.find(Product.class, p.getId())) != null) {
			System.out.println("Entity alread exists.");
			
			t.begin();
//			pt.setName(p.getName());
//			pt.setPrice(p.getPrice());
//			pt.setQuantity(p.getQuantity());
//			pt.setId(p.getId());
			em.merge(p);
			t.commit(); 
		} else {
			System.out.println("Entity not existed, use persist() to create new one.");

			t.begin();
			em.persist(p);
			t.commit(); 
		}
	}
	
	public Product readProduct(long productId) {
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		Product product = null;
		
		t.begin();
		Query query = em.createQuery("SELECT c FROM Product c WHERE c.id=" + productId);
		List resultList = query.getResultList();
		System.out.println("2222");
		for (Object o : resultList) {
			System.out.println(o);
			product = (Product) o;
			System.out.println("3333: " + product.getName());
		}
		t.commit();
	
		em.close();
		if (product != null) {
			return product;
		} else {
			return null;
		}
	}
	
	public boolean setup(GetFacMethod method) {
		
		if (method == GetFacMethod.WithoutConfig) {
			entityManagerFactory = getWithoutConfig();
		} else {
			entityManagerFactory = getWithConfig();
		}
		
		if (entityManagerFactory == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void closeFactory() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

}