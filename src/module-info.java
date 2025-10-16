module projeto {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.view to javafx.graphics, javafx.fxml;
}
