package br.com.projeto.descarteResiduo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.descarteResiduo.model.Point;

@Service
public class PointService {

	@Autowired
    private DataSource dataSource;

    public Point pointCreate(Point ponto) throws SQLException {
    	String insertSql = "INSERT INTO pontos (nome, endereco, zona, contatos, horario_expediente) VALUES (?, ?, ?, ?, ?)";
        String selectSql = "SELECT id, nome, endereco, zona, contatos, horario_expediente FROM pontos WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertPstm = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            insertPstm.setString(1, ponto.getNome());
            insertPstm.setString(2, ponto.getEndereco());
            insertPstm.setString(3, ponto.getZona());
            insertPstm.setString(4, ponto.getContatos());
            insertPstm.setString(5, ponto.getHorarioExpediente());
            insertPstm.executeUpdate();

            try (ResultSet generatedKeys = insertPstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long generatedId = generatedKeys.getLong(1);

                    try (PreparedStatement selectPstm = connection.prepareStatement(selectSql)) {
                        selectPstm.setLong(1, generatedId);
                        try (ResultSet rs = selectPstm.executeQuery()) {
                            if (rs.next()) {
                                ponto.setId(rs.getLong("id"));
                                ponto.setNome(rs.getString("nome"));
                                ponto.setEndereco(rs.getString("endereco"));
                                ponto.setZona(rs.getString("zona"));
                                ponto.setContatos(rs.getString("contatos"));
                                ponto.setHorarioExpediente(rs.getString("horario_expediente"));                                
                            }
                        }
                    }
                }
            }
        }
        return ponto;
    }
    
    public List<Point> pointRead() throws SQLException {
    	List<Point> pontos = new ArrayList<>();
        String sql = "SELECT * FROM pontos;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rst = pstm.executeQuery()) {

            while (rst.next()) {               
                Point ponto = new Point(
                        rst.getLong("id"), 
                        rst.getString("nome"), 
                        rst.getString("endereco"), 
                        rst.getString("zona"),
                        rst.getString("contatos"),
                        rst.getString("horario_expediente"));
                pontos.add(ponto);
            }
        }
        return pontos;
    }
    
    public Point pointUpdate(Long id, Point ponto) throws SQLException {
    	if (id == null || ponto == null) {
            throw new IllegalArgumentException("ID and Ponto must not be null");
        }
        
        String sql = "UPDATE pontos SET nome = ?, endereco = ?, zona = ?, contatos = ?, horario_expediente = ? WHERE id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, ponto.getNome());
            pstm.setString(2, ponto.getEndereco());
            pstm.setString(3, ponto.getZona());
            pstm.setString(4, ponto.getContatos());
            pstm.setString(5, ponto.getHorarioExpediente());
            pstm.setLong(6, id);

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                ponto.setId(id);
                return ponto;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void pointDelete(Long id) throws SQLException {
        String sql = "DELETE FROM pontos WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setLong(1, id);
            pstm.executeUpdate();
        }
    }
    
    public List<Point> pointSearch(String nome, String endereco, String zona, String contatos, String horarioExpediente) throws SQLException {
        List<Point> pontos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM pontos WHERE 1=1");

        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND nome LIKE ?");
        }
        
        if (endereco != null && !endereco.isEmpty()) {
            sql.append(" AND endereco LIKE ?");
        }
        
        if (zona != null && !zona.isEmpty()) {
            sql.append(" AND zona LIKE ?");
        }
        
        if (contatos != null && !contatos.isEmpty()) {
            sql.append(" AND contatos LIKE ?");
        }
        
        if (horarioExpediente != null && !horarioExpediente.isEmpty()) {
            sql.append(" AND horario_expediente LIKE ?");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (nome != null && !nome.isEmpty()) {
                pstm.setString(paramIndex++, "%" + nome + "%");
            }
            
            if (endereco != null && !endereco.isEmpty()) {
                pstm.setString(paramIndex++, "%" + endereco + "%");
            }
            
            if (zona != null && !zona.isEmpty()) {
                pstm.setString(paramIndex++, "%" + zona + "%");
            }
            
            if (contatos != null && !contatos.isEmpty()) {
                pstm.setString(paramIndex++, "%" + contatos + "%");
            }
            
            if (horarioExpediente != null && !horarioExpediente.isEmpty()) {
                pstm.setString(paramIndex++, "%" + horarioExpediente + "%");
            }
            

            try (ResultSet rst = pstm.executeQuery()) {
                while (rst.next()) {
                    Point ponto = new Point(
                            rst.getLong("id"),
                            rst.getString("nome"),
                            rst.getString("endereco"),
                            rst.getString("zona"),
                            rst.getString("contatos"),
                            rst.getString("horario_expediente")
                            
                    );
                    pontos.add(ponto);
                }
            }
        }
        return pontos;
    }
}
