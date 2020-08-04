import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Date;
import java.util.List;

import dao.AluguelDAO;
import dao.ClienteDAO;
import dao.FilmeDAO;
import dao.jdbc.AluguelDAOImpl;
import dao.jdbc.ClienteDAOImpl;
import dao.jdbc.FilmeDAOImpl;
import entidades.Aluguel;
import entidades.Cliente;
import entidades.Filme;


public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/teste", "postgres", "123456");
            conn.setAutoCommit(false);

            ClienteDAO clienteDAO = new ClienteDAOImpl();
            FilmeDAO filmeDAO = new FilmeDAOImpl();
            AluguelDAO aluguelDAO = new AluguelDAOImpl();
            
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1 = formato.parse("10/02/2020"); 
            java.util.Date d2 = formato.parse("15/01/2019"); 
            java.util.Date d3 = formato.parse("15/05/2018");
            Filme filme1 = null;
            Cliente cliente = null;
            Aluguel aluguel = null;
            Collection<Filme> filmes = new ArrayList<>();
            Collection<Cliente> clientes = new ArrayList<>();
            Collection<Aluguel> alugueis = new ArrayList<>();
            
            
            
            //find Filme
	        filme1 = filmeDAO.find(conn, 1);
	        System.out.println("FIND FILME");
	        System.out.println(filme1.toString());
	        
	        //findAll Filme
	        filmes = filmeDAO.list(conn);
	        System.out.println("FINDALL FILME");
	        for(Filme f : filmes) {
	        	System.out.println(f.toString());
	        }
	        
	        //deletando Filme
	        System.out.println("DELETE FILME");
            filmeDAO.delete(conn, 3);
            List<Filme> filmes2 = (List<Filme>) filmeDAO.list(conn);
            for(Filme f : filmes2) {
            	System.out.println(f.toString());
            }
            
            //inserindo Filme
            System.out.println("INSERT FILME");
            Filme f1 = new Filme(null, d1, "As Aventuras de Guliver", "ficção cientifica");
            filmeDAO.insert(conn, f1);
            filmes = filmeDAO.list(conn);
            for(Filme f : filmes) {
            	System.out.println(f.toString());
            }
            
	        //editando Filme
            System.out.println("EDIT FILME");
	        Filme f2 = filmeDAO.find(conn, 1);
	        f2.setDataLancamento(d2);
	        f2.setDescricao("Aventura");
	        f2.setNome("Avatar");
	        filmeDAO.edit(conn, f2);
	        System.out.println(filmeDAO.find(conn, 1).toString());

	        
	        //find Cliente
	        System.out.println("FIND CLIENTE");
	        cliente = clienteDAO.find(conn, 1);
	        System.out.println(cliente.toString());
	        
	        //findAll Cliente
	        System.out.println("FINDALL CLIENTE");
	        clientes = (List<Cliente>) clienteDAO.list(conn);
	        for(Cliente c : clientes) {
	        	System.out.println(c.toString());
	        }
	        
	        //delete Cliente
	        //se deletarmos o cliente perdemos todos os dados da reserva portando acredito que não seja uma boa opção deleta-la
	        /*clienteDAO.delete(conn, 1);
	        List<Cliente> listaCliente2 = (List<Cliente>) clienteDAO.list(conn);
	        for(Cliente c : listaCliente2) {
	        	System.out.println(c.toString());
	        }  */
	        
	        //edit Cliente
	        System.out.println("EDIT CLIENTE");
	        Cliente c2 = clienteDAO.find(conn, 1);
	        c2.setNome("Jonatham");
	        clienteDAO.edit(conn, c2);
	        System.out.println(clienteDAO.find(conn, 1).toString());
	        
	        //insert Cliente
	        System.out.println("INSERT CLIENTE");
	        cliente = new Cliente("Alfredo");
	        clienteDAO.insert(conn, cliente);
	        clientes = (List<Cliente>) clienteDAO.list(conn);
	        for(Cliente c : clientes) {
	        	System.out.println(c.toString());
	        }
	        
	        //find aluguel
	        System.out.println("FIND ALUGUEL");
	        aluguel = aluguelDAO.find(conn, 4);
	        System.out.println(aluguel.toString());      
	          
	        //find all
	        System.out.println("FINDALL ALUGUEL");
	        alugueis = aluguelDAO.list(conn);
	        for(Aluguel a : alugueis) {
	        	System.out.println(a.toString());
	        }
	        
	        //delete aluguel
	        System.out.println("DELETE ALUGUEL");
	        aluguelDAO.delete(conn, aluguelDAO.find(conn, 3));
	        alugueis = aluguelDAO.list(conn);
	        for(Aluguel a : alugueis) {
	        	System.out.println(a.toString());
	        }
            
            //adicionando aluguel
	        System.out.println("INSERINDO ALUGUEL");
            Filme f4 = filmeDAO.find(conn, 3);
	        Filme f5 = filmeDAO.find(conn, 2);
	        List<Filme> listaFilmes2 = new ArrayList<>();
	        listaFilmes2.add(f4);
	        listaFilmes2.add(f5);
	        cliente = clienteDAO.find(conn, 2);
	        Aluguel a = new Aluguel(null, listaFilmes2, cliente, d2, 45.4f );
	        aluguelDAO.insert(conn, a);
	        alugueis = aluguelDAO.list(conn);
	        for(Aluguel a1 : alugueis) {
	        	System.out.println(a1.toString());
	        }
            
            //editando aluguel
	        System.out.println("EDITANDO ALUGUEL");
            Filme f6 = filmeDAO.find(conn, 1);
	        Filme f7 = filmeDAO.find(conn, 2);
	        List<Filme> listaFilmes3 = new ArrayList<>();
	        listaFilmes3.add(f6);
	        listaFilmes3.add(f7);
	        Cliente c4 = clienteDAO.find(conn, 2);
            Aluguel al = new Aluguel(7, listaFilmes3, c4, d3, 5.7f);
            aluguelDAO.edit(conn, al);
            alugueis = aluguelDAO.list(conn);
	        for(Aluguel a1 : alugueis) {
	        	System.out.println(a1.toString());
	        }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim do teste.");
    }
}