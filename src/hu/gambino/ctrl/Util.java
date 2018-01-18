package hu.gambino.ctrl;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Util {
	
	// Alert létrehozása egy metódussal (ahelyett hogy egyesével mindig beállítanánk az Alert tulajdonságait)
	public static Alert createAlert(AlertType alertType, String title, String headerText, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		return alert;
	}

	// Factory: Az előbb definiált metódus kerül újrahívásra ebben a metódusban, a hiányzó paraméter értékének megadásával
	public static Alert createAlert(String title, String headerText, String contentText) {
		return createAlert(AlertType.ERROR, title, headerText, contentText);
	}

	// Factory: Az előbb definiált metódus kerül újrahívásra ebben a metódusban, a hiányzó paraméter értékének megadásával
	public static Alert createAlert(String title, String contentText) {
		return createAlert(title, null, contentText);
	}

}
