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
