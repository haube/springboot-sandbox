package net.cloudy.sytes.hello_liberty.schema;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlValidator_1_1 {

  XmlValidator_1_1() {

  }

  private static final String SCHEMA_LOCATION = "/xsd/schema.xsd"; // Dein XSD-Schema-Dateipfad

  public static void validate(String xmlContent) throws SAXException, IOException {
    SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
    // Hier stellen wir sicher, dass XML Schema 1.1 verwendet wird
    factory.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "true");
    // Lade das XSD-Schema
    Schema schema = factory.newSchema(new StreamSource(XmlValidator.class.getResourceAsStream(SCHEMA_LOCATION)));
    log.info("schema datei geladen");
    // Erstelle einen Validator und führe die Validierung durch
    Validator validator = schema.newValidator();
    log.info("validator erstellt");
    validator.setErrorHandler(new MyHandler());
    // Erstelle das XML-File und validiere es gegen das XSD
    validator.validate(new StreamSource(new StringReader(xmlContent)));
  }
}