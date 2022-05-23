package fr.epsi.rennes.poec.hadf.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import fr.epsi.rennes.poec.hadf.domain.User;
import fr.epsi.rennes.poec.hadf.domain.UserRole;
import fr.epsi.rennes.poec.hadf.exception.TechnicalException;

@Repository
public class UserDAO {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User getUserByEmail(String email) {
		String sql = "select * from public.user where email = '" + email + "'";
		try {
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				User user = new User();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setActive(rs.getBoolean("active"));
				
				String role = rs.getString("role");
				user.setRole(UserRole.get(role));
				return user;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public void addUser(User user) {
		String hash = passwordEncoder.encode(user.getPassword());
		String sql = "insert into public.user " +
					 "(email, password, role) " +
					 "values ('" + user.getEmail() + "', '" + hash + "', '" + user.getRole() + "')";
		
		try {
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

}
