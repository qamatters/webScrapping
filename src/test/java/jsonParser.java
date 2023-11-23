
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;

public class jsonParser {
    public static void main(String[] args) {
        Frame f = new Frame("JSON Formatter, Validator and Evaluator");

        final TextArea inputQuery = new TextArea("Enter your query here..");
        inputQuery.setBounds(300, 50, 1000, 60);

        Button button = new Button("Evaluate your query");
        button.setBounds(300, 110, 150, 50);
        button.setBackground(Color.green);
        button.setForeground(Color.BLACK);

        Button prettyFi = new Button("Beautify your input JSON");
        prettyFi.setBounds(300, 180, 150, 50);
        prettyFi.setBackground(Color.green);
        prettyFi.setForeground(Color.BLACK);

        Button validateJSON = new Button("Validate your input JSON");
        validateJSON.setBounds(550, 180, 150, 50);
        validateJSON.setBackground(Color.green);
        validateJSON.setForeground(Color.BLACK);

        Button clearOutput = new Button("Clear Output Filed Values");
        clearOutput.setBounds(800, 180, 150, 50);
        clearOutput.setBackground(Color.orange);
        clearOutput.setForeground(Color.BLACK);

        Button exitButton = new Button("Exit");
        exitButton.setBounds(1050, 180, 150, 50);
        exitButton.setBackground(Color.red);
        exitButton.setForeground(Color.BLACK);


        final TextArea queryPlayGround = new TextArea("{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    }\n" +
                "}");
        queryPlayGround.setBounds(300, 250, 500, 700);
        queryPlayGround.setBounds(300, 250, 700, 780);

        final TextArea output = new TextArea("Check the output");
        output.setBounds(1100, 250, 700, 780);
        // output.setFont(new Font("Serif", Font.BOLD, 18));
        DefaultTableModel model = new DefaultTableModel(new Object[][]{
                { "JsonPath", "Result" },
                { "$.store.book[*].author", "The authors of all books" },
                { "$..author", "All authors" },
                { "$.store.*", "All things, both books and bicycles" },
                { "$.store..price", "The price of everything" },
                { "$..book[2]", "The third book" },
                { "$..book[?(@.price<30 && @.category==\"fiction\")]", "Filter all fiction books cheaper than 30" },
                { "$.store.book[?(@.price < 10)]", "All books in store cheaper than 10" },
        }, new Object[]{
                "JsonPath", "Result"
        });

        // Create a table and set its model
        JTable table = new JTable(model);
        table.setBounds(1300, 50, 800, 1000);


        clearOutput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText("");
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        validateJSON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputQuery.getText().length() > 0) {
                    String inputJSON = queryPlayGround.getText();
                    String jsonValidationResult = validationMessage(inputJSON);
                    if(jsonValidationResult.contains("invalid")) {
                        output.setForeground(Color.red);
                        output.setText(jsonValidationResult);
                    } else {
                        output.setForeground(Color.blue);
                        output.setText(jsonValidationResult);
                    }
                }  else {
                    output.setForeground(Color.red);
                    output.setText("Please provide valid query to proceed.");
                }
            }
        });

        prettyFi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputQuery.getText().length() > 0) {

                    String inputJSON = queryPlayGround.getText();
                    boolean isValid = isValidJSON(inputJSON);
                    String formattedJSON = "";
                    if (isValid) {
                        try {
                            formattedJSON = prettyFyJSON(inputJSON);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        queryPlayGround.setText(formattedJSON);
                    } else {
                        queryPlayGround.setText("your json is invalid");
                    }
                }
            }
        });

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String json = queryPlayGround.getText().trim();
//                System.out.println("json is :"+ json);
                String query = inputQuery.getText().trim();
                Object result = readJson(json, query);

//                System.out.println(" Result is :" + result);

                if (result == null) {
                    output.setText("null");
                } else {
                    String text = result.toString();
                    if (text.contains("No results for path")) {
                        output.setText(text);
                    } else {
                        if (result instanceof String) {
                            try {
                                output.setText(prettyFyJSON(text));
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else if (result instanceof JSONArray) {
                            try {
                                output.setText(prettyFyJSON(((JSONArray) result).toJSONString()));
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            output.setText(text);
                        }
                    }
                }

            }
        });

        f.add(queryPlayGround);
        f.add(inputQuery);
        f.add(output);
        f.add(button);
        f.add(prettyFi);
        f.add(validateJSON);
        f.add(exitButton);
        f.add(table);
        f.add(clearOutput);

        f.setSize(new Dimension(800, 600));
        f.setMinimumSize(new Dimension(800, 600));
        f.setMaximumSize(new Dimension(1000, 800));

        f.setLayout(null);
        f.setVisible(true);
    }


    public static String prettyFyJSON(String json) throws IOException {
        Gson gson = new GsonBuilder().serializeNulls().setLenient().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(json.trim());
        String prettyJson = gson.toJson(jsonElement);
        return prettyJson.trim();
    }

    public static boolean isValidJSON(String json) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new StringReader(json));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String validationMessage(String json) {
        String jsonValidationOutput = "";
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(new StringReader(json));
            jsonValidationOutput= "Your JSON is valid";
        } catch (Exception e) {
            jsonValidationOutput = "Your JSON is invalid.";
        }
        return jsonValidationOutput;
    }

    public static Object readJson(String json, String query) {
        String requestBody = "";
        if(query.trim().equalsIgnoreCase("$")) {
            return json;
        } else {
            requestBody = JsonPath.parse(json).jsonString();
            Object evaluatedResult = jsonParse(requestBody, query.trim());
            return evaluatedResult;
        }
    }

    public static Object jsonParse(String json, String query) {
        Object output;
        Gson gson = new Gson();
        try{
            output = JsonPath.read(json,query.trim());
            JsonElement jsonElement = gson.toJsonTree(output);
            String jsonString = gson.toJson(jsonElement);
            System.out.println("output is: "+ jsonString);
            output = jsonString;
        } catch (PathNotFoundException e) {
            output = "No results for path:"+ query.trim() + ". Please check your query.";
        }
        return output;
    }
}