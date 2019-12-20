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

package com.rxjava3.reactivex.completable;

import static org.junit.Assert.assertEquals;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.TestScheduler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

public class CompletableTimerTest {
    @Test
    public void timer() {
        final TestScheduler testScheduler = new TestScheduler();

        final AtomicLong atomicLong = new AtomicLong();
        Completable.timer(2, TimeUnit.SECONDS, testScheduler).subscribe(new Action() {
            @Override
            public void run() throws Exception {
                atomicLong.incrementAndGet();
            }
        });

        assertEquals(0, atomicLong.get());

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        assertEquals(0, atomicLong.get());

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        assertEquals(1, atomicLong.get());
    }
}
