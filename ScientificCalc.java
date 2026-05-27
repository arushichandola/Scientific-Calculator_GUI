import java.awt.*;
import java.awt.event.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;

public class ScientificCalc extends JFrame implements ActionListener
{
    JButton degrad;         //toggle deg and rad
    JTextField display;
    JComboBox <String> trigBox;         //dropdown for trign fucntons

    JButton b1st;           //they change fucntions by 2nd toggle
    JButton b2nd;
    JButton b3rd;

    boolean isDegree = true;            //mode flag var
    boolean is2nd = false;          //for toggling

    public ScientificCalc()         //costricuotn initialising calc ui
    {
        setTitle("SCIENTIFIC CALCULATOR");
        setSize(520,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        //display - shows inputs and results
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Segoe UI", Font.BOLD, 42));           //sets font
        display.setMargin(new Insets(20, 20, 20, 20));
        display.setCaretColor(Color.BLACK);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));         //induces padding
        display.setBackground(new Color(18, 18, 18));
        display.setForeground(Color.WHITE);
        display.setPreferredSize(new Dimension(0,100));           //increases display size of the display panel

        //top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(32,32,32));
        topPanel.add(display);
        
        //memory panel
        JPanel memoryPanel = new JPanel(new GridLayout(1,5,5,5));
        memoryPanel.setBackground(new Color(25,25,25));
        String[] mB = {"MC", "MR", "M+", "M-", "MS"};           //memory buttons
        for(String m: mB)
        {
            JButton btn = new JButton(m);
            btn.setBackground(new Color(25,25,25));
            btn.setForeground(Color.LIGHT_GRAY);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            memoryPanel.add(btn);
        }

        topPanel.add(memoryPanel);

        //Trignometry panel
        JPanel trigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        trigPanel.setBackground(new Color(25,25,25));
        String [] trig = {"Trigonometry", "sin", "cos", "tan", "cot", "sec", "csc", "asin", "acos", "atan"};
        trigBox = new JComboBox<>(trig);
        trigBox.addActionListener(this);
        trigBox.setPreferredSize(new Dimension(180, 35));

        degrad = new JButton("DEG");
        degrad.setFont(new Font("Segoe UI", Font.BOLD, 20));
        degrad.setFocusPainted(false);
        degrad.setBorderPainted(false);
        degrad.addActionListener(this);
        degrad.setBackground(new Color(55,55,60));
        degrad.setForeground(Color.WHITE);
        
        trigPanel.add(trigBox);
        trigPanel.add(degrad);
        topPanel.add(trigPanel);

        add(topPanel, BorderLayout.NORTH);

