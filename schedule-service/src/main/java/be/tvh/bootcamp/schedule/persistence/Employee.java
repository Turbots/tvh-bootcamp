package be.tvh.bootcamp.schedule.persistence;

import be.tvh.bootcamp.schedule.model.EmployeeType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "name")
@Table(name = "EMPLOYEE")
@EqualsAndHashCode(of = "id")
public class Employee {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;

	@Column(name = "START_DATE")
	private LocalDate startDate;

	@Column(name = "END_DATE")
	private LocalDate endDate;

	@Column(name = "EMPLOYEE_TYPE")
	@Enumerated(EnumType.STRING)
	private EmployeeType type;

	@Column(name = "DEPARTMENT")
	private String department;

	@ManyToOne
	@JoinColumn(name = "BOSS_ID")
	private Employee boss;
}
