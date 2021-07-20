package com.parkit.parkingsystem.model;

import java.util.Calendar;
import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String toString(){
        String parkingSpotString;
        String inTimeString;
        String outTimeString;

        if(parkingSpot==null){
            parkingSpotString = "null";
        } else {
            parkingSpotString = parkingSpot.toString();
        }
        if(inTime==null){
            inTimeString = "null";
        } else {
            inTimeString = inTime.toString();
        }
        if(outTime==null){
            outTimeString = "null";
        } else {
            outTimeString = outTime.toString();
        }
        return "id="+id+
                " parkingSpot="+parkingSpotString+
                " vehicleRegNumber="+vehicleRegNumber+
                " price="+price+
                " inTime="+inTimeString+
                " outTime"+outTimeString;
    }
}
