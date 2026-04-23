package application.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.conexao;
import application.model.VendaModel;

public class VendaDAO {

    public void inserir(VendaModel venda){

        String sql = "INSERT INTO vendas (cliente_id, data, total) VALUES (?, NOW(), ?)";

        try(Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, venda.getClienteId());
            stmt.setDouble(2, venda.getTotal());

            stmt.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<VendaModel> ultimasCompras(int clienteId){

        List<VendaModel> lista = new ArrayList<>();

        String sql = "SELECT data, total FROM vendas WHERE cliente_id = ? ORDER BY data DESC LIMIT 5";
        
        

        try(Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, clienteId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
            	
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            	String data = rs.getTimestamp("data")
            	               .toLocalDateTime()
            	               .format(formatter);

              //  String data = rs.getTimestamp("data").toLocalDateTime().toString();
                double total = rs.getDouble("total");

                VendaModel venda = new VendaModel(clienteId, data, total);

                lista.add(venda);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return lista;
    }
}
