import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Calc_RegLog implements ActionListener
{
    Frame f1 = new Frame();
    Button b1 = new Button("Open Smart Themeable Calculator");

    String l[] = {
        "WELCOME TO SMART THEMEABLE CALCULATOR",
        "Login or Register to Proceed",
        "Email Address:",
        "Password:",
        "Username:",
        "Email Address:",
        "Password:",
        "Confirm Password:",
        "|", "|", "|", "|", "|", "|", "|", "|", "|"
    };

    String t[] = {"", "", "", "", "", ""}; // textfield

    String b[] = {
        "Login",
        "Register"
    };

    String l1[] = {
        "You are now logged in",
        "You are registered",
        "Registration not possible",
        "Login not possible",
        "Invalid email or password"
    };

    Label labels[] = new Label[l.length];
    Label status[] = new Label[l1.length];

    TextField text[] = new TextField[t.length];

    Button buttons[] = new Button[b.length];

    // Driver class for mysql is 'com.mysql.jdbc.Driver'
    static final String JDBC_DRIVERNAME = "com.mysql.jdbc.Driver";
    /* Connection URL for the mysql database is 'jdbc:mysql://localhost/reg'
        where
            'mysql' is the database
            'localhost' is the server name on which mysql is running
            'reg' is the database name
    */
    static final String DB_URL = "jdbc:mysql://localhost/reg";
    // username and password predefined as 'root'
    static final String USER = "root";
    static final String PASS = "root";

    Calc_RegLog()
    {
        int y = 120;
        for(int i = 0; i < l.length; i++) // for labels
        {
            labels[i] = new Label(l[i]);

            f1.add(labels[i]);

            labels[i].setForeground(Color.white);

            if(i == 0)
                labels[i].setFont(new Font("serif", Font.BOLD, 18));
            else if(i == 1)
                labels[i].setFont(new Font("serif", Font.BOLD, 16));
            else if(i > 1 && i < 8)
                labels[i].setFont(new Font("serif", Font.BOLD, 13));
            else if(i >= 8)
            {
                labels[i].setFont(new Font("serif", Font.PLAIN, 30));
                labels[i].setBounds(280, y, 30, 34); // for mid line
                y += 25;
            }
        }

        for(int i = 0; i < t.length; i++) // for textfields
        {
            text[i] = new TextField(t[i]);

            f1.add(text[i]);

            text[i].setFont(new Font("serif", Font.BOLD, 13));
            text[i].setForeground(Color.white);
            text[i].setBackground(Color.darkGray);

            if(i == 1 || i == 4 || i == 5) // passwords
                text[i].setEchoChar('*');
        }

        for(int i = 0; i < b.length; i++) // for buttons
        {
            buttons[i] = new Button(b[i]);

            f1.add(buttons[i]);

            buttons[i].addActionListener(this);
            buttons[i].setFont(new Font("serif", Font.BOLD, 13));
            buttons[i].setBackground(Color.cyan.darker());
        }

        for(int i = 0; i < l1.length; i++) // for status labels
        {
            status[i] = new Label(l1[i]);
            status[i].setFont(new Font("serif", Font.BOLD, 13));
            status[i].setForeground(Color.white);
        }

        f1.setLayout(null);

        labels[0].setBounds(60, 40, 525, 55);
        labels[1].setBounds(170, 80, 290, 55);

        // Login
        labels[2].setBounds(12, 138, 107, 60);
        labels[3].setBounds(35, 178, 90, 60);
        text[0].setBounds(125, 155, 148, 20); // login mail
        text[1].setBounds(125, 195, 148, 20); // login password
        buttons[0].setBounds(85, 240, 60, 40);

        // Registration
        labels[4].setBounds(360, 120, 90, 60);
        labels[5].setBounds(340, 155, 107, 60);
        labels[6].setBounds(360, 190, 90, 60);
        labels[7].setBounds(310, 225, 135, 60);
        text[2].setBounds(460, 138, 148, 20); // registration username
        text[3].setBounds(460, 173, 148, 20); // registration mail
        text[4].setBounds(460, 208, 148, 20); // registration password
        text[5].setBounds(460, 243, 148, 20); // registration password confirm
        buttons[1].setBounds(390, 290, 80, 40);

        f1.setVisible(true);
        f1.setSize(650, 400);
        f1.setTitle("Smart Themeable Calculator Login/Register Page");
        f1.setBackground(Color.gray.darker());
        f1.addWindowListener(new WindowAdapter()
        {
            // invoked when a window has been closed
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e)
    {
        Connection con = null;
        // Avoiding SQL injection attacks
        // Query and data are sent to SQL database separately
        PreparedStatement p = null;
        ResultSet rs = null;

        int i, flag = 0;

        String sql, s1, s2, s3, s4, s5, s6, s7;

        // Registration
        if(e.getSource() == buttons[1])
        {
            try
            {
                /*  to return the Class object for the class with the
                    specified name */
                Class.forName(JDBC_DRIVERNAME);

                /*  to establish connection with the specified url,
                    username and password */
                con = DriverManager.getConnection(DB_URL, USER, PASS);

                // passing parameter (?) for the values
                p = con.prepareStatement("insert into registration " +
                                            "values(?, ?, ?, ?)");

                s1 = text[2].getText();
                s2 = text[3].getText();
                s3 = text[4].getText();
                s4 = text[5].getText();

                // Password and confirmed password should match
                if(!s3.equals(""))
                {
                    if(s3.equals(s4))
                    {
                        // 1 specifies setting 1st parameter in the query
                        p.setString(1, s1);
                        p.setString(2, s2);
                        p.setString(3, s3);
                        p.setString(4, s4);
                        int k = p.executeUpdate();

                        for(i = 1; i <= 2; i++)
                            status[i].setVisible(false);

                        f1.add(status[1]);

                        status[1].setBounds(310, 330, 200, 40);
                        status[1].setVisible(true);
                    }
                    else
                    {
                        for(i = 1; i <= 2; i++)
                            status[i].setVisible(false);

                        f1.add(status[2]);

                        status[2].setBounds(330, 330, 200, 40);
                        status[2].setVisible(true);
                    }
                }
                else
                {
                    for(i = 1; i <= 2; i++)
                        status[i].setVisible(false);

                    f1.add(status[2]);

                    status[2].setBounds(330, 330, 200, 40);
                    status[2].setVisible(true);
                }
                for(i = 2; i <= 5; i++)
                    text[i].setText("");

                p.close();
                con.close();
            }
            catch(SQLException se)
            {
                f1.add(status[2]);

                status[2].setBounds(330, 330, 200, 40);

                for(i = 2; i <= 5; i++)
                    text[i].setText("");

                se.printStackTrace();
            }
            catch(Exception e1)
            {
                e1.printStackTrace();
            }
        }

        // Login
        if(e.getSource() == buttons[0])
        {
            try
            {
                Class.forName(JDBC_DRIVERNAME);

                con = DriverManager.getConnection(DB_URL, USER, PASS);

                flag = 0;

                s5 = text[0].getText();

                p=con.prepareStatement("select password from registration " +
                                            "where email = ?");

                p.setString(1, s5);

                // executes the select query
                rs = p.executeQuery();

                s6 = text[1].getText();

                while(rs.next())
                {
                    s7 = rs.getString("password");

                    if(s7.equals(s6))
                    {
                        // removing other labels
                        for(i = 0; i <= 4; i++)
                        {
                            if(i == 3 || i == 4)
                                status[i].setVisible(false);
                        }
                        f1.add(status[0]);
                        status[0].setBounds(50, 300, 170, 40);
                        status[0].setVisible(true);
                        flag = 1;
                    }
                }

                // if email or password is wrong
                if(flag == 0)
                {
                    // removing other labels
                    for(i = 0; i <= 4; i++)
                    {
                        if(i == 0 || i == 3)
                            status[i].setVisible(false);
                    }
                    f1.add(status[4]);

                    status[4].setBounds(45, 300, 190, 40);
                    status[4].setVisible(true);
                    // Removing access to STC button
                    b1.setVisible(false);
                }
                else
                {
                    f1.add(b1);

                    b1.setFont(new Font("serif", Font.BOLD, 13));
                    b1.setBackground(Color.cyan.darker());
                    b1.setBounds(10, 340, 270, 40);
                    b1.setVisible(true);
                    b1.addActionListener(this);
                }

                for(i = 0; i <= 1; i++)
                    text[i].setText("");

                rs.close();
                p.close();
                con.close();
            }
            catch(SQLException se1)
            {
                // removing other labels
                for(i = 0; i <= 4; i++)
                {
                    if(i == 0 || i == 4)
                        status[i].setVisible(false);
                }

                f1.add(status[3]);

                status[3].setBounds(40, 300, 190, 40);
                status[3].setVisible(true);

                for(i = 0; i <= 1; i++)
                    text[i].setText("");

                se1.printStackTrace();
            }
            catch(Exception e2)
            {
                e2.printStackTrace();
            }
        }

        // opening Smart Themeable Calculator
        if(e.getSource() == b1)
        {
            Calculator c = new Calculator();
        }
    }

    public static void main(String[] args)
    {
        Calc_RegLog cr = new Calc_RegLog();
    }
}

class Calculator implements ActionListener
{
    int ch;
    double ans;

    String s1, s2, s3, s4;

    String dop[] = {
        "C", "=", "Mod", "√", "log",
        "7", "8", "9", "/", "ln",
        "4", "5", "6", "✕", "sin",
        "1", "2", "3", "+", "cos",
        "0", ".", "^", "-", "tan"
    };

    String u[] = {
        "Length",
        "Temperature",
        "Base"
    };

    String lib[] = {
        "Constants",
        "Formulae",
        "Trignometric Help"
    };

    String th[] = {
        "Scarlet",
        "Azure",
        "Forest",
        "Shadow"
    };

    String c[] = {
        "π",
        "e",
        "Ω",
        "i",
        "h",
        "G",
        "c",
        ""
    };

    String cname[] = {
        "pi",
        "Euler's Number",
        "Omega",
        "Imaginary unit",
        "Planck’s constant",
        "Gravitational constant",
        "Speed of light",
        ""
    };

    String cvalue[] = {
        "3.14159",
        "2.71828",
        "0.56714",
        "√-1",
        "6.62607 x 10⁻³⁴ Js",
        "6.67408 x 10⁸ Nm²/kg²",
        "3 x 10⁸ m/s",
        ""
    };

    String formulas[] = {
        "AREAS: ",
        "Area of circle = π * (radius) * (radius)",
        "Area of square = (side) * (side)",
        "Area of rectangle = (length) * (breadth)",
        "Area of rectangle = 1/2 * (base) * (height)",
        "TRIGONOMETRIC FORMULAS:",
        "tan²θ + 1 = sec²θ",
        "cot²θ + 1 = cosec²θ",
        "sin²θ + cos²θ = 1",
        "LOGARITHMS:",
        "log(a * b) = log(a) + log(b)",
        "log(a / b) = log(a) - log(b)",
        ""
    };

    String trig[] = {
        "Angle in degree",
        "sin",
        "cos",
        "tan",
        "cosec",
        "sec",
        "cot",
        ""
    };

    String trig_0[] = {
        "0",
        "0",
        "1",
        "0",
        "not defined",
        "1",
        "not defined",
        ""
    };

    String trig_30[] = {
        "30",
        "0.5",
        "0.866",
        "0.577",
        "2",
        "1.154",
        "1.732",
        ""
    };

    String trig_45[] = {
        "45",
        "0.707",
        "0.707",
        "1",
        "1.414",
        "1.414",
        "1",
        ""
    };

    String trig_60[] = {
        "60",
        "0.866",
        "0.5",
        "1.732",
        "1.154",
        "2",
        "0.577",
        ""
    };

    String trig_90[] = {
        "90",
        "1",
        "0",
        "not defined",
        "1",
        "not defined",
        "0",
        ""
    };

    // Calculator frame
    Frame f = new Frame("Smart Themeable calculator");
    // Constants' Library Frame
    Frame fc = new Frame("Constants' Library");
    // Formulas' Library Frame
    Frame ff = new Frame("Formulas' Library");
    // Trigonometric Table Frame
    Frame ft = new Frame("Trigonometric Table");

    // Length Conversion Frame
    Frame flc = new Frame("Length Conversion");
    // Temperature Conversion Frame
    Frame ftc = new Frame("Temperature Conversion");
    // Base Conversion Frame
    Frame fbc = new Frame("Base Conversion");

    // For constants library
    Label lc1[] = new Label[c.length]; // constants
    Label lc2[] = new Label[cname.length]; // constants' names
    Label lc3[] = new Label[cvalue.length]; // constants' values

    // For trigonometric table
    Label lt1[] = new Label[trig.length]; // trigo functions
    Label lt2[] = new Label[trig_0.length]; // angles
    Label lt3[] = new Label[trig_30.length]; // angles
    Label lt4[] = new Label[trig_45.length]; // angles
    Label lt5[] = new Label[trig_60.length]; // angles
    Label lt6[] = new Label[trig_90.length]; // angles

    // For formulas library
    Label lf1[] = new Label[formulas.length]; // formulas

    // Conversion Label for Length
    Label llc = new Label("Select units for conversion");
    // Conversion Label for Temperature
    Label ltc = new Label("Select units for conversion");
    // Conversion Label for Base
    Label lbc = new Label("Select units for conversion");

    // Length options
    Choice len_enter = new Choice(); // length to enter
    Choice len_ans = new Choice(); // length answer

    // Temperature options
    Choice temp_enter = new Choice(); // temp to enter
    Choice temp_ans = new Choice(); // temp answer

    // Base options
    Choice base_enter = new Choice(); // base to enter
    Choice base_ans = new Choice(); // base answer

    // Length conversion button
    Button convert_len_button = new Button("Convert");
    // Temperature conversion button
    Button convert_temp_button = new Button("Convert");
    // Base conversion button
    Button convert_base_button = new Button("Convert");

    Button digitop[] = new Button[dop.length];

    Panel p = new Panel();

    // Calc display
    TextField tf = new TextField(18);

    // Length Enter display
    TextField text_len_enter = new TextField(10);
    // Length Ans display
    TextField text_len_ans = new TextField(10);

    // Temperature Enter display
    TextField text_temp_enter = new TextField(10);
    // Temperature Ans display
    TextField text_temp_ans = new TextField(10);

    // Base Enter display
    TextField text_base_enter = new TextField(10);
    // Base Ans display
    TextField text_base_ans = new TextField(10);

    GridLayout g = new GridLayout(5, 5, 10, 10);

    MenuBar menuBar = new MenuBar();

    Menu unit = new Menu("Unit Conversion");
    Menu library = new Menu("Libraries");
    Menu themes = new Menu("Themes");

    MenuItem unitlist[] = new MenuItem[u.length];
    MenuItem liblist[] = new MenuItem[lib.length];
    MenuItem themelist[] = new MenuItem[th.length];

    Calculator()
    {
        // operator and digit buttons
        for(int i = 0; i < dop.length; i++)
        {
            digitop[i] = new Button(dop[i]);
            digitop[i].setForeground(Color.white);
            digitop[i].setBackground(Color.darkGray);

            p.add(digitop[i]);

            digitop[i].addActionListener(this);
        }

        // unit menu
        for(int i = 0; i < u.length; i++)
        {
            unitlist[i] = new MenuItem(u[i]);
            unit.add(unitlist[i]);

            unitlist[i].addActionListener(this);
        }

        // library menu
        for(int i = 0; i < lib.length; i++)
        {
            liblist[i] = new MenuItem(lib[i]);
            library.add(liblist[i]);

            liblist[i].addActionListener(this);
        }

        // theme menu
        for(int i = 0; i < th.length; i++)
        {
            themelist[i]=new MenuItem(th[i]);
            themes.add(themelist[i]);

            themelist[i].addActionListener(this);
        }

        int y = 20;
        // constant labels in constants' library
        for(int i = 0; i < c.length; i++)
        {
            lc1[i] = new Label(c[i]);
            y += 40;
            lc1[i].setBounds(30, y, 20, 20);
            lc1[i].setFont(new Font("default", Font.BOLD, 13));
            fc.add(lc1[i]);
        }

        y = 6;
        // constant name labels in constants' library
        for(int i = 0; i < cname.length; i++)
        {
            lc2[i] = new Label(cname[i]);
            y += 40;
            lc2[i].setBounds(70, y, 170, 50);
            lc2[i].setFont(new Font("default", Font.BOLD, 13));
            fc.add(lc2[i]);
        }

        y = 6;
        // constant value labels in constants' library
        for(int i = 0; i < cvalue.length; i++)
        {
            lc3[i] = new Label(cvalue[i]);
            y += 40;
            lc3[i].setBounds(260, y, 220, 50);
            lc3[i].setFont(new Font("default", Font.BOLD, 13));
            fc.add(lc3[i]);
        }

        y = 6;
        // formula labels in formulas' library
        for(int i = 0; i < formulas.length; i++)
        {
            lf1[i] = new Label(formulas[i]);
            y += 40;
            lf1[i].setBounds(30, y, 340, 50);
            lf1[i].setFont(new Font("default", Font.BOLD, 13));
            ff.add(lf1[i]);
        }

        y = 6;
        // trigo labels in trigonometric table
        for(int i = 0; i < trig.length; i++)
        {
            lt1[i] = new Label(trig[i]);
            y += 40;
            lt1[i].setBounds(30, y, 140, 50);
            lt1[i].setFont(new Font("default", Font.BOLD, 13));
            ft.add(lt1[i]);
        }

        y = 6;
        // 0 degree labels in trigonometric table
        for(int i = 0; i < trig_0.length; i++)
        {
            lt2[i] = new Label(trig_0[i]);
            y += 40;
            lt2[i].setBounds(170, y, 90, 50);
            lt2[i].setFont(new Font("default", Font.BOLD, 13));
            ft.add(lt2[i]);
        }

        y = 6;
        // 30 degree labels in trigonometric table
        for(int i = 0; i < trig_30.length; i++)
        {
            lt3[i] = new Label(trig_30[i]);
            y += 40;
            lt3[i].setBounds(290, y, 90, 50);
            lt3[i].setFont(new Font("default", Font.BOLD, 13));
            ft.add(lt3[i]);
        }

        y = 6;
        // 45 degree labels in trigonometric table
        for(int i = 0; i < trig_45.length; i++)
        {
            lt4[i] = new Label(trig_45[i]);
            y += 40;
            lt4[i].setBounds(390, y, 90, 50);
            lt4[i].setFont(new Font("default", Font.BOLD, 13));
            ft.add(lt4[i]);
        }

        y = 6;
        // 60 degree labels in trigonometric table
        for(int i = 0; i < trig_60.length; i++)
        {
            lt5[i] = new Label(trig_60[i]);
            y += 40;
            lt5[i].setBounds(480, y, 90, 50);
            lt5[i].setFont(new Font("default", Font.BOLD, 13));
            ft.add(lt5[i]);
        }

        y = 6;
        // 90 degree labels in trigonometric table
        for(int i = 0; i < trig_90.length; i++)
        {
            lt6[i] = new Label(trig_90[i]);
            y += 40;
            lt6[i].setBounds(570, y, 90, 50);
            lt6[i].setFont(new Font("default", Font.BOLD, 13));
            ft.add(lt6[i]);
        }

        // For Length Conversion
        // choice in length enter
        len_enter.add("metre");
        len_enter.add("centimetre");
        len_enter.add("kilometre");
        len_enter.add("feet");
        len_enter.add("inch");

        // choice in length ans
        len_ans.add("metre");
        len_ans.add("centimetre");
        len_ans.add("kilometre");
        len_ans.add("feet");
        len_ans.add("inch");

        // Label in length conversion
        llc.setBounds(150, 60, 290, 50);
        llc.setFont(new Font("serif", Font.BOLD, 16));
        flc.add(llc);

        // Length enter choice in length conversion
        len_enter.setBounds(100, 180, 130, 20);
        len_enter.setBackground(Color.darkGray.darker());
        flc.add(len_enter);

        // Length ans choice in length conversion
        len_ans.setBounds(300, 180, 130, 20);
        len_ans.setBackground(Color.darkGray.darker());
        flc.add(len_ans);

        // Length enter textbox in length conversion
        text_len_enter.setBounds(100, 155, 130, 20);
        flc.add(text_len_enter);

        // Length ans textbox in length conversion
        text_len_ans.setBounds(300, 155, 130, 20);
        flc.add(text_len_ans);

        // convert button in length conversion
        convert_len_button.setBounds(220, 240, 100, 30);
        convert_len_button.setBackground(Color.darkGray.darker());

        convert_len_button.addActionListener(new ActionListener()
        {
            String s5, s6, s7;
            double num1 = 0, num2 = 0;
            public void actionPerformed(ActionEvent e)
            {
                s5 = text_len_enter.getText();
                num1 = Double.parseDouble(s5);
                s6 = len_enter.getItem(len_enter.getSelectedIndex());
                s7 = len_ans.getItem(len_ans.getSelectedIndex());
                if(s6.equals("metre"))
                {
                    if(s7.equals("metre"))
                        num2 = num1 * 1;
                    else if(s7.equals("centimetre"))
                        num2 = num1 * 100;
                    else if(s7.equals("kilometre"))
                        num2 = num1 * 0.001;
                    else if(s7.equals("feet"))
                        num2 = num1 * 3.28084;
                    else if(s7.equals("inch"))
                        num2 = num1 * 39.3701;
                }
                else if(s6.equals("centimetre"))
                {

                    if(s7.equals("metre"))
                        num2 = num1 * 0.01;
                    else if(s7.equals("centimetre"))
                        num2 = num1 * 1;
                    else if(s7.equals("kilometre"))
                        num2 = num1 * 0.00001;
                    else if(s7.equals("feet"))
                        num2 = num1 * 0.0328084;
                    else if(s7.equals("inch"))
                        num2 = num1 * 0.3937008;
                }
                else if(s6.equals("kilometre"))
                {
                    if(s7.equals("metre"))
                        num2 = num1 * 1000;
                    else if(s7.equals("centimetre"))
                        num2 = num1 * 100000;
                    else if(s7.equals("kilometre"))
                        num2 = num1 * 1;
                    else if(s7.equals("feet"))
                        num2 = num1 * 3280.84;
                    else if(s7.equals("inch"))
                        num2 = num1 * 39370.08;
                }
                else if(s6.equals("feet"))
                {
                    if(s7.equals("metre"))
                        num2 = num1 * 0.3048;
                    else if(s7.equals("centimetre"))
                        num2 = num1 * 30.48;
                    else if(s7.equals("kilometre"))
                        num2 = num1 * 0.0003048;
                    else if(s7.equals("feet"))
                        num2 = num1 * 1;
                    else if(s7.equals("inch"))
                        num2 = num1 * 12;
                }
                else if(s6.equals("inch"))
                {
                    if(s7.equals("metre"))
                        num2 = num1 * 0.0254;
                    else if(s7.equals("centimetre"))
                        num2 = num1 * 2.54;
                    else if(s7.equals("kilometre"))
                        num2 = num1 * 0.0000254;
                    else if(s7.equals("feet"))
                        num2 = num1 * 0.0833333;
                    else if(s7.equals("inch"))
                        num2 = num1 * 1;
                }
                text_len_ans.setText(Double.toString(num2));
            }
        });
        flc.add(convert_len_button);


        // For Temperature Conversion
        // choice in temp enter
        temp_enter.add("kelvin");
        temp_enter.add("fahrenheit");
        temp_enter.add("celsius");

        // choice in temp ans
        temp_ans.add("kelvin");
        temp_ans.add("fahrenheit");
        temp_ans.add("celsius");

        // Label in temp conversion
        ltc.setBounds(150, 60, 290, 50);
        ltc.setFont(new Font("serif", Font.BOLD, 16));
        ftc.add(ltc);

        // temp enter choice in temp conversion
        temp_enter.setBounds(100, 180, 130, 20);
        temp_enter.setBackground(Color.darkGray.darker());
        ftc.add(temp_enter);

        // temp ans choice in temp conversion
        temp_ans.setBounds(300, 180, 130, 20);
        temp_ans.setBackground(Color.darkGray.darker());
        ftc.add(temp_ans);

        // temp enter textbox in temp conversion
        text_temp_enter.setBounds(100, 155, 130, 20);
        ftc.add(text_temp_enter);

        // temp ans textbox in temp conversion
        text_temp_ans.setBounds(300, 155, 130, 20);
        ftc.add(text_temp_ans);

        // convert button in temp conversion
        convert_temp_button.setBounds(220, 240, 100, 30);
        convert_temp_button.setBackground(Color.darkGray.darker());

        convert_temp_button.addActionListener(new ActionListener()
        {
            String s5, s6, s7;
            double num1 = 0, num2 = 0;
            public void actionPerformed(ActionEvent e)
            {
                s5 = text_temp_enter.getText();
                num1 = Double.parseDouble(s5);
                s6 = temp_enter.getItem(temp_enter.getSelectedIndex());
                s7 = temp_ans.getItem(temp_ans.getSelectedIndex());
                if(s6.equals("kelvin"))
                {
                    if(s7.equals("kelvin"))
                        num2 = num1 * 1;
                    else if(s7.equals("fahrenheit"))
                    {
                        num2 = num1 - 273.15;
                        num2 = ((9 * num2) + 160) / 5;
                    }
                    else if(s7.equals("celsius"))
                        num2 = num1 - 273.15;
                }
                else if(s6.equals("fahrenheit"))
                {
                    if(s7.equals("kelvin"))
                    {
                        num2 = ((5 * num1) - 160) / 9;
                        num2 = num2 + 273.15;
                    }
                    else if(s7.equals("fahrenheit"))
                        num2 = num1 * 1;
                    else if(s7.equals("celsius"))
                        num2 = ((5 * num1) - 160) / 9;
                }
                else if(s6.equals("celsius"))
                {
                    if(s7.equals("kelvin"))
                        num2 = num1 + 273.15;
                    else if(s7.equals("fahrenheit"))
                        num2 = ((9 * num1) + 160) / 5;
                    else if(s7.equals("celsius"))
                        num2 = num1 * 1;
                }
                text_temp_ans.setText(Double.toString(num2));
            }
        });
        ftc.add(convert_temp_button);


        // For Base Conversion
        // choice in base enter
        base_enter.add("decimal");
        base_enter.add("binary");
        base_enter.add("octal");
        base_enter.add("hexadecimal");

        // choice in base ans
        base_ans.add("decimal");
        base_ans.add("binary");
        base_ans.add("octal");
        base_ans.add("hexadecimal");

        // Label in base conversion
        lbc.setBounds(150, 60, 290, 50);
        lbc.setFont(new Font("serif", Font.BOLD, 16));
        fbc.add(lbc);

        // base enter choice in base conversion
        base_enter.setBounds(100, 180, 130, 20);
        base_enter.setBackground(Color.darkGray.darker());
        fbc.add(base_enter);

        // base ans choice in base conversion
        base_ans.setBounds(300, 180, 130, 20);
        base_ans.setBackground(Color.darkGray.darker());
        fbc.add(base_ans);

        // base enter textbox in base conversion
        text_base_enter.setBounds(100, 155, 130, 20);
        fbc.add(text_base_enter);

        // base ans textbox in base conversion
        text_base_ans.setBounds(300, 155, 130, 20);
        fbc.add(text_base_ans);

        // convert button in base conversion
        convert_base_button.setBounds(220, 240, 100, 30);
        convert_base_button.setBackground(Color.darkGray.darker());

        convert_base_button.addActionListener(new ActionListener()
        {
            String s5, s6, s7;
            int num1 = 0;
            public void actionPerformed(ActionEvent e)
            {
                s5 = text_base_enter.getText();
                s6 = base_enter.getItem(base_enter.getSelectedIndex());
                s7 = base_ans.getItem(base_ans.getSelectedIndex());

                if(s6.equals("decimal"))
                {
                    num1 = Integer.parseInt(s5);
                    if(s7.equals("decimal"))
                        text_base_ans.setText(Integer.toString(num1));
                    else if(s7.equals("binary"))
                        text_base_ans.setText(Integer.toBinaryString(num1));
                    else if(s7.equals("octal"))
                        text_base_ans.setText(Integer.toOctalString(num1));
                    else if(s7.equals("hexadecimal"))
                        text_base_ans.setText(Integer.toHexString(num1));
                }
                else if(s6.equals("binary"))
                {
                    num1 = Integer.parseInt(s5, 2);
                    if(s7.equals("decimal"))
                        text_base_ans.setText(Integer.toString(num1));
                    else if(s7.equals("binary"))
                        text_base_ans.setText(Integer.toBinaryString(num1));
                    else if(s7.equals("octal"))
                        text_base_ans.setText(Integer.toOctalString(num1));
                    else if(s7.equals("hexadecimal"))
                        text_base_ans.setText(Integer.toHexString(num1));
                }
                else if(s6.equals("octal"))
                {
                    num1 = Integer.parseInt(s5, 8);
                    if(s7.equals("decimal"))
                        text_base_ans.setText(Integer.toString(num1));
                    else if(s7.equals("binary"))
                        text_base_ans.setText(Integer.toBinaryString(num1));
                    else if(s7.equals("octal"))
                        text_base_ans.setText(Integer.toOctalString(num1));
                    else if(s7.equals("hexadecimal"))
                        text_base_ans.setText(Integer.toHexString(num1));
                }
                else if(s6.equals("hexadecimal"))
                {
                    num1 = Integer.parseInt(s5, 16);
                    if(s7.equals("decimal"))
                        text_base_ans.setText(Integer.toString(num1));
                    else if(s7.equals("binary"))
                        text_base_ans.setText(Integer.toBinaryString(num1));
                    else if(s7.equals("octal"))
                        text_base_ans.setText(Integer.toOctalString(num1));
                    else if(s7.equals("hexadecimal"))
                        text_base_ans.setText(Integer.toHexString(num1));
                }
            }
        });
        fbc.add(convert_base_button);


        // Closing Length Conversion Frame
        flc.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                flc.setVisible(false);
            }
        });

        // Closing Temperature Conversion Frame
        ftc.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                ftc.setVisible(false);
            }
        });

        // Closing Base Conversion Frame
        fbc.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                fbc.setVisible(false);
            }
        });

        // Closing Trigonometric Table
        ft.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                ft.setVisible(false);
            }
        });

        // Closing Constants' Library Frame
        fc.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                fc.setVisible(false);
            }
        });

        // Closing Formulas' Library Frame
        ff.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                ff.setVisible(false);
            }
        });

        // Closing Calculator frame
        f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });

        menuBar.add(unit);
        menuBar.add(library);
        menuBar.add(themes);

        f.setMenuBar(menuBar);

        tf.setForeground(Color.white);
        tf.setBackground(Color.darkGray);
        tf.setFont(new Font("serif", Font.BOLD, 18));

        p.setLayout(g);

        // Constants' Library Frame
        fc.setBackground(Color.gray);
        fc.setForeground(Color.white);
        fc.setSize(500, 400);
        fc.setLayout(null);

        // Formulas' Library Frame
        ff.setBackground(Color.gray);
        ff.setForeground(Color.white);
        ff.setSize(500, 600);
        ff.setLayout(null);

        // Trigonometric Table Frame
        ft.setBackground(Color.gray);
        ft.setForeground(Color.white);
        ft.setSize(680, 400);
        ft.setLayout(null);

        // Length Conversion Frame
        flc.setBackground(Color.gray.darker());
        flc.setForeground(Color.white);
        flc.setSize(550, 330);
        flc.setLayout(null);

        // Temperature Conversion Frame
        ftc.setBackground(Color.gray.darker());
        ftc.setForeground(Color.white);
        ftc.setSize(550, 330);
        ftc.setLayout(null);

        // Base Conversion Frame
        fbc.setBackground(Color.gray.darker());
        fbc.setForeground(Color.white);
        fbc.setSize(550, 330);
        fbc.setLayout(null);

        // For calculator frame
        f.setLayout(new FlowLayout());
        f.add(tf);
        f.add(p);
        f.setSize(300, 300);
        f.setBackground(Color.gray);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        int i;
        for(i = 0; i < dop.length; i++)
        {
            if(e.getSource() == digitop[i])
            {
                if(i !=  0 && i !=  1 && i !=  2 && i !=  3 && i !=  4 &&
                   i !=  8 && i !=  9 && i != 13 && i != 14 && i != 18 &&
                   i != 19 && i != 22 && i != 23 && i != 24)
                {
                    s3 = tf.getText();
                    s4 = s3 + dop[i];
                    tf.setText(s4);
                }
                else if(i != 1)
                {
                    s1 = tf.getText();
                    tf.setText("");
                    ch = i;
                }
                else if(i == 1)
                {
                    switch(ch)
                    {
                        case 2: s2 = tf.getText();
                                ans = Double.parseDouble(s1) %
                                        Double.parseDouble(s2);
                                break;
                        case 3: ans = Math.sqrt(Double.parseDouble(s1));
                                break;
                        case 4: ans = Math.log10(Double.parseDouble(s1));
                                break;
                        case 8: s2 = tf.getText();
                                ans = Double.parseDouble(s1) /
                                        Double.parseDouble(s2);
                                break;
                        case 9: ans = Math.log(Double.parseDouble(s1));
                                break;
                        case 13:    s2 = tf.getText();
                                    ans = Double.parseDouble(s1) *
                                            Double.parseDouble(s2);
                                    break;
                        case 14:    ans = Math.sin(Double.parseDouble(s1));
                                    break;
                        case 18:    s2 = tf.getText();
                                    ans = Double.parseDouble(s1) +
                                        Double.parseDouble(s2);
                                    break;
                        case 19:    ans = Math.cos(Double.parseDouble(s1));
                                    break;
                        case 22:    s2 = tf.getText();
                                    ans = Math.pow( Double.parseDouble(s1) ,
                                            Double.parseDouble(s2) );
                                    break;
                        case 23:    s2 = tf.getText();
                                    ans = Double.parseDouble(s1) -
                                            Double.parseDouble(s2);
                                    break;
                        case 24:    ans = Math.tan(Double.parseDouble(s1));
                                    // angle is assumed to be in radians
                                    break;
                    }
                    tf.setText(Double.toString(ans));
                }
            }
        }

        // changing themes
        for(i = 0; i < th.length; i++)
        {
            if(e.getSource() == themelist[i])
            {
                if(i == 0) // Scarlet
                {
                    for(int j = 0; j < dop.length; j++)
                    {
                        digitop[j].setForeground(Color.white);
                        digitop[j].setBackground(Color.red.darker().darker().darker());
                    }
                    tf.setForeground(Color.white);
                    tf.setBackground(Color.red.darker().darker().darker());

                    f.setBackground(Color.red.darker());
                }
                else if(i == 1) // Azure
                {
                    for(int j = 0; j < dop.length; j++)
                        digitop[j].setBackground(Color.blue.darker().darker().darker());

                    tf.setBackground(Color.blue.darker().darker().darker());
                    f.setBackground(Color.blue.darker());
                }
                else if(i == 2) // Forest
                {
                    for(int j = 0; j < dop.length; j++)
                        digitop[j].setBackground(Color.green.darker().darker().darker());

                    tf.setBackground(Color.green.darker().darker().darker());
                    f.setBackground(Color.green.darker());
                }
                else // Shadow
                {
                    for(int j = 0; j < dop.length; j++)
                        digitop[j].setBackground(Color.darkGray);

                    tf.setBackground(Color.darkGray);
                    f.setBackground(Color.gray);
                }
            }
        }

        // libraries
        for(i = 0; i < lib.length; i++)
        {
            if(e.getSource() == liblist[i])
            {
                if(i == 0)
                {
                    fc.setVisible(true);
                }
                else if(i == 1)
                {
                    ff.setVisible(true);
                }
                else
                {
                    ft.setVisible(true);
                }
            }
        }

        // unit conversion
        for(i = 0; i < u.length; i++)
        {
            if(e.getSource() == unitlist[i])
            {
                if(i == 0)  // Length conversion
                {
                    flc.setVisible(true);
                }
                else if(i == 1)     // Temperature conversion
                {
                    ftc.setVisible(true);
                }
                else    // Base conversion
                {
                    fbc.setVisible(true);
                }
            }
        }
    }
}
