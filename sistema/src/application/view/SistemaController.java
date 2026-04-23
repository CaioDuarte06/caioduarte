package application.view;

import application.Sessao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class SistemaController {

	@FXML
	private MenuItem itemCliente;

	@FXML
	private MenuItem itemProcessaEstoque;

	@FXML
	private MenuItem itemProdutos;

	@FXML
	private MenuItem itemSair;

	@FXML
	private void initialize() {

	}

	public void Sair() {
		System.exit(0);
	}

//somente o estoquista e gerente pode abrir essa tela 
	public void AbrirCadastroProduto() {
		String tipo = Sessao.cargoUsuario;
		if (!tipo.equals("Estoquista") && !tipo.equals("Gerente")) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setContentText("Acesso restrito!");
			alerta.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastroProdutos.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AbrirCadastroUsuarios() {
		String tipo = Sessao.cargoUsuario;
		if (!tipo.equals("Gerente")) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setContentText("Acesso restrito!");
			alerta.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastroUsuarios.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AbrirCadastroClientes() {
		String tipo = Sessao.cargoUsuario;
		if (!tipo.equals("Vendedor") && !tipo.equals("Gerente")) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setContentText("Acesso restrito!");
			alerta.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CadastroClientes.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AbrirProcessaEstoque() {
		String tipo = Sessao.cargoUsuario;
		if (!tipo.equals("Estoquista") && !tipo.equals("Gerente")) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setContentText("Acesso restrito!");
			alerta.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/ProcessarEstoque.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void AbrirVendas(ActionEvent event) {
		String tipo = Sessao.cargoUsuario;
		if (!tipo.equals("Vendedor") && !tipo.equals("Gerente")) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setContentText("Acesso restrito!");
			alerta.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("vendas.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("Vendas");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void AbrirPDV() {
		String tipo = Sessao.cargoUsuario;
		if (!tipo.equals("Vendedor") && !tipo.equals("Gerente")) {
			Alert alerta = new Alert(Alert.AlertType.WARNING);
			alerta.setContentText("Acesso restrito!");
			alerta.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PDV.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
