import java.lang.System;


public final class UsernameProvider
{
	public static String getUsername() {
		return System.getProperty("user.name");
	}
	
	public static String getDomainName() {
	    return System.getenv("USERDOMAIN");
	}
}
