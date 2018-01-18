package hu.gambino.data;

import java.sql.Date;

public class Job extends Experience {
	private String position;
	private String responsibilities;
	
	public Job(Long id, String name, Date beginning, Date ending, Long belongsTo, String position,
			String responsibilities) {
		super(id, name, beginning, ending, belongsTo);
		this.position = position;
		this.responsibilities = responsibilities;
	}

	public String getPosition() {
		return position;
	}

	public String getResponsibilities() {
		return responsibilities;
	}
	
	
}
