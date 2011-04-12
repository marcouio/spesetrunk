package domain.wrapper;

import java.util.Iterator;
import java.util.Vector;

import domain.AbstractOggettoEntita;


public interface IWrapperEntity {

	public AbstractOggettoEntita getentitaPadre();
	public Object selectById(int id);
	public Iterator<Object> selectWhere(String where);
	public Vector<Object> selectAll();
	public boolean insert(Object oggettoEntita);
	public boolean delete(int id);
	public boolean update(Object oggettoEntita);
	public boolean deleteAll();
}
