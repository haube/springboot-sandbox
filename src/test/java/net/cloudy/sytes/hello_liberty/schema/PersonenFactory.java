package net.cloudy.sytes.hello_liberty.schema;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

import net.cloudy.sytes.generated.Adresse;
import net.cloudy.sytes.generated.IDMerkmale;
import net.cloudy.sytes.generated.NatuerlichePerson;
import net.cloudy.sytes.generated.Personen;
import net.datafaker.Faker;

class TestDataFactory {

  private static final Faker faker = new Faker(Locale.GERMAN);
  private static final Random random = new Random();

  public static NatuerlichePerson createRandom() {
    // Adresse generieren
    Adresse adresse = new Adresse();
    adresse.setStraße(faker.address().streetName());
    adresse.setPostleitzahl(faker.address().zipCode());
    adresse.setStadt(faker.address().city());
    adresse.setLand(faker.address().country());
    adresse.setBundesland(faker.address().state());

    // IDMerkmale generieren
    IDMerkmale id = new IDMerkmale();
    id.setSteuernummer(faker.idNumber().valid());

    // NatürlichePerson generieren
    NatuerlichePerson np = new NatuerlichePerson();
    np.setVorname(faker.name().firstName());
    np.setNachname(faker.name().lastName());

    // Geburtstag generieren zwischen 18 und 80 Jahren
    LocalDate geburtsdatum = faker.timeAndDate()
        .birthday(18, 80);

    np.setGeburtsdatum(geburtsdatum);

    np.setAdresse(adresse);
    np.setIDMerkmale(id);
    np.setStatus(faker.options().option("Inland", "Ausland", "Unbekannt"));

    return np;
  }

  static LocalDate randomDate(int startYear, int endYear) {
    int year = startYear + random.nextInt(endYear - startYear + 1);
    int month = 1 + random.nextInt(12);
    int day = 1 + random.nextInt(28); // sicherer für Februar
    return LocalDate.of(year, month, day);
  }

  static Personen createDefault() {
    Personen personen = new Personen();
    personen.getNatuerlichePersonOrJuristischePerson().add(createRandom());
    return personen;
  }

  static Personen createWithDate(LocalDate date) {
    Personen personen = new Personen();
    var person = createRandom();
    person.setGeburtsdatum(date);
    personen.getNatuerlichePersonOrJuristischePerson().add(person);
    return personen;
  }

  static Personen createEmpty() {
    return new Personen();

  }

  public static Object createMany(int count) {
    Personen personen = new Personen();
    for (int i = 0; i < count; i++) {
      personen.getNatuerlichePersonOrJuristischePerson().add(createRandom());
    }
    return personen;
  }
}