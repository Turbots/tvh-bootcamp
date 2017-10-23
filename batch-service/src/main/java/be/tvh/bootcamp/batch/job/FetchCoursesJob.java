package be.tvh.bootcamp.batch.job;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RefreshScope
public class FetchCoursesJob {

	private final CoursesResource coursesResource;

	public FetchCoursesJob(CoursesResource coursesResource) {
		this.coursesResource = coursesResource;
	}

	@Scheduled(fixedRateString = "${course.job.rate:1000}")
	public void fetchCourses() {
		List<CourseDto> courses = this.coursesResource.getCourses("MANAGEMENT");

		System.out.println("These are the courses:");

		courses.forEach(System.out::println);
	}
}
