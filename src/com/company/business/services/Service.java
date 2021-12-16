package com.company.business.services;

import com.company.business.entities.City;
import com.company.business.entities.Country;
import com.company.business.exception.ServiceExceptions;
import com.company.database.DataBase;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import org.xml.sax.SAXException;

import java.util.List;


@XmlAccessorType(XmlAccessType.NONE)
public class Service implements CRUDable{
    @XmlTransient
    private CountryService countryService;
    @XmlTransient
    private CityService cityService;
    @XmlTransient
    public static DataBase dataBase;


    public void run() throws JAXBException, SAXException {
        dataBase = new DataBase();
        cityService = new CityService(true);
        countryService = new CountryService(cityService);
        cityService.setCountryService(countryService);

    }

    public CountryService getCountryService() {
        return countryService;
    }

    public CityService getCityService() {
        return cityService;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void addCountry(String name) throws ServiceExceptions{
        countryService.addCountry(name);
    }

    public void addCity(String name, String nameOfCountry, int population, boolean isCapital) throws ServiceExceptions {
        cityService.addCity(name, nameOfCountry, population, isCapital);
    }

    public void readCountry() throws SAXException {
        countryService.read();

    }

    public void readCity() throws SAXException {
        cityService.read();

    }

    public void save(){
        countryService.save();
        cityService.save();
    }

    public void removeCountry(String name) throws ServiceExceptions{
        countryService.remove(name);
    }

    public void removeCity(String name) throws ServiceExceptions{
        cityService.remove(name);
    }

    public List<Country> getListOfCountries(){
        return countryService.getListOfCountries();
    }

    public List<City> getListOfCities(){
        return cityService.getListOfCities();
    }

}
