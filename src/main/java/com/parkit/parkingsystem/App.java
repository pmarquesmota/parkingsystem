package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/*
* The main class of the programm
* @author openclassrooms
 */
 final class App {
     /*
    * Used to initialize the log4J system
    * @author openclassrooms
    */
    private static final Logger LOGGER = LogManager.getLogger("App");

   /*
   * The main class, launches the project
   * @parameter String the arguments of the program
   */
   private static  void main(final String[] args) {
        LOGGER.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
