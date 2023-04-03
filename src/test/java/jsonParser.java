import com.jayway.jsonpath.JsonPath;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class jsonParser {
    public static void main(String[] args) {
        Frame f = new Frame("JSON Parser");

        final TextArea inputQuery = new TextArea("Enter your query");
        inputQuery.setBounds(300, 50, 300, 50);

        final TextArea queryPlayGround = new TextArea("Enter your JSON here");
        queryPlayGround.setBounds(300, 250, 500, 300);

        Button button = new Button("Evaluate your query");
        button.setBounds(300, 100, 150, 50);

        final TextArea output = new TextArea("Check the output");
        output.setBounds(1000, 250, 500, 300);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String json = queryPlayGround.getText();
                String query = inputQuery.getText();
                String result = readJson(json,query);
                System.out.println(" Result is :" + result);
                output.setText(result);
            }
        });

        f.add(queryPlayGround);
        f.add(inputQuery);
        f.add(output);
        f.add(button);

        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }

    public static String readJson(String json, String query) {
        String requestBody = "";
        requestBody = JsonPath.parse(json).jsonString();
        String evaluatedResult = jsonParse(requestBody, query);
        return evaluatedResult;
    }

    public static String jsonParse(String json, String query) {
    return JsonPath.read(json, query).toString();
    }
}