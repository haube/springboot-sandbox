package net.cloudy.sytes.hello_liberty.sftp.processor.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PersonenRequest {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "person")
    public List<Person> person;
}