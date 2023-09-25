import logging
import username_provider
import validator
from committer import *

logging.basicConfig(filename='committer.log',
				filemode='a',
				format='%(asctime)s,%(msecs)d %(name)s %(levelname)s %(message)s',
				datefmt='%H:%M:%S',
				level=logging.DEBUG)

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
			logging.info(f"{msg} entered by {username}")
		else:
			print("Please follow the commit message rules... Aborting commit.")
			logging.warning(f"{msg} attempted by {username}")
			return

	committer.showGreetings()

Main()
