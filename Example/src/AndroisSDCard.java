
public class AndroisSDCard {
	
	@SuppressWarnings("unused")
	private static boolean isExteranlStorageAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	public static void main(String[] args) {
/*		if (isExteranlStorageAvailable()) {
			try {
				File input = new File("/sys/class/mmc_host/mmc1");
				String cid_directory = null;
				int i = 0;
				File[] sid = input.listFiles();

				for (i = 0; i < sid.length; i++) {
					if (sid[i].toString().contains("mmc1:")) {
						cid_directory = sid[i].toString();
						String SID = (String) sid[i].toString().subSequence(
								cid_directory.length() - 4,
								cid_directory.length());
						//Log.d(TAG, " SID of MMC = " + SID);
						System.out.println("SID of MMC = " + SID);
						break;
					}
				}
				BufferedReader CID = new BufferedReader(new FileReader(
						cid_directory + "/cid"));
				String sd_cid = CID.readLine();
				//Log.d(TAG, "CID of the MMC = " + sd_cid);
				System.out.println("CID of the MMC = " + sd_cid);
			} catch (Exception e) {
				e.printStackTrace();
				//Log.e("CID_APP", "Can not read SD-card cid");
			}

		} else {
			Toast.makeText(this, "External Storage Not available!!",
					Toast.LENGTH_SHORT).show();
			System.out.println("External Storage Not available!!");
		}
*/	}




}
