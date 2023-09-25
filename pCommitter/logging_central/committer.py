from extralogging import logging

_FILENAME = 'prevcommits.txt'

class Committer:
	filepath: str

	def __init__(self, a_filepath: str) -> None:
		self.filepath = _FILENAME if a_filepath is None else a_filepath
		logging.debug(f"Commits file is at {self.filepath}");

	def commitWithMsg(self, a_msg: str):
		if len(a_msg) != 0:
			with open(self.filepath, 'a') as file:
				file.writelines([a_msg, '\n'])

	def showGreetings(self):
		print("Commit history:")
		try:
			with open(self.filepath, 'r') as file:
				lines = file.readlines()
				for line in lines:
					print('* ', line, end='')
		except:
			print("No previous commits found.")
