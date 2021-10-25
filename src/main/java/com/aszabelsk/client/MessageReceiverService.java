package com.aszabelsk.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class MessageReceiverService extends Service<List<String>> {
    private final BufferedReader reader;
    private String test = "a";
    private StringProperty lastMessageProperty = new SimpleStringProperty();

    public MessageReceiverService(BufferedReader reader) {
        this.reader = reader;
    }

    protected Task createTask() {
        return new Task<List<String>>() {
            protected List<String> call() {
                String message;
                try {
                    while ((message = reader.readLine()) != null) {
//                        System.out.println(message);
//                        getValue().add(message);
                        lastMessageProperty.set(message);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                while (true) {
//                    getValue().add(test);
//                }
                return getValue();
            }
        };
    }

    public StringProperty lastMessageProperty() {
        return lastMessageProperty;
    }
}

//public class MessageReceiver implements Runnable {
//    private final BufferedReader reader;
//
//    public MessageReceiver(BufferedReader reader) {
//        this.reader = reader;
//    }
//
//    @Override
//    public void run() {
//        String message;
//        try {
//            while ((message = reader.readLine()) != null) {
//                System.out.println(message);
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


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