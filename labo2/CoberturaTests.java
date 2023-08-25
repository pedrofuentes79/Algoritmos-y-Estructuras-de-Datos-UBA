package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CoberturaTests {
    Cobertura cobertura = new Cobertura();

    @Test
    void testFizzBuzz() {
        // mult3y5
        assertEquals(cobertura.fizzBuzz(15), "FizzBuzz");
        // mult3 no 5
        assertEquals(cobertura.fizzBuzz(9), "Fizz");
        // mult 5 no 3
        assertEquals(cobertura.fizzBuzz(10), "Buzz");
        // mult no 5 no 3
        assertEquals(cobertura.fizzBuzz(4), "4");
    }

    @Test
    void testNumeroCombinatorio() {
        // caso doble 0, doble 1, doble numero igual
        assertEquals(cobertura.numeroCombinatorio(0, 0), 1);
        assertEquals(cobertura.numeroCombinatorio(1, 1), 1);
        assertEquals(cobertura.numeroCombinatorio(2, 2), 1);                        
        // 0-1 1-0 2-0 2-1 
        assertEquals(cobertura.numeroCombinatorio(1, 0), 1);
        assertEquals(cobertura.numeroCombinatorio(0, 1), 0);     
        assertEquals(cobertura.numeroCombinatorio(2, 0), 1);                                                                   
        assertEquals(cobertura.numeroCombinatorio(0, 2), 0);
        assertEquals(cobertura.numeroCombinatorio(2, 2), 1);                                                
        assertEquals(cobertura.numeroCombinatorio(2, 2), 1);                        
    }

    @Test
    void testRepeticionesConsecutivas() {
        assertTrue(false);
    }
}
