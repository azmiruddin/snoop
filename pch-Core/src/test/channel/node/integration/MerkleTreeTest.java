package test.channel.node.integration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MerkleTreeTest {
	static MessageDigest digest;

	public static void main(String[] args) throws NoSuchAlgorithmException {
		digest = MessageDigest.getInstance("SHA-256");
		new MerkleTreeTest().run();
	}

	private void run() {
		// txid A
		byte[] A = hexStringToByteArray("b1fea52486ce0c62bb442b530a3f0132b826c74e473d1f2c220bfa78111c5082");
		System.out.println(Arrays.toString(A));
		// txid A byte-swapped
		byte[] A_little = swapEndianness(A);
		System.out.println(Arrays.toString(A_little));

		// txid B
		byte[] B = hexStringToByteArray("f4184fc596403b9d638783cf57adfe4c75c605f6356fbc91338530e9831e9e16");
		System.out.println(Arrays.toString(B));

		// txid B byte-swapped
		byte[] B_little = swapEndianness(B);
		System.out.println(Arrays.toString(B_little));

		// txid A + B concatenated
		byte[] AB_little = Arrays.copyOf(A_little, A_little.length + B_little.length);
		System.arraycopy(B_little, 0, AB_little, A_little.length, B_little.length);
		System.out.println(Arrays.toString(AB_little));

		// double hash of byte-swapped concatenated A+B
		byte[] ABdoubleHash = SHA256(SHA256(AB_little));
		System.out.println(Arrays.toString(ABdoubleHash));

		// print result byte-swapped back to big-endian
		byte[] result = swapEndianness(ABdoubleHash);
		System.out.println(Arrays.toString(result));
		System.out.println(getHex(result));
	}

	byte[] swapEndianness(byte[] hash) {
		byte[] result = new byte[hash.length];
		for (int i = 0; i < hash.length; i++) {
			result[i] = hash[hash.length - i - 1];
		}
		return result;
	}

	byte[] SHA256(byte[] obytes) {
		return digest.digest(obytes);
	}

	byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	private static final String HEXES = "0123456789abcdef";

	String getHex(byte[] raw) {
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
}