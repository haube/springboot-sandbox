package net.cloudy.sytes.hello_liberty.schema.validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHandler implements ErrorHandler {
  private List<SAXParseException> exceptions;

  public MyHandler() {
    this.exceptions = new ArrayList<>();
  }

  public List<SAXParseException> getExceptions() {
    return exceptions;
  }

  @Override
  public void warning(SAXParseException exception) {
    exceptions.add(exception);
  }

  @Override
  public void error(SAXParseException exception) {
    exceptions.add(exception);
  }

  @Override
  public void fatalError(SAXParseException exception) {
    exceptions.add(exception);
  }

  public void listParsingExceptions() throws IOException, SAXException {
    exceptions.forEach(e -> log.info(String.format("Line number: {%s}, Column number: {%s}. {%s}%n",
        e.getLineNumber(), e.getColumnNumber(), e.getMessage())));
  }

}
