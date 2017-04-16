import java.sql.*;
import java.awt.*;
import java.awt.event.*;
public class Calc_RegLog implements ActionListener
{
    Frame f1=new Frame();
    Button b1=new Button("Open Smart Themeable Calculator");
    String l[]={"WELCOME TO SMART THEMEABLE CALCULATOR","Login or Register to Proceed","Email Address:","Password:","Username:","Email Address:","Password:","Confirm Password:","|","|","|","|","|","|","|","|","|"};
    String t[]={"","","","","",""}; //textfield
    String b[]={"Login","Register"};
    String l1[]={"You are now logged in","You are registered","Registration not possible","Login not possible","Invalid email or password"};
    Label labels[]=new Label[l.length];
    Label status[]=new Label[l1.length];
    TextField text[]=new TextField[t.length];
    Button buttons[]=new Button[b.length];
    static final String JDBC_DRIVERNAME= "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/reg";
    static final String USER = "root";
    static final String PASS = "root";
    Calc_RegLog()
    {
        for(int i=0;i<l.length;i++) //for labels
        {
            labels[i]=new Label(l[i]);
            f1.add(labels[i]);
            labels[i].setForeground(Color.white);
            if(i==0)
                labels[i].setFont(new Font("serif", Font.BOLD, 18));
            else if(i==1)
                labels[i].setFont(new Font("serif", Font.BOLD, 16));
            else if(i>1 && i<8)
                labels[i].setFont(new Font("serif", Font.BOLD, 13));
            else if(i>=8)
                labels[i].setFont(new Font("serif",Font.PLAIN,30));
        }
        for(int i=0;i<t.length;i++) //for textfields
        {
            text[i]=new TextField(t[i]);
            f1.add(text[i]);
            text[i].setFont(new Font("serif", Font.BOLD, 13));
            text[i].setForeground(Color.white);
            text[i].setBackground(Color.darkGray);
            if(i==1 || i==4 || i==5) //passwords
                text[i].setEchoChar('*');
        }
        for(int i=0;i<b.length;i++) //for buttons
        {
            buttons[i]=new Button(b[i]);
            f1.add(buttons[i]);
            buttons[i].addActionListener(this);
            buttons[i].setFont(new Font("serif", Font.BOLD, 13));
            buttons[i].setBackground(Color.cyan.darker());
        }
        for(int i=0;i<l1.length;i++) //for status labels
        {
            status[i]=new Label(l1[i]);
            status[i].setFont(new Font("serif", Font.BOLD, 13));
            status[i].setForeground(Color.white);
        }
        f1.setLayout(new FlowLayout());
        f1.setLayout(null);
        labels[0].setBounds(60,40,525,55);
        labels[1].setBounds(170,80,290,55);
        //Login
        labels[2].setBounds(12,138,107,60);
        labels[3].setBounds(35,178,90,60);
        text[0].setBounds(125,155,148,20); //login mail
        text[1].setBounds(125,195,148,20); //login password
        buttons[0].setBounds(85,240,60,40);
        //Registration
        labels[4].setBounds(360,120,90,60);
        labels[5].setBounds(340,155,107,60);
        labels[6].setBounds(360,190,90,60);
        labels[7].setBounds(310,225,135,60);
        text[2].setBounds(460,138,148,20); //registration username
        text[3].setBounds(460,173,148,20); //registration mail
        text[4].setBounds(460,208,148,20); //registration password
        text[5].setBounds(460,243,148,20); //registration password confirm
        buttons[1].setBounds(390,290,80,40);
        //Mid line
        labels[8].setBounds(280,120,30,34);
        labels[9].setBounds(280,145,30,34);
        labels[10].setBounds(280,170,30,34);
        labels[11].setBounds(280,195,30,34);
        labels[12].setBounds(280,220,30,34);
        labels[13].setBounds(280,245,30,34);
        labels[14].setBounds(280,270,30,34);
        labels[15].setBounds(280,295,30,34);
        labels[16].setBounds(280,320,30,34);

        f1.setVisible(true);
        f1.setSize(650,400);
        f1.setTitle("Smart Themeable Calculator Login/Register Page");
        f1.setBackground(Color.gray.darker());
        f1.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });

    }
    public void actionPerformed(ActionEvent e)
    {
        Connection con=null;
        Statement stmt=null;
        int i,flag=0;
        String sql,s1,s2,s3,s4,s5,s6,s7;
        //Registration
        if(e.getSource()==buttons[1])
        {
            try
            {
                Class.forName(JDBC_DRIVERNAME);
                con=DriverManager.getConnection(DB_URL,USER,PASS);
                stmt=con.createStatement();
                s1=text[2].getText();
                s2=text[3].getText();
                s3=text[4].getText();
                s4=text[5].getText();
                //Password and confirmed password should match
                if(!s3.equals(""))
                {
                    if(s3.equals(s4))
                    {
                        sql="insert into registration "+"values('"+s1+"','"+s2+"','"+s3+"','"+s4+"')";
                        stmt.executeUpdate(sql);
                        for(i=1;i<=2;i++)
                            status[i].setVisible(false);
                        f1.add(status[1]);
                        status[1].setBounds(310,330,200,40);
                        status[1].setVisible(true);
                    }
                    else
                    {
                        for(i=1;i<=2;i++)
                            status[i].setVisible(false);
                        f1.add(status[2]);
                        status[2].setBounds(330,330,200,40);
                        status[2].setVisible(true);
                    }
                }
                else
                {
                    for(i=1;i<=2;i++)
                        status[i].setVisible(false);
                    f1.add(status[2]);
                    status[2].setBounds(330,330,200,40);
                    status[2].setVisible(true);
                }
                for(i=2;i<=5;i++)
                        text[i].setText("");
                con.close();
                stmt.close();
            }
            catch(SQLException se)
            {
                f1.add(status[2]);
                status[2].setBounds(330,330,200,40);
                for(i=2;i<=5;i++)
                        text[i].setText("");
            }
            catch(Exception e1){}
        }
        //Login
        if(e.getSource()==buttons[0])
        {
            try
            {
                Class.forName(JDBC_DRIVERNAME);
                con=DriverManager.getConnection(DB_URL,USER,PASS);
                stmt=con.createStatement();
                flag=0;
                s5=text[0].getText();
                sql="select password from registration "+"where email='"+s5+"'";
                ResultSet rs=stmt.executeQuery(sql);
                s6=text[1].getText();
                while(rs.next())
                {
                    s7=rs.getString("password");
                    if(s7.equals(s6))
                    {
                        //removing other labels
                        for(i=0;i<=4;i++)
                        {
                            if(i==3 || i==4)
                                status[i].setVisible(false);
                        }
                        f1.add(status[0]);
                        status[0].setBounds(50,300,170,40);
                        status[0].setVisible(true);
                        flag=1;
                    }
                }
                //if email or password is wrong
                if(flag==0)
                {
                    //removing other labels
                    for(i=0;i<=4;i++)
                    {
                        if(i==0 || i==3)
                            status[i].setVisible(false);
                    }
                    f1.add(status[4]);
                    status[4].setBounds(45,300,190,40);
                    status[4].setVisible(true);
                }
                else
                {
                    f1.add(b1);
                    b1.setFont(new Font("serif", Font.BOLD, 13));
                    b1.setBackground(Color.cyan.darker());
                    b1.setBounds(10,340,270,40);
                    b1.setVisible(true);
                    b1.addActionListener(this);
                }
                for(i=0;i<=1;i++)
                    text[i].setText("");
                rs.close();
                con.close();
                stmt.close();
            }
            catch(SQLException se1)
            {
                //removing other labels
                for(i=0;i<=4;i++)
                {
                    if(i==0 || i==4)
                        status[i].setVisible(false);
                }
                f1.add(status[3]);
                status[3].setBounds(40,300,190,40);
                status[3].setVisible(true);
                for(i=0;i<=1;i++)
                    text[i].setText("");
            }
            catch(Exception e2){}
        }
        //opening STC
        if(e.getSource()==b1)
        {
            Calculator c=new Calculator();
            //f.setVisible(true);
        }
    }
    public static void main(String[] args)
    {
        Calc_RegLog cr=new Calc_RegLog();
    }
}
class Calculator implements ActionListener
{
    int ch;
    double ans;
    String s1,s2,s3,s4;
    String dop[]={"C","=","Mod","√","log","7","8","9","/","ln","4","5","6","✕","sin","1","2","3","+","cos","0",".","^","-","tan"};
    Frame f=new Frame("Smart Themeable calculator");
    Button digitop[]=new Button[dop.length];
    Panel p=new Panel();
    TextField tf=new TextField(18);
    GridLayout g=new GridLayout(5,5,10,10);
    Calculator()
    {
        for(int i=0;i<dop.length;i++)
        {
            digitop[i]=new Button(dop[i]);
            digitop[i].setForeground(Color.white);
            digitop[i].setBackground(Color.darkGray);
            p.add(digitop[i]);
            digitop[i].addActionListener(this);
        }
        f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });
        tf.setForeground(Color.white);
        tf.setBackground(Color.darkGray);
        tf.setFont(new Font("serif", Font.BOLD, 18));
        p.setLayout(g);
        f.setLayout(new FlowLayout());
        f.add(tf);
        f.add(p);
        f.setSize(300,300);
        f.setBackground(Color.gray);
        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        int i;
        for(i=0;i<dop.length;i++)
        {
            if(e.getSource()==digitop[i])
            {
                if(i!=0 && i!=1 && i!=2 && i!=3 && i!=4 && i!=8 && i!=9 && i!=13 && i!=14 && i!=18 && i!=19 && i!=22 && i!=23 && i!=24)
                {
                    s3=tf.getText();
                    s4=s3+dop[i];
                    tf.setText(s4);
                }
                else if(i!=1)
                {
                    s1=tf.getText();
                    tf.setText("");
                    ch=i;
                }
                else if(i==1)
                {
                    switch(ch)
                    {
                        case 2: s2=tf.getText();
                                ans=Double.parseDouble(s1) % Double.parseDouble(s2);
                                break;
                        case 3: ans=Math.sqrt(Double.parseDouble(s1));
                                break;
                        case 4: ans=Math.log10(Double.parseDouble(s1));
                                break;
                        case 8: s2=tf.getText();
                                ans=Double.parseDouble(s1) / Double.parseDouble(s2);
                                break;
                        case 9: ans=Math.log(Double.parseDouble(s1));
                                break;
                        case 13:    s2=tf.getText();
                                    ans=Double.parseDouble(s1) * Double.parseDouble(s2);
                                    break;
                        case 14:    ans=Math.sin(Double.parseDouble(s1));
                                    break;
                        case 18:    s2=tf.getText();
                                    ans=Double.parseDouble(s1) + Double.parseDouble(s2);
                                    break;
                        case 19:    ans=Math.cos(Double.parseDouble(s1));
                                    break;
                        case 22:    s2=tf.getText();
                                    ans=Math.pow(Double.parseDouble(s1) , Double.parseDouble(s2));
                                    break;
                        case 23:    s2=tf.getText();
                                    ans=Double.parseDouble(s1) - Double.parseDouble(s2);
                                    break;
                        case 24:    ans=Math.tan(Double.parseDouble(s1));
                                    break;
                    }
                    tf.setText(Double.toString(ans));
                }
            }
        }
    }
}

