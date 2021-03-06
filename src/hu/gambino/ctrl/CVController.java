package hu.gambino.ctrl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;

import hu.gambino.Start;
import hu.gambino.data.CV;
import hu.gambino.data.DataProvider;
import hu.gambino.data.Education;
import hu.gambino.data.Job;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// Ez az osztály köti össze (irányítja) az FXML-ben leírt GUI-t a javaFX-el
public class CVController implements Initializable {

	// A Start osztályból kér egy DAO-t
	DataProvider dao = Start.getDAO();

	// Ez az osztály belépési pontja
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Az adatok beolvasása a táblázatba
		try {
			tableCV.setItems(FXCollections.observableArrayList(dao.getAllCVs()));
		} catch (Exception e) {
			// Hibaüzenet létrehozása
			Alert alert = Util.createAlert("HIBA", "Adatbeolvasási hiba", "Hiba történt az adatok beolvasása során!");
			alert.show();
			e.printStackTrace();
		}

		// A táblázatban lévő cellák szabályainak beállítása (az osztály melyik
		// változóját melyik oszlopba rakja)
		colId.setCellValueFactory(new PropertyValueFactory<CV, Long>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<CV, String>("name"));
		colPlaceOfBirth.setCellValueFactory(new PropertyValueFactory<CV, String>("placeOfBirth"));
		colDateOfBirth.setCellValueFactory(new PropertyValueFactory<CV, Date>("dateOfBirth"));
		colNationality.setCellValueFactory(new PropertyValueFactory<CV, String>("nationality"));
		colEmail.setCellValueFactory(new PropertyValueFactory<CV, String>("email"));
		colPhone.setCellValueFactory(new PropertyValueFactory<CV, String>("phone"));
		colDateOfCreation.setCellValueFactory(new PropertyValueFactory<CV, Date>("dateOfCreation"));

