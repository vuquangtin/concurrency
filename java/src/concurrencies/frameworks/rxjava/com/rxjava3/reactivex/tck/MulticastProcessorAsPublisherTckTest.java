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

package io.reactivex.tck;

import io.reactivex.rxjava3.processors.MulticastProcessor;

import org.reactivestreams.Publisher;

@Test
public class MulticastProcessorAsPublisherTckTest extends BaseTck<Integer> {

    public MulticastProcessorAsPublisherTckTest() {
        super(100);
    }

    @Override
    public Publisher<Integer> createPublisher(final long elements) {
        final MulticastProcessor<Integer> mp = MulticastProcessor.create();
        mp.start();

        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                while (!mp.hasSubscribers()) {
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
                    while (!mp.offer(i)) {
                        Thread.yield();
                        if (System.currentTimeMillis() - start > 1000) {
                            return;
                        }
                    }
                }
                mp.onComplete();
            }
        });
        return mp;
    }
}
