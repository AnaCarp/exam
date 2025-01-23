package eu.ase.ro.spring.mvc.response;

import java.util.List;

public class CourseResponse {
    private int id;
    private String name;
    private String trainer;
    private String description;
    private List<StudentResponse> students;

    public CourseResponse(int id, String name, String trainer, String description, List<StudentResponse> students) {
        this.id = id;
        this.name = name;
        this.trainer = trainer;
        this.description = description;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StudentResponse> getStudents() {
        return students;
    }

    public void setStudents(List<StudentResponse> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trainer='" + trainer + '\'' +
                ", description='" + description + '\'' +
                ", students=" + students +
                '}';
    }
}
