package br.univel;

import java.math.BigDecimal;

@Tabela("Carro")
public class Carro {
	
	@Coluna(nome = "ID", pk = true) 
	private int id;

	@Coluna(nome = "NOME")	
	private String carro;

	@Coluna(nome = "ANO")	
	private int ano;

	@Coluna(nome = "VALOR")	
	private BigDecimal valor;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCarro() {
		return carro;
	}
	public void setCarro(String carro) {
		this.carro = carro;
	}
	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


}