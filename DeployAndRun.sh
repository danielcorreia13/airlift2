echo "Transfering data to the Rep"
sshpass -f password ssh sd401@192.168.8.171 'mkdir -p test/Airlift'

sshpass -f password ssh sd401@192.168.8.171 'rm -rf test/Airlift/*'
sshpass -f password scp deploy/GeneralRepServer.zip sd401@192.168.8.171:test/Airlift
echo "Decompressing data."
sshpass -f password ssh sd401@192.168.8.171 'cd test/Airlift ; unzip -uq GeneralRepServer.zip'
echo "Executing program at GeneralRep node."
sshpass -f password ssh sd401@192.168.8.171 'cd test/Airlift/GeneralRepServer ; java Servers.GeneralRep.GeneralRepMain' &

echo "Transfering data to the Plane"
sshpass -f password ssh sd401@192.168.8.172 'mkdir -p test/Airlift'
sshpass -f password ssh sd401@192.168.8.172 'rm -rf test/Airlift/*'
sshpass -f password scp deploy/PlaneServer.zip sd401@192.168.8.172:test/Airlift
echo "Decompressing data."
sshpass -f password ssh sd401@192.168.8.172 'cd test/Airlift ; unzip -uq PlaneServer.zip'
echo "Executing program at Plane node."
sshpass -f password ssh sd401@192.168.8.172 'cd test/Airlift/PlaneServer ; java Servers.Plane.PlaneMain' &

echo "Transfering data to the Destination Airport"
sshpass -f password ssh sd401@192.168.8.173 'mkdir -p test/Airlift'
sshpass -f password ssh sd401@192.168.8.173 'rm -rf test/Airlift/*'
sshpass -f password scp deploy/DestAirServer.zip sd401@192.168.8.173:test/Airlift
echo "Decompressing data."
sshpass -f password ssh sd401@192.168.8.173 'cd test/Airlift ; unzip -uq DestAirServer.zip'
echo "Executing program at DestinationAirport node."
sshpass -f password ssh sd401@192.168.8.173 'cd test/Airlift/DestAirServer ; java Servers.DestinationAirport.DestinationAirportMain' &

echo "Transfering data to the Departure Airport"
sshpass -f password ssh sd401@192.168.8.174 'mkdir -p test/Airlift'
sshpass -f password ssh sd401@192.168.8.174 'rm -rf test/Airlift/*'
sshpass -f password scp deploy/DepAirServer.zip sd401@192.168.8.174:test/Airlift
echo "Decompressing data."
sshpass -f password ssh sd401@192.168.8.174 'cd test/Airlift ; unzip -uq DepAirServer.zip'
echo "Executing program at DepartureAirport node."
sshpass -f password ssh sd401@192.168.8.174 'cd test/Airlift/DepAirServer  ; java Servers.DepartureAirport.DepartureAirportMain' &

echo "Transfering data to the hostess"
sshpass -f password ssh sd401@192.168.8.175 'mkdir -p test/Airlift'
sshpass -f password ssh sd401@192.168.8.175 'rm -rf test/Airlift/*'
sshpass -f password scp deploy/HostessClient.zip sd401@192.168.8.175:test/Airlift
echo "Decompressing data."
sshpass -f password ssh sd401@192.168.8.175 'cd test/Airlift ; unzip -uq HostessClient.zip'
echo "Executing program at hostess node."
sshpass -f password ssh sd401@192.168.8.175 'cd test/Airlift/HostessClient ; java Client.HostessMain' &

echo "Transfering data to the pilot"
sshpass -f password ssh sd401@192.168.8.180 'mkdir -p test/Airlift'
sshpass -f password ssh sd401@192.168.8.180 'rm -rf test/Airlift/*'
sshpass -f password scp deploy/PilotClient.zip sd401@192.168.8.180:test/Airlift
echo "Decompressing data."
sshpass -f password ssh sd401@192.168.8.180 'cd test/Airlift ; unzip -uq PilotClient.zip'
echo "Executing program at pilot node."
sshpass -f password ssh sd401@192.168.8.180 'cd test/Airlift/PilotClient ; java Client.PilotMain' &


echo "Transfering data to the passengers"
sshpass -f password ssh sd401@192.168.8.177 'mkdir -p test/Airlift'
sshpass -f password ssh sd401@192.168.8.177 'rm -rf test/Airlift/*'
sshpass -f password scp deploy/PassengersClient.zip sd401@192.168.8.177:test/Airlift
echo "Decompressing data."
sshpass -f password ssh sd401@192.168.8.177 'cd test/Airlift ; unzip -uq PassengersClient.zip'
echo "Executing program at the passengers node."
sshpass -f password ssh sd401@192.168.8.177 'cd test/Airlift/PassengersClient ; java Client.PassengerMain'

sshpass -f password ssh sd401@192.168.8.171 'cat test/Airlift/GeneralRepServer/log*'