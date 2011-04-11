package domain;


public abstract class AbstractOggettoEntita {
//	String nomeTabella;
//	String colonnaId;
	
	public String idEntita;
	public String nome;

	public String getIdEntita() {
		return idEntita;
	}

	public void setIdEntita(String idEntita) {
		this.idEntita = idEntita;
	}

	public abstract String getnome();
	
	

//	public ResultSet getResultSet(int id, String nomeTabella, String colonnaId) {
//		Connection cn = DBUtil.getConnection();
//		String sql = "SELECT * FROM "+nomeTabella+" WHERE "+colonnaId+" = " +id;
//		
//		ResultSet rs =null;
//		try {
//			
//			Statement st = cn.createStatement();
//			rs = st.executeQuery(sql);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				cn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return rs;
//	}

}
