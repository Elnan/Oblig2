# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Olav Elnan, S236370, s236370@oslomet.no

# Arbeidsfordeling


* Olav har hatt hovedansvar for oppgave 1, 2, 3, 4, 5, 6, 7 og 8. 


# Oppgavebeskrivelse

Det var spesifisert i oppgaven at oppgave 7 ikke var nødvendig om man er alene,
men test for oppgave 8 kjørte ikke uten void nullstill() som skal gjøres i oppgave 7.

I oppgave 1 så fungerer antall() ved å iterere alle nodene og for hver node legges det til på en teller. Når det ikke er flere noder
igjen, returneres telleren. boolean tom() er sann så lenge antall() er lik 0.

Konstruktøren sjekker først om det er en null-tabell. Tabellen itereres gjennom, og så lenge verdiene ikke er null,
blir verdiene tildelt en node og pekere.

I oppgave 2a så brukes en StringBuilder til å sette sammen hvert element mellom to klammer "[]", skilt med komma ", ".
Metoden bruker itererer gjennom lista og bruker neste-pekere til å finne den neste. 
omvendtString() gjør det samme, men istedenfor å begynne ved hode og bruke neste-pekeren, starter den ved halen, og
bruker forrige-pekeren.

I oppgave 2b sjekkes det først om det er null-verdier ved hjelp av metoden requireNonNull.
Deretter opprettes det en ny node for verdien som ikke er null og den noden legges bakerst med boolean true i retur.
Om halen er tom, settes noden til både hode og hale.

I opggave 3 får jeg feil på at den går for sakte, men jeg finner ingenting som skal tilsi at den gjør det.
I metoden finnNode sjekkes det først om indeksen er mindre enn 0 eller større enn antall, noe som gjør den ugyldig.
Deretter finner metoden ut i hvilken ende av lenken den skal begynne i. Deretter itereres det oppover eller nedover,
avhengig av startpunkt, til riktig node er funnnet.

Liste<T> subliste() sjekker først om inputene "fra" og "til" er lovlige, før den returnerer en subliste med de oppgitte
plasseringene for start- og sluttpunkt.

I oppgave 4 sjekker metoden indeksTil() først om verdien som er oppgitt, finnes. Om den ikke finnes, returneres -1.
Om den finner verdien, returneres indeksen til oppgitte verdi. Boolean funnet er true om opgitte verdi finnes, og 
false om den ikke finnes.

I oppgave 5 sjekker først metoden leggInn() etter null-verdier ved hjelp av klassen Objects sin requireNonNull().
Deretter sjekkes det om indeks er ugyldig (mindre enn null eller større enn antall). Om listen er tom blir den nye
noden både hode og hale. Deretter sjekkes den om verdien skal leggges først, sist eller mellom to andre verdier.

I Oppgave 6 har vi metodene for å fjerne. fjern() sjekker først om Indeks er lovlig. Deretter sjekkes det om noden har
er tom, og om listen har ett objekt, før den fjerner noden på gitt plassering og setter pekerne til riktig node.
Metoden returnerer til slutt verdien til noden som er fjernet.

boolean fjern() sjekker om den gitte verdien finnes. Om den ikke finnes, returneres "false". Om den finnes, kjøres de
samme sjekkene som i fjern() før metoden fjerner den gitte verdien fra listen og returnerer "true".

Oppgave 7 skulle jeg egentlig ikke gjøre siden jeg er alene. Men siden en av testene for oppgave 8 ikke fungerte
uten at metoden nullstill() eksisterte, måtte jeg gjøre den også slik at testen fungerte og programmet passerte.

I oppgave 8 sjekker metoden next() om iteratorendringer er lik endringer. Om det ikke er flere elementer i listen,
så kkastes en NoSuchElementException. Deretter settes fjernOK til "true", verdien til "denne" returneres og "denne"
flyttes så tl den neste node. 

Iterator<T> iterator() returnerer DobbeltLenketListeIterator().

DobbeltLenketListeIterator() bruker finnNode() til å sette "denne" til den opgitte indeksen.

