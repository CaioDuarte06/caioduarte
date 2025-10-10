package application.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {

	    @FXML
	    private PasswordField Senha;

	    @FXML
	    private TextField Usuario;
	
	public void sair() {
		System.exit(0);
		}
	public void entrar() {

		try {
		String usuariotext=Usuario.getText();
		String senhatext=Senha.getText();
		
		if (usuariotext.equals("caio") && senhatext.equals("duarte06")) {
			Alert aviso;
			aviso=new Alert(Alert.AlertType.CONFIRMATION);
			aviso.setTitle("Confirmação");
			aviso.setContentText("Bem Vindo ao Sistema"+usuariotext);
			aviso.showAndWait();
		//------------------FECHA TELA DE LOGIN
			Usuario.getScene().getWindow().hide();
		//------------------ABRE A TELA PRINCIPAL
		Parent root = FXMLLoader.load(getClass().getResource("aplicativo.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		} else {
			Alert aviso;
			aviso=new Alert(Alert.AlertType.ERROR);
			aviso.setTitle("ERRO");
			aviso.setContentText("Usuário ou senha incorretos");
			aviso.showAndWait();
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
           /* @FXML ou @override -> indica que o codigo
              será executado assim que carregar a página */
	@FXML
private void initialize() {
	/*QUANDO PRESSIONAR ENTER NO CAMPO USUARIO
	FOCA A EDIÇÂO NO CAMPO DE SENHA */
		Usuario.setOnAction(e ->{Senha.requestFocus();});
} 
}

