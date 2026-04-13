package application.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

	@FXML
	private TextField txtUsuario;

	@FXML
	private PasswordField txtSenha;
	
	@FXML
	
	public void initialize() {

	    // ENTER no usuário → vai pra senha
	    txtUsuario.setOnAction(e -> txtSenha.requestFocus());

	    // ENTER na senha → faz login
	    txtSenha.setOnAction(e -> entrar());
	}

	@FXML
	public void entrar() {
		String usuario = txtUsuario.getText();
		String senha = txtSenha.getText();

		if (usuario.equals("a") && senha.equals("a")) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("sistema.fxml"));
				Parent root = loader.load();
				Stage stage = new Stage();
				stage.setTitle("sistema");
				stage.setScene(new Scene(root));
				stage.show();

				// Fecha a tela de login
				txtUsuario.getScene().getWindow().hide();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("Erro de login");
			alerta.setHeaderText(null);
			alerta.setContentText("Usuário ou senha incorretos!");
			alerta.showAndWait();
		}
	}
}