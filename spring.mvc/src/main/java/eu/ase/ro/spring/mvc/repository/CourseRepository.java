package eu.ase.ro.spring.mvc.repository;

import eu.ase.ro.spring.mvc.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository
extends JpaRepository<Course, Integer> {
}
