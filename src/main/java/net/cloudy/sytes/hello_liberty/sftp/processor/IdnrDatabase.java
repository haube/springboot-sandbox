package net.cloudy.sytes.hello_liberty.sftp.processor;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IdnrDatabase {

    private final Map<String, String> db = Map.of(
            "Max Mustermann 1990-01-01", "ID123",
            "Erika Mustermann 1985-05-20", "ID456"
    );

    public String findIdnr(String vorname, String nachname, String geburtsdatum) {
        return db.get(vorname + " " + nachname + " " + geburtsdatum);
    }
}