package net.cloudy.sytes.hello_liberty.sftp.processor.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Person {

    @JacksonXmlProperty(localName = "Vorname")
    private String vorname;

    @JacksonXmlProperty(localName = "Nachname")
    private String nachname;

    @JacksonXmlProperty(localName = "Geburtsdatum")
    private String geburtsdatum;

    @JacksonXmlProperty(localName = "Strasse")
    private String strasse;

    @JacksonXmlProperty(localName = "PLZ")
    private String plz;

    @JacksonXmlProperty(localName = "Ort")
    private String ort;
}