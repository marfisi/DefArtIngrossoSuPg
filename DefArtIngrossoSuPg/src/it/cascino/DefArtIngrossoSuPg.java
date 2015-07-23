package it.cascino;

import it.cascino.dao.AsAnmag0fDao;
import it.cascino.dao.PgArticoliDao;
import it.cascino.dao.PgArticoliIngrossoDao;
import it.cascino.managmentbean.AsAnmag0fDaoMng;
import it.cascino.managmentbean.PgArticoliDaoMng;
import it.cascino.managmentbean.PgArticoliIngrossoDaoMng;
import it.cascino.model.AsAnmag0f;
import it.cascino.model.PgArticoli;
import it.cascino.model.PgArticoliIngrosso;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DefArtIngrossoSuPg{
	
	private static PgArticoliDao pGarticoliDao = new PgArticoliDaoMng();
	private static List<PgArticoli> pGarticoliLs;
	
	private static PgArticoliIngrossoDao pGarticoliIngrossoDao = new PgArticoliIngrossoDaoMng();
//	private static List<PgArticoliIngrosso> pGarticoliIngrossoLs;
	
	// anagrafica articoli all'ingrosso (anmaG) (non annullati logicamente)
	private static AsAnmag0fDao aSanmag0fDao = new AsAnmag0fDaoMng();
	private static List<AsAnmag0f> aSanmag0fLs;
	
	private static List<PgArticoliIngrosso> artIngrossoLs = new ArrayList<PgArticoliIngrosso>();
	
	static Logger log;
	
	public static void main(String args[]){
		PropertyConfigurator.configure("logdir/logDefArtIngrossoSuPg.properties");
		log = Logger.getLogger(DefArtIngrossoSuPg.class);
		
		// log.debug("Test Livello DEBUG");
		// log.info("Test Livello INFO");
		// log.warn("Test Livello WARNING");
		// log.error("Test Livello ERROR");
		// log.fatal("Test Livello FATAL");
		
		pGarticoliLs = pGarticoliDao.getAll();
		aSanmag0fLs = aSanmag0fDao.getArticoliIngrosso();
		
		Iterator<PgArticoli> iter_pGarticoli = pGarticoliLs.iterator();
		Iterator<AsAnmag0f> iter_aSanmag0f = aSanmag0fLs.iterator();
		while(iter_pGarticoli.hasNext()){
			PgArticoli pgArt = iter_pGarticoli.next();
			iter_aSanmag0f = aSanmag0fLs.iterator();
			while(iter_aSanmag0f.hasNext()){
				AsAnmag0f asArt = iter_aSanmag0f.next();
				if(StringUtils.equals(StringUtils.upperCase(pgArt.getCodice()), StringUtils.stripEnd(asArt.getMcoda(), " "))){
					artIngrossoLs.add(new PgArticoliIngrosso(null, pgArt.getId(), null));
					iter_aSanmag0f.remove();
					log.info("Rimossa: " + asArt.getMcoda());
					break;
				}
			}
		}
		
		gestisci_ArticoliIngrosso();
		
		pGarticoliDao.close();
		pGarticoliIngrossoDao.close();
		aSanmag0fDao.close();
	}
	
	static void gestisci_ArticoliIngrosso(){
		log.info("start: " + "gestisci_ArticoliIngrosso");
		pGarticoliIngrossoDao.svuotaTabella();
		Iterator<PgArticoliIngrosso> iter_artIng = artIngrossoLs.iterator();
		Integer cont = 0;
		while(iter_artIng.hasNext()){
			PgArticoliIngrosso art = iter_artIng.next();
			cont++;
			art.setId(cont);
			pGarticoliIngrossoDao.salva(art);
		}
		log.info("stop: " + "gestisci_ArticoliIngrosso");
	}
}
