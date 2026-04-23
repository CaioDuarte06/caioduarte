package application.model;

public class ItemVenda {

    private String nome;
    private int quantidade;
    private double preco;
    private double subtotal;

    public ItemVenda(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.subtotal = quantidade * preco;
    }

    public String getNome() { return nome; }

    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.subtotal = quantidade * preco;
    }

    public double getPreco() { return preco; }

    public double getSubtotal() { return subtotal; }
}