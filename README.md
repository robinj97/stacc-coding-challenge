# Stacc code challenge 2021

## Oppgavebeskrivelse
> Jeg har valgt å lage ett API slik som eksempelet på stacc repoen viste.
> Api'et er ganske simpelt, ut ifra hvordan jeg forstod oppgaven, så kan man søke via navn eller dataset.
>

## Hvordan kjøre prosjektet
> mvn clean install
> 
> Jeg er usikker på hvordan du kjører en Maven applikasjon via command line.
>Gjerne åpne prosjektet i IntelliJ eller Eclipse. Det vil komme opp en Play knapp i App.java klassen.
> 
> Api'et har en simpel frontend UI som kan hentes fra 
> 
> localhost:8081
> 
> Du kan også direkte i browseren bruke API'et via URL
> 
> localhost:8081/api/pep?
> 
> Etter ? kan man søke via "name=" & "dataset="

## Kommentarer
> 1. Jeg prøvde å lage ett bedre frontend UI i Vue men møtte på mange problemer angående CORS når jeg sendte GET 
> request til backend, derfor bestemte jeg meg for å
>la være. For å oppbevare brukervennlighet har jeg laget en simpel index.html som inneholder to søkefelt og en knapp.
> 
> Det er veldig basic siden jeg ikke har så veldig mye CSS kunnskap.
> 
> 2. En av utfordringene som jeg ikke greide å løse var hosting online siden det er en maven applikasjon. 
> Jeg har mer kunnskap med deployment av Node.JS applikasjoner.
>