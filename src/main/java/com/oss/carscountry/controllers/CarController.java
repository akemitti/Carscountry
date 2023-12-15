package com.oss.carscountry.controllers;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.jpa.domain.Specification;

import com.oss.carscountry.entities.Car;
import com.oss.carscountry.repositories.CarRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/cars")
public class CarController {

    @Autowired
    private CarRepository repository;

    @GetMapping
    public List<Car> findAll() {
        List<Car> result = repository.findAll();
        return result;
    }

    @GetMapping(value= "/{id}")
    public Car findById(@PathVariable Long id) {
        try{
            Car result = repository.findById(id).get();
            return result;
        }catch(ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ", id);
        }
    }

    @GetMapping("search/{attribute}/{value}")
    public List<Car> search(@PathVariable String attribute, @PathVariable String value) {
        System.out.println(" ---------------------------"+attribute+" - "+value);
        Specification<Car> spec = new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.like(root.get(attribute), "%" + value + "%");
            }
        };
        return repository.findAll(spec);
    }

    @PostMapping
    public Car insert(@RequestBody Car car) {
        System.out.println(car.getCountry().getId());
        Car result = repository.save(car);
        return result;
    }

    @PutMapping(value="/{id}")
    public Car update(@PathVariable Long id, @RequestBody Car car) {
        if(repository.existsById(id)) {
            return repository.save(car);
        } throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao alterar dados") ;    
    }

    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable Long id) {
        if(repository.existsById(id)) {
            try {
                repository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Não foi possível excluir o carro");
            }
        }
    }
    
}
