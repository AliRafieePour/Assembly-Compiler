
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Scanner;

public class Main implements ActionListener
{
    private JFrame frame;
    private JLabel jLabel ;
    private JTextPane jTextArea ;
    private JScrollPane jScrollPane ;
    private JScrollPane mem_scroll  ;

    private JButton Sc_btn ;
    private JButton jbt1 ;
    private JButton jbt2;
    private JButton jbt3;
    private JButton jbt4;
    private JButton jbt5;
    private JButton jbt6;
    private JButton jbt7;
    private JButton jbt8;

    private JButton give_input ;

    private JTextField input ;

    private int T = -1;

    private String [] M ;
    private JButton play ;
    private JButton reset ;

    private JMenuBar mb;
    private JMenu file,edit,help;
    private JMenuItem cut,copy,paste,selectAll;
    private JTextArea mem_jtextarea ;
    JMenuItem item_hint , show_mem ;

    private JTextArea  hint_jtext_area;

    private boolean R = false ;
    private int I = 0 ;
    private int [] D ;
    private boolean Hlt = true ;

    boolean overwrite = false ;

    private  String [] AR ;//field 0 to 2 refers to control bits and field 3 refers to data stored .
    private  String [] PC ;
    private  String [] DR ;
    private  String [] AC ;
    private  String [] IR ; // IR is different from others , it will be have just two field ,one is LD ,other is ist s name .
    private  String [] TR ;

    private String [] Bus_dates = new String[2];

    private boolean FGO = false;
    private boolean FGI = false;
    private boolean IEN = false;
    private boolean  E = false;
    private int D_E = 0 ;

    private boolean Write_Mem = false ;
    private int D_R = 0 ;
    private int D_IEN  = 0 ;

    private int flag_out = 0 ;
    private int AC_code = 0 ;

    private String OUTR = "0";

