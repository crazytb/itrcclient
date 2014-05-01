package itrc_client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
 
 
public class Itrc_client {
    public static void main(String[] args) {
        try {
            String serverIP = "163.152.14.209"; // DB 서버의 IP
            
            InetAddress ownIP;
            ownIP = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(ownIP);
            byte[] mac = ni.getHardwareAddress();
            System.out.println(getTime() + "클라이언트 IP : "+ownIP.getHostAddress());
            
            String clientMacAddr = "";
            for(int i=1;i<mac.length+1;i++) {
                clientMacAddr+=String.format("%02X%s", mac[i-1],(i<mac.length)?":":"");
            }
            System.out.println(getTime() + "클라이언트 MAC: " + clientMacAddr);
            String ssidAddr = "ITRC_AP";
            System.out.println(getTime() + "클라이언트 SSID: " + ssidAddr);
            String apMacAddr = "12:12:12:12:12:12";
            System.out.println(getTime() + "클라이언트 AP MAC ADDRESS: " + apMacAddr);
            // 소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket(serverIP, 5000); // DB 서버의 IP와 포트 5000번으로 소켓 접속 시도
             
            /*
            // 소켓의 입력스트림을 얻는다.
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);  // 기본형 단위로 처리하는 보조스트림
            */
            
            // 소켓의 출력스트림을 얻는다.
            OutputStream out = socket.getOutputStream(); 
            DataOutputStream dos = new DataOutputStream(out); // 기본형 단위로 처리하는 보조스트림
            
            dos.writeUTF(clientMacAddr);
            System.out.println(getTime() + "클라이언트 MAC ADDRESS 데이터를 전송했습니다.");
            dos.writeUTF(ssidAddr);
            System.out.println(getTime() + "SSID 데이터를 전송했습니다.");
            dos.writeUTF(apMacAddr);
            System.out.println(getTime() + "AP MAC ADDRESS 데이터를 전송했습니다.");
            dos.writeUTF(getTime());
            System.out.println(getTime() + "Timestamp 데이터를 전송했습니다.");
                                     
            // 스트림과 소켓을 닫는다.
            dos.close();
            socket.close();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } // try - catch
    } // main
    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
        return f.format(new Date());
    } // getTime
} // TcpClientTest