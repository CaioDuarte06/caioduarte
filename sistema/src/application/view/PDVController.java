package application.view;

import java.util.List;

import application.model.ItemVenda;
import application.model.ProdutoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import application.Dao.ClienteDAO;
import application.Dao.VendaDAO;
import application.model.ClienteModel;
import application.model.VendaModel;

public class PDVController {

    @FXML private TextField txtBuscaProduto;
    @FXML private TextField txtDesconto;
    @FXML private TextField txtPago;

    @FXML private Label lblTotal;
    @FXML private Label lblTroco;

    // TABELA DE PRODUTOS
    @FXML private TableView<ProdutoModel> tabProdutos;
    @FXML private TableColumn<ProdutoModel, String> colProdNome;
    @FXML private TableColumn<ProdutoModel, Double> colProdPreco;
    @FXML private TableColumn<ProdutoModel, Integer> colProdQtd;

    // TABELA DO CARRINHO
    @FXML private TableView<ItemVenda> tabelaCarrinho;
    @FXML private TableColumn<ItemVenda, String> colNome;
    @FXML private TableColumn<ItemVenda, Integer> colQtd;
    @FXML private TableColumn<ItemVenda, Double> colPreco;
    @FXML private TableColumn<ItemVenda, Double> colSubtotal;
    @FXML private ComboBox<ClienteModel> cbClientes;
    @FXML private ComboBox<String> cbPagamento;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private VendaDAO vendaDAO = new VendaDAO();

    private ObservableList<ProdutoModel> listaProdutos;
    private ObservableList<ItemVenda> carrinho = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // PRODUTOS
        colProdNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colProdPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colProdQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        
        cbPagamento.getItems().addAll("Dinheiro", "Cartão", "Pix");
        cbPagamento.setValue("Dinheiro");

        // CARRINHO
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        cbClientes.getItems().addAll(clienteDAO.listar());
        carregarProdutos();
        tabelaCarrinho.setItems(carrinho);
    }

    // CARREGAR PRODUTOS
    public void carregarProdutos() {
        ProdutoModel model = new ProdutoModel(0, null, null, null, 0, 0, null, 0);
        List<ProdutoModel> produtos = model.ListarProdutos(null);

        listaProdutos = FXCollections.observableArrayList(produtos);
        tabProdutos.setItems(listaProdutos);
    }

    // ADICIONAR PRODUTO
    @FXML
    public void adicionarProduto() {

        ProdutoModel selecionado = tabProdutos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Selecione um produto!");
            return;
        }

        if (selecionado.getQuantidade() <= 0) {
            mostrarAlerta("Produto sem estoque!");
            return;
        }

        for (ItemVenda item : carrinho) {

            if (item.getNome().equals(selecionado.getNome())) {

                if (item.getQuantidade() + 1 > selecionado.getQuantidade()) {
                    mostrarAlerta("Quantidade maior que o estoque!");
                    return;
                }

                item.setQuantidade(item.getQuantidade() + 1);
                atualizarCarrinho();
                return;
            }
        }

        ItemVenda novo = new ItemVenda(
        	    selecionado.getId(),   
        	    selecionado.getNome(),
        	    1,
        	    selecionado.getPreco()
        	);

        carrinho.add(novo);
        atualizarCarrinho();
    }
    
    @FXML
    public void removerProduto() {

        ItemVenda selecionado = tabelaCarrinho.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Selecione um item do carrinho!");
            return;
        }

        // remove direto
        carrinho.remove(selecionado);

        atualizarCarrinho();
    }

    // ATUALIZAR TOTAL
    public void atualizarCarrinho() {
        tabelaCarrinho.refresh();

        double total = 0;

        for (ItemVenda item : carrinho) {
            total += item.getSubtotal();
        }

        lblTotal.setText(String.format("R$ %.2f", total));
    }
    
    @FXML
    public void finalizarVenda() {

        if (carrinho.isEmpty()) {
            mostrarAlerta("Carrinho vazio!");
            return;
        }

        ClienteModel cliente = cbClientes.getValue();

        if (cliente == null) {
            mostrarAlerta("Selecione um cliente!");
            return;
        }

        if ("Inativo".equals(cliente.getStatus())) {
            mostrarAlerta("Cliente inativo não pode comprar!");
            return;
        }

        double total = 0;

        for (ItemVenda item : carrinho) {
            total += item.getSubtotal();
        }

        String tipoPagamento = cbPagamento.getValue();

        if (tipoPagamento == null) {
            mostrarAlerta("Selecione a forma de pagamento!");
            return;
        }

        double pago = total;

        if (tipoPagamento.equals("Dinheiro")) {

            try {
                pago = Double.parseDouble(txtPago.getText());
            } catch (Exception e) {
                mostrarAlerta("Valor pago inválido!");
                return;
            }

            if (pago < total) {
                mostrarAlerta("Valor insuficiente!");
                return;
            }

            double troco = pago - total;
            lblTroco.setText(String.format("R$ %.2f", troco));

        } else {
            // CARTÃO OU PIX
            lblTroco.setText("R$ 0.00");
        }

        try {
            // SALVA VENDA NO BANCO
            VendaModel venda = new VendaModel(
                    cliente.getId(),
                    null,
                    total
            );

            vendaDAO.inserir(venda);

            // BAIXA AUTOMÁTICA DE ESTOQUE
            for (ItemVenda item : carrinho) {

                ProdutoModel produto = new ProdutoModel(
                    item.getProdutoId(),
                    null, null, null,
                    0,
                    item.getQuantidade(),
                    null,
                    0
                );

                produto.processarEstoque("Saída");
            }

            mostrarCupom();

            carrinho.clear();
            atualizarCarrinho();

            mostrarAlerta("Venda realizada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao salvar venda!");
        }
    }

    // ALERTA
    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(msg);
        a.showAndWait();
    }

    // CUPOM
    private void mostrarCupom() {
        StringBuilder cupom = new StringBuilder("CUPOM\n");

        for (ItemVenda i : carrinho) {
            cupom.append(i.getNome())
                    .append(" x")
                    .append(i.getQuantidade())
                    .append(" = R$ ")
                    .append(String.format("%.2f", i.getSubtotal()))
                    .append("\n");
        }

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(cupom.toString());
        a.showAndWait();
    }
}