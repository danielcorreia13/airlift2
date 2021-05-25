package Common;

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
    private int id;

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
     * Entity State
     *
     * @serial hostState
     */
    private int state;

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
     * String value to be transported
     */

    private String str;

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
        this.validBool1 = false;
    }

    /**
     * Return entity ID
     *
     * @return entity ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set entity ID
     *
     * @param id entity ID
     */
    public void setId(int id) {
        this.id = id;
        this.validID = true;
    }

    /**
     * Get string
     *
     * @return string
     */
    public String getStr() {
        return str;
    }
    /**
     * Set string to send
     *
     * @param str String to put
     */
    public void setStr(String str) {
        this.str = str;
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
    public void setState(int state)
    {
        this.state = state;
        this.validPilotState = true;
    }


    /**
     * Return entity state
     *
     * @return entity state
     */
    public int getState() {
        return state;
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
}