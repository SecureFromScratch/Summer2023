import java.util.logging.Level;
//import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.lang.RuntimeException;
import java.util.Scanner;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class CommitterMain
{
    private static final Logger logger = LogManager.getLogger("main");

    public static void main(String[] a_args) throws IOException
    {
    	{ // scope for username
	        String username = UsernameProvider.getUsername();
	        ThreadContext.put("user", username);
	    	
	        System.out.print(String.format("Hello %s, please enter your commit message: (leave empty to view history)\n", username));
	        System.out.print(">>> ");
    	}
    
        final String msg;
        try (final Scanner in = new Scanner(System.in))
        {
            msg = in.nextLine();
        }

        Committer committer = new Committer(logger);
        if (!msg.isEmpty()) {
	        if (Validator.IsValidCommitMsg(msg))
	        {
                committer.CommitWithMsg(msg);
                System.out.print("Changes commited with message\n");
                logger.info(String.format("%s", msg));
	        }
	        else
	        {
	            System.out.print("Please follow the commit message rules... Aborting commit.\n");
	            logger.warn(String.format("%s", msg));
	            return;
	        }
        }

        committer.ShowPreviousCommits();    
    }
}
