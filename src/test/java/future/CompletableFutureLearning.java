package future;

import future.CompletableFutureLearning.Robot.State;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

@Slf4j
public class CompletableFutureLearning {

    @Test
    public void complete() throws ExecutionException, InterruptedException {

        // when
        val robot = createFuture(Robot::new).get();

        // then
        assertEquals(State.CREATED, robot.getState());
    }

    @Test(expected = ExecutionException.class)
    public void completeExceptionally() throws Exception {

        // given
        val future = createFuture(() -> {
            Robot robot = new Robot();
            robot.destroy();
            robot.destroy(); // throw exception
            return robot.getState();
        });

        // when
        future.get();
    }

    @Test
    public void create() throws Exception {

        // given
        val executor = Executors.newSingleThreadExecutor();

        // when
        CompletableFuture.supplyAsync(Robot::new);
        CompletableFuture.supplyAsync(Robot::new, executor);
        CompletableFuture.runAsync(Robot::new);
        CompletableFuture.runAsync(Robot::new, executor);
    }

    @Test
    public void thenApply() throws Exception {

        // when
        val name = CompletableFuture
                .supplyAsync(Robot::new)
                .thenApply(Robot::getState)
                .thenApplyAsync(Enum::name)
                .get();

        // then
        assertEquals("CREATED", name);
    }

    @Test
    public void thenAccept() throws Exception {

        // given
        val robot = new Robot();

        // when
        createFuture(() -> robot)
                .thenAccept(Robot::destroy);

        assertEquals(State.CREATED, robot.getState());
        Thread.sleep(550);
        assertEquals(State.DESTROYED, robot.getState());
    }

    @Test
    public void thenRun() throws Exception {
        createFuture(Robot::new)
                .thenRun(() -> {
                    throw new RuntimeException("I don't care this exception.");
                });
    }

    @Test
    public void exceptionally() throws Exception {

        // when
        val robot = createFuture(Robot::new)
                .thenApply(Robot::destroy)
                .thenApply(Robot::destroy)
                .exceptionally(ex -> new Robot())
                .get();

        // then
        assertEquals(State.CREATED, robot.getState());
    }

    @Test
    public void handle() throws Exception {

        // when
        val robot = createFuture(Robot::new)
                .thenApply(Robot::destroy)
                .thenApply(Robot::destroy)
                .handle((ok, ex) -> Optional
                        .ofNullable(ok)
                        .orElseGet(() -> {
                            log.error("IllegalStateToDestroy", ex);
                            return new Robot();
                        }))
                .get();

        // then
        assertEquals(State.CREATED, robot.getState());
    }

    @Test
    public void thenCompose() throws Exception {

        // thenApply가 사용되어 future가 중첩됨.
        createFuture(Robot::new)
                .thenApply(created -> createFuture(Robot::new))
                .get()
                .get();

        // thenCompose를 활용해 중첩 대신 체이닝.
        createFuture(Robot::new)
                .thenCompose(created -> createFuture(Robot::new))
                .get();
    }

    @Test
    public void thenCombine() throws Exception {

        // given
        val robot1CreationFuture = createFuture(Robot::new);
        val robot2CreationFuture = createFuture(Robot::new);

        // when
        val combiningFuture = robot1CreationFuture
                .thenCombine(
                        robot2CreationFuture,
                        (robot1, robot2) -> {
                            robot1.destroy();
                            robot2.destroy();
                            return "Finished";
                        });

        // then
        assertEquals("Finished", combiningFuture.get());
    }

    private <T> CompletableFuture<T> createFuture(Callable<T> action) {
        val future = new CompletableFuture<T>();
        Executors.newSingleThreadExecutor()
                .submit(() -> {

                    try {
                        Thread.sleep(500);
                        future.complete(action.call());
                    } catch (Exception e) {
                        future.completeExceptionally(e);
                    }
                });

        return future;
    }

    static class Robot {

        @Getter
        private State state = State.CREATED;

        Robot destroy() {
            if (state == State.DESTROYED) throw new IllegalStateException("already.destroyed");
            state = State.DESTROYED;
            return this;
        }

        enum State {
            CREATED, DESTROYED
        }
    }
}
