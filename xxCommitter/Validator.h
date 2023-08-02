#ifndef __VALIDATOR_H__
#define __VALIDATOR_H__

#pragma once

#include <string>

class Validator
{
public:
    static bool IsValidCommitMsg(const std::string &a_sentance);

private:
    static constexpr size_t MIN_MSG_LENGTH = 3;
};

#endif
