package be.tvh.bootcamp.schedule.web;

import be.tvh.bootcamp.schedule.model.CourseDto;
import be.tvh.bootcamp.schedule.service.CourseService;
import be.tvh.bootcamp.schedule.web.view.CourseView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/course")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(CourseView.Summary.class)
	public Page<CourseDto> findCourses(@PageableDefault Pageable pageable) {
		return this.courseService.getAllCourses(pageable);
	}

	@GetMapping(value = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(CourseView.Detail.class)
	public ResponseEntity<CourseDto> getCourse(@PathVariable Long courseId) {
		log.info("Looking for Course [{}]", courseId);

		Optional<CourseDto> course = this.courseService.findCourse(courseId);

		if (course.isPresent()) {
			return ResponseEntity.ok(course.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/department/today")
	public List<CourseDto> findAllCoursesForTodayWhereTeacherInDepartment(
		@RequestParam(value = "department", required = false) Optional<String> department) {
		log.info("Searching for all courses for [{" + department + "}]");

		return this.courseService.findCoursesFor(LocalDate.now(), department);
	}

	@GetMapping(value = "/resetCache")
	public ResponseEntity resetCache() {
		this.courseService.resetCache();

		return ResponseEntity.ok().build();
	}
}
