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

package io.reactivex.internal.operators.maybe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.processors.PublishProcessor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.rxjava3.reactivex.exceptions.TestException;
import com.rxjava3.reactivex.testsupport.TestHelper;

public class MaybeUnsubscribeOnTest {

    @Test
    public void normal() throws Exception {
        PublishProcessor<Integer> pp = PublishProcessor.create();

        final String[] name = { null };

        final CountDownLatch cdl = new CountDownLatch(1);

        pp.doOnCancel(new Action() {
            @Override
            public void run() throws Exception {
                name[0] = Thread.currentThread().getName();
                cdl.countDown();
            }
        })
        .singleElement()
        .unsubscribeOn(Schedulers.single())
        .test(true)
        ;

        assertTrue(cdl.await(5, TimeUnit.SECONDS));

        int times = 10;

        while (times-- > 0 && pp.hasSubscribers()) {
            Thread.sleep(100);
        }

        assertFalse(pp.hasSubscribers());

        assertNotEquals(Thread.currentThread().getName(), name[0]);
    }

    @Test
    public void just() {
        Maybe.just(1)
        .unsubscribeOn(Schedulers.single())
        .test()
        .assertResult(1);
    }

    @Test
    public void error() {
        Maybe.<Integer>error(new TestException())
        .unsubscribeOn(Schedulers.single())
        .test()
        .assertFailure(TestException.class);
    }

    @Test
    public void empty() {
        Maybe.empty()
        .unsubscribeOn(Schedulers.single())
        .test()
        .assertResult();
    }

    @Test
    public void dispose() {
        TestHelper.checkDisposed(Maybe.just(1)
        .unsubscribeOn(Schedulers.single()));
    }

    @Test
    public void doubleOnSubscribe() {
        TestHelper.checkDoubleOnSubscribeMaybe(new Function<Maybe<Object>, MaybeSource<Object>>() {
            @Override
            public MaybeSource<Object> apply(Maybe<Object> v) throws Exception {
                return v.unsubscribeOn(Schedulers.single());
            }
        });
    }

    @Test
    public void disposeRace() {
        for (int i = 0; i < TestHelper.RACE_DEFAULT_LOOPS; i++) {
            PublishProcessor<Integer> pp = PublishProcessor.create();

            final Disposable[] ds = { null };
            pp.singleElement().unsubscribeOn(Schedulers.computation())
            .subscribe(new MaybeObserver<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    ds[0] = d;
                }

                @Override
                public void onSuccess(Integer value) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    ds[0].dispose();
                }
            };

            TestHelper.race(r, r);
        }
    }
}
