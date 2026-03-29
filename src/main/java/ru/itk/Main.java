package ru.itk;

public class Main {
    public static void main(String[] args) {

        BlockingQueue<Runnable> taskQueue = new BlockingQueue<>(5);

        for (int i = 0; i < 2; i++) {

            new Thread(() -> {

                for (int j = 0; j < 10; j++) {

                    try {

                        Runnable task = () -> {

                            System.out.println(Thread.currentThread().getName() + " выполняет задачу");

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                        };

                        taskQueue.enqueue(task);
                        System.out.println("Задача добавлена. Размер очереди: " + taskQueue.size());

                    } catch (InterruptedException e) {

                        Thread.currentThread().interrupt();
                        break;

                    }

                }

            }, "Producer: " + i).start();

        }

        for (int i = 0; i < 3; i++) {

            new Thread(() -> {

                while (!Thread.currentThread().isInterrupted()) {

                    try {

                        Runnable task = taskQueue.dequeue();
                        System.out.println("Задача взята. Размер очереди: " + taskQueue.size());
                        task.run();

                    } catch (InterruptedException e) {

                        Thread.currentThread().interrupt();
                        break;

                    }

                }

            }, "Consumer: " + i).start();

        }

    }

}