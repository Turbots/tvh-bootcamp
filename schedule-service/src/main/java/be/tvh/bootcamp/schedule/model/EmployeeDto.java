package be.tvh.bootcamp.schedule.model;

import be.tvh.bootcamp.schedule.web.view.EmployeeView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EmployeeDto {

	@JsonView(EmployeeView.Short.class)
	private Long id;

	@JsonView(EmployeeView.Short.class)
	private String name;

	@JsonView(EmployeeView.Short.class)
	private LocalDate startDate;

	@JsonView(EmployeeView.Short.class)
	private String department;

	@JsonView(EmployeeView.Detail.class)
	private EmployeeDto boss;
}
