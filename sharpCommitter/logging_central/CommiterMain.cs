using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;

namespace SharpCommitter
{
    class CommitterMain : IDisposable
    {
        public static void Main()
        {
            // NOTE: The code in this method does not required changing
            // This code sets up dependency injection
            using ServiceProvider services = ServiceProviderFactory.Create(typeof(CommitterMain), typeof(Committer));

            CommitterMain mainFlow = services.GetRequiredService<CommitterMain>();
            mainFlow.Execute();
        }

        private readonly ILogger m_logger;
        private readonly Committer m_committer;
        private readonly string m_username;
        private readonly IDisposable m_scopeDispose;

        public CommitterMain(ILogger<CommitterMain> a_logger, Committer a_committer, UsernameProvider a_usernameProvider) 
        {
            m_logger = a_logger;
            m_committer = a_committer;
            m_username = a_usernameProvider.GetUsername();
            m_scopeDispose = a_logger.BeginScope(new[] { new KeyValuePair<string, object>("username", m_username) });
        }
        
        void Execute()
        {
            Console.WriteLine($"Hello {m_username}, please enter your commit message: (leave empty to view history)");
            Console.Write(">>> ");
            string? msg = Console.ReadLine();
            if ((msg != null) && (msg.Length > 0)) {
                if (Validator.IsValidCommitMsg(msg))
                {
                    m_committer.CommitWithMsg(msg);
                    Console.WriteLine("Changes commited with message");
                    m_logger.LogInformation(msg);
                }
                else
                {
                    Console.WriteLine("Please follow the commit message rules... Aborting commit.");
                    m_logger.LogWarning(msg);
                    return;
                }
            }

            m_committer.ShowPreviousCommits();
        }

        public void Dispose() {
            m_scopeDispose?.Dispose();
        }
    }
}
