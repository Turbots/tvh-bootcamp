package be.tvh.bootcamp.batch.job;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoursesResourceFallback implements CoursesResource {

	@Override
	public List<CourseDto> getCourses(String department) {
		System.out.println("DANGER! Fallback enabled!");

		return new ArrayList<>();
	}
}
