package com.example.j_chenxi.fragmentdemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单线程任务执行者,循环利用线程,避免频繁创建线程
 * Created by jiangyiwang on 16/10/10.
 */
public class JobWorker {
    /**
     * 单例,并且延迟创建
     */
    private static class ExecutorHolder {
        static ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);
        static Handler HANDLER = new Handler(Looper.getMainLooper());
    }

    public static <T> void submit(Task<T> task) {
        if (ExecutorHolder.EXECUTOR.isShutdown()) {
            return;
        }
        ExecutorHolder.EXECUTOR.submit(task);
    }

    public static ExecutorService getExecutor(){
        return ExecutorHolder.EXECUTOR;
    }
    public static void shutdown() {
        ExecutorHolder.EXECUTOR.shutdown();
    }

    public static abstract class Task<T> implements Runnable {
        private boolean needCallback = true;

        protected Task() {
            this(true);
        }

        public Task(boolean needCallback) {
            this.needCallback = needCallback;
        }


        @UiThread
        public void onStart() {
            //empty for override
        }

        @WorkerThread
        public abstract T doInBackground();

        @Override
        public final void run() {
            if (needCallback) {
                ExecutorHolder.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        onStart();
                    }
                });
            }
            T result = null;
            try {
                result = doInBackground();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (needCallback) {
                final T callbackResult = result;
                ExecutorHolder.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        onComplete(callbackResult);
                    }
                });
            }
        }

        @UiThread
        public void onComplete(T result) {
            //empty for override
        }
    }


}
