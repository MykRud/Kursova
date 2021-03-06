package com.company.database;

import com.company.business.entities.City;
import com.company.business.entities.Country;
import com.company.business.services.CityService;
import com.company.business.services.CountryService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static final String PACKAGE = CountryService.class.getPackage().getName();
    private JAXBContext jc;

    public DataBase() throws JAXBException {
        jc = JAXBContext.newInstance(PACKAGE);
    }

    public void save(CountryService countryService) {
        try (OutputStream os = new FileOutputStream("countries.xml")) {
            jc = JAXBContext.newInstance(CountryService.class);
            Marshaller m = jc.createMarshaller();
            m.marshal(countryService, os);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void save(CityService cityService) {

        try (OutputStream os = new FileOutputStream("cities.xml")) {
            jc = JAXBContext.newInstance(CityService.class);
            Marshaller m = jc.createMarshaller();
            m.marshal(cityService, os);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Country> readCountries() throws SAXException {

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("countries.xsd"));

        CountryService cs = null;
        File xmlFile = new File("countries.xml");

        try (InputStream in = new FileInputStream(xmlFile)) {

            jc = JAXBContext.newInstance(CountryService.class);
            Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
            jaxbUnmarshaller.setSchema(schema);
            if (in.read() == -1)
                return new ArrayList<>();
            cs = (CountryService) jaxbUnmarshaller.unmarshal(xmlFile);

        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
        return cs != null ? cs.getListOfCountries() : new ArrayList<>();
    }

    public List<City> readCities() throws SAXException {

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("cities.xsd"));

        CityService cs = null;
        File xmlFile = new File("cities.xml");
        try (InputStream in = new FileInputStream(xmlFile)) {
            jc = JAXBContext.newInstance(CityService.class);
            Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
            jaxbUnmarshaller.setSchema(schema);
            if (in.read() == -1)
                return new ArrayList<>();
            cs = (CityService) jaxbUnmarshaller.unmarshal(xmlFile);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return cs != null ? cs.getListOfCities() : new ArrayList<>();
    }
    /*public void check() throws SAXException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        Schema s = sf.newSchema(new File("countries.xsd"));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setSchema(s);
    }
    class SimpleErrorHadler implements ErrorHandler{
        public void warning(SAXParseException e) throws SAXException{
            System.out.println(e.getMessage());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            System.out.println(exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println(exception.getMessage());
        }


    }*/
}
