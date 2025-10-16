package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class calculadoraController {
	
	@FXML
	private Button btnReset;

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


  private void initialize() {
    	
    	/*
    	iniciar programa com valores zerados */
	  txtnumero1.setText("0");
    	txtnumero2.setText("0");
    	
    	/* O setOnAction aciona o evento do componente
    	por exemplo: o click no botão
    	ou o enter em um text field */
    	
    	btnSubtrair.setOnAction(e->{Subtrair();});
    	btnMultiplicar.setOnAction(e->{Multiplicar();});
    	btnDividir.setOnAction(e->{Dividir();});
    	btnReset.setOnAction(e->{
    		txtnumero1.setText("0");
    		txtnumero2.setText("0");
    		btnResultado.setText("Resultado:");    		
    	});
    	

    	/*adicionar um escutador de evento no 
    	text field de numero 1
    	ao digitar dentro do text fiel ele vai trocar a letra
    	 por uma informação vazia atravez do replaceAll*/
    	txtnumero1.textProperty().addListener(
    	(observable, oldValue, newValue)->{
    		txtnumero1.setText(newValue.replaceAll("[^\\d.]",""));	
    	});
    	
    	txtnumero2.textProperty().addListener(
    	(observable, oldValue, newValue)->{
    		txtnumero2.setText(newValue.replaceAll("[^\\d.]",""));	
    	 });
    
    	
    
    }
    
  
    public void Somar() {
    	double numero1;
    	double numero2;
    	try {
    	 numero1 = Double.valueOf(txtnumero1.getText()); // utiliza o getText para retornar a informação digitada 
    	} catch(Exception e) {
    	 numero1=0;
    	 txtnumero1.setText("0");
    	}
    	
    	try {
       	 numero2 = Double.valueOf(txtnumero2.getText()); // utiliza o getText para retornar a informação digitada 
       	} catch(Exception e) {
       	 numero2=0;
       	txtnumero2.setText("0");
       	}
    	
    	double resultado=numero1+numero2;
    	
       	
    	String parOuImpar;
    			 
    	if (resultado % 2 == 0) {
    		parOuImpar= "é Par.";
    	
    	}else {
    		parOuImpar = "é Impar.";
    	}
    
    	
    	// retorna o valor de double para string
    	//informa o resultado na label com o setText
    	 
    	btnResultado.setText("Resultado: "+String.valueOf(resultado));
    }	
    
    public void Subtrair() {
    	double numero1 = StrToDbl(txtnumero1.getText()); // utiliza o getText para retornar a informação digitada 
    	double numero2 = StrToDbl(txtnumero2.getText());//converte o tipo de texto para double
    	txtnumero1.setText(String.valueOf(numero1));
    	txtnumero2.setText(String.valueOf(numero2));
    	
    	double resultado=numero1-numero2;
    	
    	String parOuImpar;
		 
    	if (resultado % 2 == 0) {
    		parOuImpar= "é Par.";
    	
    	}else {
    		parOuImpar = "é Impar.";
    	}
    
    	
    	// retorna o valor de double para string
    	//informa o resultado na label com o setText
    	btnResultado.setText("Resultado: "+String.valueOf(resultado));
    }
    
    public void Multiplicar() {
    	double numero1 = Double.valueOf(txtnumero1.getText()); // utiliza o getText para retornar a informação digitada 
    	double numero2 = Double.valueOf(txtnumero2.getText());//converte o tipo de texto para double
    	double resultado=numero1*numero2;
    	
    	String parOuImpar;
		 
    	if (resultado % 2 == 0) {
    		parOuImpar= "é Par.";
    	
    	}else {
    		parOuImpar = "é Impar.";
    	}
    
    	
    	// retorna o valor de double para string
    	//informa o resultado na label com o setText
    	btnResultado.setText("Resultado: "+String.valueOf(resultado));
    }
    
    public void Dividir() {
    	double numero1 = Double.valueOf(txtnumero1.getText()); // utiliza o getText para retornar a informação digitada 
    	double numero2 = Double.valueOf(txtnumero2.getText());//converte o tipo de texto para double
    	double resultado=numero1/numero2;
    	
    	String parOuImpar;
		 
    	if (resultado % 2 == 0) {
    		parOuImpar= "é Par.";
    	
    	}else {
    		parOuImpar = "é Impar.";
    	}
    
    	
    	// retorna o valor de double para string
    	//informa o resultado na label com o setText
    	btnResultado.setText("Resultado: "+String.valueOf(resultado));
    }
//metodo de converter string para double
    public static double StrToDbl(String numero) {
	try {
		return Double.valueOf(numero);
	} catch(Exception e) {
		return 0;
	}
	
}
}


