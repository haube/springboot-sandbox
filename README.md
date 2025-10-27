# Sandbox

Spielprojekt um Features und Arbeitsweisen zu testen.
U.A. Das einbinden von Xerces-j für XML Schema 1.1
Beispel wie IBMMQ in Spring Boot verwendet werden kann.

# XML Schema 1.1

Die Untersützung von XML Schema 1.1 hat mehere Probleme.

1. Das Projekt selbst baut mit ant und published nicht nach maven central. -> Daher muss die Paketverwaltung inhouse erfolgen.
2. Die IDE sollte das Feature unterstützen. Hier bricht zum Beipsiel der VS Code weg. Siehe unten. In der Folge werden Tags wie <xs:assert ..> immer als Fehlerhaft markiert.

## VSCode

Aktuell gibt es wohl keine Unterstützung im VSCode mit Redhat Xml Tools. Außer man baut das Plugin selbst.
Schema 1.1 Support in VScode gibt es noch nicht. Man muss also mit Fehlern in der XSD leben.
https://github.com/redhat-developer/vscode-xml/issues/222#issuecomment-578966678

## Im Intelli Idea:

This feature was added in Intellij 15
Settings -> Languages & Frameworks -> Schemas and DTDs -> Default XML Schemas
Funktioniert das?

## Setup

Das script install_xerces.sh ausführen. Es lädt die Abhängigen Jars runter und installiert diese im lokalen repo, diese müssen dann natürlich noch in unsere CI Nexus integriert werden damit der Build über den Jenkins funktioniert. (Für später: Zu erwartende Probleme mit JQA oder Nexus?)

### install_xerces.sh asuführen oder nachspielen.

Quelle über den einleitenden Issue: https://raw.githubusercontent.com/concretecms/concrete-cif/0a35b5d08792ebca58ea7065d720d25c88d2058f/bin/install-xerces-m2.sh

### Den Block in die pom ergänzt:

```xml
		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-spring-boot-xml-starter</artifactId>
			<version>4.15.0</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-oxm</artifactId>
		</dependency>

		<dependency>
			<groupId>xerces</groupId>
			<artifactId>xercesImpl</artifactId>
			<version>2.12.2</version>
			<classifier>xml-schema-1.1</classifier>
		</dependency>
		<dependency>
			<groupId>org.eclipse.wst.xml</groupId>
			<artifactId>xpath2</artifactId>
			<version>1.2.1</version> <!-- oder eine neuere Version -->
		</dependency>
		<dependency>
			<groupId>com.ibm.icu</groupId>
			<artifactId>icu4j</artifactId>
			<version>4.2</version> <!-- oder eine neuere Version -->
		</dependency>
		<dependency>
			<groupId>edu.princeton.cup</groupId>
			<artifactId>java-cup</artifactId>
			<version>10k</version> <!-- oder eine neuere Version -->
		</dependency>
		<dependency>
			<groupId>xml-apis</groupId>
			<artifactId>xml-apis</artifactId>
			<version>1.4.02</version> <!-- oder eine neuere Version -->
		</dependency>
```

## Die Anwendung starten und testen

`$ mvn spring-boot:run`

### Der validate11 Endpunkt prüft gegen resources/xsd/schema10.xsd

Es gibt je einen Endpunkt für den jeweilgen Validator, für 1.0 und 1.1
Im Projekt Ordner befinden ich `example_valid.xml` und `example_invalid.xml`
Bei der Invalid Datei fehlen die ID Merkmale bei einer Person.

Die Invalide XML sollte im 1.0 Validator erfolgreich durchlaufen, da hier das entsprechende Assert fehlt.
Im Validator für 1.1 kommt es zu folgendem Fehler:

```
XML ist ungültig:
        cvc-assertion: Assertion evaluation ('string(ns:Steuernummer) or string(ns:AuslaendischeSteuernummer) or string(ns:EUID_LEI)') for element 'IDMerkmale' on schema type 'IDMerkmale' did not succeed.
```

### Der validate10 Endpunkt prüft die XML gegen resources/xsd/schema11.xsd

`curl -X POST -H "Content-Type: application/xml" -d @example_invalid.xml http://localhost:8080/validate11`

`curl -X POST -H "Content-Type: application/xml" -d @example_valid.xml http://localhost:8080/validate11`

### Der validate10 Endpunkt prüft die XML gegen resources/xsd/schema10.xsd

`curl -X POST -H "Content-Type: application/xml" -d @example_invalid.xml http://localhost:8080/validate10`

`curl -X POST -H "Content-Type: application/xml" -d @example_valid.xml http://localhost:8080/validate10`

# Für IBM MQ:

Vorlage ist dieser Guide: https://developer.ibm.com/tutorials/mq-jms-application-development-with-spring-boot/

Die Anwendung muss mit dem MQ Profil gestartet werden.

```
sudo systemctl enable docker.service
# Den Docker starten.
docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --env MQ_APP_PASSWORD=passapp --env MQ_ADMIN_PASSWORD=passadm --publish 1414:1414 --publish 9443:9443 --detach ibmcom/mq
# Die Anwendung mit MQ Profil starten.
mvn spring-boot:run -Dspring-boot.run.profiles=mq
```

Im Browser oder Mit Curl folgenden GET Endpunkt aufrufen(RestEventController).
http://localhost:8080/send

# Rest und JPA package

Das die Restendpunkte aus dem rest package kommen von folgendem Tuturial.
https://spring.io/guides/tutorials/rest

## List all DB Entries

http://localhost:8080/employees

## Lookup Failed

localhost:8080/employees/99

## Entity erstellen und speichern

`curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'`

## Update einer Entity

`curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'`

## Löschen eines Datensatzes

`curl -X DELETE localhost:8080/employees/3`
