
public class Test {

	public static void main(String[] args) {
		
		System.out.println("is aws ? " + AWSEnvChecker.isAWS());
		System.out.println("instanceid : " + AWSEnvChecker.getInstanceId());
		
	}
	
}
