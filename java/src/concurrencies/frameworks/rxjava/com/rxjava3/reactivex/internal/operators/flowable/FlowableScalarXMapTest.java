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

package io.reactivex.internal.operators.flowable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableScalarXMap;
import io.reactivex.rxjava3.internal.subscriptions.EmptySubscription;
import io.reactivex.rxjava3.internal.subscriptions.ScalarSubscription;

import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.rxjava3.reactivex.exceptions.TestException;
import com.rxjava3.reactivex.testsupport.TestHelper;

public class FlowableScalarXMapTest {

    @Test
    public void utilityClass() {
        TestHelper.checkUtilityClass(FlowableScalarXMap.class);
    }

    static final class CallablePublisher implements Publisher<Integer>, Supplier<Integer> {
        @Override
        public void subscribe(Subscriber<? super Integer> s) {
            EmptySubscription.error(new TestException(), s);
        }

        @Override
        public Integer get() throws Exception {
            throw new TestException();
        }
    }

    static final class EmptyCallablePublisher implements Publisher<Integer>, Supplier<Integer> {
        @Override
        public void subscribe(Subscriber<? super Integer> s) {
            EmptySubscription.complete(s);
        }

        @Override
        public Integer get() throws Exception {
            return null;
        }
    }

    static final class OneCallablePublisher implements Publisher<Integer>, Supplier<Integer> {
        @Override
        public void subscribe(Subscriber<? super Integer> s) {
            s.onSubscribe(new ScalarSubscription<Integer>(s, 1));
        }

        @Override
        public Integer get() throws Exception {
            return 1;
        }
    }

    @Test
    public void tryScalarXMap() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        assertTrue(FlowableScalarXMap.tryScalarXMapSubscribe(new CallablePublisher(), ts, new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer f) throws Exception {
                return Flowable.just(1);
            }
        }));

        ts.assertFailure(TestException.class);
    }

    @Test
    public void emptyXMap() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();

        assertTrue(FlowableScalarXMap.tryScalarXMapSubscribe(new EmptyCallablePublisher(), ts, new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer f) throws Exception {
                return Flowable.just(1);
            }
        }));

        ts.assertResult();
    }

    @Test
    public void mapperCrashes() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();

        assertTrue(FlowableScalarXMap.tryScalarXMapSubscribe(new OneCallablePublisher(), ts, new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer f) throws Exception {
                throw new TestException();
            }
        }));

        ts.assertFailure(TestException.class);
    }

    @Test
    public void mapperToJust() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();

        assertTrue(FlowableScalarXMap.tryScalarXMapSubscribe(new OneCallablePublisher(), ts, new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer f) throws Exception {
                return Flowable.just(1);
            }
        }));

        ts.assertResult(1);
    }

    @Test
    public void mapperToEmpty() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();

        assertTrue(FlowableScalarXMap.tryScalarXMapSubscribe(new OneCallablePublisher(), ts, new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer f) throws Exception {
                return Flowable.empty();
            }
        }));

        ts.assertResult();
    }

    @Test
    public void mapperToCrashingCallable() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();

        assertTrue(FlowableScalarXMap.tryScalarXMapSubscribe(new OneCallablePublisher(), ts, new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer f) throws Exception {
                return new CallablePublisher();
            }
        }));

        ts.assertFailure(TestException.class);
    }

    @Test
    public void scalarMapToEmpty() {
        FlowableScalarXMap.scalarXMap(1, new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer v) throws Exception {
                return Flowable.empty();
            }
        })
        .test()
        .assertResult();
    }

    @Test
    public void scalarMapToCrashingCallable() {
        FlowableScalarXMap.scalarXMap(1, new Function<Integer, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(Integer v) throws Exception {
                return new CallablePublisher();
            }
        })
        .test()
        .assertFailure(TestException.class);
    }

    @Test
    public void scalarDisposableStateCheck() {
        TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
        ScalarSubscription<Integer> sd = new ScalarSubscription<Integer>(ts, 1);
        ts.onSubscribe(sd);

        assertFalse(sd.isCancelled());

        assertTrue(sd.isEmpty());

        sd.request(1);

        assertFalse(sd.isCancelled());

        assertTrue(sd.isEmpty());

        ts.assertResult(1);

        try {
            sd.offer(1);
            fail("Should have thrown");
        } catch (UnsupportedOperationException ex) {
            // expected
        }

        try {
            sd.offer(1, 2);
            fail("Should have thrown");
        } catch (UnsupportedOperationException ex) {
            // expected
        }
    }

    @Test
    public void scalarDisposableRunDisposeRace() {
        for (int i = 0; i < TestHelper.RACE_DEFAULT_LOOPS; i++) {
            TestSubscriber<Integer> ts = new TestSubscriber<Integer>();
            final ScalarSubscription<Integer> sd = new ScalarSubscription<Integer>(ts, 1);
            ts.onSubscribe(sd);

            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    sd.request(1);
                }
            };

            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    sd.cancel();
                }
            };

            TestHelper.race(r1, r2);
        }
    }

    @Test
    public void cancelled() {
        ScalarSubscription<Integer> scalar = new ScalarSubscription<Integer>(new TestSubscriber<Integer>(), 1);

        assertFalse(scalar.isCancelled());

        scalar.cancel();

        assertTrue(scalar.isCancelled());
    }
}
