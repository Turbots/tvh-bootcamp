package be.tvh.bootcamp.schedule.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CreateEmployeeDto {

	@NotBlank
	private String name;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private EmployeeType type;

	@NotBlank
	private String department;

	private Long bossId;
}
