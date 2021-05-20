package Client.common;

import Client.entities.Passenger;
import Client.entities.Pilot;

import java.io.Serializable;


/**
 * TCP Packet data type for the message passing This is the most important class
 * for the message-apssing logic as it implements the messages structures
 */
public class Message implements Serializable {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 202005L;

    /**
     * Entity ID
     *
     * @serial id
     */
    private long id;

    /**
     * Entity ID is valid
     *
     * @serial validID
     */
    private boolean validID;

    /**
     * Hostess Entity State
     *
     * @serial hostState
     */
    private int hostState;

    /**
     * Passenger Entity State
     *
     * @serial hostState
     */
    private int passengerState;

    /**
     * Pilot Entity State
     *
     * @serial hostState
     */
    private int pilotState;

    /**
     * Hostess Entity state is valid
     *
     * @serial validState
     */
    private boolean validHostState;

    /**
     * Pilot Entity state is valid
     *
     * @serial validState
     */
    private boolean validPilotState;

    /**
     * Passenger Entity state is valid
     *
     * @serial validState
     */
    private boolean validPassengerState;

    /**
     * Packet type
     *
     * @serial type
     */
    private MessageType type;

    /**
     * Packer type is valid
     *
     * @serial validType
     */
    private boolean validType;

    /**
     * First int value to be transported
     *
     * @serial int1
     */
    private int int1;

    /**
     * First int value is valid
     *
     * @serial validInt1
     */
    private boolean validInt1;

    /**
     * Second int value to be transported
     *
     * @serial int2
     */
    private int int2;

    /**
     * Second int value is valid
     *
     * @serial validInt2
     */
    private boolean validInt2;

    /**
     * Third int value to be transported
     *
     * @serial int3
     */
    private int int3;

    /**
     * Third int value is valid
     *
     * @serial validInt3
     */
    private boolean validInt3;

    /**
     * First boolean value to be transported
     *
     * @serial bool1
     */
    private boolean bool1;

    /**
     * First boolean value is valid
     *
     * @serial validBool1
     */
    private boolean validBool1;

    /**
     * Second boolean value to be transported
     *
     * @serial boole2
     */
    private boolean bool2;

    /**
     * Second boolean value is valid
     *
     * @serial validBool2
     */
    private boolean validBool2;

    /**
     * Integer array, useful to pass amount of luggages at start of passenger
     *
     * @serial intArray1
     */
    private int[] intArray1;

    /**
     * Integer Array validity
     *
     * @serial validIntArray1
     */
    private boolean validIntArray1;

    /**
     * Integer array, useful to pass amount of luggages at start of passenger
     *
     * @serial intArray2
     */
    private int[] intArray2;

    /**
     * Integer Array validity
     *
     * @serial validIntArray2
     */
    private boolean validIntArray2;

    /**
     * Packet instantiation
     */
    public Message()
    {
        this.validID = false;
        this.validHostState = false;
        this.validPilotState = false;
        this.validPassengerState = false;
        this.validType = false;
        this.validInt1 = false;
        this.validInt2 = false;
        this.validInt3 = false;
        this.validBool1 = false;
        this.validBool2 = false;
        this.validIntArray1 = false;
        this.validIntArray2 = false;
    }

    /**
     * Return entity ID
     *
     * @return entity ID
     */
    public long getId() {
        return this.id;
    }

    /**
     * Set entity ID
     *
     * @param id entity ID
     */
    public void setId(long id) {
        this.id = id;
        this.validID = true;
    }

    /**
     * Return entity ID validity
     *
     * @return entity ID is valid
     */
    public boolean getValidID() {
        return this.validID;
    }

    /**
     * Return Hostess Entity state
     *
     * @return Hostess entity state
     */
    public int getHostState()
    {
        return this.hostState;
    }

    /**
     * Return Pilot Entity state
     *
     * @return Pilot entity state
     */
    public int getPilotState()
    {
        return this.pilotState;
    }

    /**
     * Return Passenger Entity state
     *
     * @return Passenger entity state
     */
    public int getPassengerState()
    {
        return this.passengerState;
    }


    /**
     * Set Hostess entity state
     *
     * @param state hostess entity current state
     */
    public void setHostState(int state)
    {
        this.hostState = state;
        this.validHostState = true;
    }

    /**
     * Set Pilot entity state
     *
     * @param state pilot entity current state
     */
    public void setPilotState(int state)
    {
        this.pilotState = state;
        this.validPilotState = true;
    }

    /**
     * Set Pilot entity state
     *
     * @param state passenger entity current state
     */
    public void setPassengerState(int state)
    {
        this.passengerState = state;
        this.validPassengerState = true;
    }

