package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class Server
{
    public static void main(String[] args) throws IOException
    {
            Scanner Scan = new Scanner(System.in);
            //Determine ServerName and port number
            System.out.println("Please enter the server name and port number.");
            String name_Server = Scan.nextLine();
            int port_Server = Scan.nextInt();
            System.out.println("Server for " + name_Server + " and port number " + port_Server + " is booted up & is waiting for clients to connect.");
            ServerSocket serverSocket = new ServerSocket(port_Server);
            //accept Multiple Client
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket);
                ClientConnection clientConnection = new ClientConnection(clientSocket);
                clientConnection.Save_Server(port_Server, name_Server);
                CreateNewFolder(name_Server);
                String Path_credentials = "C:\\Users\\WinDows\\IdeaProjects\\network_SMTP_41" + "\\" + name_Server.trim() + "\\" + "credentials.text";
                CreateNewFile(Path_credentials);
                clientConnection.start();
            }

    }
    static class ClientConnection extends Thread {
        Socket clientSocket;
        String Username;
        String Password;
        String KeyWord;
        String Server_2;
        int port;
        String Server;
        public void Save_Server(int Port, String server)
        {
            port = Port;
            this.Server = server;
        }

        ClientConnection(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                //two object from Classes DataInputStream and DataOutputStream to input and output
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

                //to determine which Client need connection with
                while (true)
                {
                    int PortNeedClient = input.readInt();
                    if (port== PortNeedClient)
                     {
                         output.writeInt(220);
                         output.writeUTF(Server);
                         break;
                     }
                    else
                        {
                            output.writeInt(404);
                            output.writeUTF("not connected  "+Server);
                        }


                }
                //operation ‘REGISTER' or ‘LOGIN’ or ‘QUIT’
                while (true)
                {
                    String operation = input.readUTF();
                    if (operation.equalsIgnoreCase("REGISTER"))
                    {
                        Username = input.readUTF();
                        Password = input.readUTF();
                        KeyWord = input.readUTF();
                        LoginInFiLe(Username, Password, Server);
                        UserRegisterInbox(Username, Password, Server);
                        output.writeUTF("250 Hello  " + Username + "  pleased to meet you");
                        break;
                    }
                   else if (operation.equalsIgnoreCase("LOGIN"))
                    {
                        while (true)
                        {
                            Username = input.readUTF();
                            Password = input.readUTF();
                            if (IsLogin(Username, Password, Server))
                            {
                                output.writeUTF("250  " + Username + "  you are connect for  " + Server );
                                output.writeInt(1);
                                break;
                            }
                            else {
                                output.writeUTF(Username + "  you are not found in  " + Server + "  Try again");
                                output.writeInt(0);
                            }



                        }
                        break;
                    }
                   else if (operation.equalsIgnoreCase("QUIT"))
                    {
                        output.writeUTF(" 221  " + Server + "  closing connection");
                        clientSocket.close();
                        System.out.println(" the connection with the Client is closed");
                        break;
                    }


                }
                //Send OR ‘QUIT’
                String SQ=input.readUTF();
                if (SQ.equalsIgnoreCase("SEND")) {
                    while (true) {
                        String MailFrom = input.readUTF();
                        output.writeUTF(" Server: 250  " + Username + "  ...Sender ok");
                        String RCPTTo = input.readUTF();
                        String Username_2 = input.readUTF();
                        output.writeUTF("Server: 250  " + Username_2 + "  ...Recipient ok");
                        for (int i = 0; i < Username_2.length(); i++) {
                            if (Username_2.charAt(i) == '@') {
                                int A = 0;
                                char[] S = new char[10];
                                for (int j = i + 1; j < Username_2.length(); j++) {
                                    S[A] = Username_2.charAt(j);
                                    A++;
                                }
                                Server_2 = new String(S);
                                break;
                            }

                        }
                        WirteInBox(Username_2, MailFrom, Server_2);
                        WirteInBox(Username_2, RCPTTo, Server_2);
                        WirteInBox(Username_2, Username_2, Server_2);
                        String Z = input.readUTF();
                        output.writeUTF("Server: Please enter the body of your email ended by ‘&&&‘");
                        if (Z.equalsIgnoreCase("Data")) {
                            while (true) {
                                String DATA = input.readUTF();
                                if (DATA.equalsIgnoreCase("&&&")) {
                                    output.writeUTF("Server: 250 Message accepted for delivery");
                                    break;
                                }
                                WirteInBox(Username_2, DATA, Server_2);
                            }
                        }

                    }
                }
                if (SQ.equalsIgnoreCase("QUIT"))
                {
                    output.writeUTF("Server: 221  " + Server + " closing connection");
                    clientSocket.close();
                    System.out.println("the connection with the Client is closed");
                }


            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        }

    }
    public static void LoginInFiLe(String user, String pass, String foldername) throws IOException {
        String Path_credentials = "C:\\Users\\WinDows\\IdeaProjects\\network_SMTP_41" + "\\" + foldername.trim() + "\\" + "credentials.text";
        if (!IsLogin(user, pass, foldername))
        {
            FileWriter fileWriter = new FileWriter(Path_credentials,true);
            fileWriter.write(user+"\n");
            fileWriter.write(pass+"\n");
            fileWriter.close();
        }

        }

    public static boolean IsLogin(String user, String pass, String foldername) throws FileNotFoundException
    {
        boolean found = false;
        String Path_credentials = "C:\\Users\\WinDows\\IdeaProjects\\network_SMTP_41" + "\\" + foldername.trim() + "\\" + "credentials.text";
        Scanner input = new Scanner(new File(Path_credentials));
        input.useDelimiter("\n");
        while (input.hasNext()&&!found)
        {
        String UserDummy=input.next();
        String PassDummy=input.next();
        if(UserDummy.trim().equals(user.trim())&&PassDummy.trim().equals(pass.trim()))
        {
            found=true;
        }

        }
        return found;
    }

    public static void CreateNewFile(String Filepath) {
        try {
            File f = new File(Filepath);
            Boolean P= f.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void CreateNewFolder(String foldername)
    {
        File f = new File(foldername);
           Boolean S= f.mkdir();
    }

    public static void UserRegisterInbox(String username, String password, String foldername) throws FileNotFoundException
    {
        String Path_username = "C:\\Users\\WinDows\\IdeaProjects\\network_SMTP_41" + "\\" + foldername + "\\" + username;
        CreateNewFolder(Path_username);
        String Path_inbox = Path_username + "\\" + "inbox.txt";
        CreateNewFile(Path_inbox);

    }

    public static void WirteInBox(String Username_2, String Data, String Server_2) throws IOException
    {
        String pathInbox = "C:\\Users\\WinDows\\IdeaProjects\\network_SMTP_41\\"+Server_2.trim()+"\\"+Username_2.trim()+"\\"+"inbox.txt";
        FileWriter fileWriter=new FileWriter(pathInbox,true);
        fileWriter.write(Data+"\n");
        fileWriter.close();
    }
}
