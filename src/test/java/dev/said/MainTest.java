package dev.said;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
class MainTest {

    Calculator calculator = new Calculator();

    @Test
    void itShouldAddTwoNumbers() {

        // given
        int a = 20;
        int b = 30;

        // when
        int result = calculator.add(a, b);

        // then
        int expected = 50;
        assertThat(result).isEqualTo(50);

    }

    class Calculator {
        int add(int a, int b) {
            return a + b;
        }
    }

}