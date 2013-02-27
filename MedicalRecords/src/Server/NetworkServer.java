package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;

public class NetworkServer {

	private static final int PORT = 12345;
	private static final int REQUEST = 2;
	private static final int LOGIN = 1;

	OutputStream out;
	InputStream in;

	Database db;
	
	public NetworkServer(Database db) {
		this.db=db;
	}

	public void aa() {
		SSLServerSocketFactory f = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();

		try {
			SSLServerSocket s = (SSLServerSocket) f.createServerSocket(PORT);
			s.setNeedClientAuth(true);
			while (true) {
				try {
					System.out.println("dfgdf");
					SSLSocket client = (SSLSocket) s.accept();
					System.out.println("accept");
					out = client.getOutputStream();
					in = client.getInputStream();

					SSLSession session = client.getSession();
					System.out.println("getSession");
					X509Certificate cert = (X509Certificate) session
							.getPeerCertificateChain()[0];
					String subject = cert.getSubjectDN().getName();	
					System.out.println("::"+subject);
					//

					while (true) {
						int type = in.read();
						int len = in.read();
						byte[] data = read(len);
						String str = new String(data);
						System.out.println(str);
						switch (type) {
						case LOGIN:

							int res = 1;//
							out.write(res);
							break;
						case REQUEST:

							if (true) {
								
								
								
								String text = "hello";
								byte[] textdata = (text).getBytes();

								out.write(textdata.length);
								out.write(textdata);
							} else {
								out.write(0);
							}
							break;

						default:
							break;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	byte[] read(int len) throws IOException {
		byte[] b = new byte[len];
		int bytesread = 0;
		while (bytesread < len) {
			bytesread+=in.read(b, bytesread, len - bytesread);
		}
		return b;
	}

}
