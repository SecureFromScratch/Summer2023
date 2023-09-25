class Validator
{
    private static final int MIN_MSG_LENGTH = 3;
	
    // TODO: Add code to this method to only allow messages that DO NOT CONTAIN 4 Letter words
    public static boolean IsValidCommitMsg(String a_sentance)
    {
        String[] words = a_sentance.split(" ");
        for (String w : words) {
            if (w.length() == 4) {
                return false;
            }
        }
        return (a_sentance.length() >= MIN_MSG_LENGTH);
    }
}
