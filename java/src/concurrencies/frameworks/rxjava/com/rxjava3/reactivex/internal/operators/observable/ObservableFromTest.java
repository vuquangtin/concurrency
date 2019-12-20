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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.internal.fuseable.QueueFuseable;
import io.reactivex.rxjava3.internal.fuseable.ScalarSupplier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.rxjava3.reactivex.testsupport.TestHelper;
import com.rxjava3.reactivex.testsupport.TestObserverEx;

public class ObservableFromTest {

    @Test
    public void fromFutureTimeout() throws Exception {
        Observable.fromFuture(Observable.never()
        .toFuture(), 100, TimeUnit.MILLISECONDS, Schedulers.io())
        .test()
        .awaitDone(5, TimeUnit.SECONDS)
        .assertFailure(TimeoutException.class);
    }

    @Test
    public void fromPublisher() {
        Observable.fromPublisher(Flowable.just(1))
        .test()
        .assertResult(1);
    }

    @Test
    public void just10() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .test()
        .assertResult(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    @Test
    public void fromArrayEmpty() {
        assertSame(Observable.empty(), Observable.fromArray());
    }

    @Test
    public void fromArraySingle() {
        assertTrue(Observable.fromArray(1) instanceof ScalarSupplier);
    }

    @Test
    public void fromPublisherDispose() {
        TestHelper.checkDisposed(Flowable.just(1).toObservable());
    }

    @Test
    public void fromPublisherDoubleOnSubscribe() {
        TestHelper.checkDoubleOnSubscribeFlowableToObservable(new Function<Flowable<Object>, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(Flowable<Object> f) throws Exception {
                return f.toObservable();
            }
        });
    }

    @Test
    public void fusionRejected() {
        TestObserverEx<Integer> to = new TestObserverEx<Integer>(QueueFuseable.ASYNC);

        Observable.fromArray(1, 2, 3)
        .subscribe(to);

        to.assertFusionMode(QueueFuseable.NONE)
        .assertResult(1, 2, 3);
    }
}
