package be.tvh.bootcamp.schedule.persistence;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COURSE")
@ToString(of = { "id", "topic" })
@EqualsAndHashCode(of = "id")
public class Course {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "START_DATE")
	private LocalDate startDate;

	@Column(name = "END_DATE")
	private LocalDate endDate;

	@Column(name = "TOPIC", length = 4000)
	private String topic;

	@ManyToOne
	@JoinColumn(name = "TEACHER_ID")
	private Employee teacher;

	@ManyToMany
	@JoinTable(name = "COURSE_STUDENTS",
		joinColumns = @JoinColumn(name = "COURSE_ID"),
		inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
	private Set<Employee> students;

}
