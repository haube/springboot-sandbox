package net.cloudy.sytes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.XMLEvent;

public class RemoveAssert {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: RemoveAssert <input-xsd> <output-xsd>");
      System.exit(1);
    }

    String inputFile = args[0];
    String outputFile = args[1];

    File file = new File(outputFile);
    file.getParentFile().mkdirs(); // Will create parent directories if not exists

    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

    try (FileInputStream fis = new FileInputStream(inputFile);
        FileOutputStream fos = new FileOutputStream(outputFile)) {

      XMLEventReader reader = inputFactory.createXMLEventReader(fis);
      XMLEventWriter writer = outputFactory.createXMLEventWriter(fos);

      int skipDepth = 0;

      while (reader.hasNext()) {
        XMLEvent event = reader.nextEvent();

        if (event.isStartElement() && "assert".equals(event.asStartElement().getName().getLocalPart())) {
          skipDepth++;
        }

        if (skipDepth == 0) {
          writer.add(event);
        }

        if (event.isEndElement() && "assert".equals(event.asEndElement().getName().getLocalPart())) {
          skipDepth--;
        }
      }

      writer.flush();
      writer.close();
      reader.close();
    }

    System.out.println("Done removing <xs:assert> from " + inputFile);
  }
}
