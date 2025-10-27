package net.cloudy.sytes.hello_liberty.schema.validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class MyHandler implements ErrorHandler {
  public void fatalError(SAXParseException e) throws SAXException {
    // log.info("fatalError: ", e);
    throw e;
  }

  public void error(SAXParseException e) throws SAXException {
    // log.info("error: ", e);
    throw e;
  }

  public void warning(SAXParseException e) throws SAXException {
    // log.info("Warning: ", e);
  }
}