package com.example.android.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This String is used as a check to ensure that certain buttons cannot go right after another (like cannot have multiple decimals for one number ex: 55.5555.555)
    String previousOperation = "";

    //Method that will take the number that needs to be added and add it to the current text
    //Parameter is the number (0-9) the user wants to add
    public void inputNumericalValue(String numberAdd) {
        //Reference the textview
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        //Get the current text and convert it to string
        //Save the text
        String currentValue = ScreenOfNumber.getText().toString();
        //Set the text of the textview to the previous plus the number needed to be added using string addition
        ScreenOfNumber.setText(currentValue + numberAdd);
    }

    //Method that will add the operations/and extras
    //Parameter that passes in the operation that needs to be added
    public void addOperations(String operation) {
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        //String that gets the current text
        String currentValue = ScreenOfNumber.getText().toString();
        //Ensures the text is not empty before trying to use substring
        if (!currentValue.equals("")) {
            //Taking the last character from the string using substrings
            String last = currentValue.substring(currentValue.length() - 1, currentValue.length());
            //Makes sure that the operation follows a number and not another operation or such
            if (last.equals(")") || last.equals("0") || last.equals("1") || last.equals("3") || last.equals("2") || last.equals("4") || last.equals("5") || last.equals("6") || last.equals("7") || last.equals("8") || last.equals("9")) {
                //Add the operation to the text
                ScreenOfNumber.setText(currentValue + operation);
                //Save the operation added to make sure that certain ones are not able to repeat
                previousOperation = operation;
            }
            //If not it will not do anything.
        }
    }

    //Onclicks for the buttons to add the numbers and different operations
    public void ButtonOne(View v) {
        inputNumericalValue("1");
    }

    public void ButtonTwo(View v) {
        inputNumericalValue("2");
    }

    public void ButtonThree(View v) {
        inputNumericalValue("3");
    }

    public void ButtonFour(View v) {
        inputNumericalValue("4");
    }

    public void ButtonFive(View v) {
        inputNumericalValue("5");
    }

    public void ButtonSix(View v) {
        inputNumericalValue("6");
    }

    public void ButtonSeven(View v) {
        inputNumericalValue("7");
    }

    public void ButtonEight(View v) {
        inputNumericalValue("8");
    }

    public void ButtonNine(View v) {
        inputNumericalValue("9");
    }

    public void ButtonZero(View v) {
        inputNumericalValue("0");
    }

    public void ButtonExpo(View v) {
        addOperations("^(");
    }

    public void ButtonPlus(View v) {
        addOperations("+");
    }

    public void ButtonMinus(View v) {
        addOperations("-");
    }

    public void ButtonDivision(View v) {
        addOperations("/");
    }

    public void ButtonMultiply(View v) {
        addOperations("*");
    }

    public void ButtonDot(View v) {
        //Checks if a dot has been used yet
        if (!previousOperation.equals(".")) {
            //Will allow the decimal if one has not been used yet (for the number)
            addOperations(".");
        }
    }

    public void ButtonBracketOpen(View v) {
        //Can be inserted anywhere but a decimal dot so it can be inserted like a number
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        //String that gets the current text
        String currentValue = ScreenOfNumber.getText().toString();
        //Make sure it isn't emptyt
        if (!currentValue.equals("")) {
            //Taking the last character from the string making sure it isn't a "."
            String last = currentValue.substring(currentValue.length() - 1, currentValue.length());
            //If the last inputted String is not a . it's fine to use the openBracket
            if (!last.equals(".")) {
                //Check if it's a number or an operation
                if (last.equals(")") || last.equals("0") || last.equals("1") || last.equals("3") || last.equals("2") || last.equals("4") || last.equals("5") || last.equals("6") || last.equals("7") || last.equals("8") || last.equals("9")) {
                    //set up the default operation
                    inputNumericalValue("*(");
                }
                else{
                    //Add the open bracket normally because it wouldn't have to fall back to default
                    inputNumericalValue("(");
                }
            }
        }
        else{
            //This means the text is empty so it is able to add the open bracket
            inputNumericalValue("(");
        }
    }

    public void ButtonBracketClose(View v) {
        addOperations(")");
        //Can be added anywhere expect before a dot
        //If not complete open and close then it loses it's priority
    }

    //OnClick for the clear button
    public void ButtonClear(View v) {
        //Will set the text to being empty
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        ScreenOfNumber.setText("");
    }

    public void ButtonDelete(View v) {
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        String currentValue = ScreenOfNumber.getText().toString();
        //Makes sure that the string is not empty before deleting the last character
        if (currentValue.length() > 0) {
            //Get the string except for the last letter of the current text by using substrings
            String deletedOne = currentValue.substring(0, currentValue.length() - 1);
            //Set text to the new updated String
            ScreenOfNumber.setText(deletedOne);
        }

    }

    public void ButtonSubmit(View v) {
        //Just having fun
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        ScreenOfNumber.setText("Not done alg. Will fix soon!! For now do mental math!");
    }
    //Will add this later (probably will use an array or so)
    //public void ButtonPositiveNegative(View v){    }
}
