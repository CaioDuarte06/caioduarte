package application.view;

import application.Dao.ClienteDAO;
import application.model.ClienteModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientesController {

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnSalvar;

	@FXML
	private ComboBox<String> cbStatus;

	@FXML
	private TableView<ClienteModel> tabClientes;

	@FXML
	private TableColumn<ClienteModel, Integer> colId;

	@FXML
	private TableColumn<ClienteModel, String> colNome;

	@FXML
	private TableColumn<ClienteModel, String> colCpf;

	@FXML
	private TableColumn<ClienteModel, String> colEmail;

	@FXML
	private TableColumn<ClienteModel, String> colStatus;

	@FXML
	private TextField txtBuscar;

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtNome;

	private ClienteDAO clienteDAO = new ClienteDAO();

	private ObservableList<ClienteModel> listaClientes = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		cbStatus.getItems().addAll("Ativo", "Inativo");

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		txtNome.setOnAction(e -> txtCpf.requestFocus());
		txtCpf.setOnAction(e -> txtEmail.requestFocus());
		txtEmail.setOnAction(e -> salvarCliente(e));
		txtBuscar.setOnAction(e -> buscarCliente(e));

		carregarClientes();
	}

	@FXML
	void buscarCliente(ActionEvent event) {

		tabClientes.setItems(FXCollections.observableArrayList(clienteDAO.buscar(txtBuscar.getText())));
	}

	@FXML
	void excluirCliente(ActionEvent event) {

		ClienteModel c = tabClientes.getSelectionModel().getSelectedItem();

		if (c != null) {
			clienteDAO.excluir(c.getId());
			carregarClientes();
		}
	}

	private void limparCampos() {
		txtNome.clear();
		txtCpf.clear();
		txtEmail.clear();
		cbStatus.setValue(null);
	}

	private void carregarClientes() {

		tabClientes.setItems(FXCollections.observableArrayList(clienteDAO.listar()));
	}

	@FXML
	void salvarCliente(ActionEvent event) {

		if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty()) {
			alert("Preencha os campos obrigatórios!");
			return;
		}

		if (clienteDAO.existeCpf(txtCpf.getText())) {
			alert("CPF já cadastrado!");
			return;
		}

		ClienteModel c = new ClienteModel(txtNome.getText(), txtCpf.getText(), txtEmail.getText(), cbStatus.getValue());

		clienteDAO.inserir(c);

		carregarClientes();
		limparCampos();
	}

	private void alert(String mensagem) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Aviso");
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}

}
