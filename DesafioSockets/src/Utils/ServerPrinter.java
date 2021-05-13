package Utils;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ServerPrinter {

    private Queue<String> queue          = new ConcurrentLinkedQueue<>();
    public static ServerPrinter INSTANCE = null;

    private Thread threadResponse        = new Thread(){
        @Override
        public void run() {

            ArrayList<Printer> printerList = new ArrayList<>() {{
                add(new Printer("PRINTER 1"));
                add(new Printer("PRINTER 2"));
                add(new Printer("PRINTER 3"));
                add(new Printer("PRINTER 4"));
                add(new Printer("PRINTER 5"));
            }};

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(100);

                    String message = queue.poll();
                    if (message != null) {
                        Printer lastPrinter = printerList.get(0);
                        for (Printer printer : printerList) {
                            if (lastPrinter.queueSize() > printer.queueSize()) {
                                lastPrinter = printer;
                            }
                        }
                        lastPrinter.addMessage(message);
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    };

    public static ServerPrinter getInstance() {
        if (INSTANCE == null) {
            INSTANCE.startPrinting();
            INSTANCE = new ServerPrinter();
        }
        return INSTANCE;
    }

    private void startPrinting(){
        threadResponse.start();
    }

    public void addMessage(String message) {
        queue.add(message);
    }
}
