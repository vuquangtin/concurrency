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
package com.rxjava3.reactivex.flowable;

import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;

import com.rxjava3.reactivex.flowable.FlowableCovarianceTest.CoolRating;
import com.rxjava3.reactivex.flowable.FlowableCovarianceTest.ExtendedResult;
import com.rxjava3.reactivex.flowable.FlowableCovarianceTest.HorrorMovie;
import com.rxjava3.reactivex.flowable.FlowableCovarianceTest.Media;
import com.rxjava3.reactivex.flowable.FlowableCovarianceTest.Movie;
import com.rxjava3.reactivex.flowable.FlowableCovarianceTest.Rating;
import com.rxjava3.reactivex.flowable.FlowableCovarianceTest.Result;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.processors.BehaviorProcessor;

public class FlowableCombineLatestTests {
    /**
     * This won't compile if super/extends isn't done correctly on generics.
     */
    @Test
    public void covarianceOfCombineLatest() {
        Flowable<HorrorMovie> horrors = Flowable.just(new HorrorMovie());
        Flowable<CoolRating> ratings = Flowable.just(new CoolRating());

        Flowable.<Movie, CoolRating, Result> combineLatest(horrors, ratings, combine).blockingForEach(action);
        Flowable.<Movie, CoolRating, Result> combineLatest(horrors, ratings, combine).blockingForEach(action);
        Flowable.<Media, Rating, ExtendedResult> combineLatest(horrors, ratings, combine).blockingForEach(extendedAction);
        Flowable.<Media, Rating, Result> combineLatest(horrors, ratings, combine).blockingForEach(action);
        Flowable.<Media, Rating, ExtendedResult> combineLatest(horrors, ratings, combine).blockingForEach(action);

        Flowable.<Movie, CoolRating, Result> combineLatest(horrors, ratings, combine);
    }

    BiFunction<Media, Rating, ExtendedResult> combine = new BiFunction<Media, Rating, ExtendedResult>() {
        @Override
        public ExtendedResult apply(Media m, Rating r) {
            return new ExtendedResult();
        }
    };

    Consumer<Result> action = new Consumer<Result>() {
        @Override
        public void accept(Result t1) {
            System.out.println("Result: " + t1);
        }
    };

    Consumer<ExtendedResult> extendedAction = new Consumer<ExtendedResult>() {
        @Override
        public void accept(ExtendedResult t1) {
            System.out.println("Result: " + t1);
        }
    };

    @Ignore("No longer allowed")
    @Test
    public void nullEmitting() throws Exception {
        // FIXME this is no longer allowed
        Flowable<Boolean> nullObservable = BehaviorProcessor.createDefault((Boolean) null);
        Flowable<Boolean> nonNullObservable = BehaviorProcessor.createDefault(true);
//        Flowable<Boolean> combined =
//                combineLatest(nullObservable, nonNullObservable, new BiFunction<Boolean, Boolean, Boolean>() {
//                    @Override
//                    public Boolean apply(Boolean bool1, Boolean bool2) {
//                        return bool1 == null ? null : bool2;
//                    }
//                });
//        combined.subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) {
//                assertNull(aBoolean);
//            }
//        });
    }
}
