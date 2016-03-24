package data;

/**
 * Grade entity. @author MyEclipse Persistence Tools
 */

public class Grade implements java.io.Serializable {

	// Fields

	private GradeId id;
	private Integer point;

	// Constructors

	/** default constructor */
	public Grade() {
	}

	/** full constructor */
	public Grade(GradeId id, Integer point) {
		this.id = id;
		this.point = point;
	}

	// Property accessors

	public GradeId getId() {
		return this.id;
	}

	public void setId(GradeId id) {
		this.id = id;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

}