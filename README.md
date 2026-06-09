# XOXGameOOP

Java Swing ile gelistirilmis nesne yonelimli XOX (Tic-Tac-Toe) oyunu.

## Ozellikler

- 3x3, 4x4 ve 5x5 tahta secimi
- Tek oyunculu ve iki oyunculu oyun modu
- Bilgisayara karsi rastgele hamle veya Minimax tabanli akilli hamle secen yapay zeka
- X, O ve beraberlik skor takibi
- Kazanan hucreleri vurgulayan grafik arayuz
- Kategori destekli soru bankasi
- OOP prensiplerini ayri siniflar uzerinden kullanan moduler yapi

## Proje Yapisi

```text
src/
  AIStrategy.java       Yapay zeka strateji arayuzu
  RandomStrategy.java   Rastgele hamle yapan strateji
  MinimaxStrategy.java  Minimax algoritmasi kullanan strateji
  GameBoard.java        Tahta ve hamle kurallari
  GameState.java        Oyun sonuc kontrolu
  Player.java           Oyuncu temel sinifi
  HumanPlayer.java      Insan oyuncu sinifi
  ComputerPlayer.java   Bilgisayar oyuncu sinifi
  Question.java         Soru modeli
  QuestionBank.java     Soru listesi ve kategori islemleri
  XOXGame.java          Swing arayuzu ve oyun akisi
```

## Gereksinimler

- Java JDK 8 veya uzeri
- Windows icin `run.bat` ya da PowerShell icin `run.ps1`

## Derleme

```powershell
.\compile.bat
```

Alternatif olarak:

```powershell
javac -encoding UTF-8 -d out src\*.java
```

## Calistirma

Windows komut satiri:

```powershell
.\run.bat
```

PowerShell:

```powershell
.\run.ps1
```

Manuel calistirma:

```powershell
javac -encoding UTF-8 -d out src\*.java
java -cp out XOXGame
```

## OOP Kullanimi

- **Encapsulation:** `GameBoard`, `QuestionBank` ve oyuncu siniflari kendi verilerini sinif icinde saklar.
- **Inheritance:** `HumanPlayer` ve `ComputerPlayer`, `Player` sinifindan turetilir.
- **Polymorphism:** Farkli yapay zeka davranislari `AIStrategy` arayuzu uzerinden kullanilir.
- **Single Responsibility:** Tahta kurallari, oyun durumu, soru yonetimi ve arayuz ayri siniflarda tutulur.

## Notlar

Repository icinde kaynak dosyalarin yaninda derlenmis `.class` dosyalari, proje dokumanlari ve arsiv dosyalari da bulunur. Projeyi temiz sekilde yeniden derlemek icin `out` klasoru silinip komutlar tekrar calistirilabilir.
