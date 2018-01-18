package hu.gambino;

import java.io.FileInputStream;

import hu.gambino.data.DataProvider;
import hu.gambino.data.DatabaseDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Start extends Application {

	// A GUI FXML fájljának elérési útvonala
	public static String FXML_ROOT = "src/hu/gambino/fxml/";
	// Az adatbázis-kapcsolat létrehozásához szükséges cím
	private static String connectionString = "jdbc:postgresql://127.0.0.1/CurriculumVitae";

	// Singleton:
	private static DataProvider DAO;

	// A DAO-t ezzel a publikus függvénnyel kell elérni, ahhoz hogy csak egyszer
	// legyen példányosítva a fenti változó (DAO)!
	public static DataProvider getDAO() {
		// Ha a DAO-nak nincs értéke, akkor példányosítjuk a megfelelő osztályt, majd
		// visszaadjuk annak címét
		if (DAO == null) {
			DAO = new DatabaseDAO(connectionString);// DAO = new UserDAO(DATA_FILE);
		}
		return DAO;
	}

	public static void main(String[] args) {
		// A JavaFX osztály indítása
		Application.launch(args);
	}

	// Ez a metódus meghívódik az osztály indítása (Application.Launch()) után
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Ez a változó tudja betölteni az FXML fájlt!
		FXMLLoader loader = new FXMLLoader();
		// FXML fájl betöltése
		AnchorPane pane = loader.load(new FileInputStream(FXML_ROOT + "CVView.fxml"));
		// Betöltött FXML hozzáadása egy Scene osztályhoz
		Scene scene = new Scene(pane);

		// A Stage osztály beállítása:
		primaryStage.setScene(scene);
		primaryStage.setTitle("CV");
		primaryStage.show();

	}

}
