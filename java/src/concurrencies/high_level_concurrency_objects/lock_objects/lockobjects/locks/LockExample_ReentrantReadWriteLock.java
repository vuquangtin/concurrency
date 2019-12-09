package lockobjects.locks;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.concurrent.ThreadSafe;

import lombok.extern.slf4j.Slf4j;

/**
 * @author panghu
 * @Title: CountExample3
 * @ProjectName concurrency_demo
 * @Description: TODO
 * @date 19-2-16 下午3:09
 * ReentrantReadWriteLock是一种悲观读取，如果想获得写锁的话，不能有任何的读锁保持着
 */
@Slf4j
@ThreadSafe
public class LockExample_ReentrantReadWriteLock {

    private final Map<String,Data> map = new TreeMap<>();

    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    public Data get(String key){
        readLock.lock();
        try{
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }

    public Set<String> getAllKeys(){
        readLock.lock();
        try{
            return map.keySet();
        }finally {
            readLock.unlock();
        }
    }


    public Data put(String key,Data value){
        writeLock.lock();
        try{
            return map.put(key,value);
        }finally {
            writeLock.unlock();
        }
    }


    class Data{

    }

}