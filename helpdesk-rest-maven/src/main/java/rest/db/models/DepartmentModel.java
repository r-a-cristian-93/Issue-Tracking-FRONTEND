package rest.db.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.*;

@Entity
@Table(name = "departments")
@Getter @Setter
@AllArgsConstructor
public class DepartmentModel {
	@Id
	private String value;
	
	protected DepartmentModel() {}
	
	public static DepartmentModel getInstance(String value) {
		return new DepartmentModel(value);
	}
}
