package com.oss.carscountry.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cars")
public class Car{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String model;
    private String brand;
    private int launch_year;
    private String category;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Car() {}

    //getters
    public long getId(){
        return id;
    }

    public String getModel(){
        return model;
    }

    public String getBrand(){
        return brand;
    }

    public int getLaunchYear(){
        return launch_year;
    }

    public String getCategory(){
        return category;
    }

    public Country getCountry(){
        return country;
    }

    //setters
    public void setId(long id){
        this.id = id;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public void setLaunchYear(int launch_year){
        this.launch_year = launch_year;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setCountry(Country country){
        this.country = country;
    }
}
