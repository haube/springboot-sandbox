package net.cloudy.sytes.hello_liberty.schema;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.stream.Stream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import net.cloudy.sytes.generated.Personen;

class XmlGenerationTest {

  @ParameterizedTest
  @MethodSource("provideTestObjects")
  void testXmlGenerationAndValidation(String filename, Personen obj) throws Exception {
    // Marshalling
    JAXBContext context = JAXBContext.newInstance(Personen.class);
    Marshaller marshaller = context.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    StringWriter writer = new StringWriter();
    marshaller.marshal(obj, writer);
    String xml = writer.toString();

    // Schema Validation
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(new File("target/generated-xsd/schema_clean.xsd"));
    marshaller.setSchema(schema);

    Validator validator = schema.newValidator();

    try {
      validator.validate(new StreamSource(new StringReader(xml)));
    } catch (SAXException e) {
      fail("XML validation failed: " + e.getMessage());
    }

    // --- XML erzeugen ---
    File output = new File("target/" + filename);
    marshaller.marshal(obj, output);

    System.out.println("XML generiert unter: " + output.getAbsolutePath());
  }

  static Stream<Arguments> provideTestObjects() {
    return Stream.of(
        Arguments.of("default.xml", TestDataFactory.createDefault()),
        Arguments.of("birthdate_2000.xml", TestDataFactory.createWithDate(LocalDate.of(2000, 1, 1))),
        Arguments.of("empty.xml", TestDataFactory.createEmpty()));
  }
}