    /**
     * Return Hostess entity state validity
     *
     * @return Hostess entity state validity
     */
    public boolean getHostValidState()
    {
        return this.validHostState;
    }

    /**
     * Return Pilot entity state validity
     *
     * @return Pilot entity state validity
     */
    public boolean getPilotValidState()
    {
        return this.validPilotState;
    }

    /**
     * Return Passenger entity state validity
     *
     * @return Passenger entity state validity
     */
    public boolean getPassengerValidState()
    {
        return this.validPassengerState;
    }

    /**
     * Set packet type, see PacketType for possible types
     *
     * @return packet type
     */
    public MessageType getType() {
        return this.type;
    }

    /**
     * Get Packet type
     *
     * @param type
     */
    public void setType(MessageType type)
    {
        this.type = type;
        this.validType = true;
    }

    /**
     * Get type validity
     *
     * @return packet type validity
     */
    public boolean getValidType()
    {
        return this.validType;
    }

    /**
     * Get first integer value
     *
     * @return first integer
     */
    public int getInt1()
    {
        return this.int1;
    }

    /**
     * Set first integer value
     *
     * @param int1
     */
    public void setInt1(int int1)
    {
        this.int1 = int1;
        this.validInt1 = true;
    }

    /**
     * Get first integer value validity
     *
     * @return integer 1 is valid
     */
    public boolean getValidInt1()
    {
        return this.validInt1;
    }

    /**
     * Get second integer value
     *
     * @return second integer
     */
    public int getInt2()
    {
        return this.int2;
    }

    /**
     * Set second integer value
     *
     * @param int2
     */
    public void setInt2(int int2)
    {
        this.int2 = int2;
        this.validInt2 = true;
    }

    /**
     * Get second integer value validity
     *
     * @return integer 1 is valid
     */
    public boolean getValidInt2()
    {
        return this.validInt2;
    }

    /**
     * Get third integer value
     *
     * @return third integer
     */
    public int getInt3()
    {
        return this.int3;
    }

    /**
     * Set third integer value
     *
     * @param int3
     */
    public void setInt3(int int3)
    {
        this.int3 = int3;
        this.validInt3 = true;
    }

    /**
     * Get third integer value validity
     *
     * @return integer 1 is valid
     */
    public boolean getValidInt3()
    {
        return this.validInt3;
    }

    /**
     * Get first boolean value
     *
     * @return first boolean
     */
    public boolean getBool1()
    {
        return this.bool1;
    }

    /**
     * Set first boolean to given value
     *
     * @param bool1
     */
    public void setBool1(boolean bool1)
    {
        this.bool1 = bool1;
        this.validBool1 = true;
    }

    /**
     * Get first boolean value validity
     *
     * @return boolean 1 validity
     */
    public boolean getValidBool1()
    {
        return this.validBool1;
    }

    /**
     * Get second boolean value
     *
     * @return second boolean
     */
    public boolean getBool2()
    {
        return this.bool2;
    }

    /**
     * Set second boolean value to given value
     *
     * @param bool2
     */
    public void setBool2(boolean bool2)
    {
        this.bool2 = bool2;
        this.validBool2 = true;
    }

    /**
     * Get second boolean value validity
     *
     * @return boolean 1 validity
     */
    public boolean getValidBool2()
    {
        return this.validBool2;
    }

    /**
     * Get intArray1 value
     *
     * @return array
     */
    public int[] getIntArray1() {
        return this.intArray1;
    }

    /**
     * set intArray1 value to be sent
     *
     * @param intArray1
     */
    public void setIntArray1(int[] intArray1) {
        this.intArray1 = intArray1;
        this.validIntArray1 = true;
    }

    /**
     * Get validity of intArray1 field
     *
     * @return validIntArray1
     */
    public boolean getValidIntArray1() {
        return this.validIntArray1;
    }

    /**
     * Get intArray2 value
     *
     * @return array
     */
    public int[] getIntArray2() {
        return this.intArray2;
    }

    /**
     * set intArray2 value to be sent
     *
     * @param intArray2
     */
    public void setIntArray2(int[] intArray2) {
        this.intArray2 = intArray2;
        this.validIntArray2 = true;
    }

    /**
     * Get validity of intArray2 field
     *
     * @return validIntArray2
     */
    public boolean getValidIntArray2() {
        return this.validIntArray2;
    }

    /**
     * To String method
     */
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", HostState='" + getHostState() + "'" + ", type='" + getType() + "'"
                + ", int1='" + getInt1() + "'" + ", int2='" + getInt2() + "'" + ", int3='" + getInt3() + "'"
                + ", bool1='" + bool1 + "'" + ", bool2='" + bool2 + "'" + ", intArray1='" + getIntArray1() + "'"
                + ", intArray2='" + getIntArray2() + "'" + "}";
    }

}