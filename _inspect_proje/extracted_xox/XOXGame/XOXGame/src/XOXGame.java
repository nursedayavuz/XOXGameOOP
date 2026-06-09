/*
 * TicTacToe.java
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class XOXGame extends JFrame
{
  JTextField messageTextField = new JTextField();
  JPanel gamePanel = new JPanel();
  JTextField[] boxTextField = new JTextField[9];
  JLabel[] gridLabel = new JLabel[4];
  JPanel playersPanel = new JPanel();
  ButtonGroup playersButtonGroup = new ButtonGroup();
  JRadioButton twoPlayersRadioButton = new JRadioButton();
  JRadioButton onePlayerRadioButton = new JRadioButton();
  JPanel firstPanel = new JPanel();
  ButtonGroup firstButtonGroup = new ButtonGroup();
  JRadioButton youFirstRadioButton = new JRadioButton();
  JRadioButton computerFirstRadioButton = new JRadioButton();
  JPanel computerPanel = new JPanel();
  ButtonGroup computerButtonGroup = new ButtonGroup();
  JRadioButton randomRadioButton = new JRadioButton();
  JRadioButton smartRadioButton = new JRadioButton();
  JPanel buttonsPanel = new JPanel();
  JButton startStopButton = new JButton();
  JButton exitButton = new JButton();
  JTextArea questionTextArea = new JTextArea();
  JTextField answerTextField = new JTextField();
  JButton checkButton = new JButton();
  GraphicsPanel displayPanel = new GraphicsPanel();

  boolean xTurn;
  boolean canClick = false;
  int numberClicks;
  String[] possibleWins = new String[8];
  boolean gameOver;
  Random myRandom = new Random();
  int numberQuestions = 73;
	String[] qChoice = new String[numberQuestions];
	String[] aChoice = new String[numberQuestions];
	int tries;
	boolean correctAnswer;
	int questionIndex;





  public static void main(String args[])
  {
    // create frames
    new XOXGame().show();
  }

  public XOXGame()
  {
    // frame constructor
    setTitle("Tic Tac Toe");
    getContentPane().setBackground(Color.WHITE);
    setResizable(false);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent evt)
      {
        exitForm(evt);
      }
    });
    getContentPane().setLayout(new GridBagLayout());

    messageTextField = new JTextField();
    messageTextField.setPreferredSize(new Dimension(280, 50));
    messageTextField.setEditable(false);
    messageTextField.setBackground(Color.YELLOW);
    messageTextField.setForeground(Color.BLUE);
    messageTextField.setText("X's Move");
    messageTextField.setHorizontalAlignment(SwingConstants.CENTER);
    messageTextField.setFont(new Font("Arial", Font.BOLD, 24));
    GridBagConstraints gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.insets = new Insets(10, 10, 10, 10);
    getContentPane().add(messageTextField, gridConstraints);

    gamePanel.setPreferredSize(new Dimension(280, 280));
    gamePanel.setBackground(Color.WHITE);
    gamePanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.gridheight = 3;
    gridConstraints.insets = new Insets(10, 10, 10, 10);
    getContentPane().add(gamePanel, gridConstraints);

    for (int i = 0; i < 9; i++)
    {
      boxTextField[i] = new JTextField();
      boxTextField[i].setPreferredSize(new Dimension(80, 80));
      boxTextField[i].setEditable(false);
      boxTextField[i].setBackground(Color.WHITE);
      boxTextField[i].setHorizontalAlignment(SwingConstants.CENTER);
      boxTextField[i].setFont(new Font("Arial", Font.BOLD, 48));
      boxTextField[i].setBorder(null);
      gridConstraints = new GridBagConstraints();
      gridConstraints.gridx = 2 * (i % 3);
      gridConstraints.gridy = 2 * (i / 3);
      gamePanel.add(boxTextField[i], gridConstraints);
      boxTextField[i].addMouseListener(new MouseAdapter()
      {
        public void mousePressed(MouseEvent e)
        {
          boxTextFieldMousePressed(e);
        }
      });
    }

    gridLabel[0] = new JLabel();
    gridLabel[0].setPreferredSize(new Dimension(280, 10));
    gridLabel[0].setOpaque(true);
    gridLabel[0].setBackground(Color.BLUE);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.gridwidth = 5;
    gridConstraints.insets = new Insets(5, 0, 5, 0);
    gamePanel.add(gridLabel[0], gridConstraints);

    gridLabel[1] = new JLabel();
    gridLabel[1].setPreferredSize(new Dimension(280, 10));
    gridLabel[1].setOpaque(true);
    gridLabel[1].setBackground(Color.BLUE);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 3;
    gridConstraints.gridwidth = 5;
    gridConstraints.insets = new Insets(5, 0, 5, 0);
    gamePanel.add(gridLabel[1], gridConstraints);

    gridLabel[2] = new JLabel();
    gridLabel[2].setPreferredSize(new Dimension(10, 280));
    gridLabel[2].setOpaque(true);
    gridLabel[2].setBackground(Color.BLUE);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 0;
    gridConstraints.gridheight = 5;
    gridConstraints.insets = new Insets(0, 5, 0, 5);
    gamePanel.add(gridLabel[2], gridConstraints);

    gridLabel[3] = new JLabel();
    gridLabel[3].setPreferredSize(new Dimension(10, 280));
    gridLabel[3].setOpaque(true);
    gridLabel[3].setBackground(Color.BLUE);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 3;
    gridConstraints.gridy = 0;
    gridConstraints.gridheight = 5;
    gridConstraints.insets = new Insets(0, 5, 0, 5);
    gamePanel.add(gridLabel[3], gridConstraints);

    playersPanel.setPreferredSize(new Dimension(160, 75));
    playersPanel.setBackground(Color.WHITE);
    playersPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    playersPanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 0;
    gridConstraints.insets = new Insets(5, 10, 5, 10);
    getContentPane().add(playersPanel, gridConstraints);

    twoPlayersRadioButton.setText("Two Players");
    twoPlayersRadioButton.setBackground(Color.WHITE);
    twoPlayersRadioButton.setSelected(true);
    playersButtonGroup.add(twoPlayersRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.anchor = GridBagConstraints.WEST;
    playersPanel.add(twoPlayersRadioButton, gridConstraints);
    twoPlayersRadioButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        twoPlayersRadioButtonActionPerformed(e);
      }
    });

    onePlayerRadioButton.setText("One Player");
    onePlayerRadioButton.setBackground(Color.WHITE);
    playersButtonGroup.add(onePlayerRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.anchor = GridBagConstraints.WEST;
    playersPanel.add(onePlayerRadioButton, gridConstraints);
    onePlayerRadioButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        onePlayerRadioButtonActionPerformed(e);
      }
    });

    firstPanel.setPreferredSize(new Dimension(160, 75));
    firstPanel.setBackground(Color.WHITE);
    firstPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    firstPanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 1;
    gridConstraints.insets = new Insets(5, 10, 5, 10);
    getContentPane().add(firstPanel, gridConstraints);

    youFirstRadioButton.setText("You First");
    youFirstRadioButton.setBackground(Color.WHITE);
    youFirstRadioButton.setSelected(true);
    firstButtonGroup.add(youFirstRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.anchor = GridBagConstraints.WEST;
    firstPanel.add(youFirstRadioButton, gridConstraints);

    computerFirstRadioButton.setText("Computer First");
    computerFirstRadioButton.setBackground(Color.WHITE);
    firstButtonGroup.add(computerFirstRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.anchor = GridBagConstraints.WEST;
    firstPanel.add(computerFirstRadioButton, gridConstraints);

    computerPanel.setPreferredSize(new Dimension(160, 75));
    computerPanel.setBackground(Color.WHITE);
    computerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    computerPanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 2;
    gridConstraints.insets = new Insets(5, 10, 5, 10);
    getContentPane().add(computerPanel, gridConstraints);

    randomRadioButton.setText("Random Computer");
    randomRadioButton.setBackground(Color.WHITE);
    randomRadioButton.setSelected(true);
    computerButtonGroup.add(randomRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.anchor = GridBagConstraints.WEST;
    computerPanel.add(randomRadioButton, gridConstraints);

    smartRadioButton.setText("Smart Computer");
    smartRadioButton.setBackground(Color.WHITE);
    computerButtonGroup.add(smartRadioButton);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.anchor = GridBagConstraints.WEST;
    computerPanel.add(smartRadioButton, gridConstraints);

    buttonsPanel.setPreferredSize(new Dimension(160, 70));
    buttonsPanel.setBackground(Color.WHITE);
    buttonsPanel.setLayout(new GridBagLayout());
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 3;
    getContentPane().add(buttonsPanel, gridConstraints);

    startStopButton.setText("Start Game");
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    buttonsPanel.add(startStopButton, gridConstraints);
    startStopButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        startStopButtonActionPerformed(e);
      }
    });

    exitButton.setText("Exit");
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.insets = new Insets(10, 0, 0, 0);
    buttonsPanel.add(exitButton, gridConstraints);
    exitButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        exitButtonActionPerformed(e);
      }
    });

    questionTextArea = new JTextArea();
    questionTextArea.setPreferredSize(new Dimension(450, 40));
    questionTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
    questionTextArea.setLineWrap(true);
    questionTextArea.setWrapStyleWord(true);
    questionTextArea.setEditable(false);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 4;
    gridConstraints.gridwidth = 2;
    getContentPane().add(questionTextArea, gridConstraints);

    answerTextField = new JTextField();
    answerTextField.setPreferredSize(new Dimension(280, 25));
    answerTextField.setFont(new Font("Arial", Font.PLAIN, 14));
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 5;
    gridConstraints.insets = new Insets(0, 0, 10, 0);
    getContentPane().add(answerTextField, gridConstraints);

    checkButton.setText("Check Answer");
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 5;
    gridConstraints.insets = new Insets(0, 0, 10, 0);
    getContentPane().add(checkButton, gridConstraints);
    checkButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        checkButtonActionPerformed(e);
      }
    });

    displayPanel.setPreferredSize(new Dimension(490, 100));
    displayPanel.setBackground(Color.WHITE);
    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 6;
    gridConstraints.gridwidth = 2;
    gridConstraints.insets = new Insets(0, 0, 10, 0);
    getContentPane().add(displayPanel, gridConstraints);

    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((int) (0.5 * (screenSize.width - getWidth())), (int) (0.5 * (screenSize.height - getHeight())), getWidth(), getHeight());

    messageTextField.setText("Game Stopped");
    youFirstRadioButton.setEnabled(false);
    computerFirstRadioButton.setEnabled(false);
    randomRadioButton.setEnabled(false);
    smartRadioButton.setEnabled(false);
    // possible wins
    possibleWins[0] = "012";
    possibleWins[1] = "345";
    possibleWins[2] = "678";
    possibleWins[3] = "036";
    possibleWins[4] = "147";
    possibleWins[5] = "258";
    possibleWins[6] = "048";
    possibleWins[7] = "246";
   
    questionTextArea.setVisible(false);
    answerTextField.setVisible(false);
    checkButton.setVisible(false);
    displayPanel.repaint();

    numberQuestions = 73;
    qChoice[0] = "BURSA'NIN EN ÜNLÜ DAĞININ ADI NEDIR?";
    aChoice[0] = "ULUDAĞ";
    qChoice[1] = "BURSA'NIN OSMANLI DÖNEMINDEKI ILK BAŞKENT OLDUĞU YIL HANGISIDIR?";
    aChoice[1] = "1326";
    qChoice[2] = "BURSA'DA ÜNLÜ YEŞIL TÜRBEYI KIM YAPTIRMIŞTIR?";
    aChoice[2] = "ÇELEBİ MEHMET";
    qChoice[3] = "BURSA'NIN EN MEŞHUR YEMEĞI NEDIR?";
    aChoice[3] = "İSKENDER";
    qChoice[4] = "BURSA'NIN HANGI ÜRÜNÜYLE ÜNLÜDÜR?";
    aChoice[4] = "HAVLU";
    qChoice[5] = "BURSA'DA BULUNAN TARİHİ HANLARIN EN BÜYÜĞÜ HANGİSİDİR?";
    aChoice[5] = "KOZAHAN";
    qChoice[6] = "BURSA'NIN HANGI ILÇESI TERMALLERIYLE ÜNLÜDÜR?";
    aChoice[6] = "ÇEKİRGE";
    qChoice[7] = "BURSA'NIN OSMANLI KURULUŞUNDAKI ILK CAMISININ ADI NEDIR?";
    aChoice[7] = "ORHAN CAMİ";
    qChoice[8] = "BURSA'NIN HANGI NEHRI ŞEHRI BÖLER?";
    aChoice[8] = "GÖKDERE";
    qChoice[9] = "BURSA'DA ÜRETILEN ÜNLÜ KUMAŞIN ADI NEDIR?";
    aChoice[9] = "IPEK";
    qChoice[10] = "BURSA'NIN HANGI ILÇESI ZEYTIN ÜRETIMINDE ÖNEMLIDIR?";
    aChoice[10] = "MUDANYA";
    qChoice[11] = "BURSA'NIN EN ESKI KÖYÜ HANGISIDIR?";
    aChoice[11] = "CUMALIKIZIK";
    qChoice[12] = "BURSA'DA HANGI SULTANIN TÜRBESI BULUNUR?";
    aChoice[12] = "MURAT";
    qChoice[13] = "BURSA'NIN HANGI SEMTI TARİHİ EVLERIYLE ÜNLÜDÜR?";
    aChoice[13] = "TOPHANE";
    qChoice[14] = "BURSA'NIN UNESCO DÜNYA MIRASI LISTESINE GIREN KÖYÜ HANGISIDIR?";
    aChoice[14] = "CUMALIKIZIK";
    qChoice[15] = "BURSA'NIN HANGI GÖLÜ DOĞAL GÜZELLIĞIYLE ÜNLÜDÜR?";
    aChoice[15] = "ULUABAT";
    qChoice[16] = "BURSA'DA ÜRETILEN ÜNLÜ TATLI HANGISIDIR?";
    aChoice[16] = "KEMALPAŞA";
    qChoice[17] = "BURSA'NIN HANGI ILÇESI KAYAK MERKEZIYLE TANINIR?";
    aChoice[17] = "ULUDAĞ";
    qChoice[18] = "BURSA'NIN OSMANLI DÖNEMINDEKI ADI NEDIR?";
    aChoice[18] = "HÜDAVENDİGAR";
    qChoice[19] = "BURSA'DA HANGI TARİHİ KALE BULUNUR?";
    aChoice[19] = "BURSA KALESİ";
    qChoice[20] = "BURSA'NIN HANGI PARKI ŞEHIR MERKEZINDE YER ALIR?";
    aChoice[20] = "KÜLTÜRPARK";
    qChoice[21] = "BURSA'NIN HANGI ILÇESI SANAYIYLE ÖN PLANDADIR?";
    aChoice[21] = "NİLÜFER";
    qChoice[22] = "BURSA'DA HANGI CAMININ 20 KUBBESI VARDIR?";
    aChoice[22] = "ULU CAMİ";
    qChoice[23] = "BURSA'NIN HANGI MEYDANI TARİHİ SAAT KULESIYLE ÜNLÜDÜR?";
    aChoice[23] = "TOPHANE";
    qChoice[24] = "BURSA'DA HANGI FESTIVAL İNCİRİ KUTLAR?";
    aChoice[24] = "İNCİR FESTİVALİ";
    qChoice[25] = "BURSA'NIN HANGI ILÇESI DENIZ KENARINDA YER ALIR?";
    aChoice[25] = "MUDANYA";
    qChoice[26] = "BURSA'DA HANGI TARİHİ ÇARŞI ALIŞVERIŞ IÇIN ÜNLÜDÜR?";
    aChoice[26] = "KAPALI ÇARŞI";
    qChoice[27] = "BURSA'NIN HANGI ILÇESI ÜZÜM BAĞLARIYLA TANINIR?";
    aChoice[27] = "GEMLİK";
    qChoice[28] = "BURSA'DA HANGI KÖPRÜ OSMANLI DÖNEMINDEN KALMADIR?";
    aChoice[28] = "IRGANDI";
    qChoice[29] = "BURSA'NIN HANGI SEMTI YEŞIL RENGIYLE ÜNLÜDÜR?";
    aChoice[29] = "YEŞIL";
    qChoice[30] = "BURSA'NIN HANGI DAĞINDA KAYAK YAPILIR?";
    aChoice[30] = "ULUDAĞ";
    qChoice[31] = "BURSA'DA HANGI TARİHİ HAMAM HALA KULLANILMAKTADIR?";
    aChoice[31] = "ÇEKİRGE HAMAMI";
    qChoice[32] = "BURSA'NIN HANGI ILÇESI ZEYTINYAĞIYLA ÜNLÜDÜR?";
    aChoice[32] = "GEMLIK";
    qChoice[33] = "BURSA'DA HANGI OSMANLI PADİŞAHININ TÜRBESI VARDIR?";
    aChoice[33] = "OSMAN GAZİ";
    qChoice[34] = "BURSA'NIN HANGI ILÇESI KESTANESIYLE ÜNLÜDÜR?";
    aChoice[34] = "İNEGÖL";
    qChoice[35] = "BURSA'DA HANGI MEYDAN ŞEHIR MERKEZINDE BULUNUR?";
    aChoice[35] = "HEYKEL";
    qChoice[36] = "BURSA'NIN HANGI ILÇESI MOBILYASIYLA TANINIR?";
    aChoice[36] = "INEGÖL";
    qChoice[37] = "BURSA'DA HANGI TARİHİ AĞAÇ ASIRLARDIR AYAKTADIR?";
    aChoice[37] = "ÇINAR";
    qChoice[38] = "BURSA'NIN HANGI ILÇESI KÖFTESIYLE ÜNLÜDÜR?";
    aChoice[38] = "İNEGÖL";
    qChoice[39] = "BURSA'DA HANGI CAMI OSMANLI'NIN ILK BÜYÜK CAMISIDIR?";
    aChoice[39] = "ULU CAMİ";
    qChoice[40] = "BURSA'NIN HANGI GÖLÜ KUŞ CENNETI OLARAK BILINIR?";
    aChoice[40] = "ULUABAT";
    qChoice[41] = "BURSA'DA HANGI TARİHİ YAPI ZINDAN OLARAK KULLANILMIŞTIR?";
    aChoice[41] = "BURSA KALESI";
    qChoice[42] = "BURSA'NIN HANGI ILÇESI TARİHİ EVLERIYLE TANINIR?";
    aChoice[42] = "OSMANGAZİ";
    qChoice[43] = "BURSA'DA HANGI FESTIVAL IPEK ÜRETIMINI KUTLAR?";
    aChoice[43] = "IPEK FESTIVALI";
    qChoice[44] = "BURSA'NIN HANGI SEMTI OSMANLI MEZARLIĞIYLA ÜNLÜDÜR?";
    aChoice[44] = "MURADIYE";
    qChoice[45] = "BURSA'DA HANGI NEHIR ÜZERINDE BARAJ VARDIR?";
    aChoice[45] = "NİLÜFER";
    qChoice[46] = "BURSA'NIN HANGI ILÇESI SANAYI BÖLGESIYLE BILINIR?";
    aChoice[46] = "OSMANGAZİ";
    qChoice[47] = "BURSA'DA HANGI TARİHİ KÖŞK ZIYARET EDILEBILIR?";
    aChoice[47] = "HÜNKAR KÖŞKÜ";
    qChoice[48] = "BURSA'NIN HANGI ILÇESI BALIKÇILIĞIYLA ÜNLÜDÜR?";
    aChoice[48] = "MUDANYA";
    qChoice[49] = "BURSA'DA HANGI OSMANLI PADİŞAHI DOĞMUŞTUR?";
    aChoice[49] = "MURAT";
    qChoice[50] = "BURSA'NIN HANGI SEMTI TARİHİ ÇARŞILARIYLA BILINIR?";
    aChoice[50] = "YILDIRIM";
    qChoice[51] = "BURSA'DA HANGI DAĞIN ETEĞINDE ŞEHIR KURULMUŞTUR?";
    aChoice[51] = "ULUDAĞ";
    qChoice[52] = "BURSA'NIN HANGI ILÇESI TERMALLERIYLE TANINIR?";
    aChoice[52] = "KESTEL";
    qChoice[53] = "BURSA'DA HANGI TARİHİ KÖPRÜ HALA AYAKTADIR?";
    aChoice[53] = "IRGANDI";
    qChoice[54] = "BURSA'NIN HANGI ILÇESI ÜZÜMÜYLE ÜNLÜDÜR?";
    aChoice[54] = "GÜRSU";
    qChoice[55] = "BURSA'DA HANGI CAMININ MIMARI HACI IVAZ PAŞA’DIR?";
    aChoice[55] = "YEŞİL CAMİ";
    qChoice[56] = "BURSA'NIN HANGI ILÇESI OTOMOTIV SANAYISIYLE BILINIR?";
    aChoice[56] = "NILÜFER";
    qChoice[57] = "BURSA'DA HANGI TARİHİ HAN IPEK TICARETI IÇIN KULLANILMIŞTIR?";
    aChoice[57] = "KOZAHAN";
    qChoice[58] = "BURSA'NIN HANGI SEMTI OSMANLI SARAY MUTFAĞIYLA ÜNLÜDÜR?";
    aChoice[58] = "MURADİYE";
    qChoice[59] = "BURSA'DA HANGI GÖL BALIKÇILIK IÇIN ÖNEMLIDIR?";
    aChoice[59] = "IPEKCIK";
    qChoice[60] = "BURSA'NIN HANGI ILÇESI OSMANLI DÖNEMI EVLERIYLE TANINIR?";
    aChoice[60] = "YILDIRIM";
    qChoice[61] = "BURSA'DA HANGI TARİHİ YAPI SULTANLARIN DINLENME YERIYDI?";
    aChoice[61] = "HÜNKAR KÖŞKÜ";
    qChoice[62] = "BURSA'NIN HANGI ILÇESI KAPLICALARIYLA ÜNLÜDÜR?";
    aChoice[62] = "OYLAT";
    qChoice[63] = "BURSA'DA HANGI MEYDAN MODERN ALIŞVERIŞ MERKEZIYLE BILINIR?";
    aChoice[63] = "KENT MEYDANI";
    qChoice[64] = "BURSA'NIN HANGI ILÇESI TARIM ÜRÜNLERİYLE TANINIR?";
    aChoice[64] = "MUSTAFAKEMALPAŞA";
    qChoice[65] = "BURSA'DA HANGI TARİHİ ÇEŞME HALA KULLANILMAKTADIR?";
    aChoice[65] = "KAYHAN ÇEŞMESİ";
    qChoice[66] = "BURSA'NIN HANGI ILÇESI KÖY PAZARIYLA ÜNLÜDÜR?";
    aChoice[66] = "KESTEL";
    qChoice[67] = "BURSA'DA HANGI OSMANLI PADİŞAHININ BABASI BURSA'DA DOĞMUŞTUR?";
    aChoice[67] = "ORHAN GAZİ";
    qChoice[68] = "BURSA'NIN HANGI SEMTI YEŞIL ALANLARIYLA BILINIR?";
    aChoice[68] = "SOĞANLI";
    qChoice[69] = "BURSA'DA HANGI TARİHİ YAPI OSMANLI’NIN ILK SARAYIDIR?";
    aChoice[69] = "BEY SARAYI";
    qChoice[70] = "BURSA'NIN HANGI ILÇESI FINDIĞIYLA ÜNLÜDÜR?";
    aChoice[70] = "ORHANGAZİ";
    qChoice[71] = "BURSA'DA HANGI NEHIR ŞEHRE HAYAT VERIR?";
    aChoice[71] = "NİLÜFER";
    qChoice[72] = "BURSA'NIN HANGI ILÇESI KAPLICA SULARIYLA TANINIR?";
    aChoice[72] = "ÇEKİRGE";
  }

  private void exitForm(WindowEvent evt)
  {
    System.exit(0);
  }

  private void boxTextFieldMousePressed(MouseEvent e)
  {
    if (canClick)
    {
      // if one player game, cannot click if correct answer has not been entered
      if (onePlayerRadioButton.isSelected() && correctAnswer == false)
         return;
      int i;
      // get upper left corner of clicked box
      Point p = e.getComponent().getLocation();
      // determine index based on p
      for (i = 0; i < 9; i++)
      {
        if (p.x == boxTextField[i].getX() && p.y == boxTextField[i].getY())
          break;
      }
      markClickedBox(i);
    }
  }

  private void twoPlayersRadioButtonActionPerformed(ActionEvent e)
  {
    youFirstRadioButton.setEnabled(false);
    computerFirstRadioButton.setEnabled(false);
    randomRadioButton.setEnabled(false);
    smartRadioButton.setEnabled(false);
   }

  private void onePlayerRadioButtonActionPerformed(ActionEvent e)
  {
    youFirstRadioButton.setEnabled(true);
    computerFirstRadioButton.setEnabled(true);
    randomRadioButton.setEnabled(true);
    smartRadioButton.setEnabled(true);
  }

  private void startStopButtonActionPerformed(ActionEvent e)
  {
    if (startStopButton.getText().equals("Start Game"))
    {
      startStopButton.setText("Stop Game");
      twoPlayersRadioButton.setEnabled(false);
      onePlayerRadioButton.setEnabled(false);
      youFirstRadioButton.setEnabled(false);
      computerFirstRadioButton.setEnabled(false);
      randomRadioButton.setEnabled(false);
      smartRadioButton.setEnabled(false);
      exitButton.setEnabled(false);
      xTurn = true;
      messageTextField.setText("X's Turn");
      // reset boxes
      for (int i = 0; i < 9; i++)
      {
        boxTextField[i].setText("");
        boxTextField[i].setBackground(Color.WHITE);
      }
      canClick = true;
      numberClicks = 0;
      gameOver = false;
      if (onePlayerRadioButton.isSelected())
      {
        // clear question/answer
				questionTextArea.setText("");
				answerTextField.setText("");
				questionTextArea.setVisible(true);
				answerTextField.setVisible(true);
				checkButton.setVisible(true);
	      if (computerFirstRadioButton.isSelected())
  	      computerTurn();
        else
          askQuestion();
      }
      else
      {
				questionTextArea.setVisible(false);
				answerTextField.setVisible(false);
				checkButton.setVisible(false);
      }
   	}
    else
    {
      startStopButton.setText("Start Game");
      if (!gameOver)
        messageTextField.setText("Game Stopped");
      twoPlayersRadioButton.setEnabled(true);
      onePlayerRadioButton.setEnabled(true);
      if (onePlayerRadioButton.isSelected())
      {
        youFirstRadioButton.setEnabled(true);
        computerFirstRadioButton.setEnabled(true);
        randomRadioButton.setEnabled(true);
        smartRadioButton.setEnabled(true);
      }
      exitButton.setEnabled(true);
      canClick = false;
      // clear question/answer
			questionTextArea.setText("");
			answerTextField.setText("");
    }
  }

  private void askQuestion()
  {
    // pick question at random
    tries = 0;
    correctAnswer = false;
    questionIndex = myRandom.nextInt(numberQuestions);
    questionTextArea.setText(qChoice[questionIndex]);
    answerTextField.setText("");
  }

  private void exitButtonActionPerformed(ActionEvent e)
  {
    System.exit(0);
  }

  private void checkButtonActionPerformed(ActionEvent e)
  {
    String yourAnswer;
    //check for correct answer
    tries++;
    if (tries > aChoice[questionIndex].length())
      tries = aChoice[questionIndex].length();
    yourAnswer = answerTextField.getText().toUpperCase();
    if (yourAnswer.equals(aChoice[questionIndex]))
    {
      answerTextField.setText(yourAnswer + " - CORRECT. CLICK GRID.");
      correctAnswer = true;
    }
    else
      answerTextField.setText(aChoice[questionIndex].substring(0, tries));
  }

  private void markClickedBox(int i)
  {
    String whoWon = "";
    // if already clicked then exit
    if (!boxTextField[i].getText().equals(""))
      return;
    numberClicks++;
    if (xTurn)
    {
      boxTextField[i].setText("X");
      xTurn = false;
      messageTextField.setText("O's Turn");
    }
    else
    {
      boxTextField[i].setText("O");
      xTurn = true;
      messageTextField.setText("X's Turn");
    }
    // check for win - will establish a value for WhoWon
    whoWon = checkForWin();
    if (!whoWon.equals(""))
    {
      messageTextField.setText(whoWon + " wins!");
      gameOver = true;
      startStopButton.doClick();
      return;
    }
    else if (numberClicks == 9)
    {
      // draw
      messageTextField.setText("It's a draw!");
      gameOver = true;
      startStopButton.doClick();
      return;
    }
    if (onePlayerRadioButton.isSelected())
      if ((xTurn && computerFirstRadioButton.isSelected()) || (!xTurn && youFirstRadioButton.isSelected()))
        computerTurn();
      else
      	askQuestion();
  }

  private String checkForWin()
  {
    String winner = "";
    int[] boxNumber = new int[3];
    String[] mark = new String[3];
    // check all possible for wins
    for (int i = 0; i < 8; i++)
    {
      for (int j = 0; j < 3; j++)
      {
        boxNumber[j] = Integer.valueOf(String.valueOf(possibleWins[i].charAt(j))).intValue();
        mark[j] = boxTextField[boxNumber[j]].getText();
      }
      if (mark[0].equals(mark[1]) && mark[0].equals(mark[2]) && mark[1].equals(mark[2]) && !mark[0].equals(""))
      {
        // we have a winner
        winner = mark[0];
        for (int j = 0; j < 3; j++)
          boxTextField[boxNumber[j]].setBackground(Color.RED);
      }
    }
    return (winner);
  }

  private void computerTurn()
  {
    int selectedBox;
    int i, n;
    int j, k;
    String computerMark, playerMark, markToFind;
    int[] boxNumber = new int[3];
    String[] mark = new String[3];
    int emptyBox;
    int[] bestMoves = { 4, 0, 2, 6, 8, 1, 3, 5, 7 };

    if (randomRadioButton.isSelected())
    {
      // random logic
      // put mark in Nth available square
      n = myRandom.nextInt(9 - numberClicks) + 1;
      i = 0;
      for (selectedBox = 0; selectedBox < 9; selectedBox++)
      {
        if (boxTextField[selectedBox].getText().equals(""))
          i++;
        if (i == n)
          break;
      }
      // put mark in SelectedBox
      markClickedBox(selectedBox);
    }
    else
    {
      // smart computer
            // determine who has what mark
      if (computerFirstRadioButton.isSelected())
      {
        computerMark = "X";
        playerMark = "O";
      }
      else
      {
        computerMark = "O";
        playerMark = "X";
      }
      // Step 1 (K = 1) - check for win - see if two boxes hold computer mark and one is empty
      // Step 2 (K = 2) - check for block - see if two boxes hold player mark and one is empty
      for (k = 1; k <= 2; k++)
      {
        if (k == 1)
          markToFind = computerMark;
        else
          markToFind = playerMark;
        for (i = 0; i < 8; i++)
        {
          n = 0;
          emptyBox = 0;
          for (j = 0; j < 3; j++)
          {
            boxNumber[j] = Integer.valueOf(String.valueOf(possibleWins[i].charAt(j))).intValue();
            mark[j] = boxTextField[boxNumber[j]].getText();
            if (mark[j].equals(markToFind))
              n++;
            else if (mark[j].equals(""))
              emptyBox = boxNumber[j];
          }
          if (n == 2 && emptyBox != 0)
          {
            // mark empty box to win (K = 1) or block (K = 2)
            markClickedBox(emptyBox);
            return;
          }
        }
      }
      // Step 3 - find next best move
      for (i = 0; i < 9; i++)
      {
        if (boxTextField[bestMoves[i]].getText().equals(""))
        {
          markClickedBox(bestMoves[i]);
          return;
        }
      }
    }
  }

}

class GraphicsPanel extends JPanel
{
	public GraphicsPanel()
	{
	}
	public void paintComponent(Graphics g)
	{
	  Graphics2D g2D = (Graphics2D) g;
		super.paintComponent(g2D);
	  Image myImage = new ImageIcon("").getImage();
	  g2D.drawImage(myImage, 0, 0, 70, 100, this);
	  myImage = new ImageIcon("").getImage();
	  g2D.drawImage(myImage, 70, 0, 70, 100, this);
	  myImage = new ImageIcon("").getImage();
	  g2D.drawImage(myImage, 140, 0, 70, 100, this);
	  myImage = new ImageIcon("").getImage();
	  g2D.drawImage(myImage, 210, 0, 70, 100, this);
	  myImage = new ImageIcon("").getImage();
	  g2D.drawImage(myImage, 280, 0, 70, 100, this);
	  myImage = new ImageIcon("").getImage();
	  g2D.drawImage(myImage, 350, 0, 70, 100, this);
	  myImage = new ImageIcon("").getImage();
	  g2D.drawImage(myImage, 420, 0, 70, 100, this);

		g2D.dispose();
	}
}
