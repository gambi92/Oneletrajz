package hu.gambino.ctrl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Util {

	// Alert létrehozása egy metódussal (ahelyett hogy egyesével mindig beállítanánk
	// az Alert tulajdonságait)
	public static Alert createAlert(AlertType alertType, String title, String headerText, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		return alert;
	}

	// Factory: Az előbb definiált metódus kerül újrahívásra ebben a metódusban, a
	// hiányzó paraméter értékének megadásával
	public static Alert createAlert(String title, String headerText, String contentText) {
		return createAlert(AlertType.ERROR, title, headerText, contentText);
	}

	// Factory: Az előbb definiált metódus kerül újrahívásra ebben a metódusban, a
	// hiányzó paraméter értékének megadásával
	public static Alert createAlert(String title, String contentText) {
		return createAlert(title, null, contentText);
	}

	public static void writeTextToPDF(Document document, String text, float fontSize, FontFamily fontFamily,
			BaseColor baseColor, int fontStyle, Boolean newLine) throws Exception {
		Font font = new Font(fontFamily, fontSize, fontStyle, baseColor);
		Chunk id = new Chunk(text, font);
		document.add(id);
		if (newLine)
			document.add(new Paragraph());
		else
			document.add(Chunk.TABBING);
	}

	public static void writeTextToPDF(Document document, String text, float fontSize, FontFamily fontFamily,
			BaseColor baseColor, int fontStyle) throws Exception {
		writeTextToPDF(document, text, fontSize, fontFamily, baseColor, fontStyle, true);
	}

	public static void writeTextToPDF(Document document, String text, float fontSize, FontFamily fontFamily,
			BaseColor baseColor) throws Exception {
		writeTextToPDF(document, text, fontSize, fontFamily, baseColor, Font.NORMAL);
	}

	public static void writeTextToPDF(Document document, String text, float fontSize, FontFamily fontFamily)
			throws Exception {
		writeTextToPDF(document, text, fontSize, fontFamily, BaseColor.BLACK);
	}

	public static void writeTextToPDF(Document document, String text, float fontSize) throws Exception {
		writeTextToPDF(document, text, fontSize, FontFamily.TIMES_ROMAN);
	}

	public static void writeTextToPDF(Document document, String text) throws Exception {
		writeTextToPDF(document, text, 12);
	}
	
}
