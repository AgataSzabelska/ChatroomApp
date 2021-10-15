package com.aszabelsk;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReceiver implements Runnable {
    private final BufferedReader reader;

    public MessageReceiver(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//    public class MessageReceiver implements Runnable {
//        private final ObservableList<String> messages;
//
//        public MessageReceiver(ObservableList<String> messages) {
//            this.messages = messages;
//        }
//
//        @Override
//        public void run() {
//            String message;
//            try {
//                while (running.get() && (message = reader.readLine()) != null) {
//                    String finalMessage = message;
////                    Platform.runLater(() -> messages.add(finalMessage));
//                    messages.add(finalMessage);
//                }
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }