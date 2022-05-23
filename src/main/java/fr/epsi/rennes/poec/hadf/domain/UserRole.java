package fr.epsi.rennes.poec.hadf.domain;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
	ROLE_USER, ROLE_ADMIN;

	@Override
	public String getAuthority() {
		return this.name();
	}
	
	public static UserRole get(String roleString) {
		UserRole[] roles = UserRole.values();
		for (int i = 0; i < roles.length; i++) {
			UserRole role = roles[i];
			if (role.name().equals(roleString)) {
				return role;
			}
		}
		return null;
//		throw new TechnicalException("Role inconnu : " + roleString);
	}

}
