package be.tvh.bootcamp.batch.job;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(serviceId = "schedule-service", path = "/api/course", fallback = CoursesResourceFallback.class)
public interface CoursesResource {

	@GetMapping("/department/today")
	List<CourseDto> getCourses(@RequestParam("department") String department);
}
