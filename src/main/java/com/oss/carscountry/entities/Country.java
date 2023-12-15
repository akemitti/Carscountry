package com.oss.carscountry.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_country")
public class Country{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String continent;
    private int population;

    public Country() {}
    public Country(long id, String name, String continent, int population){
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.population = population;
    }

    //getters
    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getContinent(){
        return continent;
    }

    public int getPopulation(){
        return population;
    }

    //setters
    public void setId(long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setContinent(String continent){
        this.continent = continent;
    }

    public void setPopulation(int population){
        this.population = population;
    }

}
