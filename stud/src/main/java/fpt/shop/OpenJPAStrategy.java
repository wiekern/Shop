package fpt.shop;

import java.util.Map;
import java.io.IOException;
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

import fpt.com.db.AbstractDatabaseStrategy;

public class OpenJPAStrategy extends AbstractDatabaseStrategy {
	
	@PersistenceContext(unitName="openjpa")
	private EntityManagerFactory entityManagerFactory;
	
	public enum GetFacMethod {
		WithoutConfig,
		WithConfig
	}
	private GetFacMethod confMethod;
	
	public OpenJPAStrategy() {
		this.confMethod = GetFacMethod.WithoutConfig;
	}
	
	public OpenJPAStrategy(GetFacMethod method) {
		this.confMethod = method;
		
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
	
	/**
	 * Update(merge) or insert(persist) product.
	 * @param p
	 */
	public void updateProduct(fpt.com.Product p) {
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction t = em.getTransaction();
		
		Product pt = null;
		if ((pt = em.find(Product.class, p.getId())) != null) {
			System.out.println("Entity already exists, to update it.");
			
			t.begin();
			em.merge(p);
			t.commit(); 
		} else {
			System.out.println("Entity not existed, use persist() to create new one.");

			t.begin();
			em.persist(p);
			//System.out.println(p.getId());
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
		for (Object o : resultList) {
			product = (Product) o;
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


	@Override
	public fpt.com.Product readObject() throws IOException {
		Product product = null; 
		try {
			long id = IDGenerator.generateId();
			if (id > 10) {
				IDGenerator.resetIDCounter();
				return null;
			}
			product = readProduct(id);
		} catch (IDOverflowException e) {
			System.out.println("OpenJPA: ID overflow.");
		}
		return product;
	}


	@Override
	public void writeObject(fpt.com.Product obj) throws IOException {
		updateProduct(obj);	
	}


	@Override
	public void close() throws IOException {
		closeFactory();
	}


	@Override
	public void open() throws IOException {
		setup(confMethod);
	}

}
