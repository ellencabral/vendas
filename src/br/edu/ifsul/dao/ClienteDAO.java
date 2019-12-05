package br.edu.ifsul.dao;

import br.edu.ifsul.model.Cliente;
import br.edu.ifsul.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends BaseDAO {

    public List<Cliente> getClientes(){
        List<Cliente> clientes = new ArrayList<>();

        try {
            String sql = "SELECT * FROM clientes";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Cliente c = resultsetToCliente(rs);
                clientes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return clientes;
        } // finally { o finally sempre vai ser executado

        return clientes;
    }

    public List<Cliente> getClientesBySituacao(boolean situacao){
        List<Cliente> clientes = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM clientes WHERE situacao=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, situacao);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Cliente cliente = resultsetToCliente(rs);
                clientes.add(cliente);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return clientes;
        }
    }



    public List<Cliente> getClientesByName(String nome){
        List<Cliente> clientes = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM clientes WHERE LOWER(nome) LIKE ? ORDER BY nome";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Cliente cliente = resultsetToCliente(rs);
                clientes.add(cliente);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return clientes;
        }
    }

    public Cliente getClienteById(int id){
        Cliente cliente = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM clientes WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                cliente = resultsetToCliente(rs);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return cliente;
        }
    }

    public Cliente getClienteByPedido(Pedido pedido){
        Cliente cliente = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM clientes WHERE id in (SELECT id_cliente FROM pedidos WHERE id = ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pedido.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                cliente = resultsetToCliente(rs);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return cliente;
        }
    }

    public boolean insert(Cliente cliente) {
        try {
            Connection conn = getConnection();
            String sql = "INSERT INTO clientes (nome, sobrenome, situacao) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setBoolean(3, cliente.isSituacao());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Cliente cliente) {
        try {
            Connection conn = getConnection();
            String sql = "UPDATE clientes SET nome = ?, sobrenome = ?, situacao = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setBoolean(3, cliente.isSituacao());
            stmt.setInt(4, cliente.getId());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean softDelete(Cliente cliente) {
        try {
            Connection conn = getConnection();
            String sql = "UPDATE clientes SET situacao = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setInt(2, cliente.getId());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Cliente resultsetToCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setSobrenome(rs.getString("sobrenome"));
;
//        PedidoDAO pedidoDAO = new PedidoDAO();
//        cliente.setPedidos(pedidoDAO.getPedidosById(cliente));
        return cliente;
    }

}
