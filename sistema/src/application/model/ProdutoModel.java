package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import application.Sessao;
import application.conexao;
import javafx.scene.control.Alert;

public class ProdutoModel {

    private int id;
    private String nome;
    private String descricao;
    private String categoria;
    private double preco;
    private int quantidade;
    private String codigo_barras;
    private int estoque_minimo;

    public ProdutoModel(int id, String nome, String descricao, String categoria, double preco, int quantidade, String codigo_barras, int estoque_minimo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.codigo_barras = codigo_barras;
        this.estoque_minimo=estoque_minimo;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public String getCodigoBarras() { return codigo_barras; }
    public void setCodigoBarras(String codigo_barras) { this.codigo_barras = codigo_barras; }
    public int getEstoqueMinimo() { return estoque_minimo; }
    public void setEstoqueMinimo(int estoque_minimo) { this.estoque_minimo = estoque_minimo; }

    


    // Salvar produto (INSERT ou UPDATE)
    public void Salvar() {
        try (Connection conn = conexao.getConnection()) {
            if (id > 0) {
                String sql = "UPDATE produto SET nome=?, descricao=?, categoria=?, preco=?, quantidade=?, codigo_barras=?, estoque_minimo=? WHERE id=?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nome);
                    stmt.setString(2, descricao);
                    stmt.setString(3, categoria);
                    stmt.setDouble(4, preco);
                    stmt.setInt(5, quantidade);
                    stmt.setString(6, codigo_barras);
                    stmt.setInt(7, estoque_minimo);
                    stmt.setInt(8, id);
                    stmt.executeUpdate();
                }
            } else {
                String sql = "INSERT INTO produto (nome, descricao, categoria, preco, quantidade, codigo_barras, estoque_minimo) VALUES (?,?,?,?,?,?,?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, nome);
                    stmt.setString(2, descricao);
                    stmt.setString(3, categoria);
                    stmt.setDouble(4, preco);
                    stmt.setInt(5, quantidade);
                    stmt.setString(6, codigo_barras);
                    stmt.setInt(7, estoque_minimo);

                    stmt.executeUpdate();

                    // Recupera o id gerado
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Excluir produto
    public void Excluir() {
        if (id <= 0) return;
        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM produto WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buscar produto pelo nome, código de barras ou descrição
    public void Buscar(String valor) {
        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM produto WHERE nome LIKE ? OR descricao LIKE ? OR codigo_barras LIKE ? LIMIT 1")) {
            stmt.setString(1, "%" + valor + "%");
            stmt.setString(2, "%" + valor + "%");
            stmt.setString(3, "%" + valor + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
                nome = rs.getString("nome");
                descricao = rs.getString("descricao");
                categoria = rs.getString("categoria");
                preco = rs.getDouble("preco");
                quantidade = rs.getInt("quantidade");
                codigo_barras = rs.getString("codigo_barras");
                estoque_minimo = rs.getInt("estoque_minimo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar produtos
    public List<ProdutoModel> ListarProdutos(String filtro) {
        List<ProdutoModel> lista = new ArrayList<>();
        try (Connection conn = conexao.getConnection()) {
            String sql = (filtro == null || filtro.isEmpty())
                    ? "SELECT * FROM produto"
                    : "SELECT * FROM produto WHERE nome LIKE ? OR descricao LIKE ? OR categoria LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (filtro != null && !filtro.isEmpty()) {
                    stmt.setString(1, "%" + filtro + "%");
                    stmt.setString(2, "%" + filtro + "%");
                    stmt.setString(3, "%" + filtro + "%");
                }
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    lista.add(new ProdutoModel(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("categoria"),
                            rs.getDouble("preco"),
                            rs.getInt("quantidade"),
                            rs.getString("codigo_barras"),
                            rs.getInt("estoque_minimo")

                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void mostrarAviso(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(null); 
        alerta.setContentText(mensagem); // Mensagem que vou mostrar
        alerta.showAndWait();
    }
    
    public void processarEstoque(String operacao) {
        try (Connection conn = conexao.getConnection()) {

            int quantidadeMovimentada = this.quantidade;
            int novaQuantidade = 0;

            // Pega quantidade atual do produto
            String sqlBusca = "SELECT quantidade FROM produto WHERE id=?";
            try (PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {
                stmtBusca.setInt(1, this.id);
                ResultSet rs = stmtBusca.executeQuery();

                if (rs.next()) {
                    int qtdAtual = rs.getInt("quantidade");

                    if (operacao.equalsIgnoreCase("Entrada")) {
                        novaQuantidade = qtdAtual + quantidadeMovimentada;
                    } else if (operacao.equalsIgnoreCase("Saída") || operacao.equalsIgnoreCase("Saida")) {
                        if (qtdAtual < quantidadeMovimentada) {
                            // lança exceção caso estoque seja insuficiente
                            throw new Exception("Estoque insuficiente!");
                        }
                        novaQuantidade = qtdAtual - quantidadeMovimentada;
                    } else {
                        throw new Exception("Operação inválida!");
                    }
                } else {
                    throw new Exception("Produto não encontrado!");
                }
            }

            // Atualiza estoque
            String sqlUpdate = "UPDATE produto SET quantidade=? WHERE id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
                stmt.setInt(1, novaQuantidade);
                stmt.setInt(2, this.id);
                stmt.executeUpdate();
            }

            // Salva histórico
            String sqlHistorico = "INSERT INTO historico (produto_id, operacao, quantidade, data, usuario) VALUES (?, ?, ?, NOW(), ?)";
            try (PreparedStatement stmtHist = conn.prepareStatement(sqlHistorico)) {
                stmtHist.setInt(1, this.id);
                stmtHist.setString(2, operacao);
                stmtHist.setInt(3, quantidadeMovimentada);
                stmtHist.setString(4, Sessao.cargoUsuario+": " + Sessao.nomeUsuario);

                stmtHist.executeUpdate();
            }

            //  mostra aviso só se deu tudo certo
            mostrarAviso("Produto processado com sucesso!");

        } catch (Exception e) {
            // mostra alerta para o usuário, sem quebrar o programa
            mostrarAviso(e.getMessage());
        }
    }
    
    
}