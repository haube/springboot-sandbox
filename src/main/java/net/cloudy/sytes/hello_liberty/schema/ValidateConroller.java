package net.cloudy.sytes.hello_liberty.schema;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;
import net.cloudy.sytes.hello_liberty.schema.validator.MyHandler;
import net.cloudy.sytes.hello_liberty.schema.validator.XsdValidator;

@Slf4j
@RestController
public class ValidateConroller {

  private static final String SCHEMA_LOCATION_10 = "/xsd/schema10.xsd"; // Dein XSD-Schema-Dateipfad
  private static final String SCHEMA_LOCATION_11 = "/xsd/schema11.xsd"; // Dein XSD-Schema-Dateipfad
  private static final String FACTORY_INSTANCE = "http://www.w3.org/XML/XMLSchema/v1.1";

  @PostMapping("/validate10")
  public ResponseEntity<String> validateXml10(@RequestBody String xmlContent) {
    return validate(xmlContent, SCHEMA_LOCATION_10, XMLConstants.W3C_XML_SCHEMA_NS_URI);
  }

  @PostMapping("/validate11")
  public ResponseEntity<String> validateXml11(@RequestBody String xmlContent) {
    return validate(xmlContent, SCHEMA_LOCATION_11, FACTORY_INSTANCE);
  }

  private ResponseEntity<String> validate(String xmlContent, String schemaLocation, String instance) {
    try {
      // XML-Validierung aufrufen
      MyHandler handler = XsdValidator.validate(xmlContent, schemaLocation, instance);
      if (handler.getExceptions().isEmpty()) {
        return new ResponseEntity<>("\nXML ist gültig! \n\n", HttpStatus.OK);
      } else {
        List<String> messages = new LinkedList<>();
        handler.getExceptions().forEach(e -> messages.add(String.format("Line number: %s, Column number: %s. %s",
            e.getLineNumber(), e.getColumnNumber(), e.getMessage())));
        return new ResponseEntity<>("\nXML ist ungültig: \n \t" + messages + " \n\n", HttpStatus.BAD_REQUEST);
      }
    } catch (SAXException | IOException e) {
      log.error("Error Validation", e);
      return new ResponseEntity<>("\nXML ist ungültig: \n \t" + e.getMessage() + " \n\n", HttpStatus.BAD_REQUEST);
    }
  }
}
