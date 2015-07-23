package it.cascino.managmentbean;

import java.io.Serializable;
import java.util.List;
import it.cascino.model.PgArticoliIngrosso;
import it.cascino.model.PgArticoliIngrosso;
import it.cascino.dao.PgArticoliIngrossoDao;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;

public class PgArticoliIngrossoDaoMng implements PgArticoliIngrossoDao, Serializable{
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Postgresql");
	private EntityManager em = emf.createEntityManager(); // Retrieve an application managed entity manager
	private EntityTransaction utx = em.getTransaction();
	
	Logger log = Logger.getLogger(PgArticoliIngrossoDaoMng.class);
	
	@SuppressWarnings("unchecked")
	public List<PgArticoliIngrosso> getAll(){
		List<PgArticoliIngrosso> articoli = null;
		try{
			try{
				utx.begin();
				Query query = em.createNamedQuery("PgArticoliIngrosso.findAll");
				articoli = (List<PgArticoliIngrosso>)query.getResultList();
			}catch(NoResultException e){
				articoli = null;
			}
			utx.commit();
		}catch(Exception e){
			log.fatal(e.toString());
		}
		return articoli;
	}
	
	public void salva(PgArticoliIngrosso a){
		try{
			try{
				utx.begin();
//				a.setId(null);
				log.info("salva: " + a.toString());
				em.persist(a);
			}finally{
				utx.commit();
			}
		}catch(Exception e){
			log.fatal(e.toString());
		}
	}
	
	public void aggiorna(PgArticoliIngrosso a){
		try{
			try{
				utx.begin();
				log.info("aggiorna: " + a.toString());
				em.merge(a);
			}finally{
				utx.commit();
			}
		}catch(Exception e){
			log.fatal(e.toString());
		}
	}
	
	public void elimina(PgArticoliIngrosso aElimina){
		// log.info("tmpDEBUGtmp: " + "> " + "elimina(" + produttoreElimina + ")");
		try{
			try{
				utx.begin();
				PgArticoliIngrosso a = em.find(PgArticoliIngrosso.class, aElimina.getId());
				log.info("elimina: " + a.toString());
				em.remove(a);
			}finally{
				utx.commit();
			}
		}catch(Exception e){
			log.fatal(e.toString());
		}
	}
	
	public PgArticoliIngrosso getArticoloDaIdArticolo(Integer idArticolo){
		PgArticoliIngrosso articolo = null;
		try{
			try{
				utx.begin();
				Query query = em.createNamedQuery("PgArticoliIngrosso.findByIdArticolo");
				query.setParameter("idArticolo", idArticolo);
				articolo = (PgArticoliIngrosso)query.getSingleResult();
			}catch(NoResultException e){
				articolo = null;
			}
			utx.commit();
		}catch(Exception e){
			log.fatal(e.toString());
			utx.commit();
		}
		return articolo;
	}
	
	public void svuotaTabella(){
		try{
			try{
				utx.begin();
				Query query = em.createNamedQuery("PgArticoliIngrosso.svuota");
				query.executeUpdate();
			}finally{
				utx.commit();
			}
		}catch(Exception e){
			log.fatal(e.toString());
		}
	}
	
	public void close(){
		em.close();
		emf.close();
		log.info("chiuso");
	}
}
