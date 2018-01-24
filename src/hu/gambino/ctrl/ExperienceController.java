package hu.gambino.ctrl;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import hu.gambino.Start;
import hu.gambino.data.DataProvider;
import hu.gambino.data.Education;
import hu.gambino.data.Job;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

// Ez az osztály köti össze (irányítja) az FXML-ben leírt GUI-t a javaFX-el
public class ExperienceController implements Initializable {

	// A Start osztályból kér egy DAO-t
	DataProvider dao = Start.getDAO();

	// Ez a változó tárolja, hogy melyik önéletrajz adatait jelenítsük/szerkesszük
	// (belongsto oszlop az adatbázisban). Értékét a CVController nevő osztálytól
	// kapjuk!
	@FXML
	public Long CvId = null;

	// Ez az osztály belépési pontja
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// A táblázatban lévő cellák szabályainak beállítása (az osztály melyik
		// változóját melyik oszlopba rakja)
		colIdEducation.setCellValueFactory(new PropertyValueFactory<Education, Long>("id"));
		colNameEducation.setCellValueFactory(new PropertyValueFactory<Education, String>("name"));
		colBeginningEducation.setCellValueFactory(new PropertyValueFactory<Education, Date>("beginning"));
		colEndingEducation.setCellValueFactory(new PropertyValueFactory<Education, Date>("ending"));
		colAchievementEducation.setCellValueFactory(new PropertyValueFactory<Education, String>("achievement"));

