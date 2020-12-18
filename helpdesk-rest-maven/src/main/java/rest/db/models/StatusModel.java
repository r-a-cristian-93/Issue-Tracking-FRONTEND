package rest.db.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.*;

@Entity
@Table(name = "status")
@Getter @Setter
@AllArgsConstructor
public class StatusModel {
	@Id
	private String value;
	
	protected StatusModel() {}
	
	public static StatusModel getInstance(String value) {
		return new StatusModel(value);
	}
}
