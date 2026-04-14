package application.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import application.model.ClienteModel;
import application.conexao;

public class ClienteDAO {

	// SALVAR
	public void inserir(ClienteModel c) {

		String sql = "INSERT INTO clientes (nome, cpf, cnpj, email, status) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getCpf());
			stmt.setString(3, c.getCnpj());
			stmt.setString(4, c.getEmail());
			stmt.setString(5, c.getStatus());

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// LISTAR
	public List<ClienteModel> listar() {

		List<ClienteModel> lista = new ArrayList<>();

		String sql = "SELECT * FROM clientes";

		try (Connection conn = conexao.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				ClienteModel c = new ClienteModel();

				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setCpf(rs.getString("cpf"));
				c.setCnpj(rs.getString("cnpj"));
				c.setEmail(rs.getString("email"));
				c.setStatus(rs.getString("status"));

				lista.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// EXCLUIR
	public void excluir(int id) {

		String sql = "DELETE FROM clientes WHERE id = ?";

		try (Connection conn = conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// BUSCAR
	public List<ClienteModel> buscar(String texto) {

		List<ClienteModel> lista = new ArrayList<>();

		String sql = "SELECT * FROM clientes WHERE nome LIKE ? OR cpf LIKE ? OR cnpj LIKE ? OR email LIKE ?";

		try (Connection conn = conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			String busca = "%" + texto + "%";

			stmt.setString(1, busca);
			stmt.setString(2, busca);
			stmt.setString(3, busca);
			stmt.setString(4, busca);


			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ClienteModel c = new ClienteModel();

				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setCpf(rs.getString("cpf"));
				c.setCnpj(rs.getString("cnpj"));
				c.setEmail(rs.getString("email"));
				c.setStatus(rs.getString("status"));

				lista.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	// VALIDAR CPF DUPLICADO
	public boolean existeCpf(String cpf) {

		String sql = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";

		try (Connection conn = conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, cpf);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean existeCnpj(String cnpj) {

	    String sql = "SELECT COUNT(*) FROM clientes WHERE cnpj = ?";

	    try (Connection conn = conexao.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, cnpj);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
}