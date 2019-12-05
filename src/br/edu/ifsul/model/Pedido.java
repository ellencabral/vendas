package br.edu.ifsul.model;

import java.sql.Date;
import java.util.List;

public class Pedido {
    private int id;
    private String pagamento;
    private String estado;
    private Date data_criacao;
    private Date data_modificacao;
    private boolean situacao;
    private double total_pedido;
    private Cliente cliente;//expressa a ligação entre as classes Pedido  com Cliente
    private List<Item> itens;//expressa ligação entre as classes Pedido com Item
    private int nota_fiscal = 0;

    public Pedido() {
    }

    public Pedido(List<Item> itens) {
        this.itens = itens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Date getData_modificacao() {
        return data_modificacao;
    }

    public void setData_modificacao(Date data_modificacao) {
        this.data_modificacao = data_modificacao;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public double getTotal_pedido() {
        return total_pedido;
    }

    public void setTotal_pedido(double total_pedido) {
        this.total_pedido = total_pedido;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public int getNota_fiscal() {
        return nota_fiscal;
    }

    public void setNota_fiscal(int nota_fiscal) {
        this.nota_fiscal = nota_fiscal;
    }

    @Override
    public String toString() {
        return "\nPedido{" +
                "id=" + id +
                ", pagamento='" + pagamento + '\'' +
                ", estado='" + estado + '\'' +
                ", total_pedido=" + total_pedido +
                ", cliente=" + cliente +
                ", itens=" + itens +
                ", nota_fiscal=" + nota_fiscal +
                '}';
    }
}
