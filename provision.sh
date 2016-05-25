if which java >/dev/null; then
   	echo "skip java 7 installation"
else
	echo "java 7 installation"
	apt-get update
	apt-get install --yes openjdk-7-jdk
fi
