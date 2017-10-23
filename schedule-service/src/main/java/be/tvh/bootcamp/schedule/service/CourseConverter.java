package be.tvh.bootcamp.schedule.service;

import be.tvh.bootcamp.schedule.model.CourseDto;
import be.tvh.bootcamp.schedule.persistence.Course;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseConverter implements EntityConverter<Course, CourseDto> {

	private final EmployeeConverter employeeConverter;

	public CourseConverter(EmployeeConverter employeeConverter) {
		this.employeeConverter = employeeConverter;
	}

	@Override
	public CourseDto convert(Course course) {
		return CourseDto.builder()
			.id(course.getId())
			.startDate(course.getStartDate())
			.endDate(course.getEndDate())
			.topic(course.getTopic())
			.teacher(employeeConverter.convert(course.getTeacher()))
			.students(course.getStudents()
				.stream()
				.map(employeeConverter::convert)
				.collect(Collectors.toSet()))
			.build();
	}
}
