package rest.options;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter @Setter
public class RoleModel {
	@Id
	private String value;
	
	protected RoleModel() {}
}
