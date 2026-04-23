package application.view;

import java.time.LocalDate;
import java.util.List;
import application.view.HistoricoController;
import application.model.ProdutoModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProdutoEstoque {

    @FXML private Button btnBuscar;
    @FXML private Button btnProcessar;
    @FXML private Button btnHistorico;


    @FXML private TextField txtBarras;
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtPesquisar;
    @FXML private TextField txtQtd;

    @FXML private ToggleGroup rdOperacao;

    @FXML private TableView<ProdutoModel> tabEstoque;

    @FXML private TableColumn<ProdutoModel, String> colBarras;
    @FXML private TableColumn<ProdutoModel, String> colCategoria;
    @FXML private TableColumn<ProdutoModel, String> colDescricao;
    @FXML private TableColumn<ProdutoModel, Integer> colId;
    @FXML private TableColumn<ProdutoModel, String> colNome;
    @FXML private TableColumn<ProdutoModel, Integer> colQtd;
    private LocalDate hoje, primeiroDia, ultimoDia;

    private ObservableList<ProdutoModel> listaProdutos;

    private ProdutoModel produto = new ProdutoModel(0, null, null, null, 0, 0, null,0);

    // INICIALIZAÇÃO
    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colBarras.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        ListarProdutosTab(null);

        // SELEÇÃO DA TABELA
        tabEstoque.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {

                    produto = newSelection;

                    txtId.setText(String.valueOf(produto.getId()));
                    txtNome.setText(produto.getNome());
                    txtBarras.setText(produto.getCodigoBarras());
                    txtQtd.setText(String.valueOf(produto.getQuantidade()));
                }
            }
        );
        
        txtId.setOnAction(e -> txtBarras.requestFocus());
        txtBarras.setOnAction(e -> txtNome.requestFocus());
        txtNome.setOnAction(e -> txtQtd.requestFocus());
    	txtPesquisar.setOnAction(e -> Pesquisar());
        //  BOTÃO PROCESSAR
        btnProcessar.setOnAction(e -> processarEstoque());
    }

    // PROCESSAR ESTOQUE
    public void processarEstoque() {

        try {
            if (produto.getId() == 0) {
                mostrarAlerta("Selecione um produto!");
                return;
            }

            if (txtQtd.getText().isEmpty()) {
                mostrarAlerta("Informe a quantidade!");
                return;
            }

            int qtd = Integer.parseInt(txtQtd.getText());

            RadioButton operacao = (RadioButton) rdOperacao.getSelectedToggle();

            if (operacao == null) {
                mostrarAlerta("Selecione Entrada ou Saída");
                return;
            }

            produto.setQuantidade(qtd);

            //  chama model corrigido
            produto.processarEstoque(operacao.getText());

            limparCampos();
            ListarProdutosTab(null);

        } catch (NumberFormatException e) {
            mostrarAlerta("Quantidade inválida!");
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Erro ao processar estoque");
        }
    }
    
    //METODO PARA ABRIR A TELA DE HISTORICO
    
    /*public void Historico() {
        // Datas do mês atual
        hoje = LocalDate.now();
        primeiroDia = hoje.withDayOfMonth(1);
        ultimoDia = hoje.withDayOfMonth(hoje.lengthOfMonth());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/HistoricoProcessamento.fxml"));
            Parent root = loader.load();

            HistoricoController controller = loader.getController();
            controller.abrirHistorico(produto.getId(), primeiroDia, ultimoDia);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Histórico de Movimentações");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
*/
    
  //METODO PARA ABRIR A TELA DE HISTORICO
    public void Historico() {
        hoje = LocalDate.now();
        primeiroDia = hoje.withDayOfMonth(1);
        ultimoDia = hoje.withDayOfMonth(hoje.lengthOfMonth());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/HistoricoProcessamento.fxml"));
            Parent root = loader.load();

            HistoricoController controller = loader.getController();
            // Aqui passamos 0 para mostrar todos os produtos
            controller.abrirHistorico(0, primeiroDia, ultimoDia);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // PESQUISAR
    @FXML
    public void Pesquisar() {
        String filtro = txtPesquisar.getText();

        if (filtro != null && !filtro.isEmpty()) {

            produto.Buscar(filtro);
            ListarProdutosTab(filtro);

            txtId.setText(String.valueOf(produto.getId()));
            txtNome.setText(produto.getNome());
            txtQtd.setText(String.valueOf(produto.getQuantidade()));
            txtBarras.setText(produto.getCodigoBarras());
        }
    }

    //  LISTAR NA TABELA
    public void ListarProdutosTab(String filtro) {

        List<ProdutoModel> lista = produto.ListarProdutos(filtro);

        listaProdutos = FXCollections.observableArrayList(lista);

        tabEstoque.setItems(listaProdutos);
    }

    //  LIMPAR CAMPOS
    public void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtBarras.clear();
        txtQtd.clear();
        txtPesquisar.clear();
    }

    //  ALERTA PADRÃO
    private void mostrarAlerta(String msg) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}