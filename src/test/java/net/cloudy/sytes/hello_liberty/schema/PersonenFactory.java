package net.cloudy.sytes.hello_liberty.schema;

import java.time.LocalDate;

import net.cloudy.sytes.generated.Adresse;
import net.cloudy.sytes.generated.IDMerkmale;
import net.cloudy.sytes.generated.NatuerlichePerson;
import net.cloudy.sytes.generated.Personen;

class TestDataFactory {
  static Personen createDefault() {
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
    return personen;
  }

  static Personen createWithDate(LocalDate date) {
    var personen = createDefault();
    personen.getNatuerlichePerson().setGeburtsdatum(LocalDate.of(3500, 1, 1));
    return personen;
  }

  static Personen createEmpty() {
    return new Personen();

  }
}