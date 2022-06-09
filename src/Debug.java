//import java.io.*;
//
//class Debug{
//	private File inputFile;
//	private File outputFile;
//	
//	private FileOutputStream fout;
//	private FileInputStream fin;
//	
//	private BufferedOutputStream outf;
//	private BufferedInputStream inf;
//	
//	private String currentByte;
//	
//	public static void main(String[] args) throws IOException{
//		int value = 32768;
//		
//		System.out.println(value);
//		// String currBitStr = "hello ap";
//		// currBitStr = currBitStr.substring(8);
//		
//		// System.out.println(currBitStr.length());
//		// System.out.println(currBitStr);
//		// int[] i = new int[20];
//		// for (int j = 0; j < 20; j++) {
//			// i[j] = j;
//		// }
//		
//		// for (int j = 0; j < 20; j++) {
//			// System.out.println(i[j]);
//		// }
//		
//		// System.arraycopy(i, 2, i, 0, 18);
//		
//		// for (int j = 0; j < 20; j++) {
//			// System.out.println(i[j]);
//		// }
//		// System.out.println(str.length());
//		
//		// String newstr = str.substring(0, 8);
//		
//		// System.out.println(newstr.length());
//		
//		// System.out.println(newstr);
//		// byte[] array = new byte[256];
//		// for (int i = 0; i < 256; i++) {
//			// array[i] = (byte) i;
//		// }
//		
//		// for (int i = 0; i < 256; i++) {
//			// System.out.println(array[i]);
//			// else System.out.println(array[i] + 256);
//		// }
//		// byte[] array = new byte[256];
//		// byte[] arrayb = new byte[30];
//		
//		// for (int i = 127; i < 255; i++) {
//			// array[i] = (byte) i;
//			// System.out.println(array[i]);
//		// }
//		
//		// int count = 0;
//		// for (int i = 140; i <160; i++) {
//			// arrayb[count++] = (byte) i;
//		// }
//		// String str = new String(array);
//		
//		// System.out.println(str);
//		// String str1 = new String(arrayb);
//		// System.out.println(str1.length());
//		
//		// System.out.println(str.lastIndexOf(str1));
//		
//		// int a = (int) str.charAt(128);
//		
//		// byte b = (byte) str.charAt(128);
//		
//		// System.out.println(array[128]);
//		// System.out.println(a);
//		// System.out.println(b);
//		// String str = "hello apa kabar";
//		// String strnew = str.substring(0, 1);
//		// int newint = (int) str.charAt(0);
//		// System.out.println(newint);
//		// int[] arrayAsal = new int[15];
//		
//		// for (int i = 0; i < 15; i++) {
//			// arrayAsal[i] = i+1;
//		// }
//		
//		// int[] arrayBaru = {123, 345, 567, 456, 12, 678, 123}; //0-6, 7 elemn
//		
//		// System.arraycopy(arrayBaru, 0, arrayAsal, 0, 0);
//		
//		// for (int i = 0; i<15; i++) {
//			// System.out.println(arrayAsal[i]);
//		// }
//		
//		// String str = "testing doang ini";
//		
//		// System.out.println(str.length());
//		// String strnew = str.substring(17);
//		
//		// System.out.println(strnew.length());
//		// System.out.println(str.substring(1));
//		// char[] test = str.toCharArray();
//		
//		// for (int i = 0; i < str.length(); i++) {
//			// System.out.println(test[i]);
//		// }
//		
//		// System.out.println(str.substring(1));
//		// byte hello = (byte) 128;
//		
//		// int tes = hello;
//		
//		// System.out.println(hello);
//		// System.out.println(tes);
//		
//		// hello = (byte) tes;
//		
//		// System.out.println(hello);
//		// StringBuffer searchWindow = new StringBuffer(5);
//		
//		// searchWindow.append("hello apa kabar bung?");
//		
//		
//		// System.out.println(searchWindow.length());
//		
//		// searchWindow.delete(0,3);
//		
//		// System.out.println(searchWindow.length());
//		
//		// String str = "apa hello apa kabar bung???";
//		
//		// System.out.println(str.lastIndexOf("apa"));
//		// byte[] buffer = new byte[2];
//		
//		// buffer[0] = (byte) 255;
//		// buffer[1] = (byte) 100;
//		
//		// searchWindow.append(new String(buffer));
//		
//		// System.out.println(searchWindow.length());
//		
//		// searchWindow.append("hel");
//		// System.out.println(searchWindow);
//		// searchWindow.append('h');
//		// System.out.println(searchWindow);
//		// searchWindow.append("test");
//		// System.out.println(searchWindow);
//		
//		// System.out.println(searchWindow.length());
//		// System.out.println(searchWindow);
//		
//		// int char1 = searchWindow.charAt(0);
//		// int char2 = searchWindow.charAt(1);
//		
//		// System.out.println(char1);
//		
//		// System.out.println(char2);
//		
//		// System.out.println(searchWindow.length());
//		
//		// searchWindow.append('k');
//		// searchWindow.append('a');
//		// searchWindow.delete(0,1);
//		
//		// System.out.println(searchWindow.length());
//		// Debug run = new Debug();
//		
//		// byte[] newByte;
//		// newByte = new byte[5];
//		// newByte[0] = (byte) 128;
//		// newByte[1] = (byte) 160;
//		
//		// System.out.println(newByte[0]);
//		// System.out.println(newByte[1]);
//
//		// newByte = new byte[6];
//		// System.out.println(newByte[5]);
//		// run.doWork();
//				
//		// int i = (byte) 234;
//		// System.out.println(i);
//		// i= ~i;
//		// System.out.println(i);
//		
//		// byte j = (byte) 234;
//		// byte m = (byte) 97;
//		
//		// int k = j;
//		// if (k < 0) k += 256;
//		// System.out.println(k);
//		// System.out.println(j);
//		// int g = 59904 & 97;
//		
//		// System.out.println(g);
//		// Debug debug = new Debug();
//		
//		// long value = 1949;
//		// byte[] buffer = new byte[4];
//		
//		// buffer = debug.putValue(value);
//		
//		// for (int i = 0; i < 4; i++) {
//			// System.out.println(buffer[i]);
//		// }
//		
//		// System.out.println((byte) 157);
//	}
//	
//	public byte[] putValue(long _value) {
//		long newValue = 0; byte[] buff = new byte[4];
//		for (int i = 0; i < 4; i++) {
//			newValue = _value;
//			newValue <<= (8*i);
//			newValue >>>= (8*3);
//			// System.out.println(newValue);
//			buff[i] = (byte) (newValue);
//		}
//		return buff;
//	}
//	
//	public void doWork() throws IOException{
//		inputFile = new File("test.pdf");
//		fin = new FileInputStream(inputFile);
//		inf = new BufferedInputStream(fin);
//		
//		outputFile = new File("copy.txt");
//		fout = new FileOutputStream(outputFile);
//		outf = new BufferedOutputStream(fout);
//		
//		copy(inf, outf);
//		
//		inf.close();
//		outf.close();
//	}
//	
//	public static void copy(InputStream in, OutputStream out) throws IOException {
//		byte[] buffer = new byte[1024];
//		int j = 0;
//		while (j<1) {
//			int bytesRead = in.read(buffer);
//			if (bytesRead == -1) break;
//			for (int i=0;i<1024;i++){
//				System.out.println(buffer[i]);
//			}
//			j++;
//			// out.write(buffer, 0, bytesRead);
//		}
//	}
//}