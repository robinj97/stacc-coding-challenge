# Stacc code challenge 2021

## Oppgavebeskrivelse
> Jeg har valgt å lage ett API slik som eksempelet på stacc repoen viste.
> Api'et er ganske simpelt, ut ifra hvordan jeg forstod oppgaven, så kan man søke via navn,dataset eller bursdag.
> Det er også mulig å sortere resultater etter side nummer ettersom dataen som var gitt var ganske stor.
>

## Hvordan kjøre prosjektet
> Man kan søke via simpel front end til API'et mitt på:
> 
> https://robin-stacc.herokuapp.com/
> 
> Du kan også direkte i browseren bruke API'et via URL
> 
> /api/pep?
> 
> Etter ? kan man søke via "name=" & "dataset=" & "page=" & "birthDate="

## Kommentarer
> 1. Jeg prøvde å lage ett bedre frontend UI i Vue men møtte på mange problemer angående CORS når jeg sendte GET 
> request til backend, derfor bestemte jeg meg for å
>la være. For å oppbevare brukervennlighet har jeg laget en simpel index.html som inneholder søkefelt og en knapp.
> 
> Det er veldig basic siden jeg ikke har så veldig mye CSS kunnskap.
> 
> 