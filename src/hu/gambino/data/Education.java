package hu.gambino.data;

import java.sql.Date;

public class Education extends Experience {
	private String achievement;

	public Education(Long id, String name, Date beginning, Date ending, Long belongsTo, String achievement) {
		super(id, name, beginning, ending, belongsTo);
		this.achievement = achievement;
	}

	public String getAchievement() {
		return achievement;
	}


	
}
