# SMTP-Multiple-servers-
▪ We will use TCP sockets and threads.
▪ Multiple Servers and Multiple clients can all connect at the same time.
➢ We just need one class for the server and one class for the client and run each of
them multiple times.
➢ Each server should state at the beginning its name (ex. Yahoo, Gmail) and port
number.
➢ Each client should state at the beginning server port number.
▪ Each server should have a folder saved on the machine with its name (ex. Gmail).
➢ This folder is created automatically when the server boots up and states its name
for the first time.
➢ If the name already exists, no new folder will be created.
▪ Each server folder should have a file called ‘credentials’ contains the username and
password for each user.
▪ Each server folder has multiple sub-folders for users.
➢ Each sub-folder is named with username (ex. user1).
➢ Each sub-folder should have a file named ‘inbox’ where the received emails are
saved.
▪ When the client runs, there are two options.
i. Register.
➢ The client registers with a new email (ex. user1@gmail.com) and
password.
➢ A sub-folder will be created automatically in the server folder.
➢ The ‘inbox’ file will be also created automatically in this sub-folder.
➢ The username (ex.user1) and password are saved in the ‘credentials’ file.
ii. Login.
➢ No need to create a sub-folder as it already exists.
➢ No need to create ‘inbox’ file as it already exists.
➢ The username and password should be checked in the ‘credentials’ file.
▪ A client can send a new email with content (MAIL FROM, RCPT TO, DATA)
➢ This email is firstly sent to the sender server.
➢ The sender server forwards this email to the recipient server.
➢ The recipient server saves this email in the ‘inbox’ file placed in the recipient subfolder
