# SCRIPT FOR LINUX!!!
# This script allows you to build the code in case you do not have java and maven
# installed locally on your machine.
# The script will download a Docker image with java and maven and use it to build
# the application.
#
# IMPORTANT NOTE:
# - IT MUST BE EXECUTED IN THE SAME FOLDER WHERE THE SCRIPT IS LOCATED!
# - The build won't be super fast because the Docker image comes with empty Maven repo
#   meaning that all the needed dependencies will be loaded.
#   Refer to: https://hub.docker.com/_/maven for more info.

docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3-adoptopenjdk-11-openj9 mvn clean package -DskipTests