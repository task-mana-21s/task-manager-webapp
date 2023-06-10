package lt.viko.eif.pss.taskmanagerwebapp;

import lt.viko.eif.pss.taskmanagerwebapp.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void testStatus() {
        // Create a new Status object
        Status status = new Status();

        // Set the values for the status
        Long id = 1L;
        String statusName = "to-do";
        status.setId(id);
        status.setStatus(statusName);

        // Verify the values using getters
        Assertions.assertEquals(id, status.getId());
        Assertions.assertEquals(statusName, status.getStatus());
    }

    @Test
    public void testStatusEquality() {
        // Create two Status objects with the same values
        Status status1 = Status.builder()
                .Id(1L)
                .status("to-do")
                .build();
        Status status2 = Status.builder()
                .Id(1L)
                .status("to-do")
                .build();

        // Verify that the two Status objects are equal
        Assertions.assertEquals(status1, status2);
    }

    @Test
    public void testStatusInequality() {
        // Create two Status objects with different values
        Status status1 = Status.builder()
                .Id(1L)
                .status("to-do")
                .build();
        Status status2 = Status.builder()
                .Id(2L)
                .status("doing")
                .build();

        // Verify that the two Status objects are not equal
        Assertions.assertNotEquals(status1, status2);
    }
}