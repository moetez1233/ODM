package com.app.FirstApp.domain.SaveMethodes;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class VerifXML {

    public  boolean validateXMLSchema(String xsdPath, String xmlPath){

        try {
            String language = "http://www.w3.org/XML/XMLSchema/v1.1";
            SchemaFactory factory =
                    SchemaFactory.newInstance(language);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }

}
