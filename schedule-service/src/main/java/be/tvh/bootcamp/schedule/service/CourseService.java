package be.tvh.bootcamp.schedule.service;

import be.tvh.bootcamp.schedule.model.CourseDto;
import be.tvh.bootcamp.schedule.persistence.Course;
import be.tvh.bootcamp.schedule.persistence.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

	private final CourseRepository courseRepository;
	private final CourseConverter courseConverter;

	public CourseService(CourseRepository courseRepository,
		CourseConverter courseConverter) {
		this.courseRepository = courseRepository;
		this.courseConverter = courseConverter;
	}

	@Transactional
	public Optional<CourseDto> findCourse(Long courseId) {
		Optional<Course> course = this.courseRepository.findOneById(courseId);

		return course.map(this.courseConverter::convert);
	}

	public Page<CourseDto> getAllCourses(Pageable pageable) {
		return this.courseRepository
			.findAll(pageable)
			.map(this.courseConverter::convert);
	}

	public List<CourseDto> findCoursesFor(LocalDate now, Optional<String> department) {
		return this.courseRepository
			.findCoursesFor(now, department.orElse("HR"))
			.stream()
			.map(this.courseConverter::convert)
			.collect(Collectors.toList());
	}
}
