package be.tvh.bootcamp.schedule.web;

import be.tvh.bootcamp.schedule.model.CreateEmployeeDto;
import be.tvh.bootcamp.schedule.model.EmployeeDto;
import be.tvh.bootcamp.schedule.service.EmployeeService;
import be.tvh.bootcamp.schedule.web.exception.BossNotFoundException;
import be.tvh.bootcamp.schedule.web.view.EmployeeView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(EmployeeView.Short.class)
	public Page<EmployeeDto> findAll(@PageableDefault Pageable pageable) {
		log.info("Searching for all employees");

		return this.employeeService.findAllEmployees(pageable);
	}

	@GetMapping(value = "/department/{department}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(EmployeeView.Short.class)
	public Page<EmployeeDto> findAll(@PathVariable("department") String department,
		@PageableDefault Pageable pageable) {
		log.info("Searching for all employees in department [{}]", department);

		return this.employeeService.findAllEmployeesByDepartment(department, pageable);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(EmployeeView.Detail.class)
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id, @RequestParam String foo) {
		log.info("Searching for employee [{}] and parameter was [{}]", id, foo);

		Optional<EmployeeDto> employee = this.employeeService.findOneEmployee(id);

		if (employee.isPresent()) {
			return ResponseEntity.ok(employee.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createEmployee(@RequestBody @Valid CreateEmployeeDto dto, UriComponentsBuilder builder) {
		log.info("Creating employee [{}]", dto.getName());

		try {
			EmployeeDto employee = this.employeeService.createEmployee(dto);
			final UriComponents uriComponents = builder.path("/api/employee/").buildAndExpand(employee.getId());
			return ResponseEntity.created(uriComponents.toUri()).build();
		} catch (BossNotFoundException e) {
			log.error("Could not create employee - Boss with ID [{}] not found", dto.getBossId());
			return ResponseEntity.badRequest().body("Boss with ID [" + dto.getBossId() + "] was not found");
		}
	}
}
