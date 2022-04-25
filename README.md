# project_chitter

## Build / Installation

Das Projekt baut auf Maven auf [https://maven.apache.org/].

### Console

Zunächst die Sourcen kombilieren

```
$ mvn compile
```

Dann als jar verpacken

```
$ mvn assembly:single
```


### IDE

Installation / Build je nach IDE

## Start

Die Main-Klassen beinhalten die static main Funktion.

```
de.fhdw.chitter.MainChitter
de.fhdw.chitter.extern.newsticker.MainNewsticker
```

### Console

```
$ java -jar target/project_chitter-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

### IDE

ja nach IDE unterschiedlich

# Informationen

#### Beispielbenutzer

- Benutzername: Max
- Passwort: passwort

#### RestAPI
- http://localhost:8080/<topic>
- http://localhost:8080/sport
  

  
