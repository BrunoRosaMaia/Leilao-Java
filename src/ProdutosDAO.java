/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public boolean cadastrarProduto (ProdutosDTO produto){
        
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            return true;
        }
        catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        finally{
            try {
                if(prep != null) prep.close();
                if(conn != null) conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos";
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()){
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listagem.add(produto);
            }
        }
        catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return listagem;
    }
    
    public boolean venderProduto(int id){
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        try{
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            prep.setInt(1, id);
            prep.executeUpdate();
            return true;
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        finally{
            try{
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        ArrayList<ProdutosDTO> vendidos = new ArrayList<>();
        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            while (resultset.next()){
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                vendidos.add(produto);
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao listar vendidos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            try {
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return vendidos;
    }
}