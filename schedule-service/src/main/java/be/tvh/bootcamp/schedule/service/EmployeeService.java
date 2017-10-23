package be.tvh.bootcamp.schedule.service;

import be.tvh.bootcamp.schedule.model.CreateEmployeeDto;
import be.tvh.bootcamp.schedule.model.EmployeeDto;
import be.tvh.bootcamp.schedule.persistence.Employee;
import be.tvh.bootcamp.schedule.persistence.EmployeeRepository;
import be.tvh.bootcamp.schedule.web.exception.BossNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final EmployeeConverter employeeConverter;

	public EmployeeService(EmployeeRepository employeeRepository,
		EmployeeConverter employeeConverter) {
		this.employeeRepository = employeeRepository;
		this.employeeConverter = employeeConverter;
	}

	@Transactional
	public Optional<EmployeeDto> findOneEmployee(Long id) {
		return this.employeeRepository.findOneById(id).map(this.employeeConverter::convert);
	}

	@Transactional
	public Page<EmployeeDto> findAllEmployees(Pageable pageable) {
		return this.employeeRepository
			.findAll(pageable)
			.map(employeeConverter::convert);
	}

	@Transactional
	public Page<EmployeeDto> findAllEmployeesByDepartment(String department, Pageable pageable) {
		return this.employeeRepository
			.findAllByDepartment(department, pageable)
			.map(employeeConverter::convert);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public EmployeeDto createEmployee(CreateEmployeeDto dto) {
		Optional<Employee> boss = this.employeeRepository.findOneById(dto.getBossId());

		if (dto.getBossId() != null && !boss.isPresent()) {
			throw new BossNotFoundException("Boss [" + dto.getBossId() + "] does not exist");
		}

		Employee employee = Employee.builder()
			.name(dto.getName())
			.startDate(dto.getStartDate())
			.department(dto.getDepartment())
			.type(dto.getType())
			.creationDate(LocalDateTime.now())
			.boss(boss.orElse(null))
			.build();

		return this.employeeConverter.convert(this.employeeRepository.save(employee));
	}
}
