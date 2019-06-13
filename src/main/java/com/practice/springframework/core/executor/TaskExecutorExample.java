package com.practice.springframework.core.executor;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutorExample {

    private TaskExecutor taskExecutor;

    public TaskExecutorExample(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Scheduled(fixedRate = 1000)
    public void printMessages() {
        taskExecutor.execute(new MessagePrinterTask("Message"));
    }

    private class MessagePrinterTask implements Runnable {

        private String message;

        public MessagePrinterTask(String message) {
            this.message = message;
        }

        public void run() {
            System.out.println(message);
            System.out.println("MessagePrinterTask current thread: " + Thread.currentThread().getName());
        }
    }
}