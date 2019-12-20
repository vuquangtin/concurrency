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
package io.reactivex.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Ignore;
import org.junit.Test;
import org.reactivestreams.Subscription;

import com.rxjava3.reactivex.testsupport.TestHelper;

public class ExceptionsTest {

    @Ignore("Exceptions is not an enum")
    @Test
    public void constructorShouldBePrivate() {
        TestHelper.checkUtilityClass(ExceptionHelper.class);
    }

    @Test
    public void onErrorNotImplementedIsThrown() {
        List<Throwable> errors = TestHelper.trackPluginErrors();

        Observable.just(1, 2, 3).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t1) {
                throw new RuntimeException("hello");
            }

        });

        TestHelper.assertError(errors, 0, RuntimeException.class);
        assertTrue(errors.get(0).toString(), errors.get(0).getMessage().contains("hello"));
        RxJavaPlugins.reset();
    }

    /**
     * Outdated test: Observer should not suppress errors from onCompleted.
     * https://github.com/ReactiveX/RxJava/issues/3885
     */
    @Ignore("v2 components should not throw")
    @Test(expected = RuntimeException.class)
    public void onCompletedExceptionIsThrown() {
        Observable.empty()
            .subscribe(new Observer<Object>() {
                @Override
                public void onComplete() {
                    throw new RuntimeException();
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(Object o) {
                }

                @Override
                public void onSubscribe(Disposable d) {

                }
            });
    }

    @Test
    public void stackOverflowWouldOccur() {
        final PublishSubject<Integer> a = PublishSubject.create();
        final PublishSubject<Integer> b = PublishSubject.create();
        final int MAX_STACK_DEPTH = 800;
        final AtomicInteger depth = new AtomicInteger();

        a.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer n) {
                b.onNext(n + 1);
            }
        });
        b.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer n) {
                if (depth.get() < MAX_STACK_DEPTH) {
                    depth.set(Thread.currentThread().getStackTrace().length);
                    a.onNext(n + 1);
                }
            }
        });
        a.onNext(1);
        assertTrue(depth.get() >= MAX_STACK_DEPTH);
    }

    @Test(expected = StackOverflowError.class)
    public void stackOverflowErrorIsThrown() {
        Observable.just(1).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer t) {
                throw new StackOverflowError();
            }

        });
    }

    @Test(expected = ThreadDeath.class)
    public void threadDeathIsThrown() {
        Observable.just(1).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer t) {
                throw new ThreadDeath();
            }

        });
    }

    /**
     * Outdated test: throwing from onError handler.
     * https://github.com/ReactiveX/RxJava/issues/969
     */
    @Ignore("v2 components should not throw")
    @Test
    public void onErrorExceptionIsThrown() {
        try {
            Observable.error(new IllegalArgumentException("original exception")).subscribe(new Observer<Object>() {

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError(Throwable e) {
                    throw new IllegalStateException("This should be thrown");
                }

                @Override
                public void onNext(Object o) {

                }
            });
            fail("expecting an exception to be thrown");
        } catch (RuntimeException t) {
            CompositeException cause = (CompositeException) t.getCause();
            assertTrue(cause.getExceptions().get(0) instanceof IllegalArgumentException);
            assertTrue(cause.getExceptions().get(1) instanceof IllegalStateException);
        }
    }

    /**
     * Outdated test: throwing from onError.
     * https://github.com/ReactiveX/RxJava/issues/2998
     * @throws Exception on arbitrary errors
     */
    @Ignore("v2 components should not throw")
    @Test(expected = RuntimeException.class)
    public void onErrorExceptionIsThrownFromGroupBy() throws Exception {
        Observable
            .just(1)
            .groupBy(new Function<Integer, Integer>() {
                @Override
                public Integer apply(Integer integer) {
                    throw new RuntimeException();
                }
            })
            .subscribe(new Observer<GroupedObservable<Integer, Integer>>() {

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError(Throwable e) {
                    throw new RuntimeException();
                }

                @Override
                public void onNext(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {

                }
            });
    }

    /**
     * Outdated test: throwing from onError.
     * https://github.com/ReactiveX/RxJava/issues/2998
     * @throws Exception on arbitrary errors
     */
    @Ignore("v2 components should not throw")
    @Test(expected = RuntimeException.class)
    public void onErrorExceptionIsThrownFromOnNext() throws Exception {
        Observable
            .just(1)
            .doOnNext(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) {
                    throw new RuntimeException();
                }
            })
            .subscribe(new Observer<Integer>() {

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void onError(Throwable e) {
                    throw new RuntimeException();
                }

                @Override
                public void onNext(Integer integer) {

                }
            });
    }

    @Ignore("v2 components should not throw")
    @Test(expected = RuntimeException.class)
    public void onErrorExceptionIsThrownFromSubscribe() {
        Observable.unsafeCreate(new ObservableSource<Integer>() {
                              @Override
                              public void subscribe(Observer<? super Integer> observer1) {
                                  Observable.unsafeCreate(new ObservableSource<Integer>() {
                                      @Override
                                      public void subscribe(Observer<? super Integer> observer2) {
                                          throw new IllegalArgumentException("original exception");
                                      }
                                  }).subscribe(observer1);
                              }
                          }
        ).subscribe(new OnErrorFailedSubscriber());
    }

    @Ignore("v2 components should not throw")
    @Test(expected = RuntimeException.class)
    public void onErrorExceptionIsThrownFromUnsafeSubscribe() {
        Observable.unsafeCreate(new ObservableSource<Integer>() {
                              @Override
                              public void subscribe(Observer<? super Integer> observer1) {
                                  Observable.unsafeCreate(new ObservableSource<Integer>() {
                                      @Override
                                      public void subscribe(Observer<? super Integer> observer2) {
                                          throw new IllegalArgumentException("original exception");
                                      }
                                  }).subscribe(observer1);
                              }
                          }
        ).subscribe(new OnErrorFailedSubscriber());
    }

    @Ignore("v2 components should not throw")
    @Test(expected = RuntimeException.class)
    public void onErrorExceptionIsThrownFromSingleDoOnSuccess() throws Exception {
        Single.just(1)
                .doOnSuccess(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        throw new RuntimeException();
                    }
                })
                .toObservable().subscribe(new OnErrorFailedSubscriber());
    }

    @Ignore("v2 components should not throw")
    @Test(expected = RuntimeException.class)
    public void onErrorExceptionIsThrownFromSingleSubscribe() {
        Single.unsafeCreate(new SingleSource<Integer>() {
                          @Override
                          public void subscribe(SingleObserver<? super Integer> observer1) {
                              Single.unsafeCreate(new SingleSource<Integer>() {
                                  @Override
                                  public void subscribe(SingleObserver<? super Integer> observer2) {
                                      throw new IllegalArgumentException("original exception");
                                  }
                              }).subscribe(observer1);
                          }
                      }
        ).toObservable().subscribe(new OnErrorFailedSubscriber());
    }

    @Ignore("v2 components should not throw")
    @Test(expected = RuntimeException.class)
    public void onErrorExceptionIsThrownFromSingleUnsafeSubscribe() {
        Single.unsafeCreate(new SingleSource<Integer>() {
                          @Override
                          public void subscribe(final SingleObserver<? super Integer> observer1) {
                              Single.unsafeCreate(new SingleSource<Integer>() {
                                  @Override
                                  public void subscribe(SingleObserver<? super Integer> observer2) {
                                      throw new IllegalArgumentException("original exception");
                                  }
                              }).toFlowable().subscribe(new FlowableSubscriber<Integer>() {

                                  @Override
                                  public void onSubscribe(Subscription s) {
                                      s.request(Long.MAX_VALUE);
                                  }

                                  @Override
                                  public void onComplete() {
                                  }

                                  @Override
                                  public void onError(Throwable e) {
                                      observer1.onError(e);
                                  }

                                  @Override
                                  public void onNext(Integer v) {
                                      observer1.onSuccess(v);
                                  }

                              });
                          }
                      }
        ).toObservable().subscribe(new OnErrorFailedSubscriber());
    }

    private class OnErrorFailedSubscriber implements Observer<Integer> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException();
        }

        @Override
        public void onNext(Integer value) {
        }
    }

    @Test
    public void utilityClass() {
        TestHelper.checkUtilityClass(Exceptions.class);
    }

    @Test
    public void manualThrowIfFatal() {

        try {
            Exceptions.throwIfFatal(new ThreadDeath());
            fail("Didn't throw fatal exception");
        } catch (ThreadDeath ex) {
            // expected
        }

        try {
            Exceptions.throwIfFatal(new LinkageError());
            fail("Didn't throw fatal error");
        } catch (LinkageError ex) {
            // expected
        }

        try {
            ExceptionHelper.wrapOrThrow(new LinkageError());
            fail("Didn't propagate Error");
        } catch (LinkageError ex) {
            // expected
        }
    }

    @Test
    public void manualPropagate() {

        try {
            Exceptions.propagate(new InternalError());
            fail("Didn't throw exception");
        } catch (InternalError ex) {
            // expected
        }

        try {
            throw Exceptions.propagate(new IllegalArgumentException());
        } catch (IllegalArgumentException ex) {
            // expected
        }

        try {
            throw ExceptionHelper.wrapOrThrow(new IOException());
        } catch (RuntimeException ex) {
            if (!(ex.getCause() instanceof IOException)) {
                fail(ex.toString() + ": should have thrown RuntimeException(IOException)");
            }
        }
    }

    @Test
    public void errorNotImplementedNull1() {
        OnErrorNotImplementedException ex = new OnErrorNotImplementedException(null);

        assertTrue("" + ex.getCause(), ex.getCause() instanceof NullPointerException);
    }

    @Test
    public void errorNotImplementedNull2() {
        OnErrorNotImplementedException ex = new OnErrorNotImplementedException("Message", null);

        assertTrue("" + ex.getCause(), ex.getCause() instanceof NullPointerException);
    }

    @Test
    public void errorNotImplementedWithCause() {
        OnErrorNotImplementedException ex = new OnErrorNotImplementedException("Message", new TestException("Forced failure"));

        assertTrue("" + ex.getCause(), ex.getCause() instanceof TestException);

        assertEquals("" + ex.getCause(), "Forced failure", ex.getCause().getMessage());
    }
}
