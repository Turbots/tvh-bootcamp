package be.tvh.bootcamp.schedule.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	Optional<Course> findOneById(Long id);

	@Query("select c from Course c where c.startDate <= :now and c.endDate >= :now and c.teacher.department = :department")
	List<Course> findCoursesFor(@Param("now") LocalDate now, @Param("department") String department);
}
