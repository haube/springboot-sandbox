package net.cloudy.sytes.hello_liberty.sftp.processor;

import net.cloudy.sytes.hello_liberty.sftp.processor.dto.PersonenRequest;
import net.cloudy.sytes.hello_liberty.sftp.processor.dto.PersonenResponse;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileProcessingRoute extends RouteBuilder {

    private final PersonenService service;

    public FileProcessingRoute(PersonenService service) {
        this.service = service;
    }

    @Override
    public void configure() {

        // Fehler Handling (wichtig in echten Integrationen)
        onException(Exception.class)
                .handled(true)
                .log(LoggingLevel.INFO, "FEHLER bei Verarbeitung: ${exception.message}")
                .to("file:/home/michi/data/libertyapp/error");

        from("file:/home/michi/data/libertyapp/eingang"
                + "?include=.*\\.xml"
                + "&move=.done"
                + "&readLock=changed"
                + "&delay=2000")

                .routeId("personen-processing")

                .log("Neue Datei: ${file:name}")

                // XML -> Object
                .unmarshal()
                .jacksonXml(PersonenRequest.class)

                // Businesslogik
                .process(exchange -> {

                    PersonenRequest req = exchange.getMessage().getBody(PersonenRequest.class);

                    PersonenResponse resp = service.process(req);

                    exchange.getMessage().setBody(resp);
                })

                // Response schreiben
                .marshal()
                .jacksonXml()

                .to("file:/home/michi/data/libertyapp/ausgang"
                        + "?fileName=${file:name}");
    }
}