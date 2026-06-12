# XOX Orbit

XOX Orbit, klasik Tic-Tac-Toe oyununu soru-cevap mekanigi, sure baskisi, ipucu sistemi, yapay zeka zorluk seviyeleri ve modern bir Swing arayuzuyle gelistiren nesne tabanli Java projesidir.

## Projenin Amaci

Bu projenin cikis noktasi, tek sinifli basit Tic-Tac-Toe uygulamasini nesne tabanli programlamaya uygun cok sinifli bir yapiya donusturmektir. Proje sadece XOX oynatmiyor; oyuncunun hamle yapabilmesi icin once soru cevaplamasini istiyor. Boylece oyun, bilgi yarismasi ve strateji oyunu karisimi daha ozgun bir hale geliyor.

## Ozgun Ozellikler

- Landing page ve kurallar bolumu
- 3x3, 4x4 ve 5x5 tahta secimi
- Tek oyuncu ve iki oyuncu modu
- Easy, Normal ve Hard bilgisayar zorlugu
- Random, karma ve Minimax tabanli AI stratejileri
- Soru cevaplamadan hamle yapamama sistemi
- Bursa, teknoloji ve tum sorular kategori secimi
- Sureli soru sistemi
- Her soru icin bir kez ipucu hakki
- Pas hakki
- Skor takibi
- Mac sonu sonuc ekrani
- Son 5 maci gosteren mac gecmisi paneli
- Karanlik kozmik tema ve parcacik gorselleri

## Kurallar

1. Oyuncu modunu, tahta boyutunu, soru kategorisini ve bilgisayar zorlugunu sec.
2. `Start Game` butonuna bas.
3. Hamle yapmadan once gelen soruyu dogru cevapla.
4. Dogru cevap sonrasi tahta aktiflesir; bos bir kare secerek hamle yap.
5. Sure biterse veya cevap yanlissa hamle hakki rakibe gecer.
6. Satir, sutun veya capraz tamamlayan oyuncu maci kazanir.
7. Mac sonunda sonuc ekrani acilir ve mac gecmisi guncellenir.

## Calistirma

Proje standart Java Swing uygulamasidir. Ek kutuphane gerektirmez.

```bash
javac src/*.java
java -cp src XOXGame
```

Windows PowerShell icin:

```powershell
javac src\*.java
java -cp src XOXGame
```

## Sinif Yapisi

| Sinif | Gorev |
| --- | --- |
| `XOXGame` | Ana pencere, ekran gecisleri ve oyun akisi |
| `GameBoard` | Tahta verisi, hamle yapma, kazanma ve beraberlik kontrolleri |
| `GameState` | Sira, skor, oyun basladi/bitti bilgisi |
| `Player` | Insan ve bilgisayar oyunculari icin soyut ust sinif |
| `HumanPlayer` | Insan oyuncu davranisi |
| `ComputerPlayer` | AI stratejisiyle hamle yapan bilgisayar oyuncusu |
| `AIStrategy` | AI stratejileri icin ortak arayuz |
| `RandomStrategy` | Easy zorluk icin rastgele hamle |
| `NormalStrategy` | Normal zorluk icin rastgele ve akilli hamle karisimi |
| `MinimaxStrategy` | Hard zorluk icin Minimax tabanli akilli hamle |
| `Question` | Soru, cevap ve kategori modeli |
| `QuestionBank` | Sorulari saklar, kategoriye gore filtreler |
| `LandingPagePanel` | Acilis sayfasi, kurallar ve tanitim |
| `MatchResultPanel` | Mac sonu sonuc ekrani |
| `GraphicsPanel` | Parca/parcacik temali dekoratif oyun paneli |
| `Theme` | Renk, font ve tema sabitleri |
| `RoundedBorder` | Ozel yuvarlatilmis border cizimi |

## OOP Ilkeleri

### Encapsulation

`GameBoard`, tahta verisini kendi icinde saklar. Disaridan dogrudan tahta dizisine erisim yoktur; hamleler `makeMove`, `undoMove`, `getCell` gibi metotlarla kontrol edilir.

### Inheritance

`HumanPlayer` ve `ComputerPlayer`, ortak `Player` sinifindan turetilmistir. Iki oyuncu tipi ayni temel ozellikleri kullanir, fakat hamle secme davranislari farklidir.

### Polymorphism

Oyun, mevcut oyuncunun insan mi bilgisayar mi oldugunu `Player` tipi uzerinden yonetir. `chooseMove` metodu, nesnenin gercek tipine gore farkli davranir.

### Interface ve Strategy Pattern

`AIStrategy` arayuzu sayesinde bilgisayar oyuncusunun stratejisi degistirilebilir. `RandomStrategy`, `NormalStrategy` ve `MinimaxStrategy` ayni arayuzu uygular. Bu sayede zorluk sistemi, `ComputerPlayer` sinifini degistirmeden genisletilmistir.

### Single Responsibility

Her sinif belirli bir sorumluluga odaklanir. Ornegin `QuestionBank` sadece soru havuzuyla, `GameBoard` sadece tahta kurallariyla, `Theme` ise sadece gorsel sabitlerle ilgilenir.

## Kullanilan Tasarim Dili

Arayuz, `DESIGN.md`, `variables.css`, `theme.css` ve `tokens.json` dosyalarindaki koyu kozmik tasarim dilinden Java Swing'e uyarlanmistir. CSS dosyalari Swing tarafinda dogrudan calismaz; bu nedenle renk ve sekil tokenlari `Theme.java` icinde Java sabitlerine cevrilmistir.

## Notlar

- Proje Git deposu olarak baslatilmamistir; bu nedenle surum takibi manuel dosyalar uzerinden yapilir.
- Derleme sirasinda Swing'in eski API kullanimindan kaynakli uyarilar gorulebilir, fakat uygulama calisir.
- Turkce karakter kodlamasi icin IDE dosya encoding ayarinin UTF-8 olmasi onerilir.
