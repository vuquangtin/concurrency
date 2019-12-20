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
package io.reactivex.internal.operators.observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import io.reactivex.rxjava3.core.ObservableSource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

import com.rxjava3.reactivex.exceptions.TestException;

public class BlockingObservableToFutureTest {
    @Ignore("No separate file")
    @Test
    public void constructorShouldBePrivate() {
//        TestHelper.checkUtilityClass(FlowableToFuture.class);
    }

    @Test
    public void toFuture() throws InterruptedException, ExecutionException {
        Observable<String> obs = Observable.just("one");
        Future<String> f = obs.toFuture();
        assertEquals("one", f.get());
    }

    @Test
    public void toFutureList() throws InterruptedException, ExecutionException {
        Observable<String> obs = Observable.just("one", "two", "three");
        Future<List<String>> f = obs.toList().toFuture();
        assertEquals("one", f.get().get(0));
        assertEquals("two", f.get().get(1));
        assertEquals("three", f.get().get(2));
    }

    @Test(/* timeout = 5000, */expected = IndexOutOfBoundsException.class)
    public void exceptionWithMoreThanOneElement() throws Throwable {
        Observable<String> obs = Observable.just("one", "two");
        Future<String> f = obs.toFuture();
        try {
            // we expect an exception since there are more than 1 element
            f.get();
            fail("Should have thrown!");
        }
        catch (ExecutionException e) {
            throw e.getCause();
        }
    }

    @Test
    public void toFutureWithException() {
        Observable<String> obs = Observable.unsafeCreate(new ObservableSource<String>() {

            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onSubscribe(Disposables.empty());
                observer.onNext("one");
                observer.onError(new TestException());
            }
        });

        Future<String> f = obs.toFuture();
        try {
            f.get();
            fail("expected exception");
        } catch (Throwable e) {
            assertEquals(TestException.class, e.getCause().getClass());
        }
    }

    @Test(expected = CancellationException.class)
    public void getAfterCancel() throws Exception {
        Observable<String> obs = Observable.never();
        Future<String> f = obs.toFuture();
        boolean cancelled = f.cancel(true);
        assertTrue(cancelled);  // because OperationNeverComplete never does
        f.get();                // Future.get() docs require this to throw
    }

    @Test(expected = CancellationException.class)
    public void getWithTimeoutAfterCancel() throws Exception {
        Observable<String> obs = Observable.never();
        Future<String> f = obs.toFuture();
        boolean cancelled = f.cancel(true);
        assertTrue(cancelled);  // because OperationNeverComplete never does
        f.get(Long.MAX_VALUE, TimeUnit.NANOSECONDS);    // Future.get() docs require this to throw
    }

    @Test(expected = NoSuchElementException.class)
    public void getWithEmptyFlowable() throws Throwable {
        Observable<String> obs = Observable.empty();
        Future<String> f = obs.toFuture();
        try {
            f.get();
        }
        catch (ExecutionException e) {
            throw e.getCause();
        }
    }

    @Ignore("null value is not allowed")
    @Test
    public void getWithASingleNullItem() throws Exception {
        Observable<String> obs = Observable.just((String)null);
        Future<String> f = obs.toFuture();
        assertNull(f.get());
    }
}