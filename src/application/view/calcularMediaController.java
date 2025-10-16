package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class calcularMediaController {

    @FXML
    private Label lblResultado;

    @FXML
    private Button btnMedia;

    @FXML
    private TextField txtnota1;

    @FXML
    private TextField txtnota2;

    @FXML
    private TextField txtnota3;

    @FXML
    private TextField txtnota4;

    

    @FXML
private void calcularMedia() {
	
	/* txtnota1.setText("0");
		txtnota2.setText("0");
		txtnota3.setText("0");
		txtnota4.setText("0");*/
		lblResultado.setText("Resultado:");
		 btnMedia.setOnAction(e -> calcularMedia());

	
    	double numero1 = Double.valueOf(txtnota1.getText()); 
    	double numero2 = Double.valueOf(txtnota2.getText());
    	double numero3 = Double.valueOf(txtnota3.getText());
    	double numero4 = Double.valueOf(txtnota4.getText());
    	 double media = (numero1 + numero2 + numero3 + numero4) / 4;
         lblResultado.setText("Média: " + String.valueOf(media));
         
}

}

