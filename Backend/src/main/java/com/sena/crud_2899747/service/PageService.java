package com.sena.crud_2899747.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.crud_2899747.DTO.PageDTO;
import com.sena.crud_2899747.DTO.responseDTO;
import com.sena.crud_2899747.model.Pages;
import com.sena.crud_2899747.repository.Ipage;

@Service
public class PageService {
    @Autowired
    private Ipage data;
    
    public List<Pages> findAll() {
        return data.findAll();
    }

    public Optional<Pages> findById(int id) {
        return data.findById(id);
    }

    public responseDTO deletePage(int id) {
        Optional<Pages> pageOpt = findById(id);
        if (!pageOpt.isPresent()) {
            return new responseDTO("404", "La página no existe");
        }
        data.deleteById(id);
        return new responseDTO("200", "Página eliminada correctamente");
    }

    public responseDTO save(PageDTO pageDTO) {
        Pages page = convertToModel(pageDTO);
        data.save(page);
        return new responseDTO("200", "Página guardada correctamente");
    }

    public responseDTO updatePage(int id, PageDTO pageDTO) {
        Optional<Pages> pageOpt = findById(id);
        if (!pageOpt.isPresent()) {
            return new responseDTO("404", "La página no existe");
        }
        Pages updatedPage = pageOpt.get();
        updatedPage.setName(pageDTO.getName());
        updatedPage.setUrl(pageDTO.getUrl());
        updatedPage.setDescription(pageDTO.getDescription());

        data.save(updatedPage);
        return new responseDTO("200", "Página actualizada correctamente");
    }

    public PageDTO convertToDTO(Pages page) {
        return new PageDTO(
            page.getPageid(),
            page.getName(),
            page.getUrl(),
            page.getDescription()
        );
    }

    public Pages convertToModel(PageDTO pageDTO) {
        return new Pages(
            pageDTO.getPageid(),
            pageDTO.getName(),
            pageDTO.getUrl(),
            pageDTO.getDescription()
        );
    }

}
