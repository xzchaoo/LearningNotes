package a19;

/**
 * HusbandWifeId entity. @author MyEclipse Persistence Tools
 */

public class HusbandWifeId implements java.io.Serializable {

	// Fields

	private Husband husband;
	private Wife wife;

	// Constructors

	/** default constructor */
	public HusbandWifeId() {
	}

	/** full constructor */
	public HusbandWifeId(Husband husband, Wife wife) {
		this.husband = husband;
		this.wife = wife;
	}

	// Property accessors

	public Husband getHusband() {
		return this.husband;
	}

	public void setHusband(Husband husband) {
		this.husband = husband;
	}

	public Wife getWife() {
		return this.wife;
	}

	public void setWife(Wife wife) {
		this.wife = wife;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HusbandWifeId))
			return false;
		HusbandWifeId castOther = (HusbandWifeId) other;

		return ((this.getHusband() == castOther.getHusband()) || (this
				.getHusband() != null && castOther.getHusband() != null && this
				.getHusband().equals(castOther.getHusband())))
				&& ((this.getWife() == castOther.getWife()) || (this.getWife() != null
						&& castOther.getWife() != null && this.getWife()
						.equals(castOther.getWife())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getHusband() == null ? 0 : this.getHusband().hashCode());
		result = 37 * result
				+ (getWife() == null ? 0 : this.getWife().hashCode());
		return result;
	}

}