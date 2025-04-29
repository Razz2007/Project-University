    package com.sena.crud_2899747.controller;

    import org.springframework.web.bind.annotation.RestController;
    import com.sena.crud_2899747.DTO.loanDTO;
    import com.sena.crud_2899747.DTO.responseDTO;
    import com.sena.crud_2899747.model.loan;
    import com.sena.crud_2899747.service.loanService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @RestController
    @RequestMapping("/api/v1/loan")
    public class loanController {

        @Autowired
        private loanService service;

        // GET: Obtener todos los préstamos activos
        @GetMapping("/")
        public ResponseEntity<List<loanDTO>> getAllActiveLoans() {
            List<loan> loans = service.findAll();
            List<loanDTO> loanDTOs = loans.stream()
                    .map(loan -> service.convertToDTO(loan))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
        }

        // GET: Obtener un préstamo por ID
        @GetMapping("/{id}")
        public ResponseEntity<?> getLoanById(@PathVariable int id) {
            Optional<loan> loanOpt = service.findById(id);
            if (!loanOpt.isPresent()) {
                return new ResponseEntity<>(new responseDTO(HttpStatus.NOT_FOUND.toString(), 
                    "Préstamo con ID " + id + " no encontrado"), HttpStatus.NOT_FOUND);
            }
            loanDTO dto = service.convertToDTO(loanOpt.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

        // GET: Buscar préstamos por nombre de estudiante
        @GetMapping("/filter/{studentName}")
        public ResponseEntity<List<loanDTO>> getLoansByStudentName(@PathVariable String studentName) {
            List<loan> loans = service.getLoansByStudentName(studentName);
            List<loanDTO> loanDTOs = loans.stream()
                    .map(loan -> service.convertToDTO(loan))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
        }

        // POST: Registrar un nuevo préstamo
        @PostMapping("/")
        public ResponseEntity<responseDTO> registerLoan(@RequestBody loanDTO loanDTO) {
            try {
                // Log para depuración
                System.out.println("Recibido: " + loanDTO);
                
                responseDTO response = service.save(loanDTO);
                
                // Intentar convertir el status a HttpStatus
                HttpStatus status;
                try {
                    status = HttpStatus.valueOf(response.getStatus());
                } catch (Exception e) {
                    // En caso de error, usar OK por defecto
                    status = HttpStatus.OK;
                }
                
                return new ResponseEntity<>(response, status);
            } catch (Exception e) {
                // Log detallado del error
                e.printStackTrace();
                
                // Devolver respuesta de error genérica
                responseDTO errorResponse = new responseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error al procesar la solicitud: " + e.getMessage()
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // PUT: Actualizar un préstamo existente
        @PutMapping("/{id}")
        public ResponseEntity<responseDTO> updateLoan(@PathVariable int id, @RequestBody loanDTO loanDTO) {
            try {
                responseDTO response = service.updateLoan(id, loanDTO);
                
                HttpStatus status;
                try {
                    status = HttpStatus.valueOf(response.getStatus());
                } catch (Exception e) {
                    status = HttpStatus.OK;
                }
                
                return new ResponseEntity<>(response, status);
            } catch (Exception e) {
                e.printStackTrace();
                responseDTO errorResponse = new responseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error al actualizar: " + e.getMessage()
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // DELETE: Eliminar un préstamo
        @DeleteMapping("/{id}")
        public ResponseEntity<responseDTO> deleteLoan(@PathVariable int id) {
            try {
                responseDTO response = service.deleteLoan(id);
                
                HttpStatus status;
                try {
                    status = HttpStatus.valueOf(response.getStatus());
                } catch (Exception e) {
                    status = HttpStatus.OK;
                }
                
                return new ResponseEntity<>(response, status);
            } catch (Exception e) {
                e.printStackTrace();
                responseDTO errorResponse = new responseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    "Error al eliminar: " + e.getMessage()
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }