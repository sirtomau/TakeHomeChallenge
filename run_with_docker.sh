# SCRIPT FOR LINUX!!!
# This script creates a docker image called hireme (based on docker Dockerfile) and then
# runs it exposing the port 5000
# After starting it you can use:
# http://localhost:5000 to see the happy page of the micro-service
# http://localhost:5000/api/pokemon/pikachu to test the api

docker image build -t hireme .
docker run -p 5000:5000 hireme

