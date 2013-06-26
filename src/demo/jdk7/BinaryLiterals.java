package demo.jdk7;

public class BinaryLiterals {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 所有整数 int， short，long，byte都可以用二进制表示
		// An 8-bit 'byte' value:
		byte aByte = (byte) 0b00100001;

		// A 16-bit 'short' value:
		short aShort = (short) 0b1010000101000101;

		// Some 32-bit 'int' values:
		int anInt1 = 0b10100001010001011010000101000101;
		int anInt2 = 0b101;
		int anInt3 = 0B101; // The B can be upper or lower case.

		// A 64-bit 'long' value. Note the "L" suffix:
		long aLong = 0b1010000101000101101000010100010110100001010001011010000101000101L;

		// 二进制在数组等的使用
		final int[] phases = { 0b00110001, 0b01100010, 0b11000100, 0b10001001,
				0b00010011, 0b00100110, 0b01001100, 0b10011000 };

	}

}
