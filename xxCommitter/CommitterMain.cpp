#include <iostream>
#include <fstream>
#include <string>
#include <array>
#include "spdlog/spdlog.h"
#include "spdlog/fmt/ranges.h"
#include "spdlog/sinks/basic_file_sink.h"
#include "UsernameProvider.h"
#include "Committer.h"
#include "Validator.h"

static void SetupLogToFile(const std::string& a_logfilePath)
{
	auto fileLogger = spdlog::basic_logger_mt("main", a_logfilePath.c_str(), true);
    spdlog::set_default_logger(fileLogger);
    spdlog::flush_every(std::chrono::seconds(1));
}

int main()
{
	SetupLogToFile("committer.log");

    std::string username = UsernameProvider::GetUsername();

	std::cout << "Hello, " + username + ", please enter your commit message: (leave empty to view history)\n";
	std::cout << ">>> ";
	std::string msg;
	std::getline(std::cin, msg);

	Committer committer;
	if (!msg.empty())
	{
		if (Validator::IsValidCommitMsg(msg))
		{
			committer.CommitWithMsg(msg);
			std::cout << "Changes commited with message\n";
			spdlog::info("{} entered by {}", msg, username);
		}
		else
		{
			std::cout << "Please follow the commit message rules... Aborting commit.\n";
			spdlog::warn("{} attempted by {}", msg, username);
			return 1;
		}
	}

	committer.ShowPreviousCommits();

	return 0;
}
