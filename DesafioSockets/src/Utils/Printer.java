package Utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Printer {

    private Queue<String> queue = new ConcurrentLinkedQueue<>();
    private String printerName;

    public Thread threadResponse = new Thread(){
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String message = queue.poll();
                    if (message != null) {
                        System.out.println(printerName + ": " + message);
                    }
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    };

    public Printer(String name)
    {
        threadResponse.start();
        printerName = name;
    }

    public void addMessage(String message) {
        queue.add(message);
    }

    public int queueSize(){
        return queue.size();
    }
}
