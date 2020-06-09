package top.wikl.component.tools.samba;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

public class openFile {

    public static void main(String[] args) {
        String romatefilename = "file:////IPADDRESS/share/test.txt";
        readRomateFile(romatefilename);
    }

    public static void readRomateFile(String romatefilename) {
        URL urlfile;
        BufferedReader in;
        PrintWriter out;
        String content = "";
        String inputLine;
        try {
            urlfile = new URL(romatefilename);
            in = new BufferedReader(new InputStreamReader(urlfile.openStream()));
            inputLine = in.readLine();
            while (inputLine != null) {
                content += inputLine;
                inputLine = in.readLine();
            }
            System.out.println(content);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
