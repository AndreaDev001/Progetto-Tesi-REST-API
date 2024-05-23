# Progetto Tesi REST-API

## Descrizione generale
Implementazione di una **RESTFul API** utilizzando **Java** e **Spring Boot** che permette agli utilizzatori di ottenere e modificare dati riguardanti delle bacheche che permettono agli utenti di gestire un progetto più complesso.
L'applicazione mette a disposizione degli end-point per manipolare le risorse contenute all'interno del **database**, questi sono successivamente utilizzati dal client **front-end** implementato in **Angular** per semplificare l'utilizzo agli utenti finali.
E' stato utilizzato il pattern **MVC** per la gestione delle richieste ricevute dagli utenti finali.

## Funzionalità dell'applicazione

### Ruoli degli utenti
All'interno delle bacheche gli utenti hanno uno o più dei seguenti ruoli:
- **Amministratore**, questi utenti possono modificare le informazioni riguardanti una bacheca, invitare nuovi membri, creare squadre per assegnare più facilmente membri ad un obiettivo, creare ed eliminare contenitori di obiettivi e modificare informazioni riguardanti alla stessa bacheca
- **Membri**, questi utenti possono solamente modificare gli obiettivi a cui sono stati assegnati.

### Contenitori di obiettivi
All'interno delle bacheche, gli utenti amministratori possono creare degli obiettivi per dividere il lavoro riguardante il progetto da implementare, questi possono essere raggrupati in dei contenitori per ordinare al meglio il lavoro.
Ogni bachecha, appena creata possiede al suo interno i tre seguenti contenitori di obiettivi:
- **TO DO**, che deve contenere tutti gli obiettivi che devono essere completati.
- **IN PROGRESS**, che deve contenere tutti gli obiettivi che sono in fase di implementazione.
- **COMPLETED**, che deve contenere gli obiettivi che sono già stati completati.
Questi contenitori predefiniti possono essere successivamente rinominati o eliminati dagli utenti amministratori.

### Gestione degli obiettivi
Gli utenti **amministratori** di una bacheca possono creare degli obiettivi per dividere il lavoro riguardanti un progetto software, essi possono assegnare
se stessi o altri membri ad un singolo obiettivo, oppure,  per evitare di assegnare membri manualmente ad un obiettivo è possibile assegnare direttamente tutti i membri di una **squadra** ad un obiettivo.
I membri assegnati all'obiettivo possono documentarne lo stato utilizzando i seguenti elementi:
- **Liste di sotto obiettivi**, specificando un **nome**, i membri assegnati possono creare dei sotto obiettivi da raggiungere per completare lo stesso obiettivo
- **Etichette**, i membri assegnati, per documentare lo stato di un obiettivo, possono assegnare delle etichette ad un obiettivo, gli **amministratori** possono creare delle etichette da usare
  specificando un **nome** e scegliendo un **colore**
- **Collegamenti esterni**, i membri assegnati, specificando un **nome** e **url** possono creare dei collegamenti a siti esterni, come ad esempio un documento in formato pdf.
- **Commenti**, i membri assegnati, specificando un **titolo** e un **testo** possono commentare lo stato attuale dell'obiettivo, comunicando così più facilmente tra di loro.
- **File**, i membri assegnati, specificando un **nome** e scegliendo un **file** possono condividere file con altri membri
Gli utenti **amministratori** possono modificare anche gli obiettivi a cui non sono stati assegnati, inoltre possono eliminare un obiettivo o spostarlo all'interno di un altro contenitore.

### Screenshot dell'applicazione
I seguenti sono degli screenshot delle pagine principali dell'applicazione:

**Gestione Bacheca**
![Gestione Bacheca](https://i.imgur.com/v7H7mfs.png)
Pagina di **gestione della bacheca**, la più frequentamente utilizzata all'interno dell'applicazione

**Modifica Obiettivo**

![Modifica Obiettivo](https://i.imgur.com/4vZU5qu.png)
Permette ai membri di modificare un **obiettivo** precedentemente selezionato dall'utente, se questo possiede abbastanza permessi

## Video dell'applicazione
E' possibile visualizzare un video che mostra tutte le caratteristiche principali offerte dall'applicazione al seguente link:

[Video descrittivo](https://drive.google.com/file/d/1yli9SSeaz-6nQU_AUPVsHah9Kd1V87xo/view?usp=drive_link).

