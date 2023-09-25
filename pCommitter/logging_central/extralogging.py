import logging as baselogging
import username_provider

USERNAME = username_provider.getUsername()

baselogging.basicConfig(filename='committer.log',
				filemode='a',
                format='%(asctime)s,%(msecs)d %(name)s %(levelname)s %(user)s %(message)s',
				datefmt='%H:%M:%S',
				level=baselogging.DEBUG)


_baselogger = baselogging.getLogger()
logging = baselogging.LoggerAdapter(_baselogger, {'user': USERNAME})
