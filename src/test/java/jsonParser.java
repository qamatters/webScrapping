
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;

public class jsonParser {
    public static void main(String[] args) {
        Frame f = new Frame("JSON Parser");
//        f.setBackground(Color.DARK_GRAY);

        final TextArea inputQuery = new TextArea("Enter your query here..");
        inputQuery.setBounds(300, 50, 1000, 60);

        Button button = new Button("Evaluate your query");
        button.setBounds(1000, 180, 150, 50);
        button.setBackground(Color.green);
        button.setForeground(Color.BLACK);

        Button prettyFi = new Button("Beautify your input JSON");
        prettyFi.setBounds(300, 180, 150, 50);
        prettyFi.setBackground(Color.green);
        prettyFi.setForeground(Color.BLACK);

        final TextArea queryPlayGround = new TextArea("Enter your JSON here");
        queryPlayGround.setBounds(300, 250, 700, 700);
//        queryPlayGround.setBackground(Color.DARK_GRAY);
//        queryPlayGround.setForeground(Color.white);

        final TextArea output = new TextArea("Check the output");
        output.setBounds(1000, 250, 700, 700);
//        output.setBackground(Color.DARK_GRAY);
//        output.setForeground(Color.white);


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
                String json = queryPlayGround.getText();
//                System.out.println("json is :"+ json);
                String query = inputQuery.getText();
                Object result = readJson(json, query);
//                System.out.println(" Result is :" + result);

                if(result.toString().contains("No results for path")) {
                    output.setText(result.toString());
                } else {

                    if (result instanceof String) {
                        try {
                            output.setText(prettyFyJSON(result.toString()));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (result instanceof JSONArray) {
                        try {
                            output.setText(prettyFyJSON(((JSONArray) result).toJSONString()));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (result == null) {
                        output.setText("null");
                    } else {
                        try {
                            output.setText(prettyFyJSON(result.toString()));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
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

        f.setSize(400, 400);
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

    public static Object readJson(String json, String query) {
        String requestBody = "";
        requestBody = JsonPath.parse(json).jsonString();
        Object evaluatedResult = jsonParse(requestBody, query);
        return evaluatedResult;
    }

    public static Object jsonParse(String json, String query) {
        Object output;
        try{
            output = JsonPath.read(json,query);
        } catch (PathNotFoundException e) {
            output = "No results for path:"+ query + ". Please check your query.";
        }
        return output;
    }
}