		colIdJob.setCellValueFactory(new PropertyValueFactory<Job, Long>("id"));
		colNameJob.setCellValueFactory(new PropertyValueFactory<Job, String>("name"));
		colBeginningJob.setCellValueFactory(new PropertyValueFactory<Job, Date>("beginning"));
		colEndingJob.setCellValueFactory(new PropertyValueFactory<Job, Date>("ending"));
		colPositionJob.setCellValueFactory(new PropertyValueFactory<Job, String>("position"));
		colResponsibilitiesJob.setCellValueFactory(new PropertyValueFactory<Job, String>("responsibilities"));

	}

	// Adatok frissítése (újrabeolvasása az adatbázisból), majd rendezése (táblázat:
	// Education)
	@SuppressWarnings("unchecked")
	public void refreshLocalTableEducation() {
		// Az adatok beolvasása a táblázatba
		try {
			tableEducation.setItems(FXCollections.observableArrayList(dao.getEducations(CvId)));
		} catch (Exception e) {
			// Hibaüzenet létrehozása
			Alert alert = Util.createAlert("HIBA", "Adatbeolvasási hiba", "Hiba történt az adatok beolvasása során!");
			alert.show();
			e.printStackTrace();
		}

		// Táblázat rendezése
		tableEducation.getSortOrder().setAll(colIdEducation);
	}

	// Adatok frissítése (újrabeolvasása az adatbázisból), majd rendezése (táblázat:
	// Job)
	@SuppressWarnings("unchecked")
	public void refreshLocalTableJob() {
		// Az adatok beolvasása a táblázatba
		try {
			tableJob.setItems(FXCollections.observableArrayList(dao.getJobs(CvId)));
		} catch (Exception e) {
			// Hibaüzenet létrehozása
			Alert alert = Util.createAlert("HIBA", "Adatbeolvasási hiba", "Hiba történt az adatok beolvasása során!");
			alert.show();
			e.printStackTrace();
		}

		// Táblázat rendezése
		tableJob.getSortOrder().setAll(colIdJob);
	}

	// Változók deklarálása az FXML fájl alapján:
	// ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** //
	@FXML
	private Button btnDeleteEducation;
	@FXML
	private Button btnSaveEducation;
	@FXML
	private Button btnNewEducation;
	@FXML
	private Button btnDeleteJob;
	@FXML
	private Button btnSaveJob;
	@FXML
	private Button btnNewJob;

	@FXML
	private TextField txtIdEducation;
	@FXML
	private TextField txtNameEducation;
	@FXML
	private DatePicker dtpBeginningEducation;
	@FXML
	private DatePicker dtpEndingEducation;
	@FXML
	private TextField txtAchievementEducation;

	@FXML
	private TextField txtIdJob;
	@FXML
	private TextField txtNameJob;
	@FXML
	private DatePicker dtpBeginningJob;
	@FXML
	private DatePicker dtpEndingJob;
	@FXML
	private TextField txtPositionJob;
	@FXML
	private TextField txtResponsibilitiesJob;

	@FXML
	private TableView<Education> tableEducation;
	@FXML
	private TableView<Job> tableJob;

	@FXML
	private TableColumn<Education, Long> colIdEducation;
	@FXML
	private TableColumn<Education, String> colNameEducation;
	@FXML
	private TableColumn<Education, Date> colBeginningEducation;
	@FXML
	private TableColumn<Education, Date> colEndingEducation;
	@FXML
	private TableColumn<Education, String> colAchievementEducation;
	@FXML
	private TableColumn<Job, Long> colIdJob;
	@FXML
	private TableColumn<Job, String> colNameJob;
	@FXML
	private TableColumn<Job, Date> colBeginningJob;
	@FXML
	private TableColumn<Job, Date> colEndingJob;
	@FXML
	private TableColumn<Job, String> colPositionJob;
	@FXML
	private TableColumn<Job, String> colResponsibilitiesJob;

	// ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** //

	// Az FXML fájlban meghívott metódusok implementálása:
	// ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** //

	// A mentés gombra való kattintáskor meghívott metódus
	@FXML
	private void doSaveEducation(ActionEvent event) {
		// Ha nincs ID megadva, akkor nincs mentés!
		if (txtIdEducation != null && !txtIdEducation.getText().equals("")) {
			// Adatok beolvasása
			ArrayList<Education> data = null;
			try {
				data = dao.getEducations(CvId);
			} catch (Exception e) {
				// Hibaüzenet létrehozása
				Alert alert = Util.createAlert("HIBA", "Adatbeolvasási hiba",
						"Hiba történt az adatok beolvasása során!");
				alert.show();
				e.printStackTrace();
			}

			if (data != null) {
				// A GUI-ban megadott ID megkeresése, majd annak adatainak frissítése
				for (int i = 0; i < data.size(); i++) {
					if (Long.decode(txtIdEducation.getText()).equals(data.get(i).getId())) {
						// A GUI-ban megadott ID meg lett találva
						try {
							// Adatok frissítése
							dao.updateEducation(new Education(Long.decode(txtIdEducation.getText()),
									txtNameEducation.getText(), Date.valueOf(dtpBeginningEducation.getValue()),
									Date.valueOf(dtpEndingEducation.getValue()), CvId,
									txtAchievementEducation.getText()));

						} catch (Exception e) {
							// Hibaüzenet létrehozása
							Alert alert = Util.createAlert("HIBA", "Frissítési hiba",
									"Hiba történt az adatok frissítése során!");
							alert.show();
							e.printStackTrace();
						}
					}
				}
			}
		}

		// Adatok frissítése lokálisan
		refreshLocalTableEducation();
	}

	// Az új gombra való kattintáskor meghívott metódus
	@FXML
	private void doNewEducation(ActionEvent event) {
		try {
			// GUI-ban megadott adatok hozzáadása
			dao.addEducation(new Education(new Long(0), txtNameEducation.getText(),
					Date.valueOf(dtpBeginningEducation.getValue()), Date.valueOf(dtpEndingEducation.getValue()), CvId,
					txtAchievementEducation.getText()));

			// GUI-ban megadott adatok hozzáadása lokálisan
			refreshLocalTableEducation();

		} catch (Exception e) {
			// Hibaüzenet létrehozása
			Alert alert = Util.createAlert("HIBA", "Hozzáadási hiba", "Hiba történt az adatok hozzáadásánál!");
			alert.show();
			e.printStackTrace();
		}

	}

	// A törlés gombra való kattintáskor meghívott metódus
	@FXML
	private void doDeleteEducation(ActionEvent event) {
		// A jelenleg kijelölt sorhoz tartozó CV megállapítása
		Education selectedEducation = tableEducation.getSelectionModel().getSelectedItem();
		// Ha nincs kijelölve sor, akkor hibaüzenet
		if (selectedEducation == null) {
			Alert alert = Util.createAlert("HIBA", "Kiválasztási hiba", "Válasszon egy sort!");
			alert.show();
			// Amennyiben ki van jelölve egy sor
		} else {
			Alert alertConfirm = Util.createAlert(AlertType.CONFIRMATION, "TÖRLÉS MEGERŐSÍTÉSE", "",
					"Biztos hogy szeretnéd törölni a kijelölt adatot?");

			Optional<ButtonType> alertResult = alertConfirm.showAndWait();

			if (alertResult.get() == ButtonType.OK) {
				try {
					// Törlés
					dao.deleteEducation(selectedEducation);
					// Törlés lokálisan
					tableEducation.getItems().remove(selectedEducation);
				} catch (Exception e) {
					// Hibaüzenet létrehozása
					Alert alert = Util.createAlert("Hiba", "Törlési hiba!");
					alert.show();
					e.printStackTrace();
				}
			}
		}
	}

	// A mentés gombra való kattintáskor meghívott metódus
	@FXML
	private void doSaveJob(ActionEvent event) {
		// Ha nincs ID megadva, akkor nincs mentés!
		if (txtIdJob != null && !txtIdJob.getText().equals("")) {
			// Adatok beolvasása
			ArrayList<Job> data = null;
			try {
				data = dao.getJobs(CvId);
			} catch (Exception e) {
				// Hibaüzenet létrehozása
				Alert alert = Util.createAlert("HIBA", "Adatbeolvasási hiba",
						"Hiba történt az adatok beolvasása során!");
				alert.show();
				e.printStackTrace();
			}

			if (data != null) {
				// A GUI-ban megadott ID megkeresése, majd annak adatainak frissítése
				for (int i = 0; i < data.size(); i++) {
					if (Long.decode(txtIdJob.getText()).equals((data.get(i)).getId())) {
						// A GUI-ban megadott ID meg lett találva
						try {
							// Adatok frissítése
							dao.updateJob(new Job(Long.decode(txtIdJob.getText()), txtNameJob.getText(),
									Date.valueOf(dtpBeginningJob.getValue()), Date.valueOf(dtpEndingJob.getValue()),
									CvId, txtPositionJob.getText(), txtResponsibilitiesJob.getText()));

						} catch (Exception e) {
							// Hibaüzenet létrehozása
							Alert alert = Util.createAlert("HIBA", "Frissítési hiba",
									"Hiba történt az adatok frissítése során!");
							alert.show();
							e.printStackTrace();
						}
					}
				}
			}
		}

		// Adatok frissítése lokálisan
		refreshLocalTableJob();
	}

	// Az új gombra való kattintáskor meghívott metódus
	@FXML
	private void doNewJob(ActionEvent event) {
		try {
			// GUI-ban megadott adatok hozzáadása
			dao.addJob(new Job(new Long(0), txtNameJob.getText(), Date.valueOf(dtpBeginningJob.getValue()),
					Date.valueOf(dtpEndingJob.getValue()), CvId, txtPositionJob.getText(),
					txtResponsibilitiesJob.getText()));

			// Adatok frissítése lokálisan
			refreshLocalTableJob();

		} catch (Exception e) {
			// Hibaüzenet létrehozása
			Alert alert = Util.createAlert("HIBA", "Hozzáadási hiba", "Hiba történt az adatok hozzáadásánál!");
			alert.show();
			e.printStackTrace();
		}

	}

	// A törlés gombra való kattintáskor meghívott metódus
	@FXML
	private void doDeleteJob(ActionEvent event) {
		// A jelenleg kijelölt sorhoz tartozó CV megállapítása
		Job selectedJob = tableJob.getSelectionModel().getSelectedItem();
		// Ha nincs kijelölve sor, akkor hibaüzenet
		if (selectedJob == null) {
			Alert alert = Util.createAlert("HIBA", "Kiválasztási hiba", "Válasszon egy sort!");
			alert.show();
			// Amennyiben ki van jelölve egy sor
		} else {
			Alert alertConfirm = Util.createAlert(AlertType.CONFIRMATION, "TÖRLÉS MEGERŐSÍTÉSE", "",
					"Biztos hogy szeretnéd törölni a kijelölt adatot?");

			Optional<ButtonType> alertResult = alertConfirm.showAndWait();

			if (alertResult.get() == ButtonType.OK) {
				try {
					// Törlés
					dao.deleteJob(selectedJob);
					// Törlés lokálisan
					tableJob.getItems().remove(selectedJob);
				} catch (Exception e) {
					// Hibaüzenet létrehozása
					Alert alert = Util.createAlert("Hiba", "Törlési hiba!");
					alert.show();
					e.printStackTrace();
				}
			}
		}
	}

	// Egy sorra való kattintáskor meghívott metódus
	@FXML
	private void rowClickedEducation(MouseEvent event) {
		// A jelenleg kijelölt sorhoz tartozó CV megállapítása
		Education selectedEducation = tableEducation.getSelectionModel().getSelectedItem();

		if (selectedEducation != null) {
			// A kijelölt sor adatainak bemásolása a TextField-ekbe
			txtIdEducation.setText(selectedEducation.getId() + "");
			txtNameEducation.setText(selectedEducation.getName());
			dtpBeginningEducation.setValue(selectedEducation.getBeginning().toLocalDate());
			dtpEndingEducation.setValue(selectedEducation.getEnding().toLocalDate());
			txtAchievementEducation.setText(selectedEducation.getAchievement());
		}
	}

	// Egy sorra való kattintáskor meghívott metódus
	@FXML
	private void rowClickedJob(MouseEvent event) {
		// A jelenleg kijelölt sorhoz tartozó CV megállapítása
		Job selectedJob = tableJob.getSelectionModel().getSelectedItem();

		if (selectedJob != null) {
			// A kijelölt sor adatainak bemásolása a TextField-ekbe
			txtIdJob.setText(selectedJob.getId() + "");
			txtNameJob.setText(selectedJob.getName());
			dtpBeginningJob.setValue(selectedJob.getBeginning().toLocalDate());
			dtpEndingJob.setValue(selectedJob.getEnding().toLocalDate());
			txtPositionJob.setText(selectedJob.getPosition());
			txtResponsibilitiesJob.setText(selectedJob.getResponsibilities());
		}
	}

	// ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** //

}
