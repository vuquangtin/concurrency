/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.reactivex.internal.operators.flowable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Subscriber;

import com.rxjava3.reactivex.testsupport.TestHelper;

public class FlowableDoAfterTerminateTest {

    private Action aAction0;
    private Subscriber<String> subscriber;

    @Before
    public void before() {
        aAction0 = Mockito.mock(Action.class);
        subscriber = TestHelper.mockSubscriber();
    }

    private void checkActionCalled(Flowable<String> input) {
        input.doAfterTerminate(aAction0).subscribe(subscriber);
        try {
            verify(aAction0, times(1)).run();
        } catch (Throwable ex) {
            throw ExceptionHelper.wrapOrThrow(ex);
        }
    }

    @Test
    public void doAfterTerminateCalledOnComplete() {
        checkActionCalled(Flowable.fromArray("1", "2", "3"));
    }

    @Test
    public void doAfterTerminateCalledOnError() {
        checkActionCalled(Flowable.<String> error(new RuntimeException("expected")));
    }

    @Test
    public void nullActionShouldBeCheckedInConstructor() {
        try {
            Flowable.empty().doAfterTerminate(null);
            fail("Should have thrown NullPointerException");
        } catch (NullPointerException expected) {
            assertEquals("onAfterTerminate is null", expected.getMessage());
        }
    }

    @Test
    public void nullFinallyActionShouldBeCheckedASAP() {
        try {
            Flowable
                    .just("value")
                    .doAfterTerminate(null);

            fail();
        } catch (NullPointerException expected) {

        }
    }

    @Test
    public void ifFinallyActionThrowsExceptionShouldNotBeSwallowedAndActionShouldBeCalledOnce() throws Throwable {
        List<Throwable> errors = TestHelper.trackPluginErrors();
        try {
            Action finallyAction = Mockito.mock(Action.class);
            doThrow(new IllegalStateException()).when(finallyAction).run();

            TestSubscriber<String> testSubscriber = new TestSubscriber<String>();

            Flowable
                    .just("value")
                    .doAfterTerminate(finallyAction)
                    .subscribe(testSubscriber);

            testSubscriber.assertValue("value");

            verify(finallyAction).run();

            TestHelper.assertError(errors, 0, IllegalStateException.class);
            // Actual result:
            // Not only IllegalStateException was swallowed
            // But finallyAction was called twice!
        } finally {
            RxJavaPlugins.reset();
        }
    }
}