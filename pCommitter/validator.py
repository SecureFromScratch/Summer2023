_MIN_MSG_LENGTH = 3

# TODO: Add code to this method to only allow messages that DO NOT CONTAIN 4 Letter words
def isValidCommitMsg(a_sentance: str):
    return len(a_sentance) >= _MIN_MSG_LENGTH;