		// Táblázat rendezése
		tableCV.getSortOrder().setAll(colId);

	}

	// Adatok frissítése (újrabeolvasása az adatbázisból), majd rendezése
	@SuppressWarnings("unchecked")
	public void refreshLocalTable() {
		// Az adatok beolvasása a táblázatba
		try {
			tableCV.setItems(FXCollections.observableArrayList(dao.getAllCVs()));
		} catch (Exception e) {
			// Hibaüzenet létrehozása
			Alert alert = Util.createAlert("HIBA", "Adatbeolvasási hiba", "Hiba történt az adatok beolvasása során!");
			alert.show();
			e.printStackTrace();
		}

		// Táblázat rendezése
		tableCV.getSortOrder().setAll(colId);
	}

	// Változók deklarálása az FXML fájl alapján:
	// ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** //
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnNew;
	@FXML
	private Button btnBelongings;
	@FXML
	private Button btnSaveToPDF;

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPlaceOfBirth;
	@FXML
	private DatePicker dtpDateOfBirth;
	@FXML
	private TextField txtNationality;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhone;

	@FXML
	private Label lblID;
	@FXML
	private Label lblName;
	@FXML
	private Label lblPlaceOfBirth;
	@FXML
	private Label lblDateOfBirth;
	@FXML
	private Label lblNationality;
	@FXML
	private Label lblEmail;
	@FXML
	private Label lblPhone;

	@FXML
	private TableView<CV> tableCV;

	@FXML
	private TableColumn<CV, Long> colId;
	@FXML
	private TableColumn<CV, String> colName;
	@FXML
	private TableColumn<CV, String> colPlaceOfBirth;
	@FXML
	private TableColumn<CV, Date> colDateOfBirth;
	@FXML
	private TableColumn<CV, String> colNationality;
	@FXML
	private TableColumn<CV, String> colEmail;
	@FXML
	private TableColumn<CV, String> colPhone;
	@FXML
	private TableColumn<CV, Date> colDateOfCreation;
	// ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** //

	// Az FXML fájlban meghívott metódusok implementálása:
	// ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** //

	// A mentés gombra való kattintáskor meghívott metódus
	@FXML
	private void doSave(ActionEvent event) {
		// Ha nincs ID megadva, akkor nincs mentés!
		if (txtId != null && !txtId.getText().equals("")) {
			// Adatok beolvasása
			ArrayList<CV> data = null;
			try {
				data = dao.getAllCVs();
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
					if (Long.decode(txtId.getText()).equals((data.get(i)).getId())) {
						// A GUI-ban megadott ID meg lett találva
						try {
							// Adatok frissítése
							dao.updateCV(new CV(Long.decode(txtId.getText()), txtName.getText(),
									txtPlaceOfBirth.getText(), Date.valueOf(dtpDateOfBirth.getValue()),
									txtNationality.getText(), txtEmail.getText(), txtPhone.getText(),
									new Date(Calendar.getInstance().getTime().getTime())));

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
		refreshLocalTable();
	}

	// Az "Új" gombra való kattintáskor meghívott metódus
	@FXML
	private void doNew(ActionEvent event) {
		try {
			// GUI-ban megadott adatok hozzáadása
			dao.addCV(new CV(new Long(0), txtName.getText(), txtPlaceOfBirth.getText(),
					Date.valueOf(dtpDateOfBirth.getValue()), txtNationality.getText(), txtEmail.getText(),
					txtPhone.getText(), new Date(Calendar.getInstance().getTime().getTime())));

			// Adatok frissítése lokálisan
			refreshLocalTable();

		} catch (Exception e) {
			// Hibaüzenet létrehozása
			Alert alert = Util.createAlert("HIBA", "Hozzáadási hiba", "Hiba történt az adatok hozzáadásánál!");
			alert.show();
			e.printStackTrace();
		}

	}

	// A "Törlés" gombra való kattintáskor meghívott metódus
	@FXML
	private void doDelete(ActionEvent event) {
		// A jelenleg kijelölt sorhoz tartozó CV megállapítása
		CV selectedCV = tableCV.getSelectionModel().getSelectedItem();

		// Ha nincs kijelölve sor, akkor hibaüzenet
		if (selectedCV == null) {
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
					dao.deleteCV(selectedCV);
					// Törlés lokálisan
					tableCV.getItems().remove(selectedCV);
				} catch (Exception e) {
					// Hibaüzenet létrehozása
					Alert alert = Util.createAlert("Hiba", "Törlési hiba!");
					alert.show();
					e.printStackTrace();
				}
			}
		}
	}

	// A "Csatolt adatok" gombra való kattintáskor meghívott metódus
	@FXML
	private void doShowBelongings(ActionEvent event) {
		// A jelenleg kijelölt sorhoz tartozó CV megállapítása
		CV selectedCV = tableCV.getSelectionModel().getSelectedItem();

		if (selectedCV != null) {
			try {
				// Új ablak létrehozása, ahol a kijelölt önéletrajzhoz tartozó tapasztalatokat
				// szerkeszthetjük
				FXMLLoader fxmlLoader = new FXMLLoader();
				Parent root = fxmlLoader.load(new FileInputStream(Start.FXML_ROOT + "ExperienceView.fxml"));

				// A kijelölt önéletrajz ID-jének továbbadása az ExperienceControllernek
				fxmlLoader.<ExperienceController>getController().CvId = selectedCV.getId();

				// Ablak beállítása
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Experiences");

				// Miután az ablak megjelent, a táblázatok frissítése
				// Ezt azért szükséges így elvégezni, mert az ExperienceController-nek átadott
				// ID-t csak az Initialize metódus lefutása után kapja meg az osztály!
				stage.addEventHandler(WindowEvent.WINDOW_SHOWING, new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent window) {
						fxmlLoader.<ExperienceController>getController().refreshLocalTableEducation();
						fxmlLoader.<ExperienceController>getController().refreshLocalTableJob();
					}
				});

				// Bezárásnál a főablak(önéletraz szerkesztő) újbóli megjelenítése
				stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent window) {
						((Stage) ((Node) event.getSource()).getScene().getWindow()).show();
					}
				});

				stage.show();

				// Az önéletrajz szerkesztő elrejtése
				((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();

			} catch (Exception e) {
				Alert alert = Util.createAlert("HIBA", "Hiba keletkezett az ablak betöltése közben!");
				alert.show();
				e.printStackTrace();
			}
		} else {
			Alert alert = Util.createAlert("HIBA", "Kiválasztási hiba", "Válasszon egy sort!");
			alert.show();
		}
	}

	@FXML
	private void doSaveToPDF(ActionEvent event) {
		// A jelenleg kijelölt sorhoz tartozó CV megállapítása
		CV selectedCV = tableCV.getSelectionModel().getSelectedItem();
		ArrayList<Education> dataEducations = null;
		ArrayList<Job> dataJobs = null;

		if (selectedCV != null) {
			try {
				dataEducations = dao.getEducations(selectedCV.getId());
				dataJobs = dao.getJobs(selectedCV.getId());
			} catch (Exception e) {
				// Hibaüzenet létrehozása
				Alert alert = Util.createAlert("HIBA", "Adatbeolvasási hiba",
						"Hiba történt az adatok beolvasása során!");
				alert.show();
				e.printStackTrace();
			}

			if (dataEducations != null || dataJobs != null) {
				Document document = new Document();

				try {
					PdfWriter.getInstance(document, new FileOutputStream(
							selectedCV.getName().toLowerCase().replaceAll(" ", "").trim() + "CV.pdf"));
				} catch (Exception e) {
					Alert alert = Util.createAlert("HIBA", "Hiba a PDF betöltése/létrehozása közben!");
					alert.show();
					e.printStackTrace();
				}

				document.open();
				document.setPageSize(PageSize.A4);

				try {
					Util.writeTextToPDF(document, selectedCV.getName(), 20);
					Util.writeTextToPDF(document, selectedCV.getEmail(), 8);
					Util.writeTextToPDF(document, selectedCV.getPhone(), 8);
					Util.writeTextToPDF(document, "Állampolgárság: " + selectedCV.getNationality(), 8);
					Util.writeTextToPDF(document, "Születési hely: " + selectedCV.getPlaceOfBirth(), 8);
					Util.writeTextToPDF(document, "Születési idő: " + selectedCV.getDateOfBirth().toString(), 8);
					Util.writeTextToPDF(document, "Létrehozva: " + selectedCV.getDateOfCreation().toString(), 8);

					if (dataEducations.size() > 0) {
						Util.writeTextToPDF(document, " ");
						Util.writeTextToPDF(document, " ");
						Util.writeTextToPDF(document, " ");
						Util.writeTextToPDF(document, "Tanulmányok:");

						for (int i = 0; i < dataEducations.size(); i++) {
							Util.writeTextToPDF(document, dataEducations.get(i).getName(), 8);
							Util.writeTextToPDF(document, dataEducations.get(i).getBeginning().toString() + " - "
									+ dataEducations.get(i).getEnding(), 8);
							Util.writeTextToPDF(document, dataEducations.get(i).getAchievement(), 8);
							Util.writeTextToPDF(document, " ");
						}

					}

					if (dataEducations.size() > 0) {
						Util.writeTextToPDF(document, " ");
						Util.writeTextToPDF(document, " ");
						Util.writeTextToPDF(document, " ");
						Util.writeTextToPDF(document, "Munkák:");

						for (int i = 0; i < dataJobs.size(); i++) {
							Util.writeTextToPDF(document, dataJobs.get(i).getName(), 8);
							Util.writeTextToPDF(document,
									dataJobs.get(i).getBeginning().toString() + " - " + dataJobs.get(i).getEnding(), 8);
							Util.writeTextToPDF(document, dataJobs.get(i).getPosition(), 8);
							Util.writeTextToPDF(document, dataJobs.get(i).getResponsibilities(), 8);
							Util.writeTextToPDF(document, " ");
						}
					}

				} catch (Exception e) {
					Alert alert = Util.createAlert("HIBA", "Hiba keletkezett a PDF fájlba írása közben!");
					alert.show();
					e.printStackTrace();
				}

				if (document.isOpen()) {
					Alert alert = Util.createAlert(AlertType.INFORMATION, "SIKERES MENTÉS", "",
							"Az adatok sikeresen le lettek mentve egy PDF fájlba!");
					alert.show();
				}

				document.close();

			}

		} else {
			Alert alert = Util.createAlert("HIBA", "Kiválasztási hiba", "Válasszon egy sort!");
			alert.show();
		}
	}

	// Egy sorra való kattintáskor meghívott metódus
	@FXML
	private void rowClicked(MouseEvent event) {
		// A jelenleg kijelölt sorhoz tartozó CV megállapítása
		CV selectedCV = tableCV.getSelectionModel().getSelectedItem();

		if (selectedCV != null) {
			// A kijelölt sor adatainak bemásolása a TextField-ekbe
			txtId.setText(selectedCV.getId() + "");
			txtName.setText(selectedCV.getName());
			txtPlaceOfBirth.setText(selectedCV.getPlaceOfBirth());
			dtpDateOfBirth.setValue(selectedCV.getDateOfBirth().toLocalDate());
			txtNationality.setText(selectedCV.getNationality());
			txtEmail.setText(selectedCV.getEmail());
			txtPhone.setText(selectedCV.getPhone());

			// Duplán kattintva valamelyik önéletrajra ugyanazt hajtja végre mintha a
			// "Csatolt adatok" gombra kattintottunk volna
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				if (event.getClickCount() == 2) {
					try {
						// Új ablak létrehozása, ahol a kijelölt önéletrajzhoz tartozó tapasztalatokat
						// szerkeszthetjük
						FXMLLoader fxmlLoader = new FXMLLoader();
						Parent root = fxmlLoader.load(new FileInputStream(Start.FXML_ROOT + "ExperienceView.fxml"));

						// A kijelölt önéletrajz ID-jének továbbadása az ExperienceControllernek
						fxmlLoader.<ExperienceController>getController().CvId = selectedCV.getId();

						// Ablak beállítása
						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setScene(scene);
						stage.setTitle("Experiences");

						// Miután az ablak megjelent, a táblázatok frissítése
						// Ezt azért szükséges így elvégezni, mert az ExperienceController-nek átadott
						// ID-t csak az Initialize metódus lefutása után kapja meg az osztály!
						stage.addEventHandler(WindowEvent.WINDOW_SHOWING, new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent window) {
								fxmlLoader.<ExperienceController>getController().refreshLocalTableEducation();
								fxmlLoader.<ExperienceController>getController().refreshLocalTableJob();
							}
						});

						// Bezárásnál a főablak(önéletraz szerkesztő) újbóli megjelenítése
						stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent window) {
								((Stage) ((Node) event.getSource()).getScene().getWindow()).show();
							}
						});

						stage.show();

						// Az önéletrajz szerkesztő elrejtése
						((Stage) ((Node) event.getSource()).getScene().getWindow()).hide();

					} catch (Exception e) {
						Alert alert = Util.createAlert("HIBA", "Hiba keletkezett az ablak betöltése közben!");
						alert.show();
						e.printStackTrace();
					}

				}
			}
		} else {
			Alert alert = Util.createAlert("HIBA", "Kiválasztási hiba", "Válasszon egy sort!");
			alert.show();
		}
	}
	// ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** //

}
