class Validator
{
    private static final int MIN_MSG_LENGTH = 3;
	
    // TODO: Add code to this method to only allow messages that DO NOT CONTAIN 4 Letter words
    public static boolean IsValidCommitMsg(String a_sentance)
    {
        return (a_sentance.length() >= MIN_MSG_LENGTH);
    }
}
