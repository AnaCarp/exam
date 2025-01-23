package eu.ase.ro.spring.mvc.request;

public class CourseRequest {

    private String name;
    private String trainer;
    private String description;

    public CourseRequest() {
    }

    public CourseRequest(String name, String trainer, String description) {
        this.name = name;
        this.trainer = trainer;
        this.description = description;
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

    @Override
    public String toString() {
        return "CourseRequest{" +
                "name='" + name + '\'' +
                ", trainer='" + trainer + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
