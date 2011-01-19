package domain.wrapper;

import java.util.Iterator;
import java.util.Vector;


public interface IWrapperEntity {

	public Object selectById(int id);
	public Iterator<Object> selectWhere(String where);
	public Vector<Object> selectAll();
	public boolean insert(Object oggettoEntita);
	public boolean delete(int id);
	public boolean update(Object oggettoEntita);
	public boolean deleteAll();
}
