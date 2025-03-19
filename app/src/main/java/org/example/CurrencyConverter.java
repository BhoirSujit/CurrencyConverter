package org.example;

import java.util.Map;

import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.Gson;

public class CurrencyConverter extends JFrame {

    public CurrencyConverter() {
        super("Currency Converter");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        setContentPane(panel);

        //componants
        JLabel amountlbl = new JLabel("Amount");
        JLabel currlbl = new JLabel("Current");
        JLabel conlbn = new JLabel("To");

        JTextField amountTf = new JTextField();
        JComboBox currCBox = new JComboBox();
        JComboBox conCBox = new JComboBox();

        JButton convertBtn = new JButton("Convert");

        JEditorPane resultlbl = new JEditorPane();
        resultlbl.setContentType("text/html");
        resultlbl.setEditable(false);

        //layout
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(amountlbl)
                        .addComponent(currlbl)
                        .addComponent(conlbn))
                .addGroup(layout.createParallelGroup()
                        .addComponent(amountTf)
                        .addComponent(currCBox)
                        .addComponent(conCBox)
                        .addComponent(convertBtn)
                        .addComponent(resultlbl))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(amountlbl)
                        .addComponent(amountTf)
                ).addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(currlbl)
                        .addComponent(currCBox)
                ).addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(conlbn)
                        .addComponent(conCBox))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(convertBtn)
                )
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(resultlbl))
        );

        //api
        CCApi api = new CCApi();

        Map<String, String> currencyMap = api.getCurrencies();

        // Print the map
        for (Map.Entry<String, String> entry : currencyMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());

            currCBox.addItem(entry.getKey() + " (" + entry.getValue() + ")");
            conCBox.addItem(entry.getKey() + " (" + entry.getValue() + ")");
        }

        Gson gson = new Gson();

        convertBtn.addActionListener(e -> {
            String selectedcurr = (String) currCBox.getSelectedItem();
            String currKey = selectedcurr.split(" ")[0];

            String selectedcon = (String) conCBox.getSelectedItem();
            String conKey = selectedcon.split(" ")[0];

            Map<String, Object> data = api.getCurrencyRate(currKey);
            System.out.println(data);
            System.out.println(data.get("date"));
            System.out.println(currKey);
            System.out.println(data.get(currKey));

            Map<String, Double> rate = gson.fromJson(data.get(currKey).toString(), Map.class);
            System.out.println(rate);
            
            //calculate rate
            double amount = Double.parseDouble(amountTf.getText());
            double value = rate.get(conKey);
            StringBuilder result = new StringBuilder();

            result
                    .append("<h1>")
                    .append(value * amount)
                    .append("</h1>")
                    .append("<p>1 ")
                    .append(currKey).append(" = ").append(rate.get(conKey)).append(" ")
                    .append(conKey)
                    .append("<br>Exchange rate as of: ")
                    .append(data.get("date").toString())
                    .append("</p>");

            resultlbl.setText(result.toString());

        });
        setVisible(true);

    }
}
