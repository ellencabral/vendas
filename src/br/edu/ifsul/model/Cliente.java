package br.edu.ifsul.model;

import java.util.List;

public class Cliente {
    private int id;
    private String nome;
    private String sobrenome;
    private boolean situacao;
    private List<Pedido> pedidos; //expressa a ligação entre as classes Cliente com Pedido

    public Cliente() {
    }

    public Cliente(int id, String nome, String sobrenome, boolean situacao) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.situacao = situacao;
    }

    public Cliente(int id, String nome, String sobrenome, boolean situacao, List<Pedido> pedidos) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.situacao = situacao;
        this.pedidos = pedidos;
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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "\nCliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", situacao=" + situacao +
                '}';
    }
}
