package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.conexao;

public class MovimentacaoEstoqueModel {

	private int id;
	private int idProd;
	private String nomeProd;
	private String data; // String para exibir na tabela
	private int quantidade;
	private String tipo;

	// Construtor
	public MovimentacaoEstoqueModel(int id, int idProd, String nomeProd, String data, int quantidade, String tipo) {
		this.id = id;
		this.idProd = idProd;
		this.nomeProd = nomeProd;
		this.data = data;
		this.quantidade = quantidade;
		this.tipo = tipo;
	}

	// Getters
	public int getId() {
		return id;
	}

	public int getIdProd() {
		return idProd;
	}

	public String getNomeProd() {
		return nomeProd;
	}

	public String getData() {
		return data;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public String getTipo() {
		return tipo;
	}

	//  Método estático para buscar histórico
	public static List<MovimentacaoEstoqueModel> historicoMovimentacao(int produtoId, LocalDate inicio, LocalDate fim) {
		List<MovimentacaoEstoqueModel> lista = new ArrayList<>();

		String sql = "SELECT h.id, h.produto_id, p.nome AS nomeProd, h.data, h.quantidade, h.operacao "
				+ "FROM historico h " + "JOIN produto p ON h.produto_id = p.id ";

		if (produtoId > 0) {
			sql += "WHERE h.produto_id = ? AND DATE(h.data) BETWEEN ? AND ? ";
		} else {
			sql += "WHERE DATE(h.data) BETWEEN ? AND ? ";
		}

		sql += "ORDER BY h.data DESC";

		try (Connection conn = conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			int paramIndex = 1;
			if (produtoId > 0) {
			    stmt.setInt(paramIndex++, produtoId);
			}
			stmt.setDate(paramIndex++, java.sql.Date.valueOf(inicio));
			stmt.setDate(paramIndex, java.sql.Date.valueOf(fim));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				lista.add(new MovimentacaoEstoqueModel(rs.getInt("id"), rs.getInt("produto_id"),
						rs.getString("nomeProd"), rs.getTimestamp("data").toLocalDateTime().toString(),
						rs.getInt("quantidade"), rs.getString("operacao")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}
}