package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Date;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
//import static com.shazam.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
    @InjectMocks
    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeAll
    public static void setup(){
        inputReaderUtil = new InputReaderUtil();
        parkingSpotDAO = new ParkingSpotDAO();
        ticketDAO = new TicketDAO();
        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

    }

    @Test
    public void processExitingVehicleSuccessTest() {
        try {
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

            when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);

            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

            parkingService.processExitingVehicle();
            verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up test mock objects");
        }
    }

    @Test
    public void processExitingVehicleErrorTest() throws Exception {
            when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
            when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(false);

            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

            Ticket result = parkingService.processExitingVehicle();
            assertNull(result);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    public void processExitingVehicleExceptionTest() throws Exception {
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        Ticket ticket = new Ticket();
        ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
        when(ticketDAO.getTicket(anyString())).thenReturn(ticket);

        FareCalculatorService fareCalculatorService = mock(FareCalculatorService.class);
        doThrow(IllegalArgumentException.class).when(fareCalculatorService).calculateFare(ticket);

        parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        Ticket result = parkingService.processExitingVehicle();
        assertNull(result);
    }

    @Test
    public void getNextParkingNumberIfAvailableCarTest() {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);

        ParkingSpot parkingSpotIn = new ParkingSpot(1, ParkingType.CAR, true);
        ParkingSpot parkingSpotOut = parkingService.getNextParkingNumberIfAvailable();
        Assertions.assertEquals(parkingSpotIn, parkingSpotOut);
    }

    @Test
    public void getNextParkingNumberIfAvailableBikeTest() {
        when(inputReaderUtil.readSelection()).thenReturn(2);
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(1);

        ParkingSpot parkingSpotIn = new ParkingSpot(1, ParkingType.CAR, true);
        ParkingSpot parkingSpotOut = parkingService.getNextParkingNumberIfAvailable();
        Assertions.assertEquals(parkingSpotIn, parkingSpotOut);
    }

    @Test
    public void getNextParkingNumberIfAvailableExceptionTest() {
        when(inputReaderUtil.readSelection()).thenReturn(2);
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE)).thenReturn(0);

        ParkingSpot parkingSpotOut = parkingService.getNextParkingNumberIfAvailable();
        assertNull(parkingSpotOut);
    }

    @Test
    public void getNextParkingNumberIfAvailableOtherTest() {
        when(inputReaderUtil.readSelection()).thenReturn(3);

        ParkingSpot parkingSpotOut = parkingService.getNextParkingNumberIfAvailable();
        assertNull(parkingSpotOut);
    }

    @Test
    public void processIncomingVehicleTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(1);

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        Ticket ticket = new Ticket();
        ticket.setInTime(new Date(System.currentTimeMillis()));
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");

        Ticket ticketToTest = parkingService.processIncomingVehicle();
        // https://stackoverflow.com/questions/27605714/test-two-instances-of-object-are-equal-junit#comment69360336_27605802
        assertEquals(ticketToTest.toString(), ticket.toString());
    }

    @Test
    public void processIncomingVehicleNullTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR)).thenReturn(0);

        Ticket ticket = new Ticket();
        Ticket ticketToTest = parkingService.processIncomingVehicle();
        // https://stackoverflow.com/questions/27605714/test-two-instances-of-object-are-equal-junit#comment69360336_27605802
        assertEquals(ticketToTest.toString(), ticket.toString());
    }

}
