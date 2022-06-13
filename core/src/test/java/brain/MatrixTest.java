package brain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    Matrix matrix;
    Matrix matrix2;
    @BeforeEach
    void setUp() {

    }

    @Test
    void testAdd() {

    }

    @Test
    void subtract() {

    }

    @Test
    void testSubtract() {
    }

    @Test
    void multiply() {
    }

    @Test
    void randomize() {
        assertTrue(Arrays.stream(matrix.data).allMatch(
                (doubles -> Arrays.stream(doubles).allMatch((value) -> -1 < value && value <= 1))));
    }

    @Test
    void setData() {

    }
}