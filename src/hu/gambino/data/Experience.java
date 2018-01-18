package hu.gambino.data;

import java.sql.Date;

public abstract class Experience {
	private Long id;
	private String name;
	private Date beginning;
	private Date ending;
	private Long belongsTo;
	
	public Experience(Long id, String name, Date beginning, Date ending, Long belongsTo) {
		super();
		this.id = id;
		this.name = name;
		this.beginning = beginning;
		this.ending = ending;
		this.belongsTo = belongsTo;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getBeginning() {
		return beginning;
	}

	public Date getEnding() {
		return ending;
	}

	public Long getBelongsTo() {
		return belongsTo;
	}
	
	
	
	
}
