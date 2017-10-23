package be.tvh.bootcamp.schedule.model;

import be.tvh.bootcamp.schedule.web.view.CourseView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class CourseDto {

	private Long id;

	@JsonView(CourseView.Summary.class)
	private String topic;

	@JsonView(CourseView.Summary.class)
	private LocalDate startDate;

	@JsonView(CourseView.Summary.class)
	private LocalDate endDate;

	@JsonView(CourseView.Summary.class)
	private EmployeeDto teacher;

	@JsonView(CourseView.Detail.class)
	private Set<EmployeeDto> students;
}
