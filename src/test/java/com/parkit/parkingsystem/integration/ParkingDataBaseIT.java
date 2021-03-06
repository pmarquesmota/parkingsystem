package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.setDataBaseConfig(dataBaseTestConfig);
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown() {

    }

    @Test
    public void testParkingACar() {
        FareCalculatorService fareCalculatorService = new FareCalculatorService();

        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, fareCalculatorService);
        Ticket ticket = parkingService.processIncomingVehicle();
        //check that a ticket is actually saved in DB and Parking table is updated with availability
        assertEquals(ticket.getParkingSpot().getId(), ticketDAO.getTicket(ticket.getVehicleRegNumber()).getParkingSpot().getId());
        assertEquals(ticketDAO.getTicket(ticket.getVehicleRegNumber()).getParkingSpot().isAvailable(), false);
    }

    @Test
    public void testParkingLotExit() {
        testParkingACar();
        FareCalculatorService fareCalculatorService = new FareCalculatorService();
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, fareCalculatorService);
        parkingService.processExitingVehicle();
        //Check that the fare generated and out time are populated correctly in the database
        Ticket ticket = parkingService.processExitingVehicle();
        //Check that the fare generated and out time are populated correctly in the database
        Ticket ticketDb = ticketDAO.getTicket(ticket.getVehicleRegNumber());
        assertEquals(ticket.getPrice(), ticketDAO.getTicket(ticket.getVehicleRegNumber()).getPrice());
        assertEquals(ticket.getOutTime().getTime(), ticketDAO.getTicket(ticket.getVehicleRegNumber()).getOutTime().getTime(), 5000);
    }

}
