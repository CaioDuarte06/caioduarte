package application.view;

import application.Dao.ClienteDAO;
import application.Dao.VendaDAO;
import application.model.ClienteModel;
import application.model.VendaModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientesController {

    @FXML private Button btnBuscar;
    @FXML private Button btnExcluir;
    @FXML private Button btnSalvar;

    @FXML private ComboBox<String> cbStatus;

    @FXML private TableView<ClienteModel> tabClientes;
    @FXML private TableColumn<ClienteModel, Integer> colId;
    @FXML private TableColumn<ClienteModel, String> colNome;
    @FXML private TableColumn<ClienteModel, String> colCpf;
    @FXML private TableColumn<ClienteModel, String> colCnpj;
    @FXML private TableColumn<ClienteModel, String> colEmail;
    @FXML private TableColumn<ClienteModel, String> colStatus;

    @FXML private TableView<VendaModel> tabHistorico;
    @FXML private TableColumn<VendaModel, String> colData;
    @FXML private TableColumn<VendaModel, Double> colTotal;

    @FXML private TextField txtBuscar;
    @FXML private TextField txtCpf;
    @FXML private TextField txtCnpj;
    @FXML private TextField txtEmail;
    @FXML private TextField txtNome;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private VendaDAO vendaDAO = new VendaDAO();

    @FXML
    public void initialize() {

        cbStatus.getItems().addAll("Ativo", "Inativo");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // limita CPF e CNPJ
        txtCpf.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().length() <= 11 ? c : null));
        txtCnpj.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().length() <= 14 ? c : null));

        carregarClientes();
        
        tabClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, cliente) -> {

            if (cliente != null) {

                tabHistorico.setItems(
                    FXCollections.observableArrayList(
                        vendaDAO.ultimasCompras(cliente.getId())
                    )
                );

            } else {
                tabHistorico.getItems().clear();
            }
        });
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
        txtCnpj.clear();
        txtEmail.clear();
        cbStatus.setValue(null);
    }

    private void carregarClientes() {
        ObservableList<ClienteModel> lista = FXCollections.observableArrayList(clienteDAO.listar());
        tabClientes.setItems(lista);
    }

    @FXML
    void salvarCliente(ActionEvent event) {

        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String cnpj = txtCnpj.getText();
        String email = txtEmail.getText();

        // validação geral
        if (!validarCliente(nome, cpf, cnpj, email)) return;

        // valida CPF real
        if (!cpfValido(cpf)) {
            alert("CPF inválido!");
            return;
        }

        // valida CNPJ (se preenchido)
        if (!cnpj.isEmpty() && !cnpjValido(cnpj)) {
            alert("CNPJ inválido!");
            return;
        }

        // duplicidade
        if (clienteDAO.existeCpf(cpf)) {
            alert("CPF já cadastrado!");
            return;
        }

        if (!cnpj.isEmpty() && clienteDAO.existeCnpj(cnpj)) {
            alert("CNPJ já cadastrado!");
            return;
        }

        ClienteModel c = new ClienteModel(
                nome,
                cpf,
                cnpj,
                email,
                cbStatus.getValue()
        );

        clienteDAO.inserir(c);

        carregarClientes();
        limparCampos();

        alert("Cliente cadastrado com sucesso!");
    }

    // ================== VALIDAÇÕES ==================

    private boolean validarCliente(String nome, String cpf, String cnpj, String email) {

        if (nome.length() < 3 || !nome.matches("[a-zA-Z ]+")) {
            alert("Nome inválido!");
            return false;
        }

        if (!cpf.matches("\\d{11}")) {
            alert("CPF deve ter 11 números!");
            return false;
        }

        if (!cnpj.isEmpty() && !cnpj.matches("\\d{14}")) {
            alert("CNPJ deve ter 14 números!");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            alert("Email inválido!");
            return false;
        }

        if (cbStatus.getValue() == null) {
            alert("Selecione o status!");
            return false;
        }

        return true;
    }

    private boolean cpfValido(String cpf) {

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) return false;

        // bloqueia 11111111111
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma = 0, peso = 10;

            for (int i = 0; i < 9; i++)
                soma += (cpf.charAt(i) - '0') * peso--;

            int dig1 = 11 - (soma % 11);
            if (dig1 >= 10) dig1 = 0;

            soma = 0;
            peso = 11;

            for (int i = 0; i < 10; i++)
                soma += (cpf.charAt(i) - '0') * peso--;

            int dig2 = 11 - (soma % 11);
            if (dig2 >= 10) dig2 = 0;

            return dig1 == (cpf.charAt(9) - '0') &&
                   dig2 == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }

    private boolean cnpjValido(String cnpj) {

        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14) return false;

        if (cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso1 = {5,4,3,2,9,8,7,6,5,4,3,2};
            int[] peso2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

            int soma = 0;

            for (int i = 0; i < 12; i++)
                soma += (cnpj.charAt(i) - '0') * peso1[i];

            int dig1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            soma = 0;

            for (int i = 0; i < 13; i++)
                soma += (cnpj.charAt(i) - '0') * peso2[i];

            int dig2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            return dig1 == (cnpj.charAt(12) - '0') &&
                   dig2 == (cnpj.charAt(13) - '0');

        } catch (Exception e) {
            return false;
        }
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(msg);
        a.showAndWait();
    }
}