package br.edu.ifsul.dao;

import br.edu.ifsul.model.Cliente;
import br.edu.ifsul.model.Item;
import br.edu.ifsul.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO extends BaseDAO {

    Connection conn = null;

    public boolean insert(Pedido pedido) {
        try {
            conn = getConnection();
            /*
                Inicia a transação, desligando o autocommit
             */

            conn.setAutoCommit(false);

            String sql = "INSERT INTO pedidos (pagamento, estado, data_criacao, data_modificacao, id_cliente, total_pedido, situacao, nota_fiscal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // insere no banco e retorna a chave que gerou (pois é auto_increment)

            stmt.setString(1, pedido.getPagamento());
            stmt.setString(2, pedido.getEstado());
            stmt.setDate(3, new Date(new java.util.Date().getTime()));
            stmt.setDate(4, new Date(new java.util.Date().getTime()));
            stmt.setInt(5, pedido.getCliente().getId());
            stmt.setDouble(6, pedido.getTotal_pedido());
            stmt.setBoolean(7, true);
            stmt.setInt(8, 0);

            int count = stmt.executeUpdate();
            //Se inseriu, ler o id auto increment
            int id = 0;
            if (count > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                rs.close();
            }

            for (Item i : pedido.getItens()) {
                try {
                    String sqlItem = "INSERT INTO itens (id_produto, id_pedido, quantidade, total_item, situacao) VALUES (?, ?, ?, ?, ?)";
                    stmt = conn.prepareStatement(sqlItem);
                    stmt.setInt(1, i.getProduto().getId());
                    stmt.setInt(2, id);
                    stmt.setInt(3, i.getQuantidade());
                    stmt.setDouble(4, i.getTotalItem());
                    stmt.setBoolean(5, true);
                    count = stmt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            conn.commit();
            conn.setAutoCommit(true);

            stmt.close();
            conn.close();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                return false;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    public List<Pedido> getPedidos() {
        List<Pedido> pedidos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM pedidos";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Pedido c = resultsetToPedido(rs);
                pedidos.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return pedidos;
        } // finally { o finally sempre vai ser executado

        return pedidos;

    }

    public List<Pedido> getPedidosById(Cliente cliente) {
        List<Pedido> pedidos = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM pedidos WHERE id_cliente=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido c = resultsetToPedido(rs);
                pedidos.add(c);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {  //o finally sempre vai ser executado
            return pedidos;
        }
    }

    public Pedido getPedidoById(int id){
        Pedido pedido = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM pedidos WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                pedido = resultsetToPedido(rs);
            }
            rs.close();
            stmt.close();
            conn.close();
            return pedido;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkout(Pedido pedido) {
        try {
            Connection conn = getConnection();
            String sql = "UPDATE pedidos SET estado = ?, nota_fiscal = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "faturado");
            stmt.setInt(2, pedido.getId());
            stmt.setInt(3, pedido.getId());

            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Pedido resultsetToPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id"));
        pedido.setPagamento(rs.getString("pagamento"));
        pedido.setEstado(rs.getString("estado"));
        pedido.setData_criacao(rs.getDate("data_criacao"));
        pedido.setData_modificacao(rs.getDate("data_modificacao"));
        pedido.setTotal_pedido(rs.getDouble("total_pedido"));
        pedido.setNota_fiscal(rs.getInt("nota_fiscal"));

        ItemDAO itemDAO = new ItemDAO();
        pedido.setItens(itemDAO.getItensByPedido(pedido.getId()));

        ClienteDAO clienteDAO = new ClienteDAO();
        pedido.setCliente(clienteDAO.getClienteById(rs.getInt("id_cliente")));

        return pedido;
    }

}
