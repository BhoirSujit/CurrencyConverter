/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import javax.swing.SwingUtilities;

public class App {




    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        

        SwingUtilities.invokeLater(() -> {
            new CurrencyConverter();
        });
    }
}
