package jissen.e.jissennohito.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class InputStreamThread extends Thread {

	private BufferedReader br;

	private List<String> list = new ArrayList<String>();
	private ThreadLocal<String> headData = new ThreadLocal<>();


	/** コンストラクター */

	public InputStreamThread(InputStream is) {
		br = new BufferedReader(new InputStreamReader(is));
	}

	/** コンストラクター */

	public InputStreamThread(InputStream is, String charset) {
		try {

			br = new BufferedReader(new InputStreamReader(is, charset));

		} catch (UnsupportedEncodingException e) {

			throw new RuntimeException(e);

		}

	}

	@Override
	public void run() {
		try {

			for (;;) {

				String line = br.readLine();

				if (line == null) 	break;

				synchronized (this) {
					list.add(line);
				}
				headData.set(line);
			}

		} catch (IOException e) {

			throw new RuntimeException(e);

		} finally {

			try {

				br.close();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	}

	/** 文字列取得 */
	public List<String> getStringList() {
		return list;
	}

	public String getDataString(){
		return headData.get();
	}

}
