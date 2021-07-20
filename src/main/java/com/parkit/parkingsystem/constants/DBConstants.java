package com.parkit.parkingsystem.constants;

public final class DBConstants {
    /**
     * A dummy constructor for CheckStyle.
     */
    private DBConstants() {
    }

    /**
     * The prepared SQL statement for getting the next parking spot
     * from the database.
     */
    public static final String GET_NEXT_PARKING_SPOT =
            "select min(PARKING_NUMBER) "
                    + "from parking "
                    + "where AVAILABLE = true and TYPE = ?";

    /**
     * The prepared SQL statement for updating a parking spot in the database.
     */
    public static final String UPDATE_PARKING_SPOT =
            "update parking set available = ? where PARKING_NUMBER = ?";

    /**
     * The prepared SQL statement for saving a ticket in the database.
     */
    public static final String SAVE_TICKET =
            "insert into ticket("
                    + "PARKING_NUMBER, "
                    + "VEHICLE_REG_NUMBER, "
                    + "PRICE, "
                    + "IN_TIME, "
                    + "OUT_TIME) "
                    + "values(?,?,?,?,?)";

    /**
     * The prepared statement for updating a ticket.
     */
    public static final String UPDATE_TICKET =
            "update ticket set PRICE=?, OUT_TIME=? where ID=?";

    /**
     * The prepared SQL statement for getting a ticket.
     */
    public static final String GET_TICKET =
            "select t.PARKING_NUMBER, "
                    + "t.ID, "
                    + "t.PRICE, "
                    + "t.IN_TIME, "
                    + "t.OUT_TIME, "
                    + "p.TYPE "
                    + "from ticket t,parking p "
                    + "where p.parking_number = t.parking_number "
                    + "and t.VEHICLE_REG_NUMBER=? "
                    + "order by t.IN_TIME "
                    + "limit 1";
}
