package a19;

/**
 * WifeHusbandId entity. @author MyEclipse Persistence Tools
 */

public class WifeHusbandId implements java.io.Serializable {

	// Fields

	private Wife wife;
	private Husband husband;

	// Constructors

	/** default constructor */
	public WifeHusbandId() {
	}

	/** full constructor */
	public WifeHusbandId(Wife wife, Husband husband) {
		this.wife = wife;
		this.husband = husband;
	}

	// Property accessors

	public Wife getWife() {
		return this.wife;
	}

	public void setWife(Wife wife) {
		this.wife = wife;
	}

	public Husband getHusband() {
		return this.husband;
	}

	public void setHusband(Husband husband) {
		this.husband = husband;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WifeHusbandId))
			return false;
		WifeHusbandId castOther = (WifeHusbandId) other;

		return ((this.getWife() == castOther.getWife()) || (this.getWife() != null
				&& castOther.getWife() != null && this.getWife().equals(
				castOther.getWife())))
				&& ((this.getHusband() == castOther.getHusband()) || (this
						.getHusband() != null && castOther.getHusband() != null && this
						.getHusband().equals(castOther.getHusband())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getWife() == null ? 0 : this.getWife().hashCode());
		result = 37 * result
				+ (getHusband() == null ? 0 : this.getHusband().hashCode());
		return result;
	}

}