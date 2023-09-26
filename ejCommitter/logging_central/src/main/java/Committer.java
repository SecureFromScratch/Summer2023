import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
//import java.util.logging.Logger;
import org.apache.logging.log4j.Logger;

final class Committer
{
	private static final Path FILENAME = Paths.get("prevcommits.txt");

	private final Logger m_logger;
    private final Path m_filepath;

    public Committer(Logger a_logger)
    {
    	this(a_logger, FILENAME);
    }

    public Committer(Logger a_logger, Path a_filepath)
    {
    	m_logger = a_logger;
        m_filepath = a_filepath;
        a_logger.debug(String.format("Commits file is at %s", m_filepath));
    }

    public boolean CommitWithMsg(String a_msg) 
    {
        // MISSING: Call "git commit -m msg" hereI and any other git actions

        try {
			Files.write(m_filepath, Arrays.asList(a_msg), new StandardOpenOption[]{ StandardOpenOption.CREATE, StandardOpenOption.APPEND });
		} 
        catch (IOException e) {
        	m_logger.error(String.format("Failed to write commit message to file. File: %s, Message: %s", m_filepath, a_msg));
			return false;
		}
    	
        return true;                
    }

    public void ShowPreviousCommits()
    {
        try
        {
            List<String> lines = Files.readAllLines(m_filepath);
	        System.out.println("Commit history:");
	        for (String line : lines)
            {
                System.out.print("* ");
                System.out.println(line);
            }
        }
        catch (IOException ex)
        {
        	System.out.println(String.format("No previous commits found -or- an error happened: %s", ex.getMessage()));
        }
    }	
}
