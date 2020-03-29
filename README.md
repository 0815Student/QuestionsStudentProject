Rätselspiel
Umgesetzte Funktionalitäten
Gruppe 9
Einzelgruppe


Must Haves:

Man kann sich einzelne Themengebiete aussuchen oder alle nehmen
Man kann die Zahl der Fragen bestimmen
Die Fragen die gestellt werden, entsprechen dann den ausgesuchten Themen
Die Fragen erscheinen einzeln und werden einzeln beantwortet
Es gibt Multiple Choice Fragen und Fragen mit freier Eingabe der Antwort
Man bekommt Rückmeldung nach der Antwort, wenn falsch wird man über die richtige Antwort informiert
Beim Starten eines neuen Spiels werden die Fragen aus Dateien eingelesen (das sind die *.quest Dateien, * entspricht der Kategorie
Es sind 2 Themen mit 10 Fragen vordefiniert (+ 4 in einer 3. Kategorie)
Es können neue Fragen zu beliebigen Kategorien erstellt werden, diese werden dann in den vorhandenen oder neuen Dateien abgespeichert
Bei der Bedienung des Programms wird einem gesagt, was man tun kann
Das Programm fängt alle Fehler (die ich finden konnte) ab
Es gibt eine Github-Repository (https://github.com/0815Student/QuestionsStudentProject)
Variablen und Methoden haben sinnvolle Namen etc.
Es wurden keine Frameworks o.Ä. verwendet

Nice to Haves:

es gibt einen Spielmodus „Standard“, den man am Anfang aussuchen kann. Wenn man das Spiel beendet hat, kann man seinen Score in einer Highscore Datei speichern, außerhalb von Spielen kann man sich den Highscore auch anzeigen lassen
man kann über den Befehl „save“ während eines Spiels das Spiel speichern und außerhalb eines Spiels das Spiel wieder laden. Dies wurde über das „serializable“-Interface realisiert
man kann sein für sein Spiel auch zusätzlich nur Fragen eines bestimmten Typs wählen

Keywords zur Verwendung:

„q“ steht als Befehl jederzeit zum Beenden des Programms zur Verfügung
„start“ steht auch während des Spiels zur Verfügung, man muss dann bestätigen, dass man das laufende Spiel abbrechen möchte
„save“ steht nur während des Spiels zur Verfügung
multiple Choice Fragen beantwortet man durch Eintippen der entsprechenden Zahl, Fragen können eine beliebige Anzahl an Antworten haben
bei Fragen die frei eingetippt werden, wird Groß- und Kleinschreibung nicht beachtet
beim Erstellen von Fragen/Antworten sind die Keywords (also die zur Steuerung des Programms notwendigen Buchstabenkombinationen) geblockt und können nicht verwendet werden