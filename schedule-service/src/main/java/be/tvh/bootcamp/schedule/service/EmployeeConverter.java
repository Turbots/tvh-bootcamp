package be.tvh.bootcamp.schedule.service;

import be.tvh.bootcamp.schedule.model.EmployeeDto;
import be.tvh.bootcamp.schedule.persistence.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter implements EntityConverter<Employee, EmployeeDto> {

	public EmployeeDto convert(Employee employee) {
		return EmployeeDto.builder()
			.id(employee.getId())
			.name(employee.getName())
			.department(employee.getDepartment())
			.startDate(employee.getStartDate())
			.boss(employee.getBoss() == null ? null : convert(employee.getBoss()))
			.build();
	}
}
