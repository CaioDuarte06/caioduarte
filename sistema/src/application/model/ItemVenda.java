package application.model;

public class ItemVenda {

	private String nome;
	private int quantidade;
	private double preco;
	private double subtotal;
	private int produtoId;

	public ItemVenda(int produtoId, String nome, int quantidade, double preco) {
		this.produtoId = produtoId;
		this.nome = nome;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public int getProdutoId() {
		return produtoId;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
		this.subtotal = quantidade * preco;
	}

	public double getPreco() {
		return preco;
	}

	public double getSubtotal() {
	    return quantidade * preco;
	}
}