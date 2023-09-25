using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using NLog;
using NLog.Extensions.Logging;

namespace SharpCommitter
{

    class ServiceProviderFactory {
        public static ServiceProvider Create(params Type[] a_typeToRegister) {
            string projectDir = FindCsprojDirectory().FullName;

            NLog.GlobalDiagnosticsContext.Set("projectDir", projectDir);

            var logger = LogManager.GetCurrentClassLogger();
            var config = new ConfigurationBuilder()
                .SetBasePath(projectDir) //From NuGet Package Microsoft.Extensions.Configuration.Json
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .Build();

            ServiceCollection services = new();
            foreach(Type toRegister in a_typeToRegister)
            {
                services.AddTransient(toRegister);
            }

            services
                .AddSingleton<UsernameProvider>()
                .AddLogging(loggingBuilder =>
                {
                    // configure Logging with NLog
                    loggingBuilder.ClearProviders();
                    loggingBuilder.SetMinimumLevel(Microsoft.Extensions.Logging.LogLevel.Trace);
                    loggingBuilder.AddNLog(config);
                });

            return services.BuildServiceProvider();
        }

        private static System.IO.DirectoryInfo FindCsprojDirectory() 
        {
            System.IO.DirectoryInfo curdir = new(System.IO.Directory.GetCurrentDirectory());
            while (!HasCsprojDirectory(curdir))
            {
                System.IO.DirectoryInfo? parent = curdir.Parent;
                if ((parent == null) || parent.Equals(curdir.Root))
                {
                    return new(System.IO.Directory.GetCurrentDirectory());
                }

                curdir = parent;
            }

            return curdir;
        }

        private static bool HasCsprojDirectory(System.IO.DirectoryInfo a_dir) 
        {
            foreach (System.IO.FileInfo f in a_dir.EnumerateFiles())
            {
                if ((f.Extension == ".csproj") || (f.Extension == ".sln"))
                {
                    return true;
                }
            }

            return false;
        }
    }

    public class UsernameProvider
    {
        public string GetUsername() {
            string? simulated =  Environment.GetEnvironmentVariable("SIMULATED_USERNAME");
            if (simulated != null)
            {
                return simulated;
            }
            string username = Environment.UserName;
            username = char.ToUpper(username[0]) + ((username.Length > 1) ? username[1..] : "");
            return username;
        }
    }

} // namespace