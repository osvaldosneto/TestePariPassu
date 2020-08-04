package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import dao.FilmeDAO;
import entidades.Filme;

public class FilmeDAOImpl implements FilmeDAO {

	@Override
	public void insert(Connection conn, Filme filme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement(
				"insert into en_filme (id_filme, data_lancamento, nome, descricao) values (?, ?, ?, ?)");
		Integer idFilme = this.getNextId(conn);
		myStmt.setInt(1, idFilme);
		myStmt.setDate(2, this.convertJavaDateToSqlDate(filme.getDataLancamento()));
		myStmt.setString(3, filme.getNome());
		myStmt.setString(4, filme.getDescricao());
		myStmt.execute();
		conn.commit();
		filme.setIdFilme(idFilme);
	}

	@Override
	public Integer getNextId(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select nextval('seq_en_filme')");
		ResultSet rs = myStmt.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	@Override
	public void edit(Connection conn, Filme filme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("update en_filme set nome = (?), " + "data_lancamento = (?), "
				+ "descricao = (?) " + "where id_filme = (?)");

		myStmt.setString(1, filme.getNome());
		myStmt.setDate(2, this.convertJavaDateToSqlDate(filme.getDataLancamento()));
		myStmt.setString(3, filme.getDescricao());
		myStmt.setInt(4, filme.getIdFilme());
		myStmt.execute();
		conn.commit();
	}

	@Override
	public void delete(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("delete from re_aluguel_filme where id_filme = ?;"
				+ "delete from en_filme where id_filme = ?;");
		myStmt.setInt(1, idFilme);
		myStmt.setInt(2, idFilme);
		myStmt.execute();
		conn.commit();
	}

	@Override
	public Filme find(Connection conn, Integer idFilme) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_filme where id_filme = ?");
		myStmt.setInt(1, idFilme);
		ResultSet myRs = myStmt.executeQuery();

		if (!myRs.next()) {
			return null;
		}
		return new Filme(idFilme, myRs.getDate("data_lancamento"), myRs.getString("nome"), myRs.getString("descricao"));
	}

	@Override
	public Collection<Filme> list(Connection conn) throws Exception {
		PreparedStatement myStmt = conn.prepareStatement("select * from en_filme order by nome");
		ResultSet myRs = myStmt.executeQuery();
		Collection<Filme> items = new ArrayList<>();

		while (myRs.next()) {
			items.add(this.find(conn, myRs.getInt("id_filme")));
		}
		return items;
	}

	private java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

}
