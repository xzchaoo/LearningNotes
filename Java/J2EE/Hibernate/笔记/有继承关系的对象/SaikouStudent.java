package data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("saikouStudent")
public class SaikouStudent extends Student{
	private int otaku;

	public int getOtaku() {
		return otaku;
	}

	public void setOtaku(int otaku) {
		this.otaku = otaku;
	}
}
