package hu.gambino.data;

import java.sql.Date;
import java.util.ArrayList;

public class RawDataConverter {

	// A DatabaseDAO runQuery metódusából érkező ArrayList<ArrayList> szerkezetű adatot konvertálja ArrayList<CV>-re
	@SuppressWarnings({ "rawtypes" })
	public static ArrayList<CV> convertRawToCVs(ArrayList<ArrayList> rawData) throws Exception {
		ArrayList<CV> backArray = new ArrayList<CV>();

		if (rawData != null && rawData.size() > 0) {
			for (int i = 0; i < rawData.get(0).size(); i++) {
				backArray.add(new CV((Long) rawData.get(0).get(i), (String) rawData.get(1).get(i),
						(String) rawData.get(2).get(i), (Date) rawData.get(3).get(i), (String) rawData.get(4).get(i),
						(String) rawData.get(5).get(i), (String) rawData.get(6).get(i), (Date) rawData.get(7).get(i)));
			}
		}

		return backArray;
	}

	// A DatabaseDAO runQuery metódusából érkező ArrayList<ArrayList> szerkezetű adatot konvertálja ArrayList<Education>-re
	@SuppressWarnings("rawtypes")
	public static ArrayList<Education> convertRawToEducations(ArrayList<ArrayList> rawData) throws Exception {
		ArrayList<Education> backArray = new ArrayList<Education>();

		if (rawData != null && rawData.size() > 0) {
			for (int i = 0; i < rawData.get(0).size(); i++) {
				backArray.add(new Education((Long) rawData.get(0).get(i), (String) rawData.get(1).get(i),
						(Date) rawData.get(2).get(i), (Date) rawData.get(3).get(i), (Long) rawData.get(4).get(i),
						(String) rawData.get(5).get(i)));
			}
		}
		return backArray;
	}

	// A DatabaseDAO runQuery metódusából érkező ArrayList<ArrayList> szerkezetű adatot konvertálja ArrayList<Job>-ra
	@SuppressWarnings("rawtypes")
	public static ArrayList<Job> convertRawToJobs(ArrayList<ArrayList> rawData) throws Exception {
		ArrayList<Job> backArray = new ArrayList<Job>();

		if (rawData != null && rawData.size() > 0) {
			for (int i = 0; i < rawData.get(0).size(); i++) {
				backArray.add(new Job((Long) rawData.get(0).get(i), (String) rawData.get(1).get(i),
						(Date) rawData.get(2).get(i), (Date) rawData.get(3).get(i), (Long) rawData.get(4).get(i),
						(String) rawData.get(5).get(i), (String) rawData.get(6).get(i)));
			}
		}

		return backArray;
	}

}
