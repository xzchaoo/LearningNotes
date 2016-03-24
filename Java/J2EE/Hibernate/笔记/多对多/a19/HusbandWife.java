package a19;

/**
 * HusbandWife entity. @author MyEclipse Persistence Tools
 */

public class HusbandWife implements java.io.Serializable {

	// Fields

	private HusbandWifeId id;

	// Constructors

	/** default constructor */
	public HusbandWife() {
	}

	/** full constructor */
	public HusbandWife(HusbandWifeId id) {
		this.id = id;
	}

	// Property accessors

	public HusbandWifeId getId() {
		return this.id;
	}

	public void setId(HusbandWifeId id) {
		this.id = id;
	}

}