package br.edu.ifsul.dao;

import br.edu.ifsul.model.Item;
import br.edu.ifsul.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends BaseDAO {

    public boolean insert(Produto produto) {
        try {
            Connection conn = getConnection();
            String sql = "INSERT INTO produtos (nome, valor, descricao, situacao, estoque) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getDescricao());
            stmt.setBoolean(4, produto.isSituacao());
            stmt.setInt(5, produto.getEstoque());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Produto> getProdutos(){
        List<Produto> produtos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM produtos";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Produto p = resultsetToProduto(rs);
                produtos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return produtos;
        } // finally { o finally sempre vai ser executado

        return produtos;
    }

    public Produto getProdutoById(int id){
        Produto produto = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM produtos WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                produto = resultsetToProduto(rs);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produto;
        }
    }

    public List<Produto> getProdutosBySituacao(boolean situacao){
        List<Produto> produtos = new ArrayList<>();

        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM produtos WHERE situacao=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, situacao);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Produto produto = resultsetToProduto(rs);
                produtos.add(produto);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            return produtos;
        }
    }

    public Produto getProdutoByItem(Item item) {
        Produto produto = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM produtos WHERE id in (SELECT id_produto FROM itens WHERE id = ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, item.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                produto = resultsetToProduto(rs);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produto;
        }
    }

    public List<Produto> getProdutosByName(String nome){
        List<Produto> produtos = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM produtos WHERE LOWER(nome) LIKE ? ORDER BY nome";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Produto produto = resultsetToProduto(rs);
                produtos.add(produto);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return produtos;
        }
    }

    public boolean update(Produto produto) {
        try {
            Connection conn = getConnection();
            String sql = "UPDATE produtos SET nome = ?, valor = ?, descricao = ?, situacao = ?, estoque = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getDescricao());
            stmt.setBoolean(4, produto.isSituacao());
            stmt.setInt(5, produto.getEstoque());
            stmt.setInt(6, produto.getId());

            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean softDelete(Produto produto) {
        try {
            Connection conn = getConnection();
            String sql = "UPDATE produtos SET situacao = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setInt(2, produto.getId());
            int count = stmt.executeUpdate();
            stmt.close();
            conn.close();
            return count != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Produto resultsetToProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setValor(rs.getDouble("valor"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setEstoque(rs.getInt("estoque"));
        produto.setSituacao(rs.getBoolean("situacao"));
        return produto;
    }

}
