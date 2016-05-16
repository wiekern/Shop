# FPT-Aufgaben Shop für Kunde and Händler

Hausaufgaben im Modul "Fortgeschrittene Programmieretechniken". 


## MVC Struktur
Die Struktur ist MVC (Model-View-Control), GUI mit JavaFx zu implementieren.
  - **Model:** behandel Produkte, die in den Produkteliste geanzeigt werden.
  - **View:** GUI, gleichzeitig bietet View auch die APIs der Elementen wie zum Beispie "Button", die sich auf ihre Verhaltens(Events z.B. Klicken Event) beziehen. 
  - **Control:** Verbinde Model und View, um sie zu kontrollieren. Kontroller hat Referenz auf Model und View, deshalb kann er die Methoden und Atribute in Model und View aufrufen.

## Serialisierung-Strategie
1. Binäre Serialisierung
2. XML
3. XML mithilfe von XStream

Standard wrid Binäre verwendet, wenn keine Strategie ausgewählt wird.
