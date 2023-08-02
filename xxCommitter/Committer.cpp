#include "Committer.h"
#include "spdlog/spdlog.h"
#include <iostream>
#include <fstream>

Committer::Committer(const std::string &a_filepath)
	: m_filepath(a_filepath)
{
	spdlog::debug("Commits file is at {}", m_filepath);
}


bool Committer::CommitWithMsg(const std::string &a_msg) 
{
	// MISSING: Call "git commit -m msg" here, and any other git actions

	if (a_msg.empty())
	{
		return false;
	}

	std::ofstream out(m_filepath, std::ios_base::app);
	out << a_msg << "\n";
	return out.good();
}

void Committer::ShowPreviousCommits()
{
	std::ifstream file(m_filepath);
	if (!file.good()) {
        std::cout << "No previous commits found.";
		return;
	}

	while (file.good())
	{
		std::string line;
		std::getline(file, line);
		if (!line.empty())
		{
			std::cout << line << "\n";
		}
	}
}
