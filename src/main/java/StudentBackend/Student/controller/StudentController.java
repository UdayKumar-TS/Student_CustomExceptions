package StudentBackend.Student.controller;


import StudentBackend.Student.model.LoginDto;
import StudentBackend.Student.model.Student;
import StudentBackend.Student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.InvalidNameException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
@CrossOrigin
@Validated

public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudent() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getItemById(@PathVariable String id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.orElse(null);
    }


    @PostMapping
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String  deleteItem(@PathVariable String id) {
        studentService.deleteStudent(id);
        return  "deleted successfully ";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        Optional<Student> updated = studentService.updateStudent(id, updatedStudent);
        return updated.map(student -> ResponseEntity.ok().body(student))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        Optional<Student> userEmail=studentService.Login(loginDto.getEmail());
        if(userEmail.isPresent()){
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}

