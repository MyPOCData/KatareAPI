package abhi.karateAPI.utils;

public class Test {

	public static void main(String[] args) {
		GenerateUid generateUid = new GenerateUid();
		String uid = generateUid.getUidMsisdn("9818533677");
		System.out.println(uid);
	}

}
