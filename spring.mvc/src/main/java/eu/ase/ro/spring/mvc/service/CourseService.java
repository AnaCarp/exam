package eu.ase.ro.spring.mvc.service;

import eu.ase.ro.spring.mvc.model.Course;
import eu.ase.ro.spring.mvc.model.Student;
import eu.ase.ro.spring.mvc.repository.CourseRepository;
import eu.ase.ro.spring.mvc.request.CourseRequest;
import eu.ase.ro.spring.mvc.response.CourseResponse;
import eu.ase.ro.spring.mvc.response.StudentResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @PostConstruct
    private void init() {
        List<Course> courses = List.of(
                new Course("MPAI - Spring MVC", "Mr T", "totul despre modelul MVC implementat cu framekwork-ul Spring",
                        new ArrayList<>(List.of(new Student("Popescu Ion", 23)))),
                new Course("Java fundamentals", "Mr T", "hai sa invatam Java",
                        new ArrayList<>(List.of(new Student("Ionescu Maria", 22), new Student("Voicu Daniel", 22))))
        );
        courseRepository.saveAll(courses);
    }

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void save(CourseRequest request) {
        Course course = new Course(request.getName(),
                request.getTrainer(),
                request.getDescription());
        courseRepository.save(course);
    }

    public List<CourseResponse> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::toCourseResponse)
                .toList();
    }

    public CourseResponse findById(int id) {
        return courseRepository.findById(id)
                .map(this::toCourseResponse)
                .orElseThrow(() -> new RuntimeException("course not found"));
    }

    private CourseResponse toCourseResponse(
            Course course) {
        int id = course.getId();
        String name = course.getName();
        String trainer = course.getTrainer();
        String description = course.getDescription();
        List<StudentResponse> students =
                course.getStudents() == null
                        ? List.of()
                        : course.getStudents()
                        .stream()
                        .map(this::toStudentResponse)
                        .toList();

        return new CourseResponse(id, name, trainer, description, students);
    }

    private StudentResponse toStudentResponse(
            Student student) {
        return new StudentResponse(student.getId(),
                student.getName(), student.getAge());
    }


    public void updateById(Integer courseId, CourseRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("course not found"));
        course.setName(request.getName());
        course.setTrainer(request.getTrainer());
        course.setDescription(request.getDescription());

        courseRepository.save(course);
    }
    public void removeById(Integer courseId) {
        courseRepository.deleteById(courseId);
    }
    public void removeStudentById(Integer courseId, Integer studentId) {
        // Găsește cursul după ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        ArrayList<Student> newStudents = new ArrayList<>();
        // Elimină studentul cu ID-ul specificat
        if (course.getStudents() != null) {
            for (Student student: course.getStudents()
                 ) {
                if(student.getId()!=studentId){
                    newStudents.add(student);
                }
            }
            course.setStudents(newStudents);
        } else {
            throw new RuntimeException("No students found in the course");
        }


        // Salvare cursul actualizat
        courseRepository.save(course);
    }


}