    public Main()
    {
        //  String a = "110001";
        //int fg = Integer.parseInt(a);
        //  fg = ~fg ;
        //System.out.println(fg);
      /*  String ar = "1"+"0".repeat(3);
        int g = Integer.parseInt(ar,2) ;
        String fr = "1"+"0".repeat(3);
        int f= Integer.parseInt(fr,2);
        int decc = f+g;
        System.out.println(Integer.toBinaryString(decc));
        */
        //String fg = ar.substring(0,3); [)
        //ar = pc = dr = ac = ir = tr = "0".repeat(16);
        set_graphic();
        initial_registers();

        M = new String[4096];
        String adress101 =  Integer.toBinaryString(101);

        //  M[100] = "0001" + "0".repeat(12-adress101.length()) + adress101;

        // input to AC
      /*  M[100] = "0101" + "0".repeat(12-adress101.length()) + adress101;
        M[101] = "0".repeat(14) + "11" ; // data
        M[1] = "11111" + "0".repeat(11);*/

        String adress103 =  Integer.toBinaryString(103);
        String adress200 =  Integer.toBinaryString(200);
        String adress300 =  Integer.toBinaryString(300);
        String adress105 =  Integer.toBinaryString(105);
        String adress500 =  Integer.toBinaryString(500);


        // LD 3 in AC + 4 with ADD instruction + our input with STA , pB11 , BUN , ADD . Finally we will get 1000 or another numbers in order to the time of the interrupt at AC int this special example
        //  M[1] = "11111" + "0".repeat(11) ;
        // M[2] = "1100"  + "0".repeat(12) ; // BUN
     /*  M[100] = "0010"  + "0".repeat(12-adress200.length()) + adress200 ; // LDA
        M[101] = "1111" + "000010000000" ;    // ION
      M[200] = "0".repeat(14) + "11" ;   // number 3

      M[102] = "0001" + "0".repeat(12-adress300.length()) + adress300 ; // ADD
      M[300] = "0".repeat(13) + "100" ;

      M[103] = "0011" + "0".repeat(12-adress105.length()) + adress105 ;   // STA
      //M[105] will store 7 // in this example we should request for interrupt here .
      M[104] = "0001" + "0".repeat(12-adress105.length()) + adress105 ; // ADD
*/

       /* M[100] = "1111" + "000010000000" ;    // ION
        M[101] = "0010"  + "0".repeat(12-adress200.length()) + adress200 ; // LDA */   // interrupt have to occur after this instruction


       /* M[100] = "0111" + "00001" + "0".repeat(7) ; // shr
       // M[101] = "0111" + "0001" + "0".repeat(8) ; // CME
        M[101] = "0111" + "01" + "0".repeat(10) ; // CLE
         M[102] = "0111" + "0001" + "0".repeat(8) ; // CME
        M[103] = "0111" + "00001" + "0".repeat(7) ; // shr
        M[104] = "0111" + "00001" + "0".repeat(7) ; // shr*/


      /*  M[100] = "0111" + "0001" + "0".repeat(8) ; // CME
        M[101] = "0111" + "0001" + "0".repeat(8) ; // CME
        M[102] = "0111" + "01" + "0".repeat(10) ; // CLE
        M[103] = "0111" + "0001" + "0".repeat(8) ; // CME
        M[104] = "0111" + "01" + "0".repeat(10) ; // CLE*/

      /*  M[200] = "0".repeat(14) + "11" ;   // number 3
        M[100] = "0001" + "0".repeat(12-adress200.length()) + adress200 ; // ADD
        M[101]  = "0000" + "0".repeat(12-adress300.length()) + adress300 ; // AND
        M[300] = "1".repeat(16);
        M[100] = "0111" + "0".repeat(7) + "1" + "0".repeat(4);  // SPA*/

        //shift to left
        // M[100] = "0111" + "000001000000" ;
        //   M[101] =  "0111" + "000001000000" ;
        //  M[102] =  "0111" + "000001000000" ;
        //  M[103] =  "0111" + "0".repeat(11) + "1"; // Hlt instruction

        // shift to right
        //   M[100] = "0111" + "000010000000" ;
        //  M[101] = "0111" + "000010000000" ;
        //  M[102] = "0111" + "000010000000" ;
        //M[103] =  "0111" + "0".repeat(11) + "1";  //Hlt instruction


        /* M[100] = "0101" + "0".repeat(12-adress500.length()) + adress500 ; // BSA to 500 , then in 501 add AC with M[200] then in 502 BUN I to 500
         M[501] = "0001" + "0".repeat(12-adress200.length()) + adress200 ; // ADD
         M[502] = "1100" + "0".repeat(12-adress500.length()) + adress500 ; // BUM I to 500
         M[101] = "0000" + "0".repeat(12-adress300.length()) + adress300 ; // then in 101 , AND AC with M[300]
         M[102] = "0111" + "0".repeat(11) + "1";

        M[200] = "0".repeat(13) + "111" ; // number 7 ;
        M[300] = "0".repeat(13) + "100" ; // number 4*/
        // at last we have to get 0 in AC ; because 10 and 4 will be 0 .

        Bus_dates [0] = "PC"; // -- Fetch _Bus_datas
        Bus_dates [1] = "0" ;

        play.addActionListener(e->
        {

            Runtime runtime = Runtime.getRuntime();
            Process process;
            try {
                String code = jTextArea.getText();
                FileWriter myWriter = new FileWriter("source.asm");
                myWriter.flush();
                myWriter.write(code);
                myWriter.close();

                String[] command = {"C:/Users/bcz/AppData/Local/Programs/Python/Python37-32/python.exe", "process.py", "source.asm", "output.txt"};
                ProcessBuilder probuilder = new ProcessBuilder( command );
                //You can set up your work directory
                process = probuilder.start();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            mem_jtextarea.setText("");
            ArrayList<String> mem_info = new ArrayList<>() ;

            try {
                File myObj = new File("output.txt");
                Scanner myReader = new Scanner(myObj);

                if(myReader.hasNextLine())
                {
                    String first = myReader.nextLine() ;
                    mem_info.add(first);
                    String [] s = first.split(" ");

                    if (M[Integer.parseInt(s[0])] != null)
                    {
                        overwrite = true ;
                    }
                    M[Integer.parseInt(s[0])] = s[1] ;
                    String bi = Integer.toBinaryString(Integer.parseInt(s[0]));
                    PC[3] = "0".repeat(16 - bi.length()) + bi ;
                    while (myReader.hasNextLine())
                    {
                        String data = myReader.nextLine();
                        mem_info.add(data);
                        String [] p = data.split(" ");

                        if (M[Integer.parseInt(p[0])] != null)
                        {
                            overwrite = true ;
                        }

                        M[Integer.parseInt(p[0])] = p[1] ;
                    }
                    JOptionPane.showMessageDialog(frame,
                            "Compiled Successfully :) ");
                }
                else
                {
                    JOptionPane.showMessageDialog(frame,
                            "There was an error After solving ,Try Again ",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);


                    StringBuilder tf_er = new StringBuilder();
                    try
                    {
                        File er_object = new File("errors.txt");
                        Scanner er_reader = new Scanner(er_object);
                        while (er_reader.hasNextLine())
                        {
                            tf_er.append(er_reader.nextLine()).append("\n");
                        }
                        er_reader.close();
                    } catch (FileNotFoundException eee) { eee.printStackTrace(); }


                    JFrame error_frame = new JFrame("error!");
                    JTextArea error_jtext_area = new JTextArea(tf_er.toString(), 10 , 15 ) ;
                    error_jtext_area.setLineWrap(true);

                    error_jtext_area.setFont(new Font("Serif" , Font.ITALIC, 14));

                    JScrollPane error_scroll = new JScrollPane(error_jtext_area);
                    error_scroll.setBounds(500,20,650,340);

                    error_frame.add(error_scroll);
                    error_frame.setBounds(320,125,700,400);
                    error_frame.setVisible(true);
                }
                myReader.close();
            } catch (FileNotFoundException ee) {ee.printStackTrace(); }

            Hlt = false ;

            if(overwrite)
            {
                JOptionPane.showMessageDialog(frame,
                        "Overwriting memory ? Be careful!",
                        "Overwriting memory warning",
                        JOptionPane.WARNING_MESSAGE);
            }


            for (int i = 0 ; i < mem_info.size() ; i++)
            {
                mem_jtextarea.append(mem_info.get(i) + "\n");
            }

            Highlighter.HighlightPainter painter_w = new DefaultHighlighter.DefaultHighlightPainter(Color.white);
            Highlighter.HighlightPainter painter_g = new DefaultHighlighter.DefaultHighlightPainter(Color.lightGray);

            for(int i= 0 ; i < mem_info.size() ; i++)
            {
                int startIndex = 0 ;
                int endIndex   = 0 ;
                try {
                    startIndex = mem_jtextarea.getLineStartOffset(i);
                } catch (BadLocationException ex) {      ex.printStackTrace(); }
                try {
                    endIndex = mem_jtextarea.getLineEndOffset(i);
                } catch (BadLocationException ex) { ex.printStackTrace(); }

                if(i % 2 == 0)
                {
                    try {
                        mem_jtextarea.getHighlighter().addHighlight(startIndex, endIndex, painter_w);
                    } catch (BadLocationException ex) {   ex.printStackTrace(); }
                }
                else
                {
                    try {
                        mem_jtextarea.getHighlighter().addHighlight(startIndex, endIndex, painter_g);
                    } catch (BadLocationException ex) {   ex.printStackTrace();}

                }

            }



        });     //end of play button listener




        input.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if((input.getText().length() >= 8) || (e.getKeyChar() !='0' && e.getKeyChar() !='1') )
                {
                    e.consume();
                }
            }
        });

        reset.addActionListener(e ->
        {
            initial_registers();
            Hlt = true ;
            overwrite = false ;
            M = new String[4096] ;
            T = -1 ;
            mem_jtextarea.setText("//Memory");
            PrintWriter writer = null;
            try
            {
                writer = new PrintWriter("output.txt");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            assert writer != null;
            writer.print("");
            writer.close();

            PrintWriter errors = null;
            try
            {
                errors = new PrintWriter("errors.txt");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            assert errors != null;
            errors.print("");
            errors.close();

        });

        give_input.addActionListener(e ->
        {
            FGI = true ;
            String inp_txt = input.getText();
            jbt7.setText("0".repeat(8 - inp_txt.length()) + inp_txt);

        });



        Sc_btn.addActionListener(e ->
        {
            if(Hlt)
            {
                jLabel.setText("S <- 0");
            }
            else
            {

                if (T >= 3 && IEN && (FGI || FGO))
                    D_R = 1;

                if (T >= 0)
                    resume();

                boolean p = D[7] == 1 && I == 1 && T == 3;
                boolean r = D[7] == 1 && I == 0 && T == 3;

                if (/* (R && T ==2 )|| */ (D[0] == 1 && T == 5) || (D[1] == 1 && T == 5) || (D[2] == 1 && T == 5) || (D[3] == 1 && T == 4) || (D[4] == 1 && T == 4) || (D[5] == 1 && T == 5) || (D[6] == 1 && T == 6) || (r) || (p))  // conditions for Sc <- 0 :
                {
                    T = -1;
                }

                T++;

                //all for next clock
                p = D[7] == 1 && I == 1 && T == 3;
                r = D[7] == 1 && I == 0 && T == 3;

                get_Buses_data();

                AR[0] = ((T == 0 && !R) || (!R && T == 2) || (D[7] == 0 && I == 1 && T == 3)) ? "1" : "0";  // LD AR
                AR[1] = (D[5] == 1 && T == 4) ? "1" : "0"; // INC AR
                AR[2] = (R && T == 0) ? "1" : "0"; // CLR AR

                PC[0] = ((D[4] == 1 && T == 4) || (D[5] == 1 && T == 5)) ? "1" : "0";  // LD PC
                PC[1] = ((T == 1 && !R) || (R && T == 2) || (p && IR[2].charAt(6) == '1' && FGI) || (p && IR[2].charAt(7) == '1' && FGO) || (r && IR[2].charAt(11) == '1' && AC[3].charAt(0) == '0') || (r && IR[2].charAt(12) == '1' && AC[3].charAt(0) == '1') || (r && IR[2].charAt(14) == '1' && !E) || (D[6] == 1 && T == 6 && Integer.parseInt(DR[3], 2) == 0) || (r && IR[2].charAt(13) == '1' && Integer.parseInt(AC[3], 2) == 0)) ? "1" : "0"; // INC PC
                PC[2] = (R && T == 1) ? "1" : "0"; // CLR PC

                DR[0] = ((D[0] == 1 && T == 4) || (D[1] == 1 && T == 4) || (D[2] == 1 && T == 4) || (D[6] == 1 && T == 4)) ? "1" : "0";  // LD DR
                DR[1] = (D[6] == 1 && T == 5) ? "1" : "0"; // INC DR
                DR[2] = (false) ? "1" : "0"; // CLR DR   // yokh

                if(r && IR[2].charAt(15) == '1')
                {
                    Hlt = true ;
                    overwrite = false ;
                    M = new String[4096] ;
                    T = -1 ;
                    mem_jtextarea.setText("//Memory");
                    PrintWriter writer = null;
                    try
                    {
                        writer = new PrintWriter("output.txt");
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    assert writer != null;
                    writer.print("");
                    writer.close();

                    PrintWriter errors = null;
                    try
                    {
                        errors = new PrintWriter("errors.txt");
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    assert errors != null;
                    errors.print("");
                    errors.close();

                }

                //AC[0] = ((D[0] == 1 && T==5) || (D[1] == 1 && T==5) || (D[2]==1 && T==5 ) || (r && IR[2].charAt(6) == '1')  || (p && IR[2].charAt(4)=='1')  ||(r  && IR[2].charAt(8)=='1' ) || (r  && IR[2].charAt(9)=='1') )   ? "1" : "0" ;  // LD AC
                if (D[0] == 1 && T == 5) {
                    AC[0] = "1";     // AND
                    AC_code = 1;
                } else if (D[1] == 1 && T == 5) {
                    AC[0] = "1";
                    AC_code = 2;
                } else if (D[2] == 1 && T == 5) {
                    AC[0] = "1";
                    AC_code = 3;
                } else if (r && IR[2].charAt(6) == '1') {
                    AC[0] = "1";
                    AC_code = 4;
                } else if (r && IR[2].charAt(8) == '1') {
                    AC[0] = "1";
                    D_E = (AC[3].charAt(15) == '1') ? 1 : 0;
                    AC_code = 5;
                } else if (r && IR[2].charAt(9) == '1') {
                    AC[0] = "1";
                    D_E = (AC[3].charAt(0) == '1') ? 1 : 0;
                    AC_code = 6;
                } else if (p && IR[2].charAt(4) == '1') {
                    AC[0] = "1";
                    AC_code = 7;
                } else {
                    AC[0] = "0";
                }


                AC[1] = (r && IR[2].charAt(10) == '1') ? "1" : "0"; // INC AC
                AC[2] = (IR[2].charAt(4) == '1' && r) ? "1" : "0"; // CLR AC

                IR[0] = ((T == 1 && !R)) ? "1" : "0";  // LD IR

                TR[0] = (R && T == 0) ? "1" : "0";  // LD TR
                TR[1] = (false) ? "1" : "0"; // INC TR   //yokh
                TR[2] = (false) ? "1" : "0"; // CLR TR   //yokh

                flag_out = (p && IR[2].charAt(5) == '1') ? 1 : 0;

                if (R && T == 2) {
                    D_R = 0;
                    D_IEN = 0;
                    // resume();   // definitely wrong !
                } else if (T >= 3 && IEN && (FGI || FGO) && !IR[2].equals("1111" + "000001000000"))    // Solve main bug of original computer in book
                    D_R = 1;

                if (p && IR[2].charAt(8) == '1')                 ///////*****************
                {
                    D_IEN = 1;
                }
                if (p && IR[2].charAt(9) == '1') {
                    D_IEN = 0;
                }


                Write_Mem = (R && T == 1) || (D[3] == 1 && T == 4) || (D[5] == 1 && T == 4) || (D[6] == 1 && T == 6);

                if (r && IR[2].charAt(5) == '1') {
                    D_E = 0;
                } else if (r && IR[2].charAt(7) == '1') {
                    D_E = (D_E == 0) ? 1 : 0;
                }
                // Decode D[0:7] this will be work at next clock
                else if (!R && T == 2) {
                    for (int i = 0; i < 8; i++) {
                        D[i] = 0;  // should be reset
                    }
                    String binary = IR[2].substring(1, 4);
                    int dec = Integer.parseInt(binary, 2);
                    D[dec] = 1;
                    I = (IR[2].charAt(0) == '1') ? 1 : 0;

                } // End of Decode

            }
        }); // Action _ Listener

    } // End of constructor


    private void get_Buses_data()
    {
        boolean r = D[7] == 1 && I == 0 && T == 3;
        boolean p = D[7]==1 && I==1 && T==3 ;
        String B7 = Character.toString((IR[2].charAt(8)));
        String B6 = Character.toString(IR[2].charAt(9));
        if ((D[4]==1 && T==4)||(D[5]==1 &&T==5))
        {
            //x1 AR
            Bus_dates[0] = "AR";
            Bus_dates[1] = AR[3];   /* in registers, 3 refers to data except IR */
        }
        else if((T==0) || (D[5]==1 && T==4))
        {
            //x2 PC
            Bus_dates[0] = "PC" ;
            Bus_dates[1] = PC[3] ;
        }
        else if((D[2] ==1 && T==5) || (D[6] ==1 && T==6))
        {
            //x3 DR
            Bus_dates[0] = "DR";
            Bus_dates[1] = DR[3];
        }

        else if((D[3] ==1 && T==4 )||(p && IR[2].charAt(5) == '1') || (r && B6.equals("1")) || (r && B7.equals("1")) ) ///////
        {
            //x4 AC
            Bus_dates[0] = "AC";
            Bus_dates[1] = AC[3];

        }
        else if(!R && T==2)
        {
            //x5 IR
            Bus_dates[0] = "IR" ;
            Bus_dates[1] = IR[2];

        }
        else if(R && T==1)
        {
            //x6 TR
            Bus_dates[0] = "TR";
            Bus_dates[1] = TR[3];
        }
        else if((!R&&T==1) || (D[7]==0 && I==1 && T==3) || ((D[0]==1 || D[1]==1 || D[2]==1 ||D[6]==1) && T==4))
        {
            //x7 M[AR]
            Bus_dates[0] = "M[AR]";
            //  Bus_dates[1] = M[Integer.parseInt(AR[3])];
            Bus_dates[1] = M[Integer.parseInt(AR[3].substring(4,16) , 2)];
        }

    }
    private void set_graphic()
    {
        frame = new JFrame();
        frame.setTitle("Mano_Simple_Computer");

        JPanel panel = new JPanel();



        set_coloring_at_momemt();


        String txt_hint = " IN the name of God \n" +
                "the mano computer introduced in book , has a main bug : \n suppose that it is important for us to don,t let interrupt to come " +
                "from mem 200 to mem 300 \n so that we use IOF instruction to do this at memory 199 , but if we had interrupt \n exactly in " +
                "the time that we are executing this instruction , what will happen ? \n " +
                "we will have interrupt in next cycle and the IOF instruction at the end of interrupt subroutine service \n " +
                "will annul our IOF that we wrote it at mem 199 . this bug has been passed and trubleshooted in this program \n by And with  " +
                "not IOF instruction into another inputs of  R _flip_flop \n" +
                "pay attention that :  \n  if you want to give input to make interrupt in a embedded text_field , \n" +
                "you can just give binary numbers (0 or 1 ) other characters will be ignored \n" +
                "it is also okay giving less than 8 bit , automatically it will get up . \n" +
                "GOOD LUCK" ;

        hint_jtext_area = new JTextArea(txt_hint , 10,10);
        hint_jtext_area.setSize(300 , 300);
        // hint_jtext_area.setLineWrap(true);
        hint_jtext_area.setFont(new Font("Serif" , Font.ITALIC , 16));

        mem_jtextarea = new JTextArea("//Memory");
        mem_jtextarea.setFont(new Font("Serif" , Font.ITALIC , 14));
        mem_scroll    = new JScrollPane(mem_jtextarea);
        mem_scroll.setBounds(800,20,270,340);

        jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBounds(500,20,270,340);

        cut=new JMenuItem("cut");
        copy=new JMenuItem("copy");
        paste=new JMenuItem("paste");
        selectAll=new JMenuItem("selectAll");

        item_hint = new JMenuItem("Hint");
        show_mem  = new JMenuItem("Show_memory");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        item_hint.addActionListener(this);
        show_mem.addActionListener(this);

        mb=new JMenuBar();
        file=new JMenu("File");
        edit=new JMenu("Edit");
        help=new JMenu("Help");

        edit.add(cut);edit.add(copy);edit.add(paste);edit.add(selectAll);
        file.add(item_hint);
        file.add(show_mem);

        mb.add(file);mb.add(edit);mb.add(help);

        frame.getContentPane();

        jLabel = new JLabel("T 0...7");
        JLabel  hint_input = new JLabel("input :") ;
        Dimension S = jLabel.getPreferredSize();
        jLabel.setBounds(210, 460, S.width*8, S.height);

        //hint_input.setBounds(520 , 150 , S.width*8, S.height*8);
        // hint_input.setIcon(new ImageIcon("G:\\images.png"));

        jbt1 = new JButton("0".repeat(8));
        jbt2 = new JButton("0".repeat(8));
        jbt3 = new JButton("0".repeat(8));
        jbt4 = new JButton("0".repeat(8));
        jbt5 = new JButton("0".repeat(8));
        jbt6 = new JButton("0".repeat(8));
        jbt7 = new JButton("0".repeat(8));
        jbt8 = new JButton("0".repeat(8));
        play = new JButton();
        reset = new JButton("reset");
        reset.setFont(new Font("Serif" , Font.ITALIC , 14));


        try {
            ImageIcon imageIcon = new ImageIcon(Main.class.getResource("pu.png"));
            play.setIcon(imageIcon);
            play.setBounds(520,370,imageIcon.getIconWidth() - 65,imageIcon.getIconHeight() - 65);
        } catch (Exception e){e.printStackTrace(); }

        //         reset.setBounds(670,380,btn.width,btn.height);

        try {
            ImageIcon res_icon = new ImageIcon(Main.class.getResource("res2.png"));
            reset.setIcon(res_icon);
            reset.setBounds(700,370,res_icon.getIconWidth()   ,res_icon.getIconHeight()  );
        } catch (Exception ee){ee.printStackTrace(); }


        JButton AR_reg  = new JButton("AR");
        JButton PC_reg  = new JButton("PC");
        JButton DR_reg  = new JButton("DR");
        JButton AC_reg  = new JButton("AC");
        JButton IR_reg  = new JButton("IR");
        JButton TR_reg  = new JButton("TR");
        JButton INP_reg = new JButton("INPUT");
        JButton OUT_reg = new JButton("OUTPUT") ;


        Dimension btn = jbt1.getPreferredSize();

        input = new JTextField(8) ;
        input.setBounds(550,460,200,30);
        hint_input.setBounds(500 , 465 , S.width*2, S.height);

        Font fo = new Font("Serif", Font.BOLD, 20);
        input.setFont(fo);
        give_input = new JButton("submit" );
        give_input.setBounds(605,505,btn.width,btn.height);


        Sc_btn = new JButton("seq_counter");

        jbt1.setBounds(110,20,btn.width*4,btn.height);
        jbt1.setBackground(Color.MAGENTA);

        AR_reg.setBounds(10,20,btn.width,btn.height);
        AR_reg.setBackground(Color.magenta);

        jbt2.setBounds(110,60,btn.width*4,btn.height);
        jbt2.setBackground(Color.GREEN);

        PC_reg.setBounds(10,60,btn.width,btn.height);
        PC_reg.setBackground(Color.green);

        jbt3.setBounds(110,100,btn.width*4,btn.height);
        jbt3.setBackground(Color.orange);

        DR_reg.setBounds(10,100,btn.width,btn.height);
        DR_reg.setBackground(Color.orange);

        jbt4.setBounds(110,140,btn.width*4,btn.height);
        jbt4.setBackground(Color.CYAN);

        AC_reg.setBounds(10,140,btn.width,btn.height);
        AC_reg.setBackground(Color.cyan);

        jbt5.setBounds(110,180,btn.width*4,btn.height);
        jbt5.setBackground(Color.YELLOW);

        IR_reg.setBounds(10,180,btn.width,btn.height);
        IR_reg.setBackground(Color.yellow);

        jbt6.setBounds(110,220,btn.width*4,btn.height);
        jbt6.setBackground(Color.BLUE);

        TR_reg.setBounds(10,220,btn.width,btn.height);
        TR_reg.setBackground(Color.blue);

        jbt7.setBounds(110,260,btn.width*4,btn.height);
        //jbt7.setBackground(Color.GREEN);
        INP_reg.setBounds(10,260,btn.width,btn.height);

        jbt8.setBounds(110,300,btn.width*4,btn.height);
        jbt8.setBackground(Color.red);
        OUT_reg.setBounds(10,300,btn.width,btn.height);
        OUT_reg.setBackground(Color.red);

        Sc_btn.setBounds(35,400,btn.width,btn.height);
       /* Box box = Box.createVerticalBox();
        box.add(jbt1);
        box.add(Box.createVerticalStrut(20));
        box.add(jbt2);
        box.add(Box.createVerticalStrut(20));
        box.add(jbt3);
        box.add(Box.createVerticalStrut(20));
        box.add(jbt4);
        box.add(Box.createVerticalStrut(20));
        Dimension size = box.getPreferredSize();
        box.setBounds(15,20,size.width*4,size.height);
        */
        panel.setLayout(null);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(jbt1);
        panel.add(jbt2);
        panel.add(jbt3);
        panel.add(jbt4);
        panel.add(jbt5);
        panel.add(jbt6);
        panel.add(jbt7);
        panel.add(jbt8);
        panel.add(Sc_btn);
        try
        {
            panel.add(play);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        panel.add(reset);
        panel.add(jLabel);

        panel.add(input);
        panel.add(give_input);

        panel.add(jScrollPane);
        panel.add(mem_scroll) ;

        panel.add(AR_reg);
        panel.add(PC_reg);
        panel.add(DR_reg);
        panel.add(AC_reg);
        panel.add(IR_reg);
        panel.add(TR_reg);
        panel.add(INP_reg);
        panel.add(OUT_reg);
        panel.add(hint_input);

        frame.add(panel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(mb);
        frame.setSize(1153, 700);
        frame.setVisible(true);

        //  JOptionPane.showMessageDialog(null, jLabel);
    }

    private void set_coloring_at_momemt()
    {
        final StyleContext cont = StyleContext.getDefaultStyleContext();
        Color my_blue   = new Color(145, 27, 212) ;
        Color my_green = new Color(29, 209, 143);
        Color my_orange = new Color(255, 226, 34);

        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, my_blue);
        final AttributeSet org_end = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, my_green);
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        final AttributeSet NBR = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, my_orange);
        final AttributeSet INDIRECT = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.CYAN);


        DefaultStyledDocument doc = new DefaultStyledDocument()
        {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException
            {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after)
                {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W"))
                    {
                        if (text.substring(wordL, wordR).matches("(\\W)*(STA|LDA|AND|ADD|BUN|BSA|ISZ|CLA|CLE|CMA|CME|CIR|CIL|INC|SPA|SNA|SZA|SZE|HLT|INP|OUT|SKI|SKO|ION|IOF|,)") )
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);

                        else if (text.substring(wordL, wordR).matches("(\\W)*(ORG|org|end|END)"))
                            setCharacterAttributes(wordL, wordR - wordL, org_end, false);

                        else if (text.substring(wordL, wordR).matches("(\\W)*(i)"))
                            setCharacterAttributes(wordL, wordR - wordL, INDIRECT, false);


                        else if (text.substring(wordL, wordR).matches("(\\W)*(HEX|DEC)"))
                            setCharacterAttributes(wordL, wordR - wordL, NBR, false);

                        else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove (int offs, int len) throws BadLocationException
            {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*(private|public|protected)"))
                {
                    setCharacterAttributes(before, after - before, attr, false);
                } else
                {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };
        jTextArea = new JTextPane(doc);
        //  jTextArea.setBounds(10,30,100,100);
        jTextArea.setFont(new Font("TimesRoman" , Font.BOLD, 14));
        jTextArea.setText("// Write your code here");

    }

    private void initial_registers()
    {
        D = new int[]{0,0,0,0,0,0,0,0};
        AR = new String[]{"0" , "0" , "0" , "0".repeat(16),"AR"};
        String tt = Integer.toBinaryString(100);
        PC = new String[]{"0" , "0" , "0" , "0".repeat(16-tt.length()) + tt ,"PC"};

        DR = new String[]{"0" , "0" , "0" , "1".repeat(16),"DR"};
        AC = new String[]{"0" , "0" , "0" , "0".repeat(12)+"1".repeat(4),"AC"};
        jbt4.setText(AC[3]);
        IR = new String[]{"0","IR","0".repeat(16)}; //third is IR s data
        TR = new String[]{"0" , "0" , "0" , "0001"+"0".repeat(12),"TR"};
    }

    private void resume()
    {
        String concat = "";

        if(Write_Mem)
        {
            concat += "M[AR] <- " + Bus_dates[0] + ", " ;
            String index_Mem = AR[3].substring(4,16) ;
            M[Integer.parseInt(index_Mem , 2)] = Bus_dates[1] ;
            //  System.out.println(Integer.parseInt(index_Mem , 2));
            // System.out.println( M[Integer.parseInt(index_Mem , 2)]);
        }
        concat += Control_Registers( AR);
        concat += Control_Registers( PC);
        concat += Control_Registers( DR);
        concat += Control_Registers( AC);
        concat += Control_Registers( TR);

        concat += Control_Registers( IR);

        if(flag_out == 1)
        {
            concat += "OUTR <- " + "AC" + " ," ;
            OUTR = Bus_dates[1] .substring(8,16) ;
            jbt8.setText(OUTR);
        }
        boolean r = D[7] == 1 && I == 0 && T==3 ;
        boolean p = D[7] == 1 && I == 1 && T==3 ;

        if(p && IR[2].charAt(8) == '1')
        {
            concat += "IEN <- 1 ," ;
        }
        else if(p && IR[2].charAt(9) == '1')
        {
            concat += "IEN <- 0 ," ;
        }
        else if(r && IR[2].charAt(5) == '1')
        {
            concat += "E <- 0 ," ;
        }
        else if(r && IR[2].charAt(7) == '1')
        {
            concat += "E <- ~E ," ;
        }

        if(R && T==2)        //////////// ***************************
        {
            concat += "R <- 0 ," ;
            jLabel.setText("T" + T + " : " + concat);
            T = -1 ;
        }
        else
            jLabel.setText("T" + T + " : " + concat);

        R = (D_R == 1) ;
        IEN = (D_IEN == 1) ;
        E = (D_E == 1) ;
    }

    private String Control_Registers( String [] reg)
    {
        String concat ="" ;

        if (reg.length == 3)
        {
            // we are running with IR
            if(reg[0].equals("1")) // LD_IR
            {
                concat += ( reg[1] +" <- "+ Bus_dates[0] +  ",  ");
                reg[2] = Bus_dates[1];
                jbt5.setText(reg[2]);


            }
        }
        else  // if we are not running IR
        {
            if(reg[0].equals("1")) // LD_Reg here is not IR
            {
                if (!reg[4].equals("AC"))  // !LD AC
                {
                    concat += ( reg[4] +" <- "+Bus_dates[0] +  ",  ");

                    if(reg[4].equals("AR") && Bus_dates[0].equals("IR"))  // 16 to 12  one exception creates this
                    {
                        reg[3]="0".repeat(4);
                        reg[3]+= Bus_dates[1].substring(4,16);
                    }
                    else    // else with feragh bal assign et
                        reg[3] = Bus_dates[1] ;
                }       // end of if ! LD_AC

                else // LD_AC
                {
                    concat += "AC <- " ;
                    boolean r = D[7] == 1 && I == 0 && T == 3;
                    boolean p = D[7]==1 && I==1 && T==3 ;

                    if (AC_code == 1 )  // AND
                    {
                        StringBuilder tempo = new StringBuilder();
                        for (int i = 0 ;i < 16 ;i++)
                        {
                            if(AC[3].charAt(i) == '1' && DR[3].charAt(i) == '1')
                            {
                                tempo.append("1");
                            }
                            else
                            {
                                tempo.append("0");
                            }
                        }
                        //  long ac = Long.parseLong(AC[3]);    long  dr = Long.parseLong(DR[3]);       long   result = ac & dr ;
                        reg[3] = tempo.toString() ;
                        concat+= "AC ^ DR ,";
                    }
                    else if(AC_code == 2 ) // ADD
                    {
                        /*    int ac = Integer.parseInt(AC[3] , 2);
                            int dr = Integer.parseInt(DR[3] , 2);
                            int result = ac + dr ; // decimal form
                            String new_data = Integer.toBinaryString(result); // binary form
                        int C_out = 0 ;
                            if(new_data.length() < 16)
                            {
                                reg[3] = "0".repeat(16-new_data.length());
                                reg[3] += new_data ;
                            }
                            else if(new_data.length() == 17)
                            {
                                reg[3] = new_data.substring(1,17);
                                C_out = 1 ;
                            }
                            else // new data has exactly 16 az size
                            {
                                reg[3] = new_data ;
                            }
                            concat += "AC + DR ," ;*/
                        StringBuilder tmp = new StringBuilder();
                        char carry ='0';
                        for (int i = 0 ;i < 16 ;i++)
                        {
                            char cur_dr = DR[3].charAt(15-i) ;
                            char cur_ac = AC[3].charAt(15-i) ;
                            if((cur_dr == '1' && cur_ac == '1') || (cur_ac == '1' && carry == '1') || (cur_dr == '1' && carry =='1')  )
                            {
                                if(cur_ac == '1' && cur_dr == '1' && carry == '1')
                                {
                                    tmp.append("1");
                                }
                                else
                                {
                                    tmp.append("0");
                                }
                                carry = '1';
                            }
                            else
                            {
                                if(cur_ac == '1' || cur_dr == '1' || carry == '1' )
                                {
                                    tmp.append("1");
                                }
                                else
                                {
                                    tmp.append("0");
                                }

                                carry = '0' ;
                            }
                        }
                        E = (carry == '1') ;
                        reg[3] = tmp.reverse().toString();
                        concat += "AC + DR ," ;
                    }
                    else if(AC_code == 3 ) // AC <- DR
                    {
                        reg[3] = DR[3];
                        concat += "DR ,"  ;
                    }
                    else if(AC_code == 4 ) // Complement AC
                    {
                        StringBuilder tmp = new StringBuilder();
                        for (int i = 0; i < AC[3].length() ; i++)
                        {
                            if(AC[3].charAt(i) == '1')
                            {
                                tmp.append("0");
                            }
                            else
                            {
                                tmp.append("1");
                            }
                        }
                        reg[3] = tmp.toString();

                        concat += "~AC ,";
                    }
                    else if(AC_code == 7 )  // input galip

                    {
                        String temp = input.getText();
                        String ac = AC[3].substring(0,8);
                        String in = "0".repeat(8-temp.length()) + temp ;
                        AC[3] = ac + in ;
                        FGI = false ;

                        concat += "INPR ," ;

                    }
                    else if(AC_code == 5 )           // shift R
                    {
                        String tp = E ? "1" : "0" ;
                        D_E = (AC[3].charAt(15) == '1') ? 1 : 0 ; // abs ish oustah megdardehi olunop line 200 ishizaki!

                        AC[3] = tp +  AC[3].substring(0,15)   ;
                        concat += "E , E <- AC(0) , SHR ,";

                    }
                    else if(AC_code == 6 )           // shift L
                    {
                        String tp = E ? "1" : "0" ;
                        D_E = (AC[3].charAt(0) == '1') ? 1 : 0;   // abs ish oustah megdardehi olunop line 206 ishizaki!
                        AC[3] = AC[3].substring(1,16) + tp  ;
                        concat += "E , E <- AC(15) , SHL ,";
                    }

                } // end of LD AC
            } // end of if LD is active
            else if(reg[1].equals("1"))  // INC_Reg
            {
                concat += (reg[4] +" <-" + reg[4] + " + 1"+ ",  ");
                int cur = Integer.parseInt(reg[3] , 2) + 1 ; // to decimal and plush with 1

                String Binary = Integer.toBinaryString(cur);      // return back to binary form
                reg[3] = "0".repeat(16-Binary.length());
                reg[3] += Binary ;
                // String tmp = String.valueOf(cur);
                // reg[3] = "0".repeat(16-tmp.length());
                // reg[3] += tmp ;
            }
            else if(reg[2].equals("1"))  // CLR Reg
            {
                concat += ( reg[4] +" <- 0"+ ",  ");

                reg[3] = "0".repeat(16);
            }
            else
            {
                return concat;
            }
            switch (reg[4])
            {
                case "AR":
                    jbt1.setText(reg[3]);
                    break;

                case "PC":
                    jbt2.setText(reg[3]);
                    break;

                case "DR":
                    jbt3.setText(reg[3]);
                    break;

                case "AC":
                    jbt4.setText(reg[3]);
                    break;

                case "TR":
                    jbt6.setText(reg[3]);
                    break;
            }
        }

        return concat ;
    }


    public static void main(String args[])
    {
        Main test = new Main();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==cut)
            jTextArea.cut();
        else if(e.getSource()==paste)
            jTextArea.paste();
        else if(e.getSource()==copy)
            jTextArea.copy();
        else  if(e.getSource()==selectAll)
            jTextArea.selectAll();
        else if (e.getSource()==item_hint)
        {
            JFrame hint_frame = new JFrame("Hint");
            hint_frame.add(hint_jtext_area);
            hint_frame.setBounds(320,125,730,500);
            hint_frame.setVisible(true);
        }
        else if (e.getSource()==show_mem)
        {
            StringBuilder tf = new StringBuilder();
            for (int i = 0 ; i < M.length ; i++)
            {
                if (M[i] != null)
                    tf.append(i).append("  ").append(M[i]).append("\n");
            }



            JFrame memory_frame = new JFrame("Memory");
            JTextArea memory_jtext_area = new JTextArea(tf.toString(), 6 , 5 ) ;
            memory_jtext_area.setLineWrap(true);



            memory_jtext_area.setFont(new Font("Serif" , Font.ITALIC, 16));

            JScrollPane mem_scroll = new JScrollPane(memory_jtext_area);
            mem_scroll.setBounds(500,20,270,340);

            memory_frame.add(mem_scroll);
            memory_frame.setBounds(320,125,400,400);
            memory_frame.setVisible(true);
        }
    }

    private int findLastNonWordChar (String text, int index)
    {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index)
    {
        while (index < text.length())
        {
            if (String.valueOf(text.charAt(index)).matches("\\W"))
            {
                break;
            }
            index++;
        }
        return index;
    }
}
