package br.edu.ifsul.model;

public class Produto {
    private int id;
    private String nome;
    private double valor;
    private String descricao;
    private boolean situacao;
    private int estoque;

    public Produto() {
    }

    public Produto(int id, String nome, double valor, String descricao, boolean situacao, int estoque) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.situacao = situacao;
        this.estoque = estoque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "\nProduto{" +
                "id= " + id + ", " +
                "nome= " + nome + ", " +
                "valor= " + valor + ", " +
                "descricao= " + descricao + ", " +
                "situacao= " + situacao + ", " +
                "estoque= " + estoque +
                "}";
    }
}
