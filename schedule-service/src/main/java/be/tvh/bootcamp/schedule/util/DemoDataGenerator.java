package be.tvh.bootcamp.schedule.util;

import be.tvh.bootcamp.schedule.model.EmployeeType;
import be.tvh.bootcamp.schedule.persistence.Course;
import be.tvh.bootcamp.schedule.persistence.CourseRepository;
import be.tvh.bootcamp.schedule.persistence.Employee;
import be.tvh.bootcamp.schedule.persistence.EmployeeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DemoDataGenerator implements ApplicationRunner {

	private final EmployeeRepository employeeRepository;
	private final CourseRepository courseRepository;

	public DemoDataGenerator(EmployeeRepository employeeRepository,
		CourseRepository courseRepository) {
		this.employeeRepository = employeeRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Employee boss = buildEmployee("Dieter Hubau", EmployeeType.EXTERNAL, "MANAGEMENT", null);

		final Employee hubau = this.employeeRepository.save(boss);

		this.employeeRepository.save(
			Stream.of("Jacob", "Jens", "Maxwell", "Ken", "Tim A", "Tahsin", "Tim B", "Wouter")
				.map(s -> buildEmployee(s, EmployeeType.INTERNAL, "TVH Parts", hubau))
				.collect(Collectors.toList()));

		Set<Employee> students = new HashSet<>();
		employeeRepository
			.findAllByDepartment("TVH Parts", new PageRequest(0, 10))
			.forEach(students::add);

		this.courseRepository.save(Course.builder()
			.students(students)
			.teacher(hubau)
			.topic("Spring REST")
			.startDate(LocalDate.now().minusDays(3))
			.endDate(LocalDate.now())
			.build());
	}

	private Employee buildEmployee(String name, EmployeeType type, String department, Employee boss) {
		return Employee.builder()
			.department(department)
			.name(name)
			.creationDate(LocalDateTime.now())
			.startDate(LocalDate.now().minusYears(1))
			.type(type)
			.boss(boss)
			.build();
	}
}
