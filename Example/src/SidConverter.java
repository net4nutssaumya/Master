import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SidConverter {
	private final static int MASK_8_BIT = 0xff;
	private final static long MASK_32_BIT = 0xffffffffL;
	private final static long MASK_48_BIT = 0xffffffffffffL;

	public static String bytesToString(byte[] bytes) {
		if (bytes.length < 8) {
			throw new IllegalArgumentException(
					"Binary SID representation must have at least 8 bytes but passed byte array has only "
							+ bytes.length + " bytes.");
		}
		// The revision number is an unsigned 8-bit unsigned integer.
		int revision = bytes[0] & MASK_8_BIT;
		// The number of sub-authority parts is specified as an 8-bit unsigned
		// integer.
		int numberOfSubAuthorityParts = bytes[1] & MASK_8_BIT;
		if (bytes.length != 8 + numberOfSubAuthorityParts * 4) {
			throw new IllegalArgumentException(
					"According to byte 1 of the SID it total length should be "
							+ (8 + 4 * numberOfSubAuthorityParts)
							+ " bytes, however its actual length is "
							+ bytes.length + " bytes.");
		}
		// The authority is a 48-bit unsigned integer stored in big-endian
		// format.
		long authority = ByteBuffer.wrap(bytes).getLong() & MASK_48_BIT;
		// The sub-authority consists of up to 255 32-bit unsigned integers in
		// little-endian format. The number of integers is specified by
		// numberOfSubAuthorityParts.
		int[] subAuthority = new int[numberOfSubAuthorityParts];
		ByteBuffer.wrap(bytes, 8, bytes.length - 8)
				.order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().put(subAuthority);
		StringBuilder sb = new StringBuilder();
		sb.append("S-");
		sb.append(revision);
		sb.append("-");
		sb.append(authority);
		for (int subAuthorityPart : subAuthority) {
			sb.append("-");
			sb.append(subAuthorityPart & MASK_32_BIT);
		}
		return sb.toString();
	}

	public static byte[] stringToBytes(String sid) {
		if (!sid.startsWith("S-") && !sid.startsWith("s-")) {
			throw new IllegalArgumentException("Invalid SID \"" + sid
					+ "\": A valid SID must start with \"S-\".");
		}
		String[] parts = sid.split("-");
		if (parts.length < 3) {
			throw new IllegalArgumentException("Invalid SID \"" + sid
					+ "\": A valid SID must have at least two dashes.");
		}
		if (parts.length > MASK_8_BIT + 3) {
			throw new IllegalArgumentException("Invalid SID \"" + sid
					+ "\": A valid SID must not have more than 257 dashes.");
		}
		int revision;
		try {
			revision = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Invalid revision part in SID \""
							+ sid
							+ "\": The revision must be an integer number between 0 and 255.");
		}
		if (revision < 0 || revision > MASK_8_BIT) {
			throw new IllegalArgumentException(
					"Invalid revision part in SID \""
							+ sid
							+ "\": The revision must be an integer number between 0 and 255.");
		}
		int numberOfSubAuthorityParts = parts.length - 3;
		long authority;
		try {
			authority = Long.parseLong(parts[2]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Invalid authority part in SID \""
							+ sid
							+ "\": The authority must be an integer number between 0 and 281474976710655.");
		}
		if (authority < 0 || authority > MASK_48_BIT) {
			throw new IllegalArgumentException(
					"Invalid authority part in SID \""
							+ sid
							+ "\": The authority must be an integer number between 0 and 281474976710655.");
		}
		int[] subAuthority = new int[numberOfSubAuthorityParts];
		for (int i = 0; i < numberOfSubAuthorityParts; i++) {
			long subAuthorityPart;
			try {
				subAuthorityPart = Long.parseLong(parts[3 + i]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Invalid sub-authority part in SID \""
								+ sid
								+ "\": The sub-authority parts must be integer numbers between 0 and 4294967295.");
			}
			if (subAuthorityPart < 0 || subAuthorityPart > MASK_32_BIT) {
				throw new IllegalArgumentException(
						"Invalid sub-authority part in SID \""
								+ sid
								+ "\": The sub-authority parts must be integer numbers between 0 and 4294967295.");
			}
			subAuthority[i] = (int) subAuthorityPart;
		}
		byte[] bytes = new byte[8 + numberOfSubAuthorityParts * 4];
		// We have to write the authority first, otherwise it would overwrite
		// the revision and length bytes.
		ByteBuffer.wrap(bytes).putLong(authority);
		bytes[0] = (byte) revision;
		bytes[1] = (byte) numberOfSubAuthorityParts;
		ByteBuffer.wrap(bytes, 8, bytes.length - 8)
				.order(ByteOrder.LITTLE_ENDIAN).asIntBuffer().put(subAuthority);
		return bytes;
	}

}
