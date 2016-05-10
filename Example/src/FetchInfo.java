import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class FetchInfo {

	private static boolean isExteranlStorageAvailable() {

		return new File("/sys/class/mmc_host/mmc1") != null;
	}

	public static void main(String[] args) {

		InetAddress ip;
		try {

			// //////////////SD card Serial Number/////////////////////
			if (isExteranlStorageAvailable()) {
				try {
					File input = new File("/sys/class/mmc_host/mmc1");
					String cid_directory = null;
					int i = 0;
					File[] sid = input.listFiles();

					for (i = 0; i < sid.length; i++) {
						if (sid[i].toString().contains("mmc1:")) {
							cid_directory = sid[i].toString();
							String SID = (String) sid[i].toString()
									.subSequence(cid_directory.length() - 4,
											cid_directory.length());
							// Log.d(TAG, " SID of MMC = " + SID);
							System.out.println("SID of MMC = " + SID);
							break;
						}
					}
					BufferedReader CID = new BufferedReader(new FileReader(
							cid_directory + "/cid"));
					String sd_cid = CID.readLine();
					// Log.d(TAG, "CID of the MMC = " + sd_cid);
					System.out.println("CID of the MMC = " + sd_cid);
				} catch (Exception e) {
					System.out.println("Can not read SD-card cid");
					// e.printStackTrace();
					// Log.e("CID_APP", "Can not read SD-card cid");
				}

			} else {
				/*
				 * Toast.makeText(this, "External Storage Not available!!",
				 * Toast.LENGTH_SHORT).show();
				 */
				System.out.println("External Storage Not available!!");
			}

			// ///////Serial Number of c /////////////////////
			String sn = DiskUtils.getSerialNumber("C");
			/*
			 * javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
			 * null, sn, "Serial Number of C:",
			 * javax.swing.JOptionPane.DEFAULT_OPTION);
			 */
			System.out.println("Serial Number of C: " + sn);

			// /////////Motherboard serial number//////////////
			String cpuId = MiscUtils.getMotherboardSN();
			/*
			 * javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
			 * null, cpuId, "Motherboard serial number",
			 * javax.swing.JOptionPane.DEFAULT_OPTION);
			 */
			System.out.println("Motherboard serial number: " + cpuId);

			// ///////////////MAC ID Address/////////////////////////
			ip = InetAddress.getLocalHost();
			// System.out.println("Current IP address : " +
			// ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			// System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : ""));
			}
			System.out.println("Current MAC address :" + sb.toString());

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e) {

			e.printStackTrace();

		}

	}

}
