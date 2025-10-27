package net.cloudy.sytes.hello_liberty.schema.validator;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlValidator10 {

  XmlValidator10() {

  }

  private static final String SCHEMA_LOCATION = "/xsd/schema10.xsd"; // Dein XSD-Schema-Dateipfad

  public static void validate(String xmlContent) throws SAXException, IOException {
    // XSD-Schema laden
    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    log.info("Factory Created");
    Schema schema = factory.newSchema(new StreamSource(XmlValidator10.class.getResourceAsStream(SCHEMA_LOCATION)));
    log.info("Schema Created");
    // Validator erstellen
    Validator validator = schema.newValidator();
    log.info("Validator created");

    // XML validieren
    validator.validate(new StreamSource(new StringReader(xmlContent)));
    log.info("Validierung erfolgreich");
  }
}