        //buttons!
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));          //for padding around buttons
        panel.setBackground(new Color(32, 32, 32)); 
        panel.setLayout(new GridLayout(7,5,6,6));

        String [] buttons = { 
            "2nd", "π", "e", "C", "DEL",
            "x²", "1/x", "|x|", "exp", "%",
            "√x", "(", ")", "x!", "÷",
            "x^y", "7", "8", "9", "x",
            "BIN", "4", "5", "6", "-",
            "OCT", "1", "2", "3", "+",
            "HEX", "+/-", "0", ".", "="
        };

        for(int i=0; i<buttons.length; i++)
        {
            String text = buttons[i];
            JButton button = new JButton(text);
            button.setFont(new Font("Segoe UI", Font.BOLD, 18));
            button.setFocusPainted(false);
            button.setBorderPainted(false);

            if(text.equals("="))
            {
                button.setBackground(new Color(200, 150, 220));
            }
            else
            {
                button.setBackground(new Color(55,55,60));
            }
            button.setForeground(Color.WHITE);
            button.addActionListener(this);

            if(i==20)
            {
                b1st = button;
            }
            if(i == 25)
            {
                b2nd = button;
            }
            if(i == 30)
            {
                b3rd = button;
            }

            panel.add(button);
        }

        b1st.setText("log");            //by deafult to be set
        b2nd.setText("ln");
        b3rd.setText("10^x");

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        try
        {
            //dropdown
            if(e.getSource() == trigBox)
            {
                String selected = (String) trigBox.getSelectedItem();
                if(selected.equals("Trigonometry"))
                {
                    return;
                }
                double val = Double.parseDouble(evaluate(display.getText()));           //evaljae so that if there is an artihmatic expression, it is evalued firts and then taken sin or so of


                if(command.equals(")"))         //preveents extra )
                {
                    if(!openBrac(display.getText()))
                    {
                        return;
                    }
                }

                switch(selected)
                {
                    case "sin":         //sine
                        {
                            if(isDegree)            //checks for deg or rad
                            {
                                val = Math.toRadians(val);
                            }
                            display.setText(String.valueOf(Math.sin(val)));
                            break;
                        }
                    case "cos":         //cosine
                        {
                            if(isDegree)
                            {
                                val = Math.toRadians(val);
                            }
                            display.setText(String.valueOf(Math.cos(val)));
                            break;
                        }
                    case "tan":         //tan
                        {
                            if(isDegree)
                            {
                                val = Math.toRadians(val);
                            }
                            display.setText(String.valueOf(Math.tan(val)));
                            break;
                        }
                    case "cot":         //cot
                        {
                            if(isDegree)
                            {
                                val = Math.toRadians(val);
                            }
                            display.setText(String.valueOf(1/Math.tan(val)));
                            break;
                        }
                    case "sec":         //sec
                        {
                            if(isDegree)
                            {
                                val = Math.toRadians(val);
                            }
                            display.setText(String.valueOf(1/Math.cos(val)));
                            break;
                        }
                    case "csc":         //cosec
                        {
                            if(isDegree)
                            {
                                val = Math.toRadians(val);
                            }
                            display.setText(String.valueOf(1/Math.sin(val)));
                            break;
                        }
                    case "asin":            //sin inverse
                        {
                            double ans = Math.asin(val);
                            if(val<-1 || val>1)         //out of domain
                            {
                                display.setText("ERROR");
                                return;
                            }
                            if(isDegree)
                            {
                                ans = Math.toDegrees(ans);          //as asin return value in radians
                            }
                            display.setText(String.valueOf(ans));
                            break;
                        }           
                        case "acos":            //cos inverse
                        {
                            double ans = Math.acos(val);
                            if(val<-1 || val>1)         //out of domain
                            {
                                display.setText("ERROR");
                                return;
                            }
                            if(isDegree)
                            {
                                ans = Math.toDegrees(ans);
                            }
                            display.setText(String.valueOf(ans));
                            break;
                        }
                        case "atan":                //tan inverse
                        {
                            double ans = Math.atan(val);
                            if(isDegree)
                            {
                                ans = Math.toDegrees(ans);
                            }
                            display.setText(String.valueOf(ans));
                            break;
                        }
                }
                trigBox.setSelectedIndex(0);            //resets dropdown to initial menu
                return;
            }

                switch(command)
                {
                    case "C":           //clear
                        {
                            display.setText("0");
                            break;
                        }
                    case ".":           //dot
                    {
                        String text = display.getText();
                        if(!text.endsWith("."))
                        {
                            appendText(".");            //this does not allow multiple decimals in a number
                        }
                        break;
                    }
                    case "DEL":         //delete
                        {
                            String text = display.getText();
                            if(text.equals("ERROR"))            //so tat is ERROR is disllayed, it dosent give, ERRO on del
                            {
                                display.setText("0");           //deleting eould reset it to 0
                                break;
                            }
                            if(text.length() > 1)
                            {
                                display.setText(text.substring(0, text.length()-1));
                            }
                            else
                            {
                                display.setText("0");
                            }
                            break;
                        }
                    case "=":           //equal
                        {
                            display.setText(evaluate(display.getText()));
                            break;
                        }
                    case "π":           //pi
                        {
                            String current = display.getText();                 //this is to prevent the concatenation of pi when eneterted multiple times
                            char last = current.charAt(current.length()-1);
                            if(last == '-' && (current.length()==1 || "+-*/^(%".indexOf(current.charAt(current.length()-2)) != -1) )        //distinguuihes minus fro unary subtraction
                            {
                                appendText(String.valueOf(Math.PI));
                            }
                            else if(Character.isDigit(last) || last == ')' || last == '.')              //checks last charcter and if it is any of the mentioned one it is appended onto the expr
                            {
                                appendText("*" + String.valueOf(Math.PI));
                            }
                            else
                            {
                                appendText(String.valueOf(Math.PI));
                            }
                            break;
                        }
                    case "e":
                        {
                            String current = display.getText();
                            char last = current.charAt(current.length()-1);
                            if(last == '-' && (current.length()==1 || "+-*/^(%".indexOf(current.charAt(current.length()-2)) != -1) )        //distinguuihes minus fro unary subtraction
                            {
                                appendText(String.valueOf(Math.E));
                            }
                            else if(Character.isDigit(last) || last == ')' || last == '.')           //this is to prevent the concatenation of e when eneterted multiple times
                            {
                                appendText("*" + String.valueOf(Math.E));
                            }
                            else
                            {
                                appendText(String.valueOf(Math.E));
                            }
                            break;
                        }
                    case "2nd":         //if the toggle is activated
                        {
                            is2nd = !is2nd;
                            if(is2nd)
                            {
                                b1st.setText("BIN");
                                b2nd.setText("OCT");
                                b3rd.setText("HEX");
                            }
                            else
                            {
                                b1st.setText("log");
                                b2nd.setText("ln");
                                b3rd.setText("10^x");
                            }
                            break;
                        }
            
                    case "DEG":
                    case "RAD":         //without this it just toggled to RAD and nver toggled back to DEG
                        {
                            isDegree = !isDegree;
                            if(isDegree)
                            {
                                degrad.setText("DEG");
                            }
                            else
                            {
                                degrad.setText("RAD");
                            }
                            break;
                        }

                    case "√x":          //sqaure root
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            if(x<0)
                            {
                                display.setText("ERROR");
                            }
                            else
                            {
                                display.setText(String.valueOf(Math.sqrt(x)));
                            }
                            break;
                        }
                    case "x²":              //square
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            display.setText(String.valueOf(x*x));
                            break;
                        }
                    case "1/x":         //inverse
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            if(x==0)
                            {
                                display.setText("ERROR");           //divisin by zero
                            }
                            else
                            {
                                display.setText(String.valueOf(1/x));
                            }
                            break;
                        }
                    case "|x|":         //absolute value of x
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            display.setText(String.valueOf(Math.abs(x)));
                            break;
                        }
                    case "exp":         //exp
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            display.setText(String.valueOf(Math.exp(x)));
                            break;
                        }

                        case "log":
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));         //this evaluates anything as expr first and then takes log
                            if(x<=0)
                            {
                                display.setText("ERROR");
                            }
                            else
                            {
                                display.setText(String.valueOf(Math.log10(x)));
                            }
                            break;
                        }
                        case "ln":
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));         //this evaluates anything as expr first and then takes log
                            if(x<=0)
                            {
                                display.setText("ERROR");
                            }
                            else
                            {
                                display.setText(String.valueOf(Math.log(x)));
                            }
                            break;
                        }
                        case "10^x":
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));         //this evaluates anything as expr first and then takes log
                            display.setText(String.valueOf(Math.pow(10,x)));
                            break;
                        }

                    case "x!":          //factorial
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));           //this part handles decimal numbers if enteretd
                            if(x!= (int)x)
                            {
                                display.setText("ERROR");
                                break;
                            }
                            int n = (int)x;

                            if(n<0)
                            {
                                display.setText("ERROR");
                            }
                            else if(n>170)              //at 171 stack overflow occurs
                            {
                                display.setText("ERROR");
                            }
                            else
                            {
                                display.setText(String.valueOf(factorial(n)));
                            }
                            break;
                        }
                    case "x^y":         //power
                        {
                            appendText("^");
                            break;
                        }
                    case "BIN":         //binary conversion
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            if(x != (int)x)             //if x is not an integer or if it has decimal places
                            {  
                                display.setText("ERROR");
                                break;
                            }
                            int n = (int)x;
                            display.setText(Integer.toBinaryString(n));
                            break;
                        }
                    case "OCT":         //octal conversion
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            if(x != (int)x)             //if x is not an integer or if it has decimal places
                            {  
                                display.setText("ERROR");
                                break;
                            }
                            int n = (int)x;
                            display.setText(Integer.toOctalString(n));
                            break;
                        }
                    case "HEX":         //hexadecimal conversion
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            if(x != (int)x)             //if x is not an integer or if it has decimal places
                            {  
                                display.setText("ERROR");
                                break;
                            }
                            int n = (int)x;
                            display.setText(Integer.toHexString(n).toUpperCase());            //so that the letters come in uppercase
                            break;
                        }

                    case "+/-":         //toggles the sign of last
                        {
                            double x = Double.parseDouble(evaluate(display.getText()));
                            display.setText(String.valueOf(-x));
                            break;
                        }
                    case "x":           //multiplication
                        {
                            appendText("*");
                            break;
                        }
                    case "÷":           //division
                        {
                            appendText("/");
                            break;
                        }
                    case ")":           //closing bracket
                        {
                            String text = display.getText();            //to prevent empty braces or unmatched braces
                            if(!openBrac(text) || text.endsWith("("))
                            {
                                return;
                            }
                            appendText(")");
                            break;
                        }
                    default:
                        {
                            appendText(command);
                            break;
                        }
                    }
            }
            catch(Exception ex)
                {
                    display.setText("ERROR");
                }
    }

    boolean openBrac(String s)          //to prevent ) wihtout matching (
    {
        int c = 0;          //checls for imbalance in prabtheses
        for(char ch : s.toCharArray())
        {
            if(ch=='(')
            {
                c++;
            }
            if(ch==')')
            {
                c--;
            }
        }
        return c>0;
    }

    void appendText(String text)
    {
        String current = display.getText();

        if(current.isEmpty())           //to prevent starting from invalid operators
        {
            if(text.equals("*") || text.equals("/") || text.equals("%") || text.equals("^"))
            {
                return;
            }
        }

        if(text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/") || text.equals("%") || text.equals("^"))            //this is to preevent multile operators together
        {
            if(current.isEmpty())
            {
                if(!text.equals("-"))           //allows unary minus
                {
                    return;
                }
            }
            else
            {
                char last = current.charAt(current.length()-1);         //gets last charcter
                if(text.equals("-") && (last == '+' || last == '(' || last == '*' || last == '/' || last == '^' || last == '%'))            //sepcial case for allowing entering of negative numbers
                {
                    display.setText(current + text);
                    return;
                }
            if(last == '+' || last == '-' || last == '*' || last == '/' || last == '^' || last == '%')          //prevents 2 operators back to back
            {
                return;
            }
        }
    }
    
        if(current.equals("0") || current.equals("ERROR"))              //this was done so that whenerver ERROR appears after that if we click on a number, it doesnt't show ERROR5 but nly 5
        {
            current = "";
        }
        if(text.equals("."))
        {
            if(current.isEmpty())           //this is for inserting a zero before decimal, like if user types 5+. it will give 5+0.
            {
                display.setText("0.");
                return;
            }
            char last = current.charAt(current.length()-1);
            if(last == '+' || last == '-' || last == '*' || last == '/' || last == '^' || last == '(')
            {
                display.setText(current + "0.");
                return;
            }
            if(last == ')')
            {
                return;
            }

            int i = current.length() - 1;           //to prevnt mutiple decimals in the same numebr
            while(i>=0)
            {
                char ch = current.charAt(i);
                if(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '^' || ch == '(' || ch == ')')
                {
                    break;
                }
                if(ch == '.')
                {
                    return;         //here decimal already exists
                }
                i--;            
            }
        }

        if(text.equals("("))            //this si to implement a * in betwen consecutive numbers and (, i.e., 5( -> 5*(, for evaluation
        {
            if(!current.isEmpty())
            {
                char last_char = current.charAt(current.length()-1);
                if(Character.isDigit(last_char) || last_char == ')' || last_char == '.')
                {
                    current += "*";
                }
            }
        }
        display.setText(current + text);
    }


    String evaluate(String expr)            //evalutes
    {
        try
        {
            if(expr.contains("()"))
            {
                return "ERROR";
            }
            if(expr.isEmpty())
            {
                return "0";
            }
            char last = expr.charAt(expr.length()-1);
            if(last == '+' || last == '-' || last == '*' || last == '/' || last == '^')
            {
                return "ERROR";
            }


            int open = 0;               //now this part is for soti evaluation of expression where enedeing brackets are not given but = is pressed
            for(char ch : expr.toCharArray())
            {
                if(ch == '(')
                {
                    open++;
                }
                if(ch == ')')
                {
                    open--;
                }
            }
            if(open <0)         //to check negative balance
            {
                return "ERROR";
            }

            while(open>0)
            {
                expr += ")";            //this inserts ) sign till all of them close
                open--;
            }

            if(expr.contains("^"))          //we need to take the case of power ^ separately as it is not delat with in script engine
            {
                String[] parts = expr.split("\\^");
                if(parts.length == 2)
                {
                    double base = Double.parseDouble(parts[0]);
                    double power = Double.parseDouble(parts[1]);
                    return String.valueOf(Math.pow(base,power));
                }
            }

            ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
            if(engine == null)
            {
                return "ERROR";         //this is just incase it gives a null pointer error if it is run on recent jdks 
            }
            Object result = engine.eval(expr);

            if(result.toString().equals("Infinity") || result.toString().equals("NaN"))
            {
                return "ERROR";
            }
            else
            {
                return result.toString();
            }
        }
        catch(Exception ex)
        {
            return "ERROR";
        }
    }

    double factorial(int n)         //factorail
    {
        double fact = 1;
        for(int i=1; i<=n; i++)
        {
            fact = fact * i;
        }
        return fact;
    }

    public static void main (String[] args)
    {
        new ScientificCalc();
    }
}
  

//Note: since script engine has been used here, it shall run properly in JDK 8-10 (I tested it, the funtions are running in the said JDK whereas in others they're giving errors for some fucntions, so I request this to be cheked though the compatible jdk please)