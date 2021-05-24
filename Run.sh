
echo "Executing program at GeneralRep node."
sshpass -f password ssh sd401@192.168.8.171 'rm -f test/Airlift/GeneralRepServer/log*'
sshpass -f password ssh sd401@192.168.8.171 'cd test/Airlift/GeneralRepServer ; java Servers.GeneralRep.GeneralRepMain' &

echo "Executing program at Plane node."
sshpass -f password ssh sd401@192.168.8.172 'cd test/Airlift/PlaneServer ; java Servers.Plane.PlaneMain' &

echo "Executing program at DestinationAirport node."
sshpass -f password ssh sd401@192.168.8.173 'cd test/Airlift/DestAirServer ; java Servers.DestinationAirport.DestinationAirportMain' &

echo "Executing program at DepartureAirport node."
sshpass -f password ssh sd401@192.168.8.174 'cd test/Airlift/DepAirServer  ; java Servers.DepartureAirport.DepartureAirportMain' &

echo "Executing program at hostess node."
sshpass -f password ssh sd401@192.168.8.175 'cd test/Airlift/HostessClient ; java Client.HostessMain' &

echo "Executing program at pilot node."
sshpass -f password ssh sd401@192.168.8.180 'cd test/Airlift/PilotClient ; java Client.PilotMain' &

echo "Executing program at the passengers node."
sshpass -f password ssh sd401@192.168.8.177 'cd test/Airlift/PassengersClient ; java Client.PassengerMain'

sshpass -f password ssh sd401@192.168.8.171 'cat test/Airlift/GeneralRepServer/log*'

