/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package io.reactivex.internal.operators.observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.reactivex.rxjava3.core.ObservableSource;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import org.junit.Ignore;
import org.junit.Test;

import com.rxjava3.reactivex.exceptions.TestException;

public class BlockingObservableToIteratorTest {

    @Test
    public void toIterator() {
        Observable<String> obs = Observable.just("one", "two", "three");

        Iterator<String> it = obs.blockingIterable().iterator();

        assertTrue(it.hasNext());
        assertEquals("one", it.next());

        assertTrue(it.hasNext());
        assertEquals("two", it.next());

        assertTrue(it.hasNext());
        assertEquals("three", it.next());

        assertFalse(it.hasNext());

    }

    @Test(expected = TestException.class)
    public void toIteratorWithException() {
        Observable<String> obs = Observable.unsafeCreate(new ObservableSource<String>() {

            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onSubscribe(Disposables.empty());
                observer.onNext("one");
                observer.onError(new TestException());
            }
        });

        Iterator<String> it = obs.blockingIterable().iterator();

        assertTrue(it.hasNext());
        assertEquals("one", it.next());

        assertTrue(it.hasNext());
        it.next();
    }

    @Ignore("subscribe() should not throw")
    @Test(expected = TestException.class)
    public void exceptionThrownFromOnSubscribe() {
        Iterable<String> strings = Observable.unsafeCreate(new ObservableSource<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                throw new TestException("intentional");
            }
        }).blockingIterable();
        for (String string : strings) {
            // never reaches here
            System.out.println(string);
        }
    }

    @Test
    public void dispose() {
        BlockingObservableIterator<Integer> it = new BlockingObservableIterator<Integer>(128);

        assertFalse(it.isDisposed());

        it.dispose();

        assertTrue(it.isDisposed());
    }

    @Test
    public void interruptWait() {
        BlockingObservableIterator<Integer> it = new BlockingObservableIterator<Integer>(128);

        try {
            Thread.currentThread().interrupt();

            it.hasNext();
        } catch (RuntimeException ex) {
            assertTrue(ex.toString(), ex.getCause() instanceof InterruptedException);
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyThrowsNoSuch() {
        BlockingObservableIterator<Integer> it = new BlockingObservableIterator<Integer>(128);
        it.onComplete();
        it.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        BlockingObservableIterator<Integer> it = new BlockingObservableIterator<Integer>(128);
        it.remove();
    }
}
