package net.cloudy.sytes.hello_liberty.schema.validator;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XsdValidator {
  private XsdValidator() {

  }

  public static MyHandler validate(String xmlContent, String schemaLocation, String instance)
      throws SAXException, IOException {
    SchemaFactory factory = SchemaFactory.newInstance(instance);
    // Hier stellen wir sicher, dass XML Schema 1.1 verwendet wird
    factory.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "true");
    // Lade das XSD-Schema
    Schema schema = factory.newSchema(new StreamSource(XsdValidator.class.getResourceAsStream(schemaLocation)));
    log.info("schema datei geladen, " + schemaLocation);
    // Erstelle einen Validator und führe die Validierung durch
    Validator validator = schema.newValidator();
    log.info("validator erstellt");
    MyHandler handler = new MyHandler();
    validator.setErrorHandler(handler);

    // Erstelle das XML-File und validiere es gegen das XSD
    validator.validate(new StreamSource(new StringReader(xmlContent)));

    if (!handler.getExceptions().isEmpty()) {
      log.info("Validierung mit Fehlern");
      handler.listParsingExceptions();

    } else {
      log.info("Validierung erfolgreich");
    }
    return handler;
  }
}
