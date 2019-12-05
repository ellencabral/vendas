package br.edu.ifsul.dao;

import br.edu.ifsul.model.Item;
import br.edu.ifsul.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends BaseDAO {

    public Item getItemById(int id) {
        Item item = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM itens WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, item.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                item = resultsetToItem(rs);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return item;
        }
    }

    public List<Item> getItensByPedido(int idPedido) {
        List<Item> itens = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM itens WHERE id_pedido=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Item item = resultsetToItem(rs);
                itens.add(item);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return itens;
        }
    }

    public boolean softDelete(Item item) {
        try {
            Connection conn = getConnection();
            String sql = "UPDATE itens SET situacao = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setInt(2, item.getId());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Item resultsetToItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setTotalItem(rs.getDouble("total_item"));
        item.setSituacao(rs.getBoolean("situacao"));

        ProdutoDAO produtoDAO = new ProdutoDAO();
        item.setProduto(produtoDAO.getProdutoById(rs.getInt("id_produto")));

        return item;
    }
}
