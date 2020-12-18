package rest.db.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter @Setter
@AllArgsConstructor
public class RoleModel {
	@Id
	private String value;
	
	protected RoleModel() {}
	
	public static RoleModel getInstance(String value) {
		return new RoleModel(value);
	}
}
