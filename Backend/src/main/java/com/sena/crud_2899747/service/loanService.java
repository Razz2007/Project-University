package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.loanDTO;
import com.sena.crud_2899747.model.loan;
import com.sena.crud_2899747.model.book;
import com.sena.crud_2899747.model.student;
import com.sena.crud_2899747.repository.Iloan;
import com.sena.crud_2899747.repository.Ibook;
import com.sena.crud_2899747.repository.Istudent;

import java.util.List;
import java.util.Optional;

@Service
public class loanService {

    @Autowired
    private Iloan data;
    
    @Autowired
    private Ibook bookRepository;
    
    @Autowired
    private Istudent studentRepository;

    // Obtiene todos los préstamos activos
    public List<loan> findAll() {
        return data.getActiveLoans();
    }

    // Filtra préstamos por nombre de estudiante con consulta personalizada
    public List<loan> getLoansByStudentName(String filter) {
        return data.findByStudentName(filter);
    }

    // Busca un préstamo por ID
    public Optional<loan> findById(int id) {
        return data.findById(id);
    }

    // Elimina un préstamo según su ID
    public responseDTO deleteLoan(int id) {
        Optional<loan> loanOpt = findById(id);
        if (!loanOpt.isPresent()) {
            return new responseDTO(HttpStatus.NOT_FOUND.toString(), "Error: El préstamo no existe.");
        }

        data.deleteById(id);

        return new responseDTO(HttpStatus.OK.toString(), "Préstamo eliminado correctamente.");
    }

    // Registra un nuevo préstamo con validaciones y manejo de entidades
    public responseDTO save(loanDTO loanDTO) {
        try {
            if (loanDTO.getLoanDate() == null) {
                return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "La fecha de préstamo es obligatoria.");
            }

            if (loanDTO.getStudent() == null || loanDTO.getBook() == null) {
                return new responseDTO(HttpStatus.BAD_REQUEST.toString(), "El estudiante y el libro son obligatorios.");
            }

            // Verificar que el estudiante exista
            Optional<student> studentOpt = studentRepository.findById(loanDTO.getStudent().getStudentId());
            if (!studentOpt.isPresent()) {
                return new responseDTO(HttpStatus.BAD_REQUEST.toString(), 
                    "Error: El estudiante con ID " + loanDTO.getStudent().getStudentId() + " no existe.");
            }

            // Verificar que el libro exista
            Optional<book> bookOpt = bookRepository.findById(loanDTO.getBook().getBookId());
            if (!bookOpt.isPresent()) {
                return new responseDTO(HttpStatus.BAD_REQUEST.toString(), 
                    "Error: El libro con ID " + loanDTO.getBook().getBookId() + " no existe.");
            }

            // Crear el objeto loan con las referencias correctas
            loan loanRegister = new loan(
                0,
                studentOpt.get(),            // Usar la entidad persistente
                bookOpt.get(),               // Usar la entidad persistente
                loanDTO.getLoanDate(),
                loanDTO.getReturnDate(),
                loanDTO.getStatus()
            );

            // Guardar el préstamo
            data.save(loanRegister);
            
            return new responseDTO(HttpStatus.OK.toString(), "Préstamo registrado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return new responseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
                "Error al procesar la solicitud: " + e.getMessage());
        }
    }

    // Actualiza la información de un préstamo existente
    public responseDTO updateLoan(int id, loanDTO dto) {
        try {
            Optional<loan> loanOpt = data.findById(id);
            if (!loanOpt.isPresent()) {
                return new responseDTO(HttpStatus.NOT_FOUND.toString(), 
                    "Error: El préstamo con ID " + id + " no existe.");
            }

            // Verificar que el estudiante exista
            Optional<student> studentOpt = studentRepository.findById(dto.getStudent().getStudentId());
            if (!studentOpt.isPresent()) {
                return new responseDTO(HttpStatus.BAD_REQUEST.toString(), 
                    "Error: El estudiante con ID " + dto.getStudent().getStudentId() + " no existe.");
            }

            // Verificar que el libro exista
            Optional<book> bookOpt = bookRepository.findById(dto.getBook().getBookId());
            if (!bookOpt.isPresent()) {
                return new responseDTO(HttpStatus.BAD_REQUEST.toString(), 
                    "Error: El libro con ID " + dto.getBook().getBookId() + " no existe.");
            }

            loan existingLoan = loanOpt.get();
            existingLoan.setLoanDate(dto.getLoanDate());
            existingLoan.setReturnDate(dto.getReturnDate());
            existingLoan.setStatus(dto.getStatus());
            existingLoan.setStudent(studentOpt.get());  // Usar la entidad persistente
            existingLoan.setBook(bookOpt.get());        // Usar la entidad persistente

            data.save(existingLoan);

            return new responseDTO(HttpStatus.OK.toString(), "Préstamo actualizado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return new responseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 
                "Error al actualizar: " + e.getMessage());
        }
    }

    // Conversión de `loan` a `loanDTO`
    public loanDTO convertToDTO(loan loan) {
        return new loanDTO(
                loan.getLoanId(),
                loan.getStudent(),
                loan.getBook(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

    // Conversión de `loanDTO` a `loan` - No se usa directamente para persistencia
    public loan convertToModel(loanDTO loanDTO) {
        return new loan(
                0,
                loanDTO.getStudent(),
                loanDTO.getBook(),
                loanDTO.getLoanDate(),
                loanDTO.getReturnDate(),
                loanDTO.getStatus()
        );
    }
}