package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class calculadoraController {

    @FXML
    private Button btnDividir;

    @FXML
    private Button btnMultiplicar;

    @FXML
    private Label btnResultado;

    @FXML
    private Button btnSoma;

    @FXML
    private Button btnSubtrair;

    @FXML
    private TextField txtnumero1;

    @FXML
    private TextField txtnumero2;


 public void Somar() {
	 double numero1;
	 double numero2;
	 try {
		 
    	numero1 = Double.valueOf(txtnumero1.getText());
	 } catch(Exception e) {
		 numero1=0;
		 txtnumero1.setText("0");
	 }
	 
	 try {
    	numero2 = Double.valueOf(txtnumero2.getText());
	 } catch(Exception e) {
		 numero2=0;
		 txtnumero2.setText("0");
	 }
    	
    	double resultado=numero1+numero2;
    	
    	btnResultado.setText(String.valueOf(resultado));
    	/*retorna o valor de double para string informa o resultado na label com o setText*/
    }


 public void Subtrair() {
 	double numero1 = StrToDbl(txtnumero1.getText());
 	double numero2 = StrToDbl(txtnumero2.getText());
 	double resultado=numero1-numero2;
 	
 	btnResultado.setText(String.valueOf(resultado));
 	
 }
 public void Dividir() {
	 	double numero1 = Double.valueOf(txtnumero1.getText());
	 	double numero2 = Double.valueOf(txtnumero2.getText());
	 	double resultado=numero1/numero2;
	 	
	 	btnResultado.setText(String.valueOf(resultado));
	 	
	 }
 public void Multiplicar() {
	 	double numero1 = Double.valueOf(txtnumero1.getText());
	 	double numero2 = Double.valueOf(txtnumero2.getText());
	 	double resultado=numero1*numero2;
	 	
	 	btnResultado.setText(String.valueOf(resultado));
	 	
 }

// metodo de converter string para double

private static double StrToDbl(String numero) {
	
try {
	return Double.valueOf(numero);

}catch(Exception e){
	return 0;
	
}
}

private void initialize(){
btnSubtrair.setOnAction(e->{Subtrair();});
btnMultiplicar.setOnAction(e->{Multiplicar();});
btnDividir.setOnAction(e->{Dividir();});
txtnumero1.setText("0");
txtnumero2.setText("0");
btnResultado.setText("Resultado:");
}
}


