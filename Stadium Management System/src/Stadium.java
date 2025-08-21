import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.io.*;
import java.sql.*;
public class Stadium {
    public static void main(String[] args)throws Exception{
        Scanner sc = new Scanner(System.in);
        String dbUrl = "jdbc:mysql://localhost:3306/stadium1";
        String dbUser = "root";
        String dbPass = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        SetUser s=new SetUser();
        Staff staff=new Staff();
        LoginedUSer lu=new LoginedUSer();
        if(con!=null)
        {
            System.out.println("==========================================================");
            System.out.println("====== Welcome To Narendra Modi Cricket Stadium Web ======");
            System.out.println("==========================================================");
            while (true) {
                System.out.println();
                System.out.println("======= Welcome To Home Page =======");
                System.out.println("1. For Staff Login.");
                System.out.println("2. For User Registration.");
                System.out.println("3. For User Login.");
                System.out.println("4. EXIT");
                int choice=0;
                boolean b = true;
                while (b) {
                    try
                    {
                        System.out.println("Enter Choice : ");
                        choice = sc.nextInt();
                        b=false;
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("Invalid Choice , Please Enter Valid Choice .");
                        sc.next();
                    }
                }
                switch (choice) {
                    case 1:
                        staff.loginStaff(con);
                        break;
                    case 2:
                        s.newUser(con);
                        break;
                    case 3:
                        lu.loginUser(con);
                        break;
                    case 4:
                        System.out.println("----- Thank You For Visit -----");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Choice , Enter Valid Choice");
                        break;
                }
            }
        }
        sc.close();
    }
}
class Match
{
    int match_id;
    String match;
    String match_date;
    String match_time;
    int available_ticket;
    public Match(int match_id, String match, String match_date,String match_time,int available_ticket) {
        this.match_id = match_id;
        this.match = match;
        this.match_date = match_date;
        this.match_time = match_time;
        this.available_ticket=available_ticket;
    }
    public String toString() {
        return "id : " + match_id + "                      Match : " + match + "\nMatch Date : " + match_date + "      Match Time : "
                + match_time + "\nAvailable Tickets : "+available_ticket;
    }
}
class User
{
    String name;
    int userId;
    String password;
    String mobile;
    public User(String name, int userId, String password, String mobile) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.mobile = mobile;
    }
}
class SetUser
{
    Scanner sc=new Scanner(System.in);
    Get g = new Get();
    void newUser(Connection con) throws Exception
    {
        System.out.println("----- Welcome To Registration Page -----");
        boolean p = true;
        String name="";
        while(p)
        {
            System.out.println("Enter Your Name : ");
            String s_name=sc.next();
            String temp_name=s_name.toLowerCase();
            int x=0;
            for (int i=0;i<s_name.length();i++) {
                if(temp_name.charAt(i)>='a'&&temp_name.charAt(i)<='z')
                {
                    x++;
                }
            }
            if(x==s_name.length())
            {
                name=s_name;
                break;
            }
            else
            {
                System.out.println("Invalid Name, Enter Valid Name");
            }
        }
        int userId =  0;
        boolean b = true;
        while (b) {
            try
            {
                System.out.println("Enter UserId : ");
                userId=sc.nextInt();
                b=false;
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid User ID , Please Enter User ID in Integer Data Type.");
                sc.next();
            }
        }
        System.out.println("Enter Password : ");
        String password=sc.next();
        System.out.println("Enter Mobile no. : ");
        String mobile=sc.next();
        int x = 0;
        if(mobile.length()==10)
        {
            for (int i = 0; i < 10; i++) {
                if (mobile.charAt(i)>='0' && mobile.charAt(i)<='9') {
                    x++;
                }
            }
        }
        if(x == 10)
        {
            try
            {
                String sql="insert into user(user_id,user_name,password,mobile_no) values(?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, userId);
                pst.setString(2, name);
                pst.setString(3, password);
                pst.setString(4, mobile);
                int r = pst.executeUpdate();
                if(r>0)
                {
                    Get g = new Get();
                    String dateTime = g.getDateTime();
                    File f=new File("src/"+mobile+".txt");
                    f.createNewFile();
                    BufferedWriter bw=new BufferedWriter(new FileWriter(f));
                    bw.write(dateTime);
                    bw.newLine();
                    bw.write("1 new Message : ");
                    bw.newLine();
                    bw.write("     Your Mobile No. Registered At Narendra Modi Cricket Stadium Web.");
                    bw.newLine();
                    bw.close();
                    System.out.println("------- Registration Success -------");
                }
                else
                {
                    System.out.println(" Sorry , Registration Failed ");
                }
            }
            catch(SQLIntegrityConstraintViolationException e)
            {
                System.out.println("Registration Failed");
                System.out.println("User ID Already exist , Please Register With Different User ID");
            }
        }
        else
        {
            System.out.println("Invalid Mobile No. , Registration Failed");
        }
    }
    void changePassword(Connection con) throws Exception
    {
        System.out.println("Enter UserId : ");
        int userId=sc.nextInt();
        String old_pass="";
        String sql = "select password from user where user_id='"+userId+"'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int i=0;
        while (rs.next()) {
            old_pass = rs.getString(1);
            i++;
        }
        rs.close();
        if(i>0)
        {
            System.out.println("Enter Old Password : ");
            String pass = sc.next();
            if(pass.equals(old_pass))
            {
                System.out.println("Enter New Password : ");
                String new_pass=sc.next();
                String sql1="update user set password = '"+new_pass+"' where user_id='"+userId+"'";
                PreparedStatement pst1=con.prepareStatement(sql1);
                int r = pst1.executeUpdate();
                if(r>0)
                {
                    System.out.println("------ Password Changed Successfully ------");
                }
            }
            else
            {
                System.out.println("Password Not Matched , Password Changing Failed");
            }
        }
        else
        {
            System.out.println("No User Id Found");
        }
    }
    boolean deleteUser(Connection con,int userId) throws Exception
    {
        String sql1 = "select mobile_no from user where user_id="+userId;
        PreparedStatement pst1 = con.prepareStatement(sql1);
        ResultSet rs = pst1.executeQuery();
        boolean b = false;
        while (rs.next()) {
            int otp=(int)(Math.random()*1000000);
            String mob = rs.getString(1);
            File f = new File("src/"+mob+".txt");
            RandomAccessFile file = new RandomAccessFile(f,"rw");
            file.seek(file.length());
            file.writeBytes("\n"+g.getDateTime());
            file.writeBytes("\n1 new Message : ");
            file.writeBytes("\n   Your OTP for Delete User is "+otp+"\n");
            System.out.println("OTP sent to Your Registered Mobile No. "+mob);
            int p=3;
            while (p>0) {
                System.out.println("Enter OTP here : ");
                int fOtp=sc.nextInt();
                if (otp==fOtp) {
                    String sql = "{call delete_user(?)}";
                    CallableStatement cst = con.prepareCall(sql);
                    cst.setInt(1, userId);
                    int r = cst.executeUpdate();
                    if(r>0)
                    {
                        System.out.println("----- User Deleted Successfully -----");
                        b=true;
                    }
                    break;
                }
                else
                {
                    System.out.println("Invalid OTP , Attempt left : "+--p);
                }
            }
            file.close();
        }
        if (b) {
            return false;
        }
        else
        {
            System.out.println("User Deletion Failed");
            return true;
        }
    }
}
class LoginedUSer
{
    Scanner sc = new Scanner(System.in);
    void loginUser(Connection con) throws Exception
    {
        System.out.println("------ Welcome to User Login Page ------");
        int user_id=0;
        boolean p1 = true;
        while (p1) {
            try
            {
                System.out.println("Enter User ID : ");
                user_id = sc.nextInt();
                p1=false;
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid ID , Please Enter Valid User ID .");
                sc.next();
            }
        }
        String sql = "select password from user where user_id='"+user_id+"'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int i=0;
        String pass="";
        while (rs.next()) {
            pass = rs.getString(1);
            i++;
        }
        if(i>0)
        {
            System.out.println("Enter Password : ");
            String password = sc.next();
            if (pass.equals(password)) {
                System.out.println("----- Login Successfull -----");
                loginedUser(con, user_id);
            }
            else
            {
                System.out.println("Password Not Matched.");
                System.out.println("Login Failed");
            }
        }
        else
        {
            System.out.println("User Id Not Found");
        }
    }
    void loginedUser(Connection con,int userId) throws Exception
    {
        Ticket t = new Ticket();
        SetUser su = new SetUser();
        int choice=0;
        boolean x = true;
        while (x) {
            System.out.println("-------- Welcome to User Page --------");
            System.out.println("1. Book Ticket");
            System.out.println("2. SMS Tickets to Your Mobile No.");
            System.out.println("3. Get Matches Details ");
            System.out.println("4. Change Password ");
            System.out.println("5. Delete Acoount");
            System.out.println("6. Go To Home Page");
            boolean b = true;
            while (b) {
                try
                {
                    System.out.println("Enter Choice : ");
                    choice = sc.nextInt();
                    b=false;
                }
                catch(InputMismatchException e)
                {
                    System.out.println("Invalid Choice , Please Enter Valid Choice .");
                    sc.next();
                }
            }
            switch (choice) {
                case 1:
                {
                    t.bookTicket(con, userId);
                    break;
                }
                case 2:
                {
                    t.smsTicket(con, userId);
                    break;
                }
                case 3:
                {
                    getMatch(con);
                    break;
                }
                case 4:
                {
                    su.changePassword(con);
                    break;
                }
                case 5:
                {
                    x=su.deleteUser(con, userId);
                    break;
                }
                case 6:
                {
                    x=false;
                    break;
                }
                default:
                {
                    System.out.println("Enter Valid Choice From 1 to 6");
                    break;
                }
            }
        }
    }
    ArrayList<Integer> getMatch(Connection con) throws Exception
    {
        ArrayList<Integer> al = new ArrayList<>();
        String sql="select * from matches";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        System.out.println("------- Available Matches -------");
        LinkedList ll = new LinkedList();
        while (rs.next()) {
            al.add(rs.getInt(1));
            Match m = new Match(rs.getInt(1),rs.getString(2),rs.getString(5),rs.getString(6),rs.getInt(7));
            ll.add(m);
        }
        ll.display();
        return al;
    }
}
class Staff
{
    Scanner sc = new Scanner(System.in);
    void loginStaff(Connection con) throws Exception
    {
        LoginedStaff ls =new LoginedStaff();
        System.out.println("------ Welcome to Staff Login Page ------");
        int staff_id=0;
        boolean p1 = true;
        while (p1) {
            try
            {
                System.out.println("Enter Staff ID : ");
                staff_id = sc.nextInt();
                p1=false;
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid ID , Please Enter Valid Staff ID .");
                sc.next();
            }
        }
        String sql = "select password from staff where staff_id='"+staff_id+"'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int i=0;
        String pass="";
        while (rs.next()) {
            pass = rs.getString(1);
            i++;
        }
        if(i>0)
        {
            System.out.println("Enter Password : ");
            String password = sc.next();
            if (pass.equals(password)) {
                System.out.println("----- Login Successfull -----");
                ls.main12(con);
            }
            else
            {
                System.out.println("Password Not Matched.");
                System.out.println("Login Failed");
            }
        }
        else
        {
            System.out.println("Staff Id Not Found");
        }
    }
}
class LoginedStaff
{
    Scanner sc = new Scanner(System.in);
    void main12(Connection con) throws Exception
    {
        int choice=0;
        while (choice!=6) {
            System.out.println();
            System.out.println("========= Staff Work Page =========");
            System.out.println("1. Add Match");
            System.out.println("2. Remove Match");
            System.out.println("3. Get Last Booked Ticket Details");
            System.out.println("4. Get Match Detail By Match id");
            System.out.println("5. Get User Detail By User id");
            System.out.println("6. Go To Home Page");
            boolean b = true;
            while (b) {
                try
                {
                    System.out.println("Enter Choice : ");
                    choice = sc.nextInt();
                    b=false;
                }
                catch(InputMismatchException e)
                {
                    System.out.println("Invalid Choice , Please Enter Valid Choice .");
                    sc.next();
                }
            }
            switch (choice) {
                case 1:
                {
                    sc.nextLine();
                    System.out.println("Enter Match Name : ");
                    String name=sc.nextLine();
                    int match_id=0;
                    boolean p1 = true;
                    while (p1) {
                        try
                        {
                            System.out.println("Enter Match ID : ");
                            match_id = sc.nextInt();
                            p1=false;
                        }
                        catch(InputMismatchException e)
                        {
                            System.out.println("Invalid ID , Please Enter Valid Match ID .");
                            sc.next();
                        }
                    }
                    sc.nextLine();
                    System.out.println("Enter Series or Tournament Name : ");
                    String sName=sc.nextLine();
                    System.out.println("Enter Match Format : ");
                    String match_format=sc.next();
                    String date = "";
                    boolean p = true;
                    while (p) {
                        int x = 0;
                        System.out.println("Enter Match Date (yyyy-mm-dd) : ");
                        date=sc.next();
                        if(date.length()==10)
                        {
                            for (int i = 0; i < 4; i++) {
                                if(date.charAt(i)>='0' && date.charAt(i)<='9')
                                {
                                    x++;
                                }
                            }
                            if(date.charAt(4)=='-')
                            {
                                x++;
                            }
                            for (int i = 5; i < 7; i++) {
                                if(date.charAt(i)>='0' && date.charAt(i)<='9')
                                {
                                    x++;
                                }
                            }
                            if(date.charAt(7)=='-')
                            {
                                x++;
                            }
                            for (int i = 8; i < 10; i++) {
                                if(date.charAt(i)>='0' && date.charAt(i)<='9')
                                {
                                    x++;
                                }
                            }
                            if(date.equals(0000-00-00))
                            {
                                x=0;
                            }
                            if(x==10)
                            {
                                ArrayList<String> al = new ArrayList<>();
                                String sql="select match_date from matches";
                                PreparedStatement pst = con.prepareStatement(sql);
                                ResultSet rs = pst.executeQuery();
                                while (rs.next()) {
                                    al.add(rs.getString(1));
                                }
                                if (al.contains(date)) {
                                    System.out.println("Match Already Available On This Date, Please Enter Another Date");
                                }
                                else
                                {
                                    p=false;
                                    break;
                                }
                            }
                            else
                            {
                                System.out.println("Invalid Date Type, Enter this format (yyyy-mm-dd)");
                            }
                        }
                        else
                        {
                            System.out.println("Invalid Date Type, Enter this format (yyyy-mm-dd)");
                        }
                    }
                    String time = "";
                    boolean q = true;
                    while (q) {
                        int x = 0;
                        System.out.println("Enter Match Time (hh:mm:ss) : ");
                        time = sc.next();
                        if(time.length()==8)
                        {
                            for (int i = 0; i < 2; i++) {
                                if(time.charAt(i)>='0' && time.charAt(i)<='9')
                                {
                                    x++;
                                }
                            }
                            if(time.charAt(2)==':')
                            {
                                x++;
                            }
                            for (int i = 3; i < 5; i++) {
                                if(time.charAt(i)>='0' && time.charAt(i)<='9')
                                {
                                    x++;
                                }
                            }
                            if(time.charAt(5)==':')
                            {
                                x++;
                            }
                            for (int i = 6; i < 8; i++) {
                                if(time.charAt(i)>='0' && time.charAt(i)<='9')
                                {
                                    x++;
                                }
                            }
                            if(time.equals("00:00:0000"))
                            {
                                x = 0;
                                System.out.println("Invalid Time, Enter Valid Time");
                            }
                            if(x==8)
                            {
                                q=false;
                                break;
                            }
                            else
                            {
                                System.out.println("Invalid Time Type, Enter this format (hh:mm:ss)");
                            }
                        }
                        else
                        {
                            System.out.println("Invalid Time Type, Enter this format (hh:mm:ss)");
                        }
                    }
                    try 
                    {
                        String sql="insert into matches(match_id,match_name,series_tournament_name,match_format,match_date,match_time,available_ticket) values (?,?,?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setInt(1, match_id);
                        pst.setString(2, name);
                        pst.setString(3, sName);
                        pst.setString(4, match_format);
                        pst.setString(5, date);
                        pst.setString(6, time);
                        pst.setInt(7, 132000);
                        int r = pst.executeUpdate();
                        if(r>0)
                        {
                            System.out.println("------- Match Added Successfully -------");
                        }
                        else
                        {
                            System.out.println(" Sorry , Match Adding Failed ");
                        }
                    }
                    catch(SQLIntegrityConstraintViolationException e)
                    {
                        System.out.println("Match Adding Failed");
                        System.out.println("Match ID Already exist , Please Add Match With Different Match ID");
                    }
                    break;
                }
                case 2 :
                {
                    int id=0;
                    boolean p1 = true;
                    while (p1) {
                        try
                        {
                            System.out.println("Enter Match ID : ");
                            id = sc.nextInt();
                            p1=false;
                        }
                        catch(InputMismatchException e)
                        {
                            System.out.println("Invalid ID , Please Enter Valid Match ID .");
                            sc.next();
                        }
                    }
                    String sql = "{call delete_match(?)}";
                    CallableStatement cst = con.prepareCall(sql);
                    cst.setInt(1, id);
                    int r = cst.executeUpdate();
                    if(r>0)
                    {
                        System.out.println("----- Match Deleted Successfully For Match id = "+id+" -----");
                    }
                    else
                    {
                        System.out.println("Match id Not Found");
                    }
                    break;
                }
                case 3:
                {
                    int ticketId;
                    int matchId;
                    int userId;
                    String stand="";
                    int ticketPrice;
                    int bookedTickets;
                    int totalPaid;
                    String payMethod="";
                    String sql="select * from ticket";
                    PreparedStatement pst1= con.prepareStatement(sql);
                    ResultSet rs1 = pst1.executeQuery();
                    int i=0;
                    while (rs1.next()) {
                        i++;
                    }
                    Stack s = new Stack(i);
                    PreparedStatement pst= con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        i++;
                        ticketId=rs.getInt(1);
                        matchId = rs.getInt(2);
                        userId = rs.getInt(3);
                        stand=rs.getString(4);
                        ticketPrice=rs.getInt(5);
                        bookedTickets=rs.getInt(6);
                        totalPaid=rs.getInt(7);
                        payMethod=rs.getString(8);
                        TicketDetail td = new TicketDetail(ticketId, matchId, userId, stand, ticketPrice, bookedTickets, totalPaid, payMethod);
                        s.push(td);
                    }
                    System.out.println("----- Last Ticket Details -----");
                    System.out.println(s.pop());
                    break;
                }
                case 4:
                {
                    int id=0;
                    boolean p1 = true;
                    while (p1) {
                        try
                        {
                            System.out.println("Enter Match ID : ");
                            id = sc.nextInt();
                            p1=false;
                        }
                        catch(InputMismatchException e)
                        {
                            System.out.println("Invalid ID , Please Enter Valid Match ID .");
                            sc.next();
                        }
                    }
                    String sql = "select * from matches where match_id="+id;
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    int i =0;
                    while(rs.next()) {
                        i++;
                        System.out.println("------ Match Details ------");
                        System.out.println("Match Id : "+rs.getInt(1));
                        System.out.println("Match Name : "+rs.getString(2));
                        System.out.println("Series/Tournament Name : "+rs.getString(3));
                        System.out.println("Match Format : "+rs.getString(4));
                        System.out.println("Match Date : "+rs.getString(5));
                        System.out.println("Match Time : "+rs.getString(6));
                    }
                    if(i==0)
                    {
                        System.out.println("Match Not Found");
                    }
                    break;
                }
                case 5:
                {
                    int id=0;
                    boolean p1 = true;
                    while (p1) {
                        try
                        {
                            System.out.println("Enter User ID To Print User Details : ");
                            id = sc.nextInt();
                            p1=false;
                        }
                        catch(InputMismatchException e)
                        {
                            System.out.println("Invalid ID , Please Enter Valid User ID .");
                            sc.next();
                        }
                    }
                    String sql = "select * from user where user_id="+id;
                    PreparedStatement pst = con.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    int i =0;
                    while(rs.next()) {
                        i++;
                        System.out.println("------ User Details ------");
                        System.out.println("User Id : "+rs.getInt(1));
                        System.out.println("Name : "+rs.getString(2));
                        System.out.println("Mobile No. : "+rs.getString(4));
                    }
                    if(i==0)
                    {
                        System.out.println("User Not Found");
                    }
                    break;
                }
                case 6:
                {
                    break;
                }
                default:
                {
                    System.out.println("Invalid Choice , Enter Valid Choice");
                    break;
                }
            }
        }
    }
}
class Ticket
{
    Scanner sc = new Scanner(System.in);
    Get g = new Get();
    void bookTicket(Connection con,int userId)throws Exception
    {
        int user_id=userId;
        int match_id;
        String stand="";
        int ticket_price=0;
        int no_of_ticket;
        double total_payments;
        String payment_method="";
        LoginedUSer lu = new LoginedUSer();
        ArrayList<Integer> al=lu.getMatch(con);
        System.out.println("====== Welcome To Narendra Modi Stadium Booking Page ======");
        int matchId=0;
        boolean p1 = true;
        while (p1) {
            try
            {
                System.out.println("Enter Match Id From Above Match Details :");
                matchId = sc.nextInt();
                p1=false;
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid ID , Please Enter Valid Match ID .");
                sc.next();
            }
        }
        if(al.contains(matchId))
        {
            match_id=matchId;
            String sql2="select available_ticket from matches where match_id = "+match_id;
            PreparedStatement pst2 = con.prepareStatement(sql2);
            ResultSet rs2 = pst2.executeQuery();
            int available_ticket=0;
            while (rs2.next()) {
                available_ticket=rs2.getInt(1);
            }
            System.out.println("  Stand_No.  |  Stand  |  Seat_Price  ");
            System.out.println("     1       |    A    |     1000     ");
            System.out.println("     2       |    B    |     2000     ");
            System.out.println("     3       |    C    |     5000     ");
            System.out.println("     4       |    D    |     10000     ");
            int sNo=0;
            while (sNo<=0 || sNo>4) {
                boolean p2 = true;
                while (p2) {
                    try
                    {
                        System.out.println("Enter Stand No. From Table : ");
                        sNo = sc.nextInt();
                        p2=false;
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("Invalid Stand No. , Please Enter Valid Stand No. ");
                        sc.next();
                    }
                }
                switch (sNo) {
                    case 1:
                        stand = "A";
                        ticket_price = 1000;
                        break;
                    case 2:
                        stand = "B";
                        ticket_price = 2000;
                        break;
                    case 3:
                        stand = "C";
                        ticket_price = 5000;
                        break;
                    case 4:
                        stand = "D";
                        ticket_price = 10000;
                        break;
                    default :
                        System.out.println("Invalid Choice, Please Enter Valid Stand No.");
                        break;
                }
            }
            no_of_ticket = 0;
            boolean p3 = true;
            while (p3) {
                try
                {
                    System.out.println("Enter No. Of Tickets You Want To Buy :");
                    no_of_ticket = sc.nextInt();
                    p3=false;
                }
                catch(InputMismatchException e)
                {
                    System.out.println("Invalid No. , Please Enter Valid No.");
                    sc.next();
                }
            }
            if(available_ticket-no_of_ticket>0)
            {
                total_payments = ticket_price*no_of_ticket;
                System.out.println("Ticket Price = "+ticket_price);
                System.out.println("Total Tickets = "+no_of_ticket);
                System.out.println("--------------------");
                System.out.println("Total Bill : "+total_payments);
                System.out.println("--------------------");
                System.out.println("-- Available Payment Methods --");
                System.out.println("1. UPI");
                System.out.println("2. Debit Card");
                System.out.println("3. Credit Card");
                System.out.println("4. Netbanking");
                System.out.println("Enter Your Payment Method No.");
                int pmNo=sc.nextInt();
                switch (pmNo) {
                    case 1:
                        payment_method="UPI";
                        break;
                    case 2:
                        payment_method="Debit Card";
                        break;
                    case 3:
                        payment_method="Credit Card";
                        break;
                    case 4:
                        payment_method="Netbanking";
                        break;
                }
                String sql1 = "select mobile_no from user where user_id="+userId;
                PreparedStatement pst1 = con.prepareStatement(sql1);
                ResultSet rs = pst1.executeQuery();
                while (rs.next()) {
                    int otp=(int)(Math.random()*1000000);
                    String mob = rs.getString(1);
                    File f = new File("src/"+mob+".txt");
                    RandomAccessFile file = new RandomAccessFile(f,"rw");
                    file.seek(file.length());
                    file.writeBytes("\n"+g.getDateTime());
                    file.writeBytes("\n1 new Message : ");
                    file.writeBytes("\n   From Narendar Modi Stadium Web : ");
                    file.writeBytes("\n   Total Payment For Book Ticket is "+total_payments+" Rs.");
                    file.writeBytes("\n   Your OTP for Payment Confimation is "+otp+"\n");
                    System.out.println("OTP sent to Your Registered Mobile No. "+mob);
                    int p=3;
                    boolean b = false;
                    while (p>0) {
                        System.out.println("Enter OTP To Confirm Your Payment : ");
                        int fOtp=sc.nextInt();
                        if (otp==fOtp) {
                            b = true;
                            System.out.print("Processing Payment");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);
                            System.out.println(".");
                            System.out.println("Payment Confimed !!!");
                            System.out.print("Redirecting To The User Page , Please Wait");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(1000);
                            System.out.println(".");
                            file.seek(file.length());
                            file.writeBytes("\n"+g.getDateTime());
                            file.writeBytes("\n1 new Message : ");
                            file.writeBytes("\n   From Your Bank : ");
                            file.writeBytes("\n   Rs. "+total_payments+" Debited From Bank Account Through "+payment_method+".\n");
                            file.seek(file.length());
                            file.writeBytes("\n"+g.getDateTime());
                            file.writeBytes("\n1 new Message : ");
                            file.writeBytes("\n   From Narendar Modi Stadium Web : ");
                            file.writeBytes("\n   Congratulation, Your Tickets Are Booked.");
                            file.writeBytes("\n   You Can SMS Ticket's Full Details From Narendra Modi Stadium Web.");
                            file.writeBytes("\n   Thank You.\n");
                            break;
                        }
                        else
                        {
                            System.out.println("Invalid OTP , Attempt left : "+--p);
                        }
                    }
                    if(b)
                    {
                        String sql = "insert into ticket(match_id,user_id,stand,ticket_price,no_of_tickets,total_payments,payment_method) values (?,?,?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setInt(1, match_id);
                        pst.setInt(2, user_id);
                        pst.setString(3, stand);
                        pst.setInt(4, ticket_price);
                        pst.setInt(5, no_of_ticket);
                        pst.setDouble(6, total_payments);
                        pst.setString(7, payment_method);
                        pst.executeUpdate();
                        String sql3 = "update matches set available_ticket = ? where match_id = "+match_id;
                        PreparedStatement pst3 = con.prepareStatement(sql3);
                        pst3.setInt(1, available_ticket-no_of_ticket);
                        pst3.executeUpdate();
                    }
                    else
                    {
                        System.out.println("All Attempts Are Over, Payment Cancelled ");
                        System.out.println("Sorry Your Booking is Cancelled");
                    }
                    file.close();
                }
            }
            else
            {
                System.out.println("Sorry, Insufficient Tickets Available.");
                System.out.println("Booking Canceled");
            }
        }
        else
        {
            System.out.println("Match Id Not Found , Redirecting To User Page.");
        }
    }
    void smsTicket(Connection con,int userId) throws Exception
    {
        int ticketId;
        String stand="";
        int ticketPrice;
        int bookedTickets;
        int totalPaid;
        int matchId;
        String payMethod="";
        String userName="";
        String mobile="";
        String matchName="";
        String matchDate="";
        String matchTime="";
        String sql="select * from ticket where user_id="+userId;
        PreparedStatement pst= con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int i = 0;
        while (rs.next()) {
            i++;
            matchId = rs.getInt(2);
            ticketId=rs.getInt(1);
            stand=rs.getString(4);
            ticketPrice=rs.getInt(5);
            bookedTickets=rs.getInt(6);
            totalPaid=rs.getInt(7);
            payMethod=rs.getString(8);
            String sql1 = "select user_name,mobile_no from user where user_id="+userId;
            PreparedStatement pst1 =  con.prepareStatement(sql1);
            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                userName=rs1.getString(1);
                mobile=rs1.getString(2);
            }
            String sql2 = "select match_name,match_date,match_time from matches where match_id="+matchId;
            PreparedStatement pst2 =  con.prepareStatement(sql2);
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next()) {
                matchName=rs2.getString(1);
                matchDate=rs2.getString(2);
                matchTime=rs2.getString(3);
            }
            File f =  new File("src/"+mobile+".txt");
            RandomAccessFile file = new RandomAccessFile(f,"rw");
            file.seek(file.length());
            file.writeBytes("\n"+g.getDateTime());
            file.writeBytes("\n1 new Message : ");
            file.writeBytes("\n   From Narendar Modi Stadium Web : ");
            file.writeBytes("\n       TICKET'S DETAILS  ");
            file.writeBytes("\n   Ticket ID : "+ticketId);
            file.writeBytes("\n   Ticket Booked By : "+userName);
            file.writeBytes("\n   Match Name : "+matchName);
            file.writeBytes("\n   Match Date : "+matchDate);
            file.writeBytes("\n   Match Time : "+matchTime);
            file.writeBytes("\n   Stand Name : "+stand);
            file.writeBytes("\n   Ticket Price : "+ticketPrice);
            file.writeBytes("\n   Booked Tickets : "+bookedTickets);
            file.writeBytes("\n   Total Amount Paid : "+totalPaid);
            file.writeBytes("\n   Method Of Payment : "+payMethod+"\n");
            System.out.println("Tickets Details Sent Successfully to Mobile No. : "+mobile);
            file.close();
        }
        if(i==0)
        {
            System.out.println("Not Any Tickets Booked By You, User ID : "+userId);
        }
    }
}
class TicketDetail
{
    int ticket_id;
    int match_id;
    int user_id;
    String stand;
    int ticket_price;
    int no_of_tickets;
    double total_payments;
    String payment_method;
    public TicketDetail(int ticket_id, int match_id, int user_id, String stand, int ticket_price, int no_of_tickets,
            double total_payments, String payment_method) {
        this.ticket_id = ticket_id;
        this.match_id = match_id;
        this.user_id = user_id;
        this.stand = stand;
        this.ticket_price = ticket_price;
        this.no_of_tickets = no_of_tickets;
        this.total_payments = total_payments;
        this.payment_method = payment_method;
    }
    @Override
    public String toString() {
        return "Ticket ID : " + ticket_id + "\nMatch ID : " + match_id + "\nUser ID : " + user_id + "\nStand Name : "
                + stand + "\nTicket Price : " + ticket_price + "\nNo. Of Tickets Booked : " + no_of_tickets + "\nTotal Payment : "
                + total_payments + "\nPayment Method : " + payment_method + "\n";
    }
}
class Get
{
    String getDateTime()
    {
        String n;
        String s =new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        n ="Date - "+s+"      Time - "+new SimpleDateFormat("hh:mm:ss").format(new Date());
        return n;
    }
}
class Stack
{
    int top;
    TicketDetail[] td; 
    Stack(int i)
    {
        td=new TicketDetail[i];
        top=-1;
    }
    void push(TicketDetail td1)
    {
        top++;
        td[top]=td1;
    }
    TicketDetail pop()
    {
        top--;
        return td[top+1];
    }
}
class LinkedList
{
    class Node
    {
        Match m;
        Node next;
        Node (Match m)
        {
            this.m=m;
            next=null;
        }
    }    
    Node first=null;
    void add(Match m)
    {
        Node n = new Node(m);
        if(first==null)
        {
            first=n;
        }
        else
        {
            n.next = first;
            first = n;
        }
    }
    void display()
    {
        Node temp = first;
        while (temp != null) {
            System.out.println(temp.m);
            System.out.println("============================================================");
            temp = temp.next;
        }
    }
}