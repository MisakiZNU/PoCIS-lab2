package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 200;
    private JFrame frame;
    private Map<String,Double> inf = new HashMap<>();
    //конструктор класу
    public Main(){
        frame = createFrame();
        inf = loadCrossCourse("D:\\Codes\\PoCIS\\Lab2\\Lab2\\src\\main\\java\\org\\example\\inf.txt");
        JComboBox<String> convertFrom = createComboBox(50, 50, inf);
        JComboBox<String> convertTo = createComboBox(250, 50, inf);
        JTextField textFieldFrom = createTextField(50,100);
        JTextField textFieldTo = createTextField(250,100);
        JButton button = createButton(175, 60, convertFrom, convertTo,inf, textFieldFrom, textFieldTo);
    }
    //отримання назв валют для виведення в комбо-бокс
    private String[] getCurrencyName (Map<String,Double> inf){
        return inf.keySet().toArray(new String[0]);
    }
    //створення основного вікна програми
    private JFrame createFrame(){
        JFrame frame = new JFrame("Конвертор валют");
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }
    //створення текстовго поля для введення\виводу значень
    private JTextField createTextField(int x, int y){
        JTextField textField = new JTextField();
        textField.setBounds(x,y,100,25);
        frame.add(textField);
        return textField;
    }
    //створення кнопки для виконання конвертації
    private JButton createButton(int x, int y, JComboBox<String> from, JComboBox<String> to, Map<String, Double> inf, JTextField textFieldFrom, JTextField textFieldTo ){
        JButton button = new JButton(">");
        button.setBounds(x,y,50,50);
        frame.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromCurrency = (String) from.getSelectedItem();
                String toCurrency = (String) to.getSelectedItem();
                double amount = Double.parseDouble(textFieldFrom.getText());
                double res = convertCurrency(fromCurrency, toCurrency, inf, amount);
                DecimalFormat numForm = new DecimalFormat("#.00");
                textFieldTo.setText(String.valueOf(numForm.format(res)));
            }
        });
        return button;
    }
    //створення комбо-боксу для вибору валют
    private JComboBox<String> createComboBox(int x, int y, Map<String, Double> inf) {
        String[] items = getCurrencyName(inf);
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBounds(x, y, 100, 25);
        frame.add(comboBox);
        return comboBox;
    }
    //завантаження курсів валют з файлу
    private Map<String,Double> loadCrossCourse (String filePath){
        Map<String,Double> crossCoursePair = new HashMap<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String currency = parts[0].trim();
                    double rate = Double.parseDouble(parts[1].trim());
                    crossCoursePair.put(currency, rate);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не було знайдено");
        }
        return crossCoursePair;
    }
    //конвертація валют
    private double convertCurrency(String from, String to, Map<String,Double> pair, double money){
       if(pair.containsKey(from) && pair.containsKey(to)){
       double fromNum = pair.get(from);
       double toNum = pair.get(to);
       return money * (toNum / fromNum);
       } else {return -1;}
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(Main::new);
    }
}