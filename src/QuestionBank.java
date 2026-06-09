/*
 * OOP Principles: Encapsulation hides the question list, and Single Responsibility
 * keeps question storage, filtering, and random selection in one class.
 */

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuestionBank {
    private List<Question> questions;
    private Random random;

    public QuestionBank() {
        questions = new ArrayList<Question>();
        random = new Random();
        loadQuestions();
    }

    public Question getRandomQuestion() {
        return questions.get(random.nextInt(questions.size()));
    }

    public Question getRandomQuestion(String category) {
        if (category == null || category.trim().length() == 0) {
            return getRandomQuestion();
        }
        List<Question> filteredQuestions = new ArrayList<Question>();
        for (Question question : questions) {
            if (question.getCategory().equalsIgnoreCase(category.trim())) {
                filteredQuestions.add(question);
            }
        }
        if (filteredQuestions.isEmpty()) {
            return getRandomQuestion();
        }
        return filteredQuestions.get(random.nextInt(filteredQuestions.size()));
    }

    public int getTotalCount() {
        return questions.size();
    }

    public List<String> getCategories() {
        Set<String> categories = new LinkedHashSet<String>();
        for (Question question : questions) {
            categories.add(question.getCategory());
        }
        return new ArrayList<String>(categories);
    }

    private void loadQuestions() {
        add("BURSA'NIN EN ÜNLÜ DAĞININ ADI NEDIR?", "ULUDAĞ", "BURSA");
        add("BURSA'NIN OSMANLI DÖNEMINDEKI ILK BAŞKENT OLDUĞU YIL HANGISIDIR?", "1326", "BURSA");
        add("BURSA'DA ÜNLÜ YEŞIL TÜRBEYI KIM YAPTIRMIŞTIR?", "ÇELEBİ MEHMET", "BURSA");
        add("BURSA'NIN EN MEŞHUR YEMEĞI NEDIR?", "İSKENDER", "BURSA");
        add("BURSA'NIN HANGI ÜRÜNÜYLE ÜNLÜDÜR?", "HAVLU", "BURSA");
        add("BURSA'DA BULUNAN TARİHİ HANLARIN EN BÜYÜĞÜ HANGİSİDİR?", "KOZAHAN", "BURSA");
        add("BURSA'NIN HANGI ILÇESI TERMALLERIYLE ÜNLÜDÜR?", "ÇEKİRGE", "BURSA");
        add("BURSA'NIN OSMANLI KURULUŞUNDAKI ILK CAMISININ ADI NEDIR?", "ORHAN CAMİ", "BURSA");
        add("BURSA'NIN HANGI NEHRI ŞEHRI BÖLER?", "GÖKDERE", "BURSA");
        add("BURSA'DA ÜRETILEN ÜNLÜ KUMAŞIN ADI NEDIR?", "IPEK", "BURSA");
        add("BURSA'NIN HANGI ILÇESI ZEYTIN ÜRETIMINDE ÖNEMLIDIR?", "MUDANYA", "BURSA");
        add("BURSA'NIN EN ESKI KÖYÜ HANGISIDIR?", "CUMALIKIZIK", "BURSA");
        add("BURSA'DA HANGI SULTANIN TÜRBESI BULUNUR?", "MURAT", "BURSA");
        add("BURSA'NIN HANGI SEMTI TARİHİ EVLERIYLE ÜNLÜDÜR?", "TOPHANE", "BURSA");
        add("BURSA'NIN UNESCO DÜNYA MIRASI LISTESINE GIREN KÖYÜ HANGISIDIR?", "CUMALIKIZIK", "BURSA");
        add("BURSA'NIN HANGI GÖLÜ DOĞAL GÜZELLIĞIYLE ÜNLÜDÜR?", "ULUABAT", "BURSA");
        add("BURSA'DA ÜRETILEN ÜNLÜ TATLI HANGISIDIR?", "KEMALPAŞA", "BURSA");
        add("BURSA'NIN HANGI ILÇESI KAYAK MERKEZIYLE TANINIR?", "ULUDAĞ", "BURSA");
        add("BURSA'NIN OSMANLI DÖNEMINDEKI ADI NEDIR?", "HÜDAVENDİGAR", "BURSA");
        add("BURSA'DA HANGI TARİHİ KALE BULUNUR?", "BURSA KALESİ", "BURSA");
        add("BURSA'NIN HANGI PARKI ŞEHIR MERKEZINDE YER ALIR?", "KÜLTÜRPARK", "BURSA");
        add("BURSA'NIN HANGI ILÇESI SANAYIYLE ÖN PLANDADIR?", "NİLÜFER", "BURSA");
        add("BURSA'DA HANGI CAMININ 20 KUBBESI VARDIR?", "ULU CAMİ", "BURSA");
        add("BURSA'NIN HANGI MEYDANI TARİHİ SAAT KULESIYLE ÜNLÜDÜR?", "TOPHANE", "BURSA");
        add("BURSA'DA HANGI FESTIVAL İNCİRİ KUTLAR?", "İNCİR FESTİVALİ", "BURSA");
        add("BURSA'NIN HANGI ILÇESI DENIZ KENARINDA YER ALIR?", "MUDANYA", "BURSA");
        add("BURSA'DA HANGI TARİHİ ÇARŞI ALIŞVERIŞ IÇIN ÜNLÜDÜR?", "KAPALI ÇARŞI", "BURSA");
        add("BURSA'NIN HANGI ILÇESI ÜZÜM BAĞLARIYLA TANINIR?", "GEMLİK", "BURSA");
        add("BURSA'DA HANGI KÖPRÜ OSMANLI DÖNEMINDEN KALMADIR?", "IRGANDI", "BURSA");
        add("BURSA'NIN HANGI SEMTI YEŞIL RENGIYLE ÜNLÜDÜR?", "YEŞIL", "BURSA");
        add("BURSA'NIN HANGI DAĞINDA KAYAK YAPILIR?", "ULUDAĞ", "BURSA");
        add("BURSA'DA HANGI TARİHİ HAMAM HALA KULLANILMAKTADIR?", "ÇEKİRGE HAMAMI", "BURSA");
        add("BURSA'NIN HANGI ILÇESI ZEYTINYAĞIYLA ÜNLÜDÜR?", "GEMLIK", "BURSA");
        add("BURSA'DA HANGI OSMANLI PADİŞAHININ TÜRBESI VARDIR?", "OSMAN GAZİ", "BURSA");
        add("BURSA'NIN HANGI ILÇESI KESTANESIYLE ÜNLÜDÜR?", "İNEGÖL", "BURSA");
        add("BURSA'DA HANGI MEYDAN ŞEHIR MERKEZINDE BULUNUR?", "HEYKEL", "BURSA");
        add("BURSA'NIN HANGI ILÇESI MOBILYASIYLA TANINIR?", "INEGÖL", "BURSA");
        add("BURSA'DA HANGI TARİHİ AĞAÇ ASIRLARDIR AYAKTADIR?", "ÇINAR", "BURSA");
        add("BURSA'NIN HANGI ILÇESI KÖFTESIYLE ÜNLÜDÜR?", "İNEGÖL", "BURSA");
        add("BURSA'DA HANGI CAMI OSMANLI'NIN ILK BÜYÜK CAMISIDIR?", "ULU CAMİ", "BURSA");
        add("BURSA'NIN HANGI GÖLÜ KUŞ CENNETI OLARAK BILINIR?", "ULUABAT", "BURSA");
        add("BURSA'DA HANGI TARİHİ YAPI ZINDAN OLARAK KULLANILMIŞTIR?", "BURSA KALESI", "BURSA");
        add("BURSA'NIN HANGI ILÇESI TARİHİ EVLERIYLE TANINIR?", "OSMANGAZİ", "BURSA");
        add("BURSA'DA HANGI FESTIVAL IPEK ÜRETIMINI KUTLAR?", "IPEK FESTIVALI", "BURSA");
        add("BURSA'NIN HANGI SEMTI OSMANLI MEZARLIĞIYLA ÜNLÜDÜR?", "MURADIYE", "BURSA");
        add("BURSA'DA HANGI NEHIR ÜZERINDE BARAJ VARDIR?", "NİLÜFER", "BURSA");
        add("BURSA'NIN HANGI ILÇESI SANAYI BÖLGESIYLE BILINIR?", "OSMANGAZİ", "BURSA");
        add("BURSA'DA HANGI TARİHİ KÖŞK ZIYARET EDILEBILIR?", "HÜNKAR KÖŞKÜ", "BURSA");
        add("BURSA'NIN HANGI ILÇESI BALIKÇILIĞIYLA ÜNLÜDÜR?", "MUDANYA", "BURSA");
        add("BURSA'DA HANGI OSMANLI PADİŞAHI DOĞMUŞTUR?", "MURAT", "BURSA");
        add("BURSA'NIN HANGI SEMTI TARİHİ ÇARŞILARIYLA BILINIR?", "YILDIRIM", "BURSA");
        add("BURSA'DA HANGI DAĞIN ETEĞINDE ŞEHIR KURULMUŞTUR?", "ULUDAĞ", "BURSA");
        add("BURSA'NIN HANGI ILÇESI TERMALLERIYLE TANINIR?", "KESTEL", "BURSA");
        add("BURSA'DA HANGI TARİHİ KÖPRÜ HALA AYAKTADIR?", "IRGANDI", "BURSA");
        add("BURSA'NIN HANGI ILÇESI ÜZÜMÜYLE ÜNLÜDÜR?", "GÜRSU", "BURSA");
        add("BURSA'DA HANGI CAMININ MIMARI HACI IVAZ PAŞA'DIR?", "YEŞİL CAMİ", "BURSA");
        add("BURSA'NIN HANGI ILÇESI OTOMOTIV SANAYISIYLE BILINIR?", "NILÜFER", "BURSA");
        add("BURSA'DA HANGI TARİHİ HAN IPEK TICARETI IÇIN KULLANILMIŞTIR?", "KOZAHAN", "BURSA");
        add("BURSA'NIN HANGI SEMTI OSMANLI SARAY MUTFAĞIYLA ÜNLÜDÜR?", "MURADİYE", "BURSA");
        add("BURSA'DA HANGI GÖL BALIKÇILIK IÇIN ÖNEMLIDIR?", "IPEKCIK", "BURSA");
        add("BURSA'NIN HANGI ILÇESI OSMANLI DÖNEMI EVLERIYLE TANINIR?", "YILDIRIM", "BURSA");
        add("BURSA'DA HANGI TARİHİ YAPI SULTANLARIN DINLENME YERIYDI?", "HÜNKAR KÖŞKÜ", "BURSA");
        add("BURSA'NIN HANGI ILÇESI KAPLICALARIYLA ÜNLÜDÜR?", "OYLAT", "BURSA");
        add("BURSA'DA HANGI MEYDAN MODERN ALIŞVERIŞ MERKEZIYLE BILINIR?", "KENT MEYDANI", "BURSA");
        add("BURSA'NIN HANGI ILÇESI TARIM ÜRÜNLERİYLE TANINIR?", "MUSTAFAKEMALPAŞA", "BURSA");
        add("BURSA'DA HANGI TARİHİ ÇEŞME HALA KULLANILMAKTADIR?", "KAYHAN ÇEŞMESİ", "BURSA");
        add("BURSA'NIN HANGI ILÇESI KÖY PAZARIYLA ÜNLÜDÜR?", "KESTEL", "BURSA");
        add("BURSA'DA HANGI OSMANLI PADİŞAHININ BABASI BURSA'DA DOĞMUŞTUR?", "ORHAN GAZİ", "BURSA");
        add("BURSA'NIN HANGI SEMTI YEŞIL ALANLARIYLA BILINIR?", "SOĞANLI", "BURSA");
        add("BURSA'DA HANGI TARİHİ YAPI OSMANLI'NIN ILK SARAYIDIR?", "BEY SARAYI", "BURSA");
        add("BURSA'NIN HANGI ILÇESI FINDIĞIYLA ÜNLÜDÜR?", "ORHANGAZİ", "BURSA");
        add("BURSA'DA HANGI NEHIR ŞEHRE HAYAT VERIR?", "NİLÜFER", "BURSA");
        add("BURSA'NIN HANGI ILÇESI KAPLICA SULARIYLA TANINIR?", "ÇEKİRGE", "BURSA");

        add("JAVA'DA BIR SINIFIN BASKA BIR SINIFTAN TUREMESINE NE DENIR?", "KALITIM", "TEKNOLOJI");
        add("NESNEYE YONELIK PROGRAMLAMANIN KISALTMASI NEDIR?", "OOP", "TEKNOLOJI");
        add("JAVA'DA TANIMLANAN ANCAK GOVDESI OLMAYAN METODA NE DENIR?", "SOYUT METOT", "TEKNOLOJI");
        add("JAVA'DA INTERFACE ILE GERCEKLESTIRILEN KAVRAM NEDIR?", "COKBICIMLILIK", "TEKNOLOJI");
        add("BIR SINIFIN BASKA SINIFLAR ICIN SABLONA DONUSMESINE NE DENIR?", "SOYUT SINIF", "TEKNOLOJI");
        add("SINIF ICINDE VERILERIN PRIVATE TUTULMASI HANGI OOP ILKESIDIR?", "KAPSULLEME", "TEKNOLOJI");
        add("AYNI ISIMLI METODLARIN FARKLI PARAMETRELERLE YAZILMASINA NE DENIR?", "OVERLOADING", "TEKNOLOJI");
        add("UST SINIFTAKI METODUN ALT SINIFTA YENIDEN YAZILMASINA NE DENIR?", "OVERRIDING", "TEKNOLOJI");
        add("NESNE OLUSTURULURKEN CALISAN OZEL METODA NE DENIR?", "CONSTRUCTOR", "TEKNOLOJI");
        add("BIR METODUN KENDI KENDINI CAGIRMASINA NE DENIR?", "REKURSİYON", "TEKNOLOJI");
        add("VERILERI KUCUKTEN BUYUGE DIZME ISLEMINE GENEL OLARAK NE DENIR?", "SIRALAMA", "TEKNOLOJI");
        add("LIFO MANTIGIYLA CALISAN VERI YAPISINA NE DENIR?", "STACK", "TEKNOLOJI");
        add("FIFO MANTIGIYLA CALISAN VERI YAPISINA NE DENIR?", "QUEUE", "TEKNOLOJI");
        add("DUGUMLERDEN OLUSAN BAGLANTILI VERI YAPISINA NE DENIR?", "LINKED LIST", "TEKNOLOJI");
        add("JAVA'DA COKLU KALITIM YERINE KULLANILAN YAPI NEDIR?", "INTERFACE", "TEKNOLOJI");
        add("BIR SINIFIN NESNESINI OLUSTURMA ISLEMINE NE DENIR?", "NESNE OLUSTURMA", "TEKNOLOJI");
        add("ALGORITMANIN CALISMA SURESINI ANALIZ ETME GOSTERIMI NEDIR?", "BIG O", "TEKNOLOJI");
        add("VERININ DISARIDAN KONTROLLU ERISILMESI ICIN KULLANILAN METOTLAR NEDIR?", "GETTER SETTER", "TEKNOLOJI");
        add("NESNELERIN AYNI ARAYUZLE FARKLI DAVRANMASINA NE DENIR?", "COKBICIMLILIK", "TEKNOLOJI");
        add("BIR PROJEDE HER SINIFIN TEK BIR GOREVI OLMASI HANGI ILKEDIR?", "TEK SORUMLULUK", "TEKNOLOJI");
    }

    private void add(String question, String answer, String category) {
        questions.add(new Question(question, answer, category));
    }
}
