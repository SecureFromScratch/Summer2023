using Microsoft.Extensions.Logging;

namespace SharpCommitter
{
    class Committer
    {
        private static readonly string FILENAME = "prevcommits.txt"; // THIS SHOULD BE ON A SHARED NETWORK DRIVE/MOUNT and taken from configuration

        private readonly string m_filepath;

        public Committer(ILogger<Committer> a_logger)
        {
            m_filepath = FILENAME;
            a_logger.LogDebug("Commits file is at {filepath}", m_filepath);
        }

        public bool CommitWithMsg(string a_msg) 
        {
            // MISSING: Call "git commit -m msg" hereI and any other git actions

            File.AppendAllText(m_filepath, a_msg + "\n");
            return true;                
        }

        public void ShowPreviousCommits()
        {
            try
            {
                Console.WriteLine("Commit history:");
                string[] lines = File.ReadAllLines(m_filepath);
                foreach (string line in lines)
                {
                    Console.Write("* ");
                    Console.WriteLine(line);
                }
            }
            catch (FileNotFoundException)
            {
                Console.WriteLine("No previous commits found.");
            }
        }
    }
}
