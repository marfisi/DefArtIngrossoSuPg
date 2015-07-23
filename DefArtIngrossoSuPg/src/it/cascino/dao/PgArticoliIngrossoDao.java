package it.cascino.dao;

import java.util.List;
import it.cascino.model.PgArticoliIngrosso;

public interface PgArticoliIngrossoDao{
	List<PgArticoliIngrosso> getAll();
	
	void salva(PgArticoliIngrosso a);
	
	void aggiorna(PgArticoliIngrosso a);
	
	void elimina(PgArticoliIngrosso a);

	PgArticoliIngrosso getArticoloDaIdArticolo(Integer idArticolo);
		
	void svuotaTabella();
	
	void close();
}
