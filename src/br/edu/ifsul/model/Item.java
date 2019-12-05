package br.edu.ifsul.model;

public class Item {
    private int id;
    private int quantidade;
    private boolean situacao;
    private double total_item;
    private Produto produto; //expressa a ligação entre as classes Item com Produto

    public Item() {
    }

    public Item(int id, int quantidade, boolean situacao, double total_item, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.situacao = situacao;
        this.total_item = total_item;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public double getTotalItem() {
        return total_item;
    }

    public void setTotalItem(double totalItem) {
        this.total_item = total_item;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "\nItem{" +
                ", quantidade=" + quantidade +
                ", situacao=" + situacao +
                ", total_item=" + total_item +
                ", produto=" + produto +
                '}';
    }
}
