package application.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.conexao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import application.Sessao;

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

	    try (Connection con = conexao.getConnection()) {

	    	PreparedStatement ps = con.prepareStatement(
	    		    "SELECT * FROM usuario WHERE (cpf = ? OR nome = ? OR email= ?) AND senha = ?"
	    		);

	        ps.setString(1, txtUsuario.getText());//nome
	        ps.setString(2, txtUsuario.getText());// ou cpf
	        ps.setString(3, txtUsuario.getText());// ou email
	        ps.setString(4, txtSenha.getText());//senha

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            // Diferentes formas de logar
	            Sessao.cargoUsuario = rs.getString("cargo");
	            Sessao.nomeUsuario = rs.getString("nome");
	            Sessao.emailUsuario = rs.getString("email");


	           /* System.out.println("Logado como:");
	            System.out.println("Cargo: " + Sessao.cargoUsuario);
	            System.out.println("Nome: " + Sessao.nomeUsuario);*/


	            // abrir sistema
	            FXMLLoader loader = new FXMLLoader(
	                getClass().getResource("sistema.fxml")
	            );

	            Parent root = loader.load();
	            Stage stage = new Stage();
	            stage.setTitle("Sistema");
	            stage.setScene(new Scene(root));
	            stage.show();

	            // fecha login
	            txtUsuario.getScene().getWindow().hide();

	        } else {
	            Alert alerta = new Alert(Alert.AlertType.ERROR);
	            alerta.setContentText("Usuário ou senha incorretos!");
	            alerta.showAndWait();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}