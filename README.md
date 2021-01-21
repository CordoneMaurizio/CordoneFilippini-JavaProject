# CordoneFilippini - WeatherViewer

## Progetto assegnato
- API REFERENCE: 
  https://openweathermap.org/current ,  
  https://openweathermap.org/forecast5 ,
  https://openweathermap.org/history .
- OBIETTIVO:  
  Implementare un sistema per studiare l'umidità di varie città, scelte dall'utente. Occorre basarsi su dati storici, dati attuali e predizioni dei giorni futuri di quelle città.   Da questi dati si ricaveranno delle statistiche. Inoltre, occorre salvare i dati attuali per un paio di settimane, in modo tale da verificare se le predizioni siano affidabili.
- STATS E FILTRI: 
  Statistiche mensili dell'anno attuale, riguardanti valori minimi, massimi, media e varianza dell'umidità. Filtraggio delle statistiche cambiando la finestra temporale 
  (uno o più anni precedenti). 
  Generare statistiche sulla quantità di previsioni azzeccate, in base ad una soglia di errore, ed in base ai giorni di predizione (da 1 a 5 giorni successivi). 
  Filtraggio modificando la soglia di errore.

## Progetto svolto
WeatherViewer fornisce opzioni di monitoraggio di dati metereologici.
Il programma offre all' utente la possibilià di ottenere informazioni riguardanti una lista di località da lui scelte.
Questa versione del programma si concentra su dati e statistiche riguardanti i soli valori di umidità ottenute tramite chiamata ad API esterne fornite dalla piattaforma https://openweathermap.org . 

<p>
  <img src = "https://github.com/CordoneMaurizio/CordoneFilippini-JavaProject/blob/main/WeatherViewerUML/OpenWheather_UseCase.jpg">
    <h6> idea iniziale dei casi d'uso
    </h6>
  <img>
 </p>
 
## Funzionamento
il framework di springboot permette al programma di interfacciarsi attraverso web server di tipo tomcat e di rispondere ad API con rotte predefinite:
| comando | descrizione |
|----|----------|
| /add/{nome città} | permette di aggiungere una nuova città alla lista di località monitorate |
| /remove/{nome città} | rimuove una città dalla lista |
| /list | visualizza la lista di città monitorate |
| /stats | visualizza per ogni città monitorata, dati storici e statistiche. ritorna inoltre dati di previsioni future |
| /update | aggiorna i dati a disposizione andando a chiamare nuovamente le API esterne |
| /call/{nome città} | visualizza dati metereologici di una città richiesta senza aggiungerla alla lista |
| /call | ( rotta utilizzata durante  primi test) visualizza i dati metereologici di Termoli, cittadina marittima molisana |

il programma prevede metodi di salvataggio dei dati su file JSON posizionati nella cartella "WeatherViewer/WeatherViewer/JSONdati".

### /add/{nome città}

API con metodo di tipo GET
- oltre ad aggiungere una città alla lista di località monitorate, il comando ha lo scopo di recuperare automaticamente dati storici, fino a cinque giorni nel passato, e previsioni future, in modo da poter usufruirne immdiatamente per il calcolo di statistiche.
il comando termina ritornando la list di città in formato JSON.

```json 
{
  "città monitorate" :[
    "Roma"
    "Termoli"
  ]  
}
```
### /remove/{nime città}

API con metodo di tipo GET 
- il comando rimuove la città scelta andando ad escluterla dal monitoraggio e dal processo di calcolo delle statistiche.
vengono in oltre cancellati i dati su file JSON relativi alla città.

### /stats

API con metodo di tipo POST
- richiama i metodi di calcolo delle statistiche effettuate su dati storici fornendo i seguenti valori:
umidità minima
umidità massima
umidità media
L'arco temporale entro cui calcolare le statistiche è definito dal RequestBody opportunamente formattato

```json 
{
    "inizio":"2021-01-17 00:00:00",
    "fine":"2021-01-21 00:00:00"
}
```
in caso di formattazione errata il programma ritorna un eccezione BAD_REQUEST con messaggio relativo all' errore.

Questi dati sono forniti insieme all' elenco dei dati storici seguito dai dati sulle previsioni future.

```json 
{
    "termoli": {
        "Statistiche su arco temporale scelto": {
            "valore minimo": 36,
            "valore massimo": 97,
            "media": 67.17525773195877
        },
        "dati storici": {
            "list": [
                {
                    "umidità": 82,
                    "data": "2021-01-21 00:00:00"
                },
            ]            
        },
        "previsioni": {
           "list": [
                {
                    "umidità": 77,
                    "data": "2021-01-21 21:00:00"
                },
           ]                   
        }      
}             
```
La struttura mostrata si ripete includendo i dati di tutte le città monitorate.
in caso di rimozione di una città, non verranno visualizzati i relativi dati e statistiche.

### /upload

Il comando richiama metodi per l'aggiornamento dei dati storici andando ad aggiungere i dati correnti a quelli già disponibili,
sovrascrive i dati riguardanti le previsioni future fornendone di nuovi.

## Software utilizzati

- Ambiente di sviluppo - Eclipse
- Framework - Springboot
- Software di creazione UML - UML designer
- ambiente di test - Postman

## Autori

- Cordone Maurizio
- Filippini Enrico



