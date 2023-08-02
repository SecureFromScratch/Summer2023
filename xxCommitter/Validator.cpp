#include "Validator.h"

// TODO: Add code to this method to only allow messages that DO NOT CONTAIN 4 Letter words
bool Validator::IsValidCommitMsg(const std::string &a_sentance)
{
	return (a_sentance.size() >= MIN_MSG_LENGTH);
}
