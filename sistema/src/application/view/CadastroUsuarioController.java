package application.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.conexao;

public class CadastroUsuarioController {

	@FXML
	private Button btnSalvar;

	@FXML
	private ComboBox<String> cbCargo;

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtNome;

	@FXML
	private PasswordField txtSenha;

	@FXML
	public void initialize() {
		cbCargo.setItems(FXCollections.observableArrayList("Vendedor", "Gerente", "Estoquista"));
		txtNome.setOnAction(e -> txtEmail.requestFocus());
		txtEmail.setOnAction(e -> txtCpf.requestFocus());
		txtCpf.setOnAction(e -> txtSenha.requestFocus());
		txtSenha.setOnAction(e -> salvarUsuario());

	}

	@FXML
	private void salvarUsuario() {
		String nome = txtNome.getText().trim();
		String cpf = txtCpf.getText().trim();
		String email = txtEmail.getText().trim();
		String senha = txtSenha.getText().trim();
		String cargo = cbCargo.getValue();

		if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty() || cargo == null) {
			alert("Preencha todos os campos!");
			return;
		}

		if (!cpf.matches("\\d{11}")) {
			System.out.println("CPF inválido!");
			return;
		}

		// Usa a classe de conexão
		try (Connection con = conexao.getConnection()) {

			// Verifica duplicado
			PreparedStatement psCheck = con.prepareStatement("SELECT * FROM usuario WHERE cpf = ?");
			psCheck.setString(1, cpf);
			ResultSet rs = psCheck.executeQuery();
			if (rs.next()) {
				System.out.println("CPF já cadastrado!");
				return;
			}

			// Insere usuário
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO usuario(nome, cpf, email, senha, cargo) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, nome);
			ps.setString(2, cpf);
			ps.setString(3, email);
			ps.setString(4, senha);
			ps.setString(5, cargo);
			ps.executeUpdate();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Sucesso");
			alert.setContentText("Usuário cadastrado com sucesso!");
			alert.showAndWait();

			// Limpa campos
			txtNome.clear();
			txtCpf.clear();
			txtEmail.clear();
			txtSenha.clear();
			cbCargo.getSelectionModel().clearSelection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void alert(String mensagem) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Aviso");
		alert.setHeaderText(null);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}