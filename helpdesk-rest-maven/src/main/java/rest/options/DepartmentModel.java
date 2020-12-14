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
@Table(name = "departments")
@Getter @Setter
public class DepartmentModel {
	@Id
	private String value;
	
	protected DepartmentModel() {}
}
