#ifndef __COMMITTER_H__
#define __COMMITTER_H__

#pragma once

#include <string>

class Committer
{
public:
	Committer(const std::string &a_filepath = FILENAME);

	bool CommitWithMsg(const std::string &a_msg) ;
	void ShowPreviousCommits();

private:
	static constexpr const char *FILENAME = "prevcommits.txt";

	std::string m_filepath;
};

#endif
