package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import entidades.Aluguel;
import entidades.Filme;

public class AluguelDAOImpl implements AluguelDAO{
	
	@Override
	public void insert(Connection conn, Aluguel aluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement(
				"insert into en_aluguel (id_aluguel, id_cliente, data_aluguel, valor) values (?, ?, ?, ?)");
		Integer idAluguel = this.getNextId(conn);
		myStmt.setInt(1, idAluguel);
		myStmt.setInt(2, aluguel.getCliente().getIdCliente());
		myStmt.setDate(3, this.convertJavaDateToSqlDate(aluguel.getDataAluguel()));
		myStmt.setFloat(4, aluguel.getValor());
		myStmt.execute();
		conn.commit();
		List<Filme> listaFilmes = aluguel.getFilmes();
		this.insertFilmes(idAluguel, listaFilmes, conn);

	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_aluguel')");
		ResultSet rs = myStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Aluguel aluguel) throws Exception {
		
		PreparedStatement myStmt = conn.prepareStatement("delete from re_aluguel_filme where id_aluguel = ?;"
				+ "update en_aluguel set id_cliente = (?), data_aluguel = (?), valor = (?)"
				+ "where id_aluguel = (?)");
		myStmt.setInt(1, aluguel.getIdAluguel());
		myStmt.setInt(2, aluguel.getCliente().getIdCliente());
		myStmt.setDate(3, this.convertJavaDateToSqlDate(aluguel.getDataAluguel()));
		myStmt.setFloat(4,  aluguel.getValor());
		myStmt.setInt(5, aluguel.getIdAluguel());
		myStmt.execute();
		conn.commit();
		
		this.insertFilmes(aluguel.getIdAluguel(), aluguel.getFilmes(), conn);
		
	}

	@Override
	public void delete(Connection conn, Aluguel aluguel) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("delete from re_aluguel_filme where id_aluguel = ?;"
				 + "delete from en_aluguel where id_aluguel = ?;");
		myStmt.setInt(1, aluguel.getIdAluguel());
		myStmt.setInt(2, aluguel.getIdAluguel());
		myStmt.execute();
		conn.commit();
	}

	@Override
	public Aluguel find(Connection conn, Integer idAluguel) throws Exception {

		FilmeDAO filmeDAO = new FilmeDAOImpl();
		ClienteDAO clienteDAO = new ClienteDAOImpl();
		List<Filme> listaFilmes = new ArrayList<>();
		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel where id_aluguel = ?");
		PreparedStatement myStmtFilmes = conn.prepareStatement("select * from re_aluguel_filme where id_aluguel = ?");

		myStmt.setInt(1, idAluguel);
        ResultSet myRs = myStmt.executeQuery();
        myStmtFilmes.setInt(1, idAluguel);
        ResultSet myRsFilmes = myStmtFilmes.executeQuery();
        
        if(!myRsFilmes.next()) {
        	if(myRs.next()) {
        		return new Aluguel(idAluguel, null, clienteDAO.find(conn, myRs.getInt("id_cliente")), myRs.getDate("data_aluguel"), myRs.getFloat("valor"));
        	} else {
        		return null;
        	}
        } else {
        	myRs.next();
        }
 
        do {
        	Filme f = filmeDAO.find(conn, myRsFilmes.getInt("id_filme"));
        	if(f != null) {
        		listaFilmes.add(f);
        	}
        } while(myRsFilmes.next());   
        return new Aluguel(idAluguel, listaFilmes, clienteDAO.find(conn, myRs.getInt("id_cliente")), myRs.getDate("data_aluguel"), myRs.getFloat("valor"));

	}

	@Override
	public Collection<Aluguel> list(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_aluguel order by id_aluguel");
        ResultSet myRs = myStmt.executeQuery();
        Collection<Aluguel> items = new ArrayList<>();    
        while (myRs.next()) {
        	items.add(this.find(conn, myRs.getInt("id_aluguel")));
        } 
        return items;
	}
	
	private java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	private void insertFilmes(Integer idAluguel, List<Filme> lista, Connection conn) throws SQLException {
		for(Filme f : lista) {
			PreparedStatement myStmtFilme = conn.prepareStatement(
					"insert into re_aluguel_filme (id_filme, id_aluguel) values (?, ?)");
			myStmtFilme.setInt(1, f.getIdFilme());
			myStmtFilme.setInt(2, idAluguel);
			myStmtFilme.execute();
		}
		conn.commit();
	}

}
