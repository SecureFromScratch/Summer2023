using System.Linq.Expressions;

namespace SharpCommitter
{
    class Validator
    {
        private const int MIN_MSG_LENGTH = 3;

        // TODO: Add code to this method to only allow messages that DO NOT CONTAIN 4 Letter words
        public static bool IsValidCommitMsg(string a_sentance)
        {
            return (a_sentance.Length >= MIN_MSG_LENGTH);
        }
    }
}
