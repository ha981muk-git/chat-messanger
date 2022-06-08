package edu.fra.uas.net.utill;

/**
 * This Class converts object to byte[] and byte[] to object
 *
 * @author kalnaasan
 */
public class Serialize {

	/**
	 * @param obj object that is converted to byte[]
	 * @return a byte[]
	 * @throws IOException
	 */
	public static byte[] toBytes(Object obj) throws IOException {
		byte[] data = null;

		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			objectOutputStream.flush();
			objectOutputStream.close();
			byteArrayOutputStream.close();
			data = byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object fromBytes(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		return objectInputStream.readObject();
	}
}
