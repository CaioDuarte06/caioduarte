package application.view;

import java.time.LocalDate;
import java.util.List;

import application.model.MovimentacaoEstoqueModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HistoricoController {

    @FXML private Button btnBuscar;

    @FXML private TableColumn<MovimentacaoEstoqueModel, String> colData;
    @FXML private TableColumn<MovimentacaoEstoqueModel, Integer> colID;
    @FXML private TableColumn<MovimentacaoEstoqueModel, Integer> colIdProd;
    @FXML private TableColumn<MovimentacaoEstoqueModel, String> colNome;
    @FXML private TableColumn<MovimentacaoEstoqueModel, Integer> colQtd;
    @FXML private TableColumn<MovimentacaoEstoqueModel, String> colTipo;
    @FXML private TableColumn<MovimentacaoEstoqueModel, String> colUsuario;

    @FXML private DatePicker dtFinal;
    @FXML private DatePicker dtInicio;

    @FXML private TableView<MovimentacaoEstoqueModel> tabHistorico;

    private LocalDate hoje, primeiroDia, ultimoDia;

    @FXML
    public void initialize() {
        // Configura colunas da tabela
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIdProd.setCellValueFactory(new PropertyValueFactory<>("idProd"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeProd"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        // Datas padrão do mês atual
        hoje = LocalDate.now();
        primeiroDia = hoje.withDayOfMonth(1);
        ultimoDia = hoje.withDayOfMonth(hoje.lengthOfMonth());

        dtInicio.setValue(primeiroDia);
        dtFinal.setValue(ultimoDia);

        // Carrega todo o histórico por padrão
        abrirHistorico(0, primeiroDia, ultimoDia); // 0 = todos os produtos
    }

    @FXML
    public void buscarPorPeriodo() {
        LocalDate inicio = dtInicio.getValue();
        LocalDate fim = dtFinal.getValue();

        if (inicio == null || fim == null) {
            mostrarAlerta("Informe as datas de início e fim!");
            return;
        }

        abrirHistorico(0, inicio, fim); // 0 = todos os produtos
    }

    /**
     * Abre histórico para um produto específico ou todos os produtos
     * @param produtoId 0 = todos os produtos, >0 = produto específico
     * @param inicio data inicial
     * @param fim data final
     */
    public void abrirHistorico(int produtoId, LocalDate inicio, LocalDate fim) {
        try {
            List<MovimentacaoEstoqueModel> historico = 
                MovimentacaoEstoqueModel.historicoMovimentacao(produtoId, inicio, fim);

            ObservableList<MovimentacaoEstoqueModel> listaHistorico = FXCollections.observableArrayList(historico);
            tabHistorico.setItems(listaHistorico);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao carregar histórico!");
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
}