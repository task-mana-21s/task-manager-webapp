package lt.viko.eif.pss.taskmanagerwebapp;

import lt.viko.eif.pss.taskmanagerwebapp.model.Status;
import lt.viko.eif.pss.taskmanagerwebapp.model.Status.StatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**

 Unit tests for the Status class.
 */
public class StatusTests {

    private Status status;

    /**

     Setup method executed before each test case.
     */
    @BeforeEach
    public void setup() {
        status = new Status();
    }

    /**

     Test case to verify the setId method.
     */
    @Test
    public void setId_ValidId_SetsId() {
        Long id = 1L;
        status.setId(id);

        Assertions.assertEquals(id, status.getId());
    }

    /**

     Test case to verify the setStatus method with a valid StatusEnum.
     */
    @Test
    public void setStatus_ValidStatusEnum_SetsStatus() {
        StatusEnum statusEnum = StatusEnum.DOING;
        status.setStatus(statusEnum);

        Assertions.assertEquals(statusEnum, status.getStatus());
    }

    /**

     Test case to verify the setStatus method with a null StatusEnum.
     Expects an IllegalArgumentException to be thrown.
     */
    @Test
    public void setStatus_NullStatusEnum_ThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> status.setStatus(null));
    }

    /**

     Test case to verify the setStatusEnum method.
     */
    @Test
    public void setStatusEnum_ValidEnum_SetsStatus() {
        status.setStatusEnum(StatusEnum.DONE);

        Assertions.assertEquals(StatusEnum.DONE, status.getStatus());
    }

    /**

     Test case to verify the getStatusEnum method with an initialized status.
     */
    @Test
    public void getStatusEnum_InitializedStatus_ReturnsStatusEnum() {
        status.setStatus(StatusEnum.TO_DO);

        Assertions.assertEquals(StatusEnum.TO_DO, status.getStatusEnum());
    }

    /**

     Test case to verify the getStatusEnum method with a null status.
     */
    @Test
    public void getStatusEnum_NullStatus_ReturnsNull() {
        Assertions.assertNull(status.getStatusEnum());
    }
}
