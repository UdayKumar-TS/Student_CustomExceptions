package StudentBackend.Student.service;

import StudentBackend.Student.exception.CustomExceptions;
import StudentBackend.Student.model.LoginDto;
import StudentBackend.Student.model.Student;
import StudentBackend.Student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private static final String EMAIL_PATTERN = "^[a-z0-9+_.-]+@[a-z0-9.-]+$";

    public Student createStudent(Student student) {
        ValidateName(student.getName());
        validateEmail(student.getEmail());
        checkIfEmailExists(student.getEmail());
        ValidateAge(student.getAge());
        String grade=student.getGrade();
       String Grade= grade.toUpperCase();
        ValidateGrade(Grade);
        return studentRepository.save(student);
    }

    private void ValidateName(String name) {
        if (name != null && name.startsWith(" ")) {
            throw new CustomExceptions.InvalidNameException("Name should not contain a space at the beginning");
        }
    }
    private void validateEmail(String email) {
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new CustomExceptions.InvalidEmailException("Invalid email format");
        }
    }

    private void checkIfEmailExists(String email) {
        Optional<Student> existingStudent = studentRepository.findByEmail(email);
        if (existingStudent.isPresent()) {
            throw new CustomExceptions.EmailAlreadyExistsException("Email already exists");
        }
    }

    private void ValidateAge(Integer age) {
        if(!(age <20 && age>6)){
            throw new CustomExceptions.InvalidAgeException("Age should be Greater then 6 and less then 20");
        }
    }


    private void ValidateGrade(String grade) {
        if (!(grade.equals("A") || grade.equals("B") || grade.equals("C") || grade.equals("D"))) {
            throw new CustomExceptions.InvalidGradeException("Grade should be A, B, C, or D");
        }
    }

    public Optional<Student> Login(String email){
        return studentRepository.findByEmail(email);
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    public Optional<Student> updateStudent(String id, Student updatedStudent) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);
        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setAge(updatedStudent.getAge());
            existingStudent.setGrade(updatedStudent.getGrade());
            existingStudent.setAddress(updatedStudent.getAddress());
            return Optional.of(studentRepository.save(existingStudent));
        } else {
            return Optional.empty(); // Student with given id not found
        }
    }

}
