from extralogging import logging
import username_provider
import validator
from committer import *

def Main():
	username = username_provider.getUsername()

	print(f"Hello {username}, please enter your commit message: (leave empty to view history)")
	print(">>>", end=' ')

	msg = input()

	committer = Committer(None)
	if len(msg) > 0:
		if validator.isValidCommitMsg(msg):
			committer.commitWithMsg(msg)
			print("Changes commited with message")
			logging.info(msg) # username is output automatically
		else:
			print("Please follow the commit message rules... Aborting commit.")
			logging.warning(msg)
			return

	committer.showGreetings()

Main()
