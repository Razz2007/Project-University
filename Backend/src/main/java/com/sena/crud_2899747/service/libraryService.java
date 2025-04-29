package com.sena.crud_2899747.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.DTO.libraryDTO;
import com.sena.crud_2899747.model.library;
import com.sena.crud_2899747.repository.Ilibrary;


import java.util.List;
import java.util.Optional;

@Service
public class libraryService {

    @Autowired
    private Ilibrary libraryRepository;

    public List<library> findAll() {
        return libraryRepository.findAll();
    }

    public Optional<library> findById(int id) {
        return libraryRepository.findById(id);
    }

    public responseDTO deleteLibrary(int id) {
        Optional<library> libraryOpt = findById(id);
        if (!libraryOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: La biblioteca con ID " + id + " no existe");
        }
        
        libraryRepository.deleteById(id);
        
        return new responseDTO(HttpStatus.OK.toString(),
                "Biblioteca eliminada correctamente");
    }

    public responseDTO saveLibrary(libraryDTO libraryDTO) {
        if (libraryDTO.getNameLibrary().length() < 1 ||
                libraryDTO.getNameLibrary().length() > 100) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre de la biblioteca debe tener entre 1 y 100 caracteres");
        }
        
        library library = convertToModel(libraryDTO);
        libraryRepository.save(library);
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Biblioteca guardada correctamente");
    }

    public responseDTO updateLibrary(int id, libraryDTO dto) {
        Optional<library> libraryOpt = libraryRepository.findById(id);
        if (!libraryOpt.isPresent()) {
            return new responseDTO(
                    HttpStatus.NOT_FOUND.toString(),
                    "Error: La biblioteca con ID " + id + " no existe");
        }
    
        if (dto.getNameLibrary().length() < 1 || dto.getNameLibrary().length() > 100) {
            return new responseDTO(
                    HttpStatus.BAD_REQUEST.toString(),
                    "El nombre debe tener entre 1 y 100 caracteres");
        }
    
        library existingLibrary = libraryOpt.get();
        existingLibrary.setNameLibrary(dto.getNameLibrary());
        existingLibrary.setLocation(dto.getLocation());
        existingLibrary.setOpeningTime(dto.getOpeningTime());
        existingLibrary.setClosingTime(dto.getClosingTime());
        existingLibrary.setCapacity(dto.getCapacity());

        libraryRepository.save(existingLibrary);
    
        return new responseDTO(
                HttpStatus.OK.toString(),
                "Biblioteca actualizada correctamente");
    }

    public libraryDTO convertToDTO(library library) {
        return new libraryDTO(
                0,
                library.getLocation(),
                null, library.getOpeningTime(),
                library.getClosingTime(),
                library.getCapacity()
                );
    }

    public library convertToModel(libraryDTO libraryDTO) {
        return new library(
                0,
                libraryDTO.getNameLibrary(),
                libraryDTO.getLocation(),
                libraryDTO.getOpeningTime(),
                libraryDTO.getClosingTime(),
                libraryDTO.getCapacity(),
                true);
    }
}
