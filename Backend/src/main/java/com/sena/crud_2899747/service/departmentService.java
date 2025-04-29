package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.departmentDTO;
import com.sena.crud_2899747.model.department;
import com.sena.crud_2899747.repository.Idepartment;
import java.util.List;
import java.util.Optional;

@Service
public class departmentService {

    @Autowired
    private Idepartment data;

    // Retorna la lista de departamentos activos (se asume que el repositorio posee este método)
    public List<department> findAll() {
        return data.getListDepartmentActive();
    }

    // Filtra departamentos por nombre (ignorando mayúsculas y minúsculas)
    public List<department> getListDepartmentForName(String filter) {
        return data.findByDepartmentNameContainingIgnoreCase(filter);
    }

    // Busca un departamento según su ID
    public Optional<department> findById(int id) {
        return data.findById(id);
    }

    // Elimina un departamento según su ID
    public responseDTO deleteDepartment(int id) {
        Optional<department> dept = findById(id);
        if (!dept.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El departamento no existe");
        }
        data.deleteById(id);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Departamento eliminado correctamente");
    }

    // Registra un nuevo departamento validando el nombre y presupuesto
    public responseDTO save(departmentDTO departmentDTO) {
        if (departmentDTO.getName_department().length() < 1 ||
            departmentDTO.getName_department().length() > 50) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre del departamento debe estar entre 1 y 50 caracteres");
        }
        if (departmentDTO.getBudget() <= 0) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El presupuesto debe ser mayor que cero");
        }
        department departmentRegister = converToModel(departmentDTO);
        data.save(departmentRegister);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Departamento guardado correctamente");
    }

    // Actualiza un departamento existente
    public responseDTO updateDepartment(int id, departmentDTO dto) {
        Optional<department> deptOpt = data.findById(id);
        if (!deptOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: El departamento con ID " + id + " no existe");
        }
        if (dto.getName_department().length() < 1 || dto.getName_department().length() > 50) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre del departamento debe estar entre 1 y 50 caracteres");
        }
        if (dto.getBudget() <= 0) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El presupuesto debe ser mayor que cero");
        }
        department existingDepartment = deptOpt.get();
        existingDepartment.setDepartmentName(dto.getName_department());
        existingDepartment.setLocationDepartment(dto.getLocation_department());
        existingDepartment.setDirector(dto.getDirector());
        existingDepartment.setBudget(dto.getBudget());

        data.save(existingDepartment);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Departamento actualizado correctamente");
    }

    // Convierte una entidad department a su correspondiente DTO
    public departmentDTO convertToDTO(department department) {
        return new departmentDTO(
                department.getDepartmentName(),
                department.getLocationDepartment(),
                department.getDirector(),
                department.getBudget());
    }

    // Convierte un DTO departmentDTO a una entidad department
    public department converToModel(departmentDTO departmentDTO) {
        return new department(
                0,
                departmentDTO.getName_department(),
                departmentDTO.getLocation_department(),
                departmentDTO.getDirector(),
                departmentDTO.getBudget(),
                true);
    }
}
