package Client.common;

public enum MessageType
{
    /**
     * Hostess message types
     */
    PREPARE_FOR_PASS_BOARDING, WAIT_FOR_NEXT_FLIGHT, GET_N_PASSENGERS, WAIT_FOR_NEXT_PASSENGER, CHECK_DOCUMENTS, INFORM_PLANE_READY_TAKEOFF,


    /**
     * Passenger message types
     */
    WAIT_IN_QUEUE, BOARD_THE_PLANE, WAIT_END_FLIGHT, LEAVE_PLANE, SHOW_DOCUMENTS,

    /**
     * Pilot message types
     */
    PARK_AT_TRANSFER_GATE, INFORM_PLANE_READY_BOARDING, WAIT_FOR_ALL_IN_BOARD, FLY_TO_DESTINATION, SET_AT_DESTINATION, ANNOUNCE_ARRIVAL, FLY_TO_DEPARTURE
}
