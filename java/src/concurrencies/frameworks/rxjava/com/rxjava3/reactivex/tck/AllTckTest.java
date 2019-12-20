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

import org.testng.annotations.Test;
import org.reactivestreams.Publisher;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Predicate;

@Test
public class AllTckTest extends BaseTck<Boolean> {

	@Override
	public Publisher<Boolean> createPublisher(final long elements) {
		return Flowable.range(1, 1000).all(new Predicate<Integer>() {
			@Override
			public boolean test(Integer e) throws Exception {
				return e < 800;
			}
		}).toFlowable();
	}

	@Override
	public long maxElementsFromPublisher() {
		return 1;
	}
}
