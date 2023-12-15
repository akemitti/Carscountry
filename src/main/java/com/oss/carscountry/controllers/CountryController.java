package com.oss.carscountry.controllers;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.web.server.ResponseStatusException;

import com.oss.carscountry.entities.Country;
import com.oss.carscountry.repositories.CountryRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/countries")
public class CountryController {

    @Autowired
    private CountryRepository repository;

    @GetMapping
    public List<Country> findAll() {
        List<Country> result = repository.findAll();
        return result;
    }

    @GetMapping(value= "/{id}")
    public Country findById(@PathVariable Long id) {
        try{
            Country result = repository.findById(id).get();
            return result;
        } catch(ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ", id);
        }
    }

    @GetMapping("search/{attribute}/{value}")
    public List<Country> search(@PathVariable String attribute, @PathVariable String value) {
    Specification<Country> spec = new Specification<Country>() {
        @Override
        public Predicate toPredicate(Root<Country> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            return builder.like(root.get(attribute), "%" + value + "%");
        }
    };
    return repository.findAll(spec);
}

    @PostMapping
    public Country insert(@RequestBody Country country) {
        Country result = repository.save(country);
        return result;
    }

    @PutMapping(value="/{id}")
    public Country update(@PathVariable Long id, @RequestBody Country country) {
        if(repository.existsById(id)) {
            return repository.save(country);
        } throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao alterar dados") ;
    }

    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable Long id) {
        if(repository.existsById(id)) {
            try {
                repository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Não foi possível excluir o pais");
            }
        }
    }
}
