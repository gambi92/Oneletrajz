package hu.gambino.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseDAO implements DataProvider {
	private Connection conn;
	private String connectionString;

	public DatabaseDAO(String connectionString) {
		this.connectionString = connectionString;
	}

	// Adatbázis driver betöltése, kapcsolódás...
	private void init() throws Exception {
		// JDBC Driver betöltése
		Class.forName("org.postgresql.Driver");

		// Felhasználói adatok lementése
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "006554");

		// Kapcsolat létrehozása
		conn = DriverManager.getConnection(connectionString, props);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<ArrayList> runQuery(String queryString) throws Exception {
		// Adatbázis inicializálás
		init();
		ArrayList<ArrayList> backArray = null;

		// Csatlakozás
		Statement st = conn.createStatement();

		// SQL kód futtatása attól függően, hogy milyen paranccsal kezdődik
		ResultSet rs = null;
		if (queryString.toLowerCase().startsWith("update") || queryString.toLowerCase().startsWith("insert")
				|| queryString.toLowerCase().startsWith("delete"))
			st.executeUpdate(queryString);
		else
			rs = st.executeQuery(queryString);

		// Végigmegyünk az adatbázisban lévő elemeken
		if (rs != null) {
			// Adatbázisból visszakapott táblázat adatainak lekérése (Oszlop neve, típusa)
			ResultSetMetaData metaData = rs.getMetaData();

			// Visszaadandó ArrayList-ek szerkezetének felépítése
			backArray = new ArrayList<ArrayList>();
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				backArray.add(new ArrayList<>());
			}

			// Soronként végigmegyünk az adatbázisból kapott adatokon
			while (rs.next()) {
				// Minden sor oszlopait egy külön ArrayListben tárolunk
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					backArray.get(i - 1).add(rs.getObject(i));
				}
			}
			// Kapcsolatok lezárása
			rs.close();
		}

		st.close();
		conn.close();

		return backArray;
	}

	// Az összes önéletrajz beolvasása az adatbázisból, majd azon adatok visszaadása
	// a megfelelő formátumba konvertálva
	@Override
	public ArrayList<CV> getAllCVs() throws Exception {
		return RawDataConverter.convertRawToCVs(runQuery("SELECT * FROM cv;"));
	}

	// A paraméterben megadott id-hoz tartozó tanulmányok beolvasása az
	// adatbázisból, majd azon adatok visszaadása a megfelelő formátumba konvertálva
	@Override
	public ArrayList<Education> getEducations(Long id) throws Exception {
		return RawDataConverter
				.convertRawToEducations(runQuery("SELECT * FROM education WHERE belongsto='" + id.longValue() + "';"));
	}

	// A paraméterben megadott id-hoz tartozó munkák beolvasása az adatbázisból,
	// majd azon adatok visszaadása a megfelelő formátumba konvertálva
	@Override
	public ArrayList<Job> getJobs(Long id) throws Exception {
		return RawDataConverter
				.convertRawToJobs(runQuery("SELECT * FROM job WHERE belongsto='" + id.longValue() + "';"));
	}

	// A paraméterben megadott önéletrajz hozzáadása az adatbázishoz
	@Override
	public void addCV(CV cv) throws Exception {
		runQuery("INSERT INTO cv (name,placeofbirth,dateofbirth,nationality,email,phone,dateofcreation) VALUES ('"
				+ cv.getName() + "','" + cv.getPlaceOfBirth() + "','" + cv.getDateOfBirth() + "','"
				+ cv.getNationality() + "','" + cv.getEmail() + "','" + cv.getPhone() + "','" + cv.getDateOfCreation()
				+ "');");
	}

	// A paraméterben megadott tanulmány hozzáadása az adatbázishoz
	@Override
	public void addEducation(Education education) throws Exception {
		runQuery("INSERT INTO education (name,beginning,ending,belongsto,achievement) VALUES ('" + education.getName()
				+ "','" + education.getBeginning() + "','" + education.getEnding() + "','" + education.getBelongsTo()
				+ "','" + education.getAchievement() + "');");
	}

	// A paraméterben megadott munka hozzáadása az adatbázishoz
	@Override
	public void addJob(Job job) throws Exception {
		runQuery("INSERT INTO job (name,beginning,ending,belongsto,position,responsibilities) VALUES ('" + job.getName()
				+ "','" + job.getBeginning() + "','" + job.getEnding() + "','" + job.getBelongsTo() + "','"
				+ job.getPosition() + "','" + job.getResponsibilities() + "');");
	}

	// A paraméterben megadott önéletrajz adatainak frissítése az adatbázisban
	@Override
	public void updateCV(CV cv) throws Exception {
		runQuery("UPDATE cv SET name='" + cv.getName() + "', placeofbirth='" + cv.getPlaceOfBirth() + "', dateofbirth='"
				+ cv.getDateOfBirth() + "', nationality='" + cv.getNationality() + "', email='" + cv.getEmail()
				+ "', phone='" + cv.getPhone() + "', dateofcreation='" + cv.getDateOfCreation() + "' WHERE id='"
				+ cv.getId() + "';");
	}

	// A paraméterben megadott tanulmány adatainak frissítése az adatbázisban
	@Override
	public void updateEducation(Education education) throws Exception {
		runQuery("UPDATE education SET name='" + education.getName() + "', beginning='" + education.getBeginning()
				+ "', ending='" + education.getEnding() + "', belongsto='" + education.getBelongsTo()
				+ "', achievement='" + education.getAchievement() + "' WHERE id='" + education.getId()
				+ "' AND belongsto='" + education.getBelongsTo() + "';");
	}

	// A paraméterben megadott munka adatainak frissítése az adatbázisban
	@Override
	public void updateJob(Job job) throws Exception {
		runQuery("UPDATE job SET name='" + job.getName() + "', beginning='" + job.getBeginning() + "', ending='"
				+ job.getEnding() + "', belongsto='" + job.getBelongsTo() + "', position='" + job.getPosition()
				+ "', responsibilities='" + job.getResponsibilities() + "' WHERE id='" + job.getId()
				+ "' AND belongsto='" + job.getBelongsTo() + "';");
	}

	// A paraméterben megadott önéletrajz törlése az adatbázisból (ID alapján)
	@Override
	public void deleteCV(CV cv) throws Exception {
		runQuery("DELETE FROM cv WHERE id='" + cv.getId() + "';");
	}

	// A paraméterben megadott tanulmány törlése az adatbázisból (ID alapján)
	@Override
	public void deleteEducation(Education education) throws Exception {
		runQuery("DELETE FROM education WHERE id='" + education.getId() + "';");
	}

	// A paraméterben megadott munka törlése az adatbázisból (ID alapján)
	@Override
	public void deleteJob(Job job) throws Exception {
		runQuery("DELETE FROM job WHERE id='" + job.getId() + "';");
	}

}
