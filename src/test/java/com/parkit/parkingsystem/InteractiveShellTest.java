package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InteractiveShellTest {
    @Mock
    private static InputReaderUtil inputReaderUtil;

    @Disabled
    @Test
    public void processExitingVehicleTest() {
        when(inputReaderUtil.readSelection())
                .thenReturn(1)
                .thenReturn(3);
        InteractiveShell.loadInterface();
    }
}
