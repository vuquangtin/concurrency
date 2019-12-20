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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.processors.PublishProcessor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Subscriber;

import com.rxjava3.reactivex.testsupport.TestHelper;
import com.rxjava3.reactivex.testsupport.TestObserverEx;
import com.rxjava3.reactivex.testsupport.TestSubscriberEx;

public class FlowableToSortedListTest {

    @Test
    public void sortedListFlowable() {
        Flowable<Integer> w = Flowable.just(1, 3, 2, 5, 4);
        Flowable<List<Integer>> flowable = w.toSortedList().toFlowable();

        Subscriber<List<Integer>> subscriber = TestHelper.mockSubscriber();
        flowable.subscribe(subscriber);

        verify(subscriber, times(1)).onNext(Arrays.asList(1, 2, 3, 4, 5));
        verify(subscriber, Mockito.never()).onError(any(Throwable.class));
        verify(subscriber, times(1)).onComplete();
    }

    @Test
    public void sortedListWithCustomFunctionFlowable() {
        Flowable<Integer> w = Flowable.just(1, 3, 2, 5, 4);
        Flowable<List<Integer>> flowable = w.toSortedList(new Comparator<Integer>() {

            @Override
            public int compare(Integer t1, Integer t2) {
                return t2 - t1;
            }

        }).toFlowable();

        Subscriber<List<Integer>> subscriber = TestHelper.mockSubscriber();
        flowable.subscribe(subscriber);

        verify(subscriber, times(1)).onNext(Arrays.asList(5, 4, 3, 2, 1));
        verify(subscriber, Mockito.never()).onError(any(Throwable.class));
        verify(subscriber, times(1)).onComplete();
    }

