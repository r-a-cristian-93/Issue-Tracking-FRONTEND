package rest;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@Getter @Setter
public class UserInfo {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Integer id;
	private String email;
	private String department;
	private String role;
	private String password;
	
	protected UserInfo() {}
}
