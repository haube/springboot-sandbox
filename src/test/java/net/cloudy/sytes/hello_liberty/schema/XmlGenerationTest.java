import java.io.File;
import java.time.LocalDate;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import net.cloudy.sytes.generated.Adresse;
import net.cloudy.sytes.generated.IDMerkmale;
import net.cloudy.sytes.generated.NatuerlichePerson;
import net.cloudy.sytes.generated.Personen;

public class XmlGenerationTest {

  @Test
  void generateXmlFromJaxb() throws Exception {

    // JAXB Context mit deinem Root-Element
    JAXBContext context = JAXBContext.newInstance(Personen.class);

    // XML Marshaller
    Marshaller marshaller = context.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    // Optional: Schema validieren (empfohlen!)
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(new File("target/generated-xsd/schema_clean.xsd"));
    marshaller.setSchema(schema);

    // --- Daten befüllen ---
    Adresse adresse = new Adresse();
    adresse.setStraße("Hauptstraße 1");
    adresse.setPostleitzahl("12345");
    adresse.setStadt("Berlin");
    adresse.setLand("Deutschland");
    adresse.setBundesland("Berlin");

    IDMerkmale id = new IDMerkmale();
    id.setSteuernummer("123/4567/8901");

    NatuerlichePerson np = new NatuerlichePerson();
    np.setVorname("Max");
    np.setNachname("Mustermann");
    np.setGeburtsdatum(LocalDate.of(1990, 1, 1));
    np.setAdresse(adresse);
    np.setIDMerkmale(id);
    np.setStatus("Inland");

    Personen personen = new Personen();
    personen.setNatuerlichePerson(np);

    // --- XML erzeugen ---
    File output = new File("target/generated-person.xml");
    marshaller.marshal(personen, output);

    System.out.println("XML generiert unter: " + output.getAbsolutePath());
  }
}