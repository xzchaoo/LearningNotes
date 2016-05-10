package org.xzc.jaas;

import java.security.Principal;

import javax.security.auth.Subject;

/**
 * Created by xzchaoo on 2016/4/14 0014.
 */
public class RolePrincipal implements Principal {
	private String role;

	public RolePrincipal(String role) {
		if (role == null) throw new IllegalArgumentException("role can not be null.");
		this.role = role;
	}

	@Override
	public String getName() {
		return this.role;
	}

	@Override
	public String toString() {
		return "role." + role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RolePrincipal that = (RolePrincipal) o;

		return !(role != null ? !role.equals(that.role) : that.role != null);

	}

	@Override
	public int hashCode() {
		return role != null ? role.hashCode() : 0;
	}
}
