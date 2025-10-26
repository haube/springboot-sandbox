package net.cloudy.sytes.hello_liberty.schema;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class Validate {
  @PostMapping("/validate")
  public ResponseEntity<String> validateXml(@RequestBody String xmlContent) {
    try {
      // XML-Validierung aufrufen
      XmlValidator_1_1.validate(xmlContent);
      return new ResponseEntity<>("\nXML ist gültig! \n\n", HttpStatus.OK);
    } catch (SAXException | IOException e) {
      log.error("Error Validation", e);
      return new ResponseEntity<>("\nXML ist ungültig: \n \t" + e.getMessage() + " \n\n", HttpStatus.BAD_REQUEST);
    }
  }
}
