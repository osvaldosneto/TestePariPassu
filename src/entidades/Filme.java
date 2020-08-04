package entidades;

import java.util.Date;

public class Filme {

	private Integer idFilme;
	private Date dataLancamento;
	private String nome;
	private String descricao;

	public Filme() {
	}

	public Filme(Integer idFilme, Date dataLancamento, String nome, String descricao) {
		this.idFilme = idFilme;
		this.dataLancamento = dataLancamento;
		this.nome = nome;
		this.descricao = descricao;
	}

	public Integer getIdFilme() {
		return idFilme;
	}

	public Filme setIdFilme(Integer idFilme) {
		this.idFilme = idFilme;
		return this;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public Filme setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public Filme setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public String getDescricao() {
		return descricao;
	}

	public Filme setDescricao(String descricao) {
		this.descricao = descricao;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Id Filme = ");
		builder.append(idFilme + "\n");
		builder.append("Nome = ");
		builder.append(nome + "\n");
		builder.append("Data lancamento = ");
		builder.append(dataLancamento + "\n");
		builder.append("Descricao = ");
		builder.append(descricao + "\n");
		return builder.toString();
	}

}