    @Test
    public void withFollowingFirstFlowable() {
        Flowable<Integer> f = Flowable.just(1, 3, 2, 5, 4);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), f.toSortedList().toFlowable().blockingFirst());
    }

    @Test
    public void backpressureHonoredFlowable() {
        Flowable<List<Integer>> w = Flowable.just(1, 3, 2, 5, 4).toSortedList().toFlowable();
        TestSubscriber<List<Integer>> ts = new TestSubscriber<List<Integer>>(0L);

        w.subscribe(ts);

        ts.assertNoValues();
        ts.assertNoErrors();
        ts.assertNotComplete();

        ts.request(1);

        ts.assertValue(Arrays.asList(1, 2, 3, 4, 5));
        ts.assertNoErrors();
        ts.assertComplete();

        ts.request(1);

        ts.assertValue(Arrays.asList(1, 2, 3, 4, 5));
        ts.assertNoErrors();
        ts.assertComplete();
    }

    @Test(timeout = 2000)
    @Ignore("PublishProcessor no longer emits without requests so this test fails due to the race of onComplete and request")
    public void asyncRequestedFlowable() {
        Scheduler.Worker w = Schedulers.newThread().createWorker();
        try {
            for (int i = 0; i < 1000; i++) {
                if (i % 50 == 0) {
                    System.out.println("testAsyncRequested -> " + i);
                }
                PublishProcessor<Integer> source = PublishProcessor.create();
                Flowable<List<Integer>> sorted = source.toSortedList().toFlowable();

                final CyclicBarrier cb = new CyclicBarrier(2);
                final TestSubscriberEx<List<Integer>> ts = new TestSubscriberEx<List<Integer>>(0L);
                sorted.subscribe(ts);
                w.schedule(new Runnable() {
                    @Override
                    public void run() {
                        await(cb);
                        ts.request(1);
                    }
                });
                source.onNext(1);
                await(cb);
                source.onComplete();
                ts.awaitDone(1, TimeUnit.SECONDS);
                ts.assertTerminated();
                ts.assertNoErrors();
                ts.assertValue(Arrays.asList(1));
            }
        } finally {
            w.dispose();
        }
    }

    @Test
    public void sorted() {
        Flowable.just(5, 1, 2, 4, 3).sorted()
        .test()
        .assertResult(1, 2, 3, 4, 5);
    }

    @Test
    public void sortedComparator() {
        Flowable.just(5, 1, 2, 4, 3).sorted(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        })
        .test()
        .assertResult(5, 4, 3, 2, 1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void toSortedListCapacityFlowable() {
        Flowable.just(5, 1, 2, 4, 3).toSortedList(4).toFlowable()
        .test()
        .assertResult(Arrays.asList(1, 2, 3, 4, 5));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void toSortedListComparatorCapacityFlowable() {
        Flowable.just(5, 1, 2, 4, 3).toSortedList(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        }, 4).toFlowable()
        .test()
        .assertResult(Arrays.asList(5, 4, 3, 2, 1));
    }

    @Test
    public void sortedList() {
        Flowable<Integer> w = Flowable.just(1, 3, 2, 5, 4);
        Single<List<Integer>> single = w.toSortedList();

        SingleObserver<List<Integer>> observer = TestHelper.mockSingleObserver();
        single.subscribe(observer);
        verify(observer, times(1)).onSuccess(Arrays.asList(1, 2, 3, 4, 5));
        verify(observer, Mockito.never()).onError(any(Throwable.class));
    }

    @Test
    public void sortedListWithCustomFunction() {
        Flowable<Integer> w = Flowable.just(1, 3, 2, 5, 4);
        Single<List<Integer>> single = w.toSortedList(new Comparator<Integer>() {

            @Override
            public int compare(Integer t1, Integer t2) {
                return t2 - t1;
            }

        });

        SingleObserver<List<Integer>> observer = TestHelper.mockSingleObserver();
        single.subscribe(observer);
        verify(observer, times(1)).onSuccess(Arrays.asList(5, 4, 3, 2, 1));
        verify(observer, Mockito.never()).onError(any(Throwable.class));
    }

    @Test
    public void withFollowingFirst() {
        Flowable<Integer> f = Flowable.just(1, 3, 2, 5, 4);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), f.toSortedList().blockingGet());
    }

    @Test
    @Ignore("Single doesn't do backpressure")
    public void backpressureHonored() {
        Single<List<Integer>> w = Flowable.just(1, 3, 2, 5, 4).toSortedList();
        TestObserver<List<Integer>> to = new TestObserver<List<Integer>>();

        w.subscribe(to);

        to.assertNoValues();
        to.assertNoErrors();
        to.assertNotComplete();

//        ts.request(1);

        to.assertValue(Arrays.asList(1, 2, 3, 4, 5));
        to.assertNoErrors();
        to.assertComplete();

//        ts.request(1);

        to.assertValue(Arrays.asList(1, 2, 3, 4, 5));
        to.assertNoErrors();
        to.assertComplete();
    }

    @Test(timeout = 2000)
    @Ignore("PublishProcessor no longer emits without requests so this test fails due to the race of onComplete and request")
    public void asyncRequested() {
        Scheduler.Worker w = Schedulers.newThread().createWorker();
        try {
            for (int i = 0; i < 1000; i++) {
                if (i % 50 == 0) {
                    System.out.println("testAsyncRequested -> " + i);
                }
                PublishProcessor<Integer> source = PublishProcessor.create();
                Single<List<Integer>> sorted = source.toSortedList();

                final CyclicBarrier cb = new CyclicBarrier(2);
                final TestObserverEx<List<Integer>> to = new TestObserverEx<List<Integer>>();
                sorted.subscribe(to);
                w.schedule(new Runnable() {
                    @Override
                    public void run() {
                        await(cb);
//                        ts.request(1);
                    }
                });
                source.onNext(1);
                await(cb);
                source.onComplete();
                to.awaitDone(1, TimeUnit.SECONDS);
                to.assertTerminated();
                to.assertNoErrors();
                to.assertValue(Arrays.asList(1));
            }
        } finally {
            w.dispose();
        }
    }
    static void await(CyclicBarrier cb) {
        try {
            cb.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (BrokenBarrierException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void toSortedListCapacity() {
        Flowable.just(5, 1, 2, 4, 3).toSortedList(4)
        .test()
        .assertResult(Arrays.asList(1, 2, 3, 4, 5));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void toSortedListComparatorCapacity() {
        Flowable.just(5, 1, 2, 4, 3).toSortedList(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        }, 4)
        .test()
        .assertResult(Arrays.asList(5, 4, 3, 2, 1));
    }
}