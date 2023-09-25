import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.lang.RuntimeException;
import java.util.Scanner;

final class CommitterMain
{
	private static void setupFileLogger(String a_name, String a_filepath) {
		final Logger logger = Logger.getLogger("main");

		try {  
			// This block configure the logger with handler and formatter  
			final FileHandler fh = new FileHandler(a_filepath);  
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  
		} catch (IOException e) {  
		    throw new RuntimeException(e.getMessage());  
		}
		logger.setUseParentHandlers(false);
	}

    public static void main(String[] a_args) throws IOException
    {
    	setupFileLogger("main", "committer.log");
		final Logger logger = Logger.getLogger("main");

        String username = UsernameProvider.getUsername();

    	
        System.out.print(String.format("Hello %s, please enter your commit message: (leave empty to view history)\n", username));
        System.out.print(">>> ");

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
                logger.log(Level.INFO, String.format("%s entered by %s", msg, username));
	        }
	        else
	        {
	            System.out.print("Please follow the commit message rules... Aborting commit.\n");
	            logger.log(Level.WARNING, String.format("%s  attempted by %s", msg, username));
	            return;
	        }
        }

        committer.ShowPreviousCommits();    
    }
}
