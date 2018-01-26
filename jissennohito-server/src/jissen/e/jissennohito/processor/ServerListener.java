package jissen.e.jissennohito.processor;

import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class ServerListener {
	public static final double NOT_NUMBER = -810;

//	private static final String[] CMD_WINDOWS = {"cmd","/c", "e:\\cygwin\\bin\\python3.6m.exe", "host.py"};
	private static final String[] CMD_WINDOWS = {"cmd","/c", "C:\\Python34\\python.exe", "host.py"};
	private static final String[] CMD_LINUX = {"python3","host.py"};

	public static void main(String[] args) {
		String[] cmd = null;
		if(SystemUtils.IS_OS_WINDOWS)cmd = CMD_WINDOWS;
		if(SystemUtils.IS_OS_LINUX)cmd = CMD_LINUX;
		if(cmd == null){
			System.err.println("Not supported OS. exiting...");
			System.exit(1);
		}

		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.redirectErrorStream(true);//merge stderr to stdin

		try{
			Process process = pb.start();

			//shutdown hook
			Runtime.getRuntime().addShutdownHook(new Thread(() ->
			process.destroyForcibly()));

			InputStreamThread stdout = new InputStreamThread(process.getInputStream());
			stdout.start();
			double data;
			do{
				data = getRotation(stdout);
				System.out.println(data);

			}while(data < 0);
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	private static double getRotation(InputStreamThread in){
//		synchronized (in) {
//			return in.getStringList().stream()
//					.mapToDouble(s -> NumberUtils.isCreatable(s) ? NumberUtils.createDouble(s) : NOT_NUMBER)
//					.reduce((first, second) -> second).orElse(NOT_NUMBER);
//		}
		String str = in.getDataString();
		return NumberUtils.isCreatable(str)? NumberUtils.createDouble(str) : NOT_NUMBER;
	}

}
