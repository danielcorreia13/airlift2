mkdir -p deploy/DepAirServer/
mkdir -p deploy/DestAirServer/
mkdir -p deploy/GeneralRepServer/
mkdir -p deploy/PlaneServer/
mkdir -p deploy/HostessClient/
mkdir -p deploy/PilotClient/
mkdir -p deploy/PassengersClient/

rm -f deploy/*.zip
rm -rf deploy/DepAirServer/*
rm -rf deploy/DestAirServer/*
rm -rf deploy/GeneralRepServer/*
rm -rf deploy/PlaneServer/*
rm -rf deploy/HostessClient/*
rm -rf deploy/PilotClient/*
rm -rf deploy/PassengersClient/*

cp -r src/* deploy/DepAirServer
cp -r src/* deploy/DestAirServer
cp -r src/* deploy/PlaneServer
cp -r src/* deploy/GeneralRepServer
cp -r src/* deploy/HostessClient
cp -r src/* deploy/PassengersClient
cp -r src/* deploy/PilotClient

cd deploy/DepAirServer/
find -name "*.java" > sources.txt
javac @sources.txt
rm -f sources.txt
cd ../DestAirServer
find -name "*.java" > sources.txt
javac @sources.txt
rm -f sources.txt
cd ../GeneralRepServer
find -name "*.java" > sources.txt
javac @sources.txt
rm -f sources.txt
cd ../PlaneServer
find -name "*.java" > sources.txt
javac @sources.txt
rm -f sources.txt
cd ../PassengersClient
find -name "*.java" > sources.txt
javac @sources.txt
rm -f sources.txt
cd ../HostessClient
find -name "*.java" > sources.txt
javac @sources.txt
rm -f sources.txt
cd ../PilotClient
find -name "*.java" > sources.txt
javac @sources.txt
rm -f sources.txt
cd ../

find . -name "*.java" -type f -delete

zip -r DepAirServer.zip DepAirServer
zip -r DestAirServer.zip DestAirServer
zip -r PlaneServer.zip PlaneServer
zip -r GeneralRepServer.zip GeneralRepServer
zip -r PilotClient.zip PilotClient
zip -r HostessClient.zip HostessClient
zip -r PassengersClient.zip PassengersClient

