package rest.options;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
public class AdminModel{
	@Id
	private Long id;
	private String email;
	
	protected AdminModel() {};
}
