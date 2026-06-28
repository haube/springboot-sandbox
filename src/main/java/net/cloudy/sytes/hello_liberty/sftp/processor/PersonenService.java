package net.cloudy.sytes.hello_liberty.sftp.processor;

import net.cloudy.sytes.hello_liberty.sftp.processor.dto.Person;
import net.cloudy.sytes.hello_liberty.sftp.processor.dto.PersonResult;
import net.cloudy.sytes.hello_liberty.sftp.processor.dto.PersonenRequest;
import net.cloudy.sytes.hello_liberty.sftp.processor.dto.PersonenResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PersonenService {

    private final IdnrDatabase db;

    public PersonenService(IdnrDatabase db) {
        this.db = db;
    }

    public PersonenResponse process(PersonenRequest request) {

        PersonenResponse response = new PersonenResponse();
        response.results = new ArrayList<>();

        for (Person p : request.person) {

            String idnr = db.findIdnr(
                    p.getVorname(),
                    p.getNachname(),
                    p.getGeburtsdatum()
            );

            PersonResult r = new PersonResult();
            r.vorname = p.getVorname();
            r.nachname = p.getNachname();

            if (idnr != null) {
                r.idnr = idnr;
                r.statusCode = "OK";
            } else {
                r.idnr = null;
                r.statusCode = "NOT_FOUND";
            }

            response.results.add(r);
        }

        return response;
    }
}