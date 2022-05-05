package com.company;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    static String User = "";
    static String Pass;
    static int State=0;
    public static void main(String[] args) throws IOException
    {
        InetAddress ip = InetAddress.getByName("localhost");
        Scanner Scan=new Scanner(System.in);
        //connected for Server
        System.out.println("Please enter server port number.");
        int Port = Scan.nextInt();
        Socket client = new Socket (ip,Port);
        DataInputStream input = new DataInputStream(client.getInputStream());
        DataOutputStream output = new DataOutputStream(client.getOutputStream());
        output.writeInt(Port);
        int V = input.readInt();
        String X = input.readUTF();
        System.out.println(V + " " + X);


        while (true) {

            //Enter operation want do it and enter username and password
            System.out.println("Please choose ‘REGISTER' or ‘LOGIN’ or ‘QUIT’.");
            String option = Scan.next();
            output.writeUTF(option);
            if (option.equalsIgnoreCase("REGISTER")) {
                System.out.println("Please enter an email and a password.");
                User = Scan.next();
                Pass = Scan.next();
                output.writeUTF(User);
                output.writeUTF(Pass);
                output.writeUTF("HELLO");
                System.out.println("HELLO " + User);
                String str = input.readUTF();
                System.out.println(str);
                State = 1;
            }
            if (option.equalsIgnoreCase("LOGIN"))
            {
                while (State==0) {
                    System.out.println("Please enter an email and a password.");
                    User = Scan.next();
                    Pass = Scan.next();
                    output.writeUTF(User);
                    output.writeUTF(Pass);
                    String str = input.readUTF();
                    System.out.println(str);
                    State = input.readInt();
                }
            }
            if (option.equalsIgnoreCase("QUIT")) {
                String Quit = input.readUTF();
                System.out.println( Quit);
                Scan.close();
                client.close();
                break;
            }
            //Send or Quite
            String Send_Close="";
            if(State==1) {
                System.out.println("Please choose ‘SEND’ or ‘QUIT’.");
                Send_Close = Scan.next();
                output.writeUTF(Send_Close);
            }
           if(Send_Close.equalsIgnoreCase("SEND")&&State==1)
           {
               String dummy;
               System.out.println("MAIL FROM  "+User);
               output.writeUTF("MAIL FROM  "+User);
               dummy=input.readUTF();
               System.out.println(dummy);
               System.out.println("RCPT TO");
               String User_2=Scan.next();
               output.writeUTF("RCPT TO");
               output.writeUTF(User_2);
               String str=input.readUTF();
               System.out.println(str);
               System.out.println("DATA");
               output.writeUTF("DATA");
               dummy=input.readUTF();
               System.out.println(dummy);
               while (true)
               {
                  String Data=Scan.nextLine();
                  output.writeUTF(Data);
                  if(Data.equalsIgnoreCase("&&&"))
                  {
                      String Xm=input.readUTF();
                      System.out.println(Xm);
                      break;
                  }
               }

           }
            if(Send_Close.equalsIgnoreCase("Quit"))
            {
                String Quit=input.readUTF();
                System.out.println(Quit);
                Scan.close();
                client.close();
                break;
            }


        }

    }

}
