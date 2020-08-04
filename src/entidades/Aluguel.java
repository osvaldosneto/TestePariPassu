package entidades;

import java.util.Date;
import java.util.List;

public class Aluguel {
	private Integer idAluguel;
	private List<Filme> filmes;
	private Cliente cliente;
	private Date dataAluguel;
	private float valor;

	public Aluguel() {
	}

	public Aluguel(Integer idAluguel, List<Filme> filmes, Cliente cliente, Date dataAluguel, float valor) {
		this.idAluguel = idAluguel;
		this.filmes = filmes;
		this.cliente = cliente;
		this.dataAluguel = dataAluguel;
		this.valor = valor;
	}

	public Integer getIdAluguel() {
		return idAluguel;
	}

	public Aluguel setIdAluguel(Integer idAluguel) {
		this.idAluguel = idAluguel;
		return this;
	}

	public List<Filme> getFilmes() {
		return filmes;
	}

	public Aluguel setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
		return this;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Aluguel setCliente(Cliente cliente) {
		this.cliente = cliente;
		return this;
	}

	public Date getDataAluguel() {
		return dataAluguel;
	}

	public Aluguel setDataAluguel(Date dataAluguel) {
		this.dataAluguel = dataAluguel;
		return this;
	}

	public float getValor() {
		return valor;
	}

	public Aluguel setValor(float valor) {
		this.valor = valor;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Id Aluguel = ");
		builder.append(idAluguel + "\n");
		if(this.getFilmes() == null) {
			builder.append("Sem filmes no aluguel, verifique se o filme foi excluído do acervo. \n");
		} else {
			for (Filme f : this.getFilmes()) {
				builder.append(f.toString());
			}
		}
		builder.append(this.getCliente().toString());
		builder.append("Data Aluguel = ");
		builder.append(dataAluguel + "\n");
		builder.append("Valor = ");
		builder.append(valor + "\n");
		return builder.toString();
	}

}
