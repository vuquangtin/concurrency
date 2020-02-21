package com.thread.shutdownhook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */


public class ShutdownHookUnitTest {

    @Test
    public void givenAHook_WhenShutsDown_ThenHookShouldBeExecuted() {
        Thread printingHook = new Thread(() -> System.out.println("In the middle of a shutdown"));
        Runtime.getRuntime().addShutdownHook(printingHook);
    }

    @Test
    public void addingAHook_WhenThreadAlreadyStarted_ThenThrowsAnException() {
        Thread longRunningHook = new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {}
        });
        longRunningHook.start();

        assertThatThrownBy(() -> Runtime.getRuntime().addShutdownHook(longRunningHook))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Hook already running");
    }

    @Test
    public void addingAHook_WhenAlreadyExists_ThenAnExceptionWouldBeThrown() {
        Thread unfortunateHook = new Thread(() -> {});
        Runtime.getRuntime().addShutdownHook(unfortunateHook);

        assertThatThrownBy(() -> Runtime.getRuntime().addShutdownHook(unfortunateHook))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Hook previously registered");
    }

    @Test
    public void removeAHook_WhenItIsAlreadyRegistered_ThenWouldDeRegisterTheHook() {
        Thread willNotRun = new Thread(() -> System.out.println("Won't run!"));
        Runtime.getRuntime().addShutdownHook(willNotRun);

        assertThat(Runtime.getRuntime().removeShutdownHook(willNotRun)).isTrue();
    }
}