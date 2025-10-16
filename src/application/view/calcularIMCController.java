package application.view;


	
import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import application.view.calculadoraController;

public class calcularIMCController {

    @FXML
    private TextField altura;

    @FXML
    private Label btnResultado;

    @FXML
    private Button btncalculadora;

    @FXML
    private TextField peso;
    
    @FXML
    private TextField nome;
    
    
    @FXML
    private void btncalculadora() {
    	String txtnome= nome.getText();
    	double txtpeso= Double.valueOf(peso.getText());
    	
    	double txtaltura= calculadoraController.StrToDbl(altura.getText());
    	double imc=txtpeso / (txtaltura*txtaltura);
    	//btnResultado.setText("O seu IMC é: " +String.valueOf(imc));
    	btnResultado.setText(
    	String.format(nome.getText()+"O seu IMC é: %.2f",imc)+" " +classificarIMC(imc));
    	
    }    

    private String classificarIMC(double imc) {
    if(imc<18.5) {
    	return "Abaixo do peso";
    } else if(imc<24.9) {
    	return "Peso normal";
    }else if(imc<34.9) {
    	return "Obesidade Grau I";
    }
	return null;
    }

}


