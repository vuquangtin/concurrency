package asynchronous.delay;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.text.SimpleDateFormat;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


public class DelayQueueDemo2 {

    private static class DelayedElement implements Delayed {
        private long delta;
        private long trigger;
        private String data;

        public DelayedElement(String data, long delayInMilliseconds) {
            this.delta = delayInMilliseconds;
            this.data = data;
            this.trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            // 为什么还要转一下（两个操作数都是NANOSECONDS为单位啊）
            return unit.convert(this.trigger-System.nanoTime(), NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            DelayedElement that = (DelayedElement) o;
            return Long.compare(this.trigger, that.trigger);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("DelayedElement{");
            sb.append("delay=").append(delta);
            sb.append(", expire=").append(new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(MILLISECONDS.convert(trigger, NANOSECONDS)));
            sb.append(", data='").append(data).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        DelayQueue<DelayedElement> delayQueue = new DelayQueue<>();

        DelayedElement element1 = new DelayedElement("我延迟1秒", 1000);
        DelayedElement element2 = new DelayedElement("我延迟2秒", 2 * 1000);
        DelayedElement element3 = new DelayedElement("我延迟10秒", 10 * 1000);
        DelayedElement element4 = new DelayedElement("我延迟30秒", 30 * 1000);
        DelayedElement element5 = new DelayedElement("我延迟60秒", 60 * 1000);
        DelayedElement element6 = new DelayedElement("我延迟5 * 60秒", 5 * 60 * 1000);

        delayQueue.offer(element1);
        delayQueue.offer(element2);
        delayQueue.offer(element3);
        delayQueue.offer(element4);
        delayQueue.offer(element5);
        delayQueue.offer(element6);

        DelayedElement expireElement;
        while (true) {
            try {
                expireElement = delayQueue.take();
                System.out.println(expireElement);
            } catch (InterruptedException e) {
            }
        }
    }
}