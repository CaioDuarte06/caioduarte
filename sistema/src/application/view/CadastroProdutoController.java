package application.view;

import java.util.List;

import application.model.ProdutoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CadastroProdutoController {

	@FXML
	private Button btnBuscar;

	@FXML
	private TextField txtBuscar;

	@FXML
	private Button btnSalvar;

	@FXML
	private TextField txtCategoria;

	@FXML
	private TextField txtDescricao;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtPreco;

	@FXML
	private TextField txtQuantidade;

	@FXML
	private Button btnExcluir;

	@FXML
	private TextField txtPrecoCusto;

	@FXML
	private TextField txtMargem;

	@FXML
	private TextField txtPrecoVenda;
	
	@FXML
	private TextField txtEstoqueMinimo;

	@FXML
	private TableColumn<ProdutoModel, String> colCategoria;

	@FXML
	private TableColumn<ProdutoModel, String> colDescricao;

	@FXML
	private TableColumn<ProdutoModel, Integer> colId;

	@FXML
	private TableColumn<ProdutoModel, String> colNome;

	@FXML
	private TableColumn<ProdutoModel, Double> colPreco;

	@FXML
	private TableColumn<ProdutoModel, Integer> colQtd;
	
	@FXML
	private TableColumn<ProdutoModel, Integer> colEstoque;

	@FXML
	private TableView<ProdutoModel> tabProdutos;

	@FXML
	private TableColumn<ProdutoModel, String> colCodigoBarras;

	@FXML
	private TextField txtCodigoBarras;

	private ObservableList<ProdutoModel> listaProdutos;

	// CRIANDO O OBJETO
	ProdutoModel produto = new ProdutoModel(0, null, null, null, 0, 0, null, 0);

	// METODO PARA SALVAR O CADASTRO DO PRODUTO
	public void Salvar() {

		// VALIDA CAMPOS PRIMEIRO
		if (txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() || txtCategoria.getText().isEmpty()
				|| txtPrecoVenda.getText().isEmpty() || txtQuantidade.getText().isEmpty()) {

			String erro = "";

			if (txtNome.getText().isEmpty())
				erro += "\nNome";
			if (txtDescricao.getText().isEmpty())
				erro += "\nDescrição";
			if (txtCategoria.getText().isEmpty())
				erro += "\nCategoria";
			if (txtPrecoVenda.getText().isEmpty())
				erro += "\nPreço de Venda";
			if (txtQuantidade.getText().isEmpty())
				erro += "\nQuantidade";

			Alert mensagem = new Alert(Alert.AlertType.WARNING);
			mensagem.setContentText("Preencha os campos:" + erro);
			mensagem.showAndWait();
		} else {

			try {
				ProdutoModel produto = new ProdutoModel(0, null, null, null, 0, 0, null, 0);

				produto.setNome(txtNome.getText());
				produto.setDescricao(txtDescricao.getText());
				produto.setCategoria(txtCategoria.getText());
				produto.setPreco(Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")));
				//produto.setPreco(Double.parseDouble(txtPrecoVenda.getText()));
				produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
				produto.setEstoqueMinimo(Integer.parseInt(txtEstoqueMinimo.getText()));
				// Se o usuário não digitou, gera automaticamente
				if (txtCodigoBarras.getText().isEmpty()) {
					produto.setCodigoBarras(gerarCodigoBarras());
				} else {
					produto.setCodigoBarras(txtCodigoBarras.getText());
				}
				produto.Salvar();

				// limpar campos
				txtNome.clear();
				txtDescricao.clear();
				txtCategoria.clear();
				txtPrecoCusto.clear();
				txtMargem.clear();
				txtPrecoVenda.clear();
				txtQuantidade.clear();
				txtEstoqueMinimo.clear();


				ListarProdutosTab(null);

			} catch (Exception e) {
				Alert erro = new Alert(Alert.AlertType.ERROR);
				erro.setContentText("Erro nos dados (preço ou quantidade inválidos)");
				erro.showAndWait();
			}
		}
	}

	// METODO PARA BUSCAR O CADASTRO DO PRODUTO
	public void Pesquisar() {
		if (!txtBuscar.getText().isEmpty()) {
			// ProdutoModel produto= new ProdutoModel(0,null, null, null, 0, 0);

			// executa o metodo de buscar
			produto.Buscar(txtBuscar.getText());
			ListarProdutosTab(txtBuscar.getText());
			// Informar os valores nos campos do formulario
			// txtId.setText(String.valueOf(produto.getId()));
			txtNome.setText(produto.getNome()); // Usa o Get Para buscar informação
			txtDescricao.setText(produto.getDescricao());
			txtCategoria.setText(produto.getCategoria());
			txtPreco.setText(String.valueOf(produto.getPreco()));
			txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
			txtCodigoBarras.setText(produto.getCodigoBarras());

		} else {

		}
	}

	// METODO PARA EXCLUIR CADASTRO
	public void Excluir() {

		// pega o item selecionado na tabela
		ProdutoModel selecionado = tabProdutos.getSelectionModel().getSelectedItem();

		// verifica se selecionou algo
		if (selecionado != null) {

			// exclui do banco
			selecionado.Excluir();

			// atualiza a tabela
			ListarProdutosTab(null);

			// limpa os campos
			txtNome.clear();
			txtDescricao.clear();
			txtCategoria.clear();
			txtPrecoCusto.clear();
			txtMargem.clear();
			txtPrecoVenda.clear();
			txtQuantidade.clear();
			txtEstoqueMinimo.clear();

		} else {

			// mensagem se nada foi selecionado
			Alert mensagem = new Alert(Alert.AlertType.WARNING);
			mensagem.setContentText("Selecione um produto na tabela!");
			mensagem.showAndWait();
		}

		/*
		 * produto.Excluir(); txtNome.setText(""); txtDescricao.setText("");
		 * txtCategoria.setText(""); txtPreco.setText(""); txtQuantidade.clear();
		 */

	}

	private void calcularPreco() {
		try {
			double custo = Double.parseDouble(txtPrecoCusto.getText());
			double margem = Double.parseDouble(txtMargem.getText());

			double venda = custo + (custo * margem / 100);

			txtPrecoVenda.setText(String.format(java.util.Locale.US, "%.2f", venda));
		} catch (Exception e) {
			txtPrecoVenda.setText("");
		}
	}

	// METODO INITIALIZE VINCULA DIRETAMENTE O CONTROLE COM O FXML
	// A PALAVRA FXML É RESERVADA DO JAVAFX PARA REALIZAR A INTERAÇÃO
	public void initialize() {

		txtPrecoCusto.textProperty().addListener((obs, oldVal, newVal) -> calcularPreco());
		txtMargem.textProperty().addListener((obs, oldVal, newVal) -> calcularPreco());

		// ATRIBUI O TIPO DE INFORMAÇÃO DOS GETTERS DA MODEL EX: return this.id;
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));

		colId.setCellFactory(column -> new javafx.scene.control.TableCell<ProdutoModel, Integer>() {
			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.format("%06d", item));
				}
			}
		});
		colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

		colPreco.setCellFactory(column -> new javafx.scene.control.TableCell<ProdutoModel, Double>() {
			@Override
			protected void updateItem(Double item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.format("R$ %.2f", item).replace(".", ","));
				}
			}
		});
		colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		colCodigoBarras.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
		colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoqueMinimo"));

		

		ListarProdutosTab(null);

		// AO APERTAR ENTER ELE VAI BUSCAR
		txtBuscar.setOnAction(e -> {
			Pesquisar();
		});

		tabProdutos.setOnMouseClicked(event -> {
			ProdutoModel p = tabProdutos.getSelectionModel().getSelectedItem();

			if (p != null) {
				txtNome.setText(p.getNome());
				txtDescricao.setText(p.getDescricao());
				txtCategoria.setText(p.getCategoria());
				txtPrecoVenda.setText(String.valueOf(p.getPreco()));
				txtQuantidade.setText(String.valueOf(p.getQuantidade()));
				txtCodigoBarras.setText(p.getCodigoBarras());
			}
		});

		txtNome.setOnAction(e -> txtDescricao.requestFocus());
		txtDescricao.setOnAction(e -> txtCategoria.requestFocus());
		txtCategoria.setOnAction(e -> txtPrecoCusto.requestFocus());
		txtPrecoCusto.setOnAction(e -> txtMargem.requestFocus());
		txtMargem.setOnAction(e -> txtQuantidade.requestFocus());
		txtQuantidade.setOnAction(e -> txtCodigoBarras.requestFocus());
		txtCodigoBarras.setOnAction(e -> Salvar());
		txtBuscar.setOnAction(e -> Pesquisar());

		
		tabProdutos.setRowFactory(tv -> new TableRow<ProdutoModel>() {
		    @Override
		    protected void updateItem(ProdutoModel produto, boolean empty) {
		        super.updateItem(produto, empty);

		        if (produto == null || empty) {
		            setStyle("");
		        } else if (produto.getQuantidade() <= produto.getEstoqueMinimo()) {
		            setStyle("-fx-background-color: #ffcccc;");
		        } else {
		            setStyle("");
		        }
		    }
		});
		
	}

	public void ListarProdutosTab(String valor) {
		List<ProdutoModel> produtos = produto.ListarProdutos(valor);
		listaProdutos = FXCollections.observableArrayList(produtos);
		tabProdutos.setItems(listaProdutos);
	}

	public String gerarCodigoBarras() {
		// Gera um número aleatório positivo de 13 dígitos
		long numero = (long) (Math.random() * 1_000_000_000_0000L);
		return String.format("%013d", numero);
	}
}