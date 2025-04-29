package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.courseDTO;
import com.sena.crud_2899747.model.course;
import com.sena.crud_2899747.repository.Icourse;
import java.util.List;
import java.util.Optional;

@Service
public class courseService {

    @Autowired
    private Icourse data;

    public List<course> findAll() {
        return data.getListCourseActive();
    }

    public List<course> getListCourseForName(String filter) {
        return data.findByNameContainingIgnoreCase(filter);
    }

    public Optional<course> findById(int id) {
        return data.findById(id);
    }

    public responseDTO deleteCourse(int id) {
        Optional<course> course = findById(id);
        if (!course.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El curso no existe");
        }

        data.deleteById(id);

        return new responseDTO(HttpStatus.OK.toString(),
                "Curso eliminado correctamente");
    }

    public responseDTO save(courseDTO courseDTO) {
        if (courseDTO.getName().length() < 1 ||
                courseDTO.getName().length() > 100) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre debe estar entre 1 y 100 caracteres");
        }

        course courseRegister = convertToModel(courseDTO);
        data.save(courseRegister);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Curso guardado correctamente");
    }

    public responseDTO updateCourse(int id, courseDTO dto) {
        Optional<course> courseOpt = data.findById(id);
        if (!courseOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El curso con ID " + id + " no existe");
        }

        if (dto.getName().length() < 1 || dto.getName().length() > 100) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre debe estar entre 1 y 100 caracteres");
        }

        course existingCourse = courseOpt.get();
        existingCourse.setName(dto.getName());
        existingCourse.setCredits(dto.getCredits());
        existingCourse.setLevel(dto.getLevel());
        existingCourse.setPrerequisites(dto.getPrerequisites());
        existingCourse.setStatus(dto.isStatus());

        data.save(existingCourse);

        return new responseDTO(
                HttpStatus.OK.toString(),
                "Curso actualizado correctamente");
    }

    public courseDTO convertToDTO(course course) {
        return new courseDTO(
                course.getCourseId(),
                course.getName(),
                course.getCredits(),
                course.getLevel(),
                course.getPrerequisites(),
                course.isStatus());
    }

    public course convertToModel(courseDTO courseDTO) {
        return new course(
                courseDTO.getCourseId(),
                courseDTO.getName(),
                courseDTO.getCredits(),
                courseDTO.getLevel(),
                courseDTO.getPrerequisites(),
                courseDTO.isStatus());
    }
}
