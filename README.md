Since this tool uses SSHJ and not JSCH because of stupid issues
you will need to follow a few steps to get this working outside of the IDE

# Setup
1. Click package within the Maven tab
2. Copy and paste the "lib" folder into a the folder you wish to run this from (found in target/classes/lib)
3. Copy and paste the jar from the maven build into the folder you wish to run this from (found in target folder)
4. Run this command: java -cp "AutoUpload-1.0-SNAPSHOT.jar;lib/*" me.rhys.uploader.Main

# How to use:
1. put plugin into the output folder that is generated and it will upload to the target server

If working, output in the console will look like this
![image](https://user-images.githubusercontent.com/40679762/140316204-b8c6bdb9-bf6f-487e-9222-2fb308d9961e.png)
