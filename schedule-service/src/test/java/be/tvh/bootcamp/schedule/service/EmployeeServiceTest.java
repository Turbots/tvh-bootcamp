package be.tvh.bootcamp.schedule.service;

import be.tvh.bootcamp.schedule.model.EmployeeDto;
import be.tvh.bootcamp.schedule.persistence.Employee;
import be.tvh.bootcamp.schedule.persistence.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	private EmployeeService employeeService;
	private EmployeeRepository mockEmployeeRepository = mock(EmployeeRepository.class);

	private final static Random RANDOM = new Random();

	@Before
	public void init() {
		this.employeeService =
			new EmployeeService(this.mockEmployeeRepository, new EmployeeConverter());
	}

	@Test
	public void whenFindingAllEmployeesExpectTwoResults() {
		// given
		List<Employee> entities = Stream.of("Frederic Bousson", "Dieter Verlinde")
			.map(s -> Employee.builder()
				.id(RANDOM.nextLong())
				.name(s)
				.department(UUID.randomUUID().toString())
				.build())
			.collect(Collectors.toList());

		when(mockEmployeeRepository.findAll()).thenReturn(entities);

		// when
		Page<EmployeeDto> employees = this.employeeService.findAllEmployees(new PageRequest(0, 10));

		// then
		assertNotNull("No employees found", employees);
		assertEquals(2, employees.getTotalElements());

		assertThat(employees, contains(
			hasProperty("id", is(notNullValue())),
			hasProperty("department", is(notNullValue())))
		);
	}
}
