# exam-administration
Dieses Repository beinhaltet das begleitende Projektbeispiel zum Buch [Agile objektorientierte Software-Entwicklung](https://link.springer.com/book/10.1007/978-3-658-33395-9). Entsprechend der Entwicklung im Buch, finden Sie zu jedem Kapitel der Entwicklungsphase den jeweiligen Stand und können diesen auschecken.

## Voraussetzungen
Die Anwendung wurde mit Hilfe von [Apache NetBeans 12.4](https://netbeans.apache.org/download/nb124/nb124.html) entwickelt. Grundsätzlich können Sie die Anwendung aber auch mit anderen Entwicklungsumgebungen verwalten. Neben den definierten Abhängigkeiten, werden als Ausführungsumgebung eingesetzt:
-	[Payara Server 5.2021.5](https://www.payara.fish/)
-	[PostgreSQL 13.1](https://www.postgresql.org/)
-	[Java 11](https://www.azul.com/downloads/?version=java-11-lts&package=jdk)
-	
In der Regel können Sie auch neuere Versionen nutzen, die Anwendung ist jedoch nur mit diesen Versionen getestet. Aufgrund der Kompatibilität des Applikationsservers sollten Sie auf höhere Versionen von Java verzichten oder Java 11 zumindest parallel installieren. Die Konfiguration des Applikationsservers (Datenbankverbindung) wird im Buch erläutert.
Wenn Sie NetBeans nutzen, sollten Sie Payara dort zunächst einrichten, um die Anwendung anschließend komfortabel bauen und verteilen (deploy) zu können. Anstatt Payara separat herunterzuladen, können Sie NetBeans diese Aufgabe auch [direkt erledigen lassen]( https://blog.payara.fish/adding-payara-server-to-netbeans).

## Ausführung
Um die Anwendung zu starten, wählen Sie in NetBeans einfach zunächst `Clean and Build` und anschließend `Run`. Anschließend wird der jeweilige Entwicklungsstand in Payara ausgeführt.
Alternativ können Sie die hinterlegten Maven Kommandos auch auf der Kommandozeile selbst ausführen und die Web-Anwendung auf den Server ausrollen.
