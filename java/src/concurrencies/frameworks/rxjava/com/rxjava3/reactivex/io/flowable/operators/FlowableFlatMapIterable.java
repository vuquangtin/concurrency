package com.rxjava3.reactivex.io.flowable.operators;

import io.reactivex.rxjava3.core.Flowable;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

public class FlowableFlatMapIterable {

    private static final Logger log = LoggerFactory.getLogger(FlowableFlatMapIterable.class);

    public static void main(String[] args) {

        List<User> users = ImmutableList.of(
                new User(ImmutableList.of(new Order(Locale.CANADA, 1L))),
                new User(ImmutableList.of(new Order(Locale.CHINA, 10L), new Order(Locale.CHINA, 20L))),
                new User(ImmutableList.of(new Order(Locale.GERMANY, 100L))),
                new User(ImmutableList.of(new Order(Locale.ITALY, 1000L), new Order(Locale.ITALY, 2000L)))
        );

        Flowable<Order> flowable = Flowable.fromIterable(users)
                .flatMapIterable(u -> u.getOrders());

        flowable.subscribe(
                o -> log.info("Order price {} {}", o.getCurrencyCode(), o.getAmount()),
                ex -> log.error("Error {}", ex.getMessage())
        );

    }

    private static class User {

        private List<Order> orders;

        public User(List<Order> orders ) {
            this.orders = orders;
        }

        private List<Order> getOrders() {
            return orders;
        }

    }

    private static class Order {

        private Currency currency;
        private Long amount;

        public Order(Locale locale, Long amount) {
            this.currency = Currency.getInstance(locale);
            this.amount = amount;
        }

        public String getCurrencyCode() {
            return currency.getCurrencyCode();
        }

        public Long getAmount() {
            return amount;
        }

    }

}
