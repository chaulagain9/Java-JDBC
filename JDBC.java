
import java.sql.*;
import java.util.Scanner;

final class sxc8268_P4 {
    final static String user = "sxc8268";
    final static String password = "Apple123";
    final static String db = "sxc8268";
    final static String jdbc = "jdbc:mysql://localhost:3306/" + db + "?user=" + user + "&password=" + password;

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(jdbc);
        Statement stmt = con.createStatement();

        Scanner input = new Scanner(System.in);     /* Asking for the input for the user. Menu Selection*/
        System.out.println("1. Check if Pilot is busy on a certain day and show the pilot assignments for this day.\n"
                + "2. Assign a Pilot to a flight leg instance \n" + "3. Add a Pilot \n" + "4. Quit (Enter q to quit)");
        char menu = input.next().charAt(0);

        if (menu == '1') {                             /*If the user inputs 1. Do the corresponding task of assigning pilot.  */

            Scanner input2 = new Scanner(System.in);            /*Asking the user for Pilot's name input*/
            System.out.print("Please enter the Pilot name: ");
            String name = input2.nextLine();

            Scanner input3 = new Scanner(System.in);          /* Asking the user for the date of availability*/
            System.out.print("Please enter the Date: ");
            String date = input3.nextLine();
            ResultSet rs = stmt.executeQuery("Select * from PilotFlyAssignments WHERE Pilot_Name='" + name 
                    + "'  AND Flight_Date='" + date + "' ");         /*Result set  */

            if (rs.first()) {
                System.out.println("Pilot is busy today!! His Schedule's for today");  /*If not present in result set say pilot is busy*/
                System.out.println(" ");
                do {
                    System.out.println(rs.getString("PilotID") + " " + rs.getString("Pilot_Name") + " "
                            + rs.getString("Flight_Number") + " " + rs.getString("From_City") + " "
                            + rs.getString("To_City") + " " + rs.getString("Flight_Date"));
                } while (rs.next());
            } else {
                System.out.println("The Pilot is available for  " + date);   /*If pilot is available for that date*/
            }
            rs.close();
        } else if (menu == '2') {                   /*When the user enters 2*/

            Scanner input4 = new Scanner(System.in);                    /*Pilot ID whom you want to assign the flight leg Instance*/
            System.out.print(" Enter the Pilot's ID who you want to assign the flight leg : ");
            int pilot_id = input4.nextInt();

            Scanner inputSeq = new Scanner(System.in);          /* Asking the user for the Flight Number (FLNO)*/
            System.out.print("Please enter the Flight FLNO: ");
            int flno = inputSeq.nextInt();

            Scanner input5 = new Scanner(System.in);            /* Asking the user for the flight Seq*/
            System.out.print("Please enter the flight's Seq: ");
            int seq = input5.nextInt();

            Scanner input6 = new Scanner(System.in);            /* Asking the user for the Flight Date*/
            System.out.print("Please enter the FDate: ");
            String fdate = input6.nextLine();

            ResultSet vs = stmt.executeQuery("Select * FROM FlightLegInstance WHERE FLNO =" + flno + " AND Seq=" + seq
                    + " AND FDate='" + fdate + "' ");
            if (vs.first()) {

                stmt.executeUpdate("Update FlightLegInstance SET Pilot=" + pilot_id + " WHERE FLNO=" + flno
                        + " AND Seq=" + seq + " AND FDate='" + fdate + "'");

                System.out.println("The pilot have been assigned successfully to that leg. ");
                System.out.println("");
                ResultSet qs = stmt.executeQuery("Select * From FlightLegInstance");
                while (qs.next()) {
                    System.out.println(qs.getInt("FLNO") + " " + qs.getInt("Seq") + " " + qs.getString("FDate") + " "
                            + qs.getInt("Pilot"));
                }
                qs.close();
            } else {
             System.out.println("There is no Flight Leg in the input you entered. ");   /* Outputs no Flight Leg in that certain Date*/
            }
        }

        else if (menu == '3') {

            Scanner input7 = new Scanner(System.in);                                       /* Asking the user for the new Pilot's ID*/
            System.out.print("Please enter the new Pilot's ID: ");
            int new_pilot = input7.nextInt();

            Scanner inputname = new Scanner(System.in);                                     /* Asking the new Pilot's name*/
            System.out.print("Please enter the new Pilot's name: ");
            String pilotname = inputname.nextLine();

            Scanner inputdate = new Scanner(System.in);                                     /* Asking the user for the hiring date*/
            System.out.print("Please enter the Pilot's Date Hired: ");
            String pilotdate = inputname.nextLine();

            ResultSet ts = stmt.executeQuery("Select ID from Pilot Where ID='" + new_pilot + "' ");

            if (ts.first()) {
                do {
                    System.out.println("ID already Exists ");               /* Output if the ID already Exists*/
                    System.exit(0);
                } while (ts.next());

            } else {
                ResultSet ps = stmt.executeQuery("Select Name from Pilot where Name= '" + pilotname + "'   ");
                if (ps.first()) {
                    Scanner inputy_n = new Scanner(System.in);
                    System.out.print("The name already exists. Do you still want to continue(Y/N):  ");
                    char yes_no = inputy_n.next().charAt(0);

                    if (yes_no == 'Y') {
                        PreparedStatement st = con
                                .prepareStatement("INSERT INTO Pilot(ID,Name,DateHired) VALUES(?, ?, ?)");      /* Inserting into the Pilot's table*/
                        st.setInt(1, new_pilot);
                        st.setString(2, pilotname);
                        st.setString(3, pilotdate);
                        st.executeUpdate();
                    } else {
                        System.out.println("Thank you. The program ended!!");
                    }

                } else {
                    PreparedStatement st = con.prepareStatement("INSERT INTO Pilot(ID,Name,DateHired) VALUES(?, ?, ?)"); /*Inserting into Pilot's table.  */
                    st.setInt(1, new_pilot);
                    st.setString(2, pilotname);
                    st.setString(3, pilotdate);
                    
                    st.executeUpdate();
                }
                ps.close();
            }
            ts.close();

            ResultSet sam = stmt.executeQuery("select * from Pilot");                               /*Printing the pilot's table */
            while (sam.next()) {
                int p = sam.getInt("ID");
                String na = sam.getString("Name");
                String da = sam.getString("DateHired");
                System.out.println(p + " " + na + " " + da);
            }
        } else {
            System.out.println("The program ended.");  /* If pressed anything other than 1,2,3. Ends the program.  */
            System.exit(0);
        }
        stmt.close();
        con.close();
    }
}
