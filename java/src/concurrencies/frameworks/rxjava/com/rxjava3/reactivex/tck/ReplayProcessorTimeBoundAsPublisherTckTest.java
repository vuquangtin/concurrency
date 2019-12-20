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

package com.rxjava3.reactivex.tck;

import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;

@Test
public class ReplayProcessorTimeBoundAsPublisherTckTest extends BaseTck<Integer> {

    public ReplayProcessorTimeBoundAsPublisherTckTest() {
        super(100);
    }

    @Override
    public Publisher<Integer> createPublisher(final long elements) {
        final ReplayProcessor<Integer> pp = ReplayProcessor.createWithTime(1, TimeUnit.MINUTES, Schedulers.computation());

        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                while (!pp.hasSubscribers()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        return;
                    }

                    if (System.currentTimeMillis() - start > 200) {
                        return;
                    }
                }

                for (int i = 0; i < elements; i++) {
                    pp.onNext(i);
                }
                pp.onComplete();
            }
        });
        return pp;
    }
}