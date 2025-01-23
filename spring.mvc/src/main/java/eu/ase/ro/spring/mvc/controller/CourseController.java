package eu.ase.ro.spring.mvc.controller;

import eu.ase.ro.spring.mvc.request.CourseRequest;
import eu.ase.ro.spring.mvc.response.CourseResponse;
import eu.ase.ro.spring.mvc.response.StudentResponse;
import eu.ase.ro.spring.mvc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String coursePage(Model model) {
        List<CourseResponse> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "courses/index";
    }

    @PostMapping("/courses")
    public String save(@ModelAttribute CourseRequest request,
                       @RequestParam(required = false) Integer courseId) {
        if (courseId == null) {
            courseService.save(request);
        }else {
            courseService.updateById(courseId, request);
        }
        return "redirect:/home";
    }

    @GetMapping("/courses/{courseId}/students/enroll")
    public String navigateToEnrollPage(@PathVariable Integer courseId) {
        System.out.println("course id: " + courseId);
        return "courses/enroll";
    }

    @PostMapping("/courses/{courseId}/students/enroll")
    public String enroll(@PathVariable Integer courseId) {
        System.out.println("Student has been enrolled to course " + courseId);
        return "redirect:/";
    }

    @GetMapping("/courses/add")
    public String navigateToAddPage() {
        return "courses/add";
    }

    @GetMapping("/courses/{courseId}/edit")
    public String navigateToEditPage(@PathVariable Integer courseId,
                                     Model model) {
        CourseResponse course = courseService.findById(courseId);
        model.addAttribute("course", course);
        return "courses/add";
    }
    @PostMapping("/courses/{courseId}/remove")
    public String removeCourse(@PathVariable Integer courseId) {
        if (courseId != null) {
            courseService.removeById(courseId);
        }
        return "redirect:/courses";
    }

    @PostMapping("/courses/{courseId}/students/{studentId}/unenroll")
    public String removeStudent(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        if (courseId != null && studentId != null) {
            // Apelăm direct serviciul pentru a elimina studentul
            courseService.removeStudentById(courseId, studentId);
        }
        return "redirect:/courses";
    }

    @PostMapping("/courses/{courseId}/students/enroll")
    public String enrollStudent(@PathVariable Integer courseId) {
        if (courseId != null) {
            // Apelăm direct serviciul pentru a inrola studentul

        }
        return "redirect:/courses";
    }
}
