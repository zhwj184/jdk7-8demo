package puzzle;

public class ByteCompare {

	public static void main(String[] args) {
		for(byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE;b++){
			if(b == 0x90){
				System.out.println("Byte 0x90");
			}
		}
		for(byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE;b++){
			if(b == 0x90){ // b = 144 but (byte)b = -112
				System.out.println("Byte 0x90");
			}
		}	
		for(byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE;b++){
			if(b == (byte)0x90){ 
				System.out.println("Byte 0x90");
			}
		}
		for(byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE;b++){
			if((b & 0xff) == 0x90){ 
				System.out.println("Byte 0x90");
			}
		}
	}
}
