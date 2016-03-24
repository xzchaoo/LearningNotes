package a19;

/**
 * WifeHusband entity. @author MyEclipse Persistence Tools
 */

public class WifeHusband implements java.io.Serializable {

	// Fields

	private WifeHusbandId id;

	// Constructors

	/** default constructor */
	public WifeHusband() {
	}

	/** full constructor */
	public WifeHusband(WifeHusbandId id) {
		this.id = id;
	}

	// Property accessors

	public WifeHusbandId getId() {
		return this.id;
	}

	public void setId(WifeHusbandId id) {
		this.id = id;
	}

}