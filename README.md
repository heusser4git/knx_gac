# knx_gac
Semesterarbeit 1


=> TODO git aus dem zipten projekt entfernen (Version Control Root Mapping entfernen)


Installationsanleitung:
**********************

System-Voraussetzungen:
-----------------------
- JDK 11 oder neuer
- IntelliJ (Entwickelt unter IntelliJ IDEA 2021.2.1 (Ultimate Edition))
- MariaDB Server 10.6.4 oder neuer
- Datenbank User mit folgender Berechtigung:
	- Globale Rechte: SELECT, SHOW DATABSES, CREATE, UPDATE


Programm in IntelliJ einrichten und starten
-------------------------------------------
- ZIP entpacken
- in IntelliJ Menu File|Open... wählen
	- den entpackten Ordner (knx_gac-master) suchen und anwählen -> OK
- Im Project: knx_gac-master->src->main->java->ch.ibw.knxgac->View öffnen
	- Die Klasse 	KnxGacApplication mit Doppelklick öffnen
- Mit Menu Run|Run... -> KnxGacApplication anklicken


Systemkonfiguration (GUI)
*************************
Die Systemkonfiguration ist für die Einrichtung der Datenbankverbindung und das definieren CSV-Export-Verzeichnis da.
Servertyp wählen: MARIADB
Server:          localhost (bei lokal installiertem Datenbankserver)
Server Port:     3306 (default Port für MySQL)
Datenbank-Name:  frei wählbar (nur Buchstaben erlaubt)
User:            Datenbankuser siehe Systemvoraussetzungen
Passwort:        dito User
CSV Ausgabepfad: Verzeichnis mit Schreibrechten auswählen

Die Systemkonfiguration wird in einem File configruation.txt unter ch.ibw.knxgac abgelegt.

