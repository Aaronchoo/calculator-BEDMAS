package com.example.android.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
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
    int numberOfBrackets = 0;

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
        numberOfBrackets+=1;
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
                if (last.equals(")") || last.equals("(") || last.equals("0") || last.equals("1") || last.equals("3") || last.equals("2") || last.equals("4") || last.equals("5") || last.equals("6") || last.equals("7") || last.equals("8") || last.equals("9")) {
                    //set up the default operation
                    inputNumericalValue("(");
                }
                previousOperation = "(";
            }
        } else {
            //This means the text is empty so it is able to add the open bracket
            inputNumericalValue("(");
            previousOperation = "(";
        }
        //Increase number of left brackets
        numberOfBrackets += 1;
    }

    public void ButtonBracketClose(View v) {
        addOperations(")");
        //Can be added anywhere expect before a dot
        //If not complete open and close then it loses it's priority
        //one less open bracket to match
        numberOfBrackets -= 1;

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
            //Get the last character
            String lastOne = currentValue.substring(currentValue.length() - 1, currentValue.length());
            if (lastOne.equals(".")) previousOperation = "random";
            else if(lastOne.equals(")")) numberOfBrackets+=1;
            else if(lastOne.equals("("))numberOfBrackets-=1;
            //Get the string except for the last letter of the current text by using substrings
            String deletedOne = currentValue.substring(0, currentValue.length() - 1);
            //Set text to the new updated String
            ScreenOfNumber.setText(deletedOne);
        }

    }

    public void ButtonSubmit(View v) {
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        //Get the math problem the user wants to calculator to solve
        String mathQuestion = ScreenOfNumber.getText().toString();
        //The last letter/character
        String lastOne = mathQuestion.substring(mathQuestion.length() - 1, mathQuestion.length());
        //Delete unnecessary info
        if (lastOne.equals("*") || lastOne.equals("/") || lastOne.equals("-") || lastOne.equals("+") || lastOne.equals("^") || lastOne.equals("(")) {
            Toast.makeText(this, "Cannot end math question with that!", Toast.LENGTH_SHORT).show();
        }//Must check if there are equal amoutn of brackets as user may mess up and forget a few
        else if (numberOfBrackets != 0) {
            //If it's positive it means they have too many open brackets
            if (numberOfBrackets > 0) {
                //Tells them to add more close brackets
                Toast.makeText(this, "You have one too many open brackets! Is this a mistake?", Toast.LENGTH_SHORT).show();
            } else {
                //If there are too many closed brackets then it will inform the user there are too many closed brackets
                Toast.makeText(this, "You have one too many closing brackets! Is this a mistake?", Toast.LENGTH_SHORT).show();
            }
        } else {
            //Start the alg to solve it
            //Have the number of operations for the question/eq'n
            int numberOfOperations = 0;
            //Holds number of brackets somethign is inside
            int bracketsInside = 0;
            //Hold the priority of each plus which it is
            int[][] priorityOfOperations = new int[mathQuestion.length()][2];
            for (int i = 0; i < mathQuestion.length(); i++) {
                //Now to check each indivudal character
                if (mathQuestion.substring(i, i + 1).equals("+")) {
                    //The lower the number the less priority it will receive
                    priorityOfOperations[numberOfOperations][0] = 1 + 10 * bracketsInside;
                    ;
                    //Increase the ocunter
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("-")) {
                    //The lower the number the less priority it will receive
                    priorityOfOperations[numberOfOperations][1] = 1 + 10 * bracketsInside;
                    ;
                    //Increase the ocunter
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("/")) {
                    //The priority will be above addition/subtraction but not above the other two
                    priorityOfOperations[numberOfOperations][0] = 2 + 10 * bracketsInside;
                    ;
                    //increase the counter
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("*")) {
                    //The priority will be above addition/subtraction but not above the other two
                    priorityOfOperations[numberOfOperations][1] = 2 + 10 * bracketsInside;
                    ;
                    //increase the counter
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("^")) {
                    //Give a higher priority to the expoenent
                    priorityOfOperations[numberOfOperations][0] = 3 + 10 * bracketsInside;
                    ;
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("(")) {
                    //Check if it's before/after a number
                    if (i > 0) {
                        //Get the number/operation that was done before the bracket
                        String last = mathQuestion.substring(i - 1, i);
                        //If the previous value was a number or closing bracket
                        if (last.equals(")") || last.equals("0") || last.equals("1") || last.equals("3") || last.equals("2") || last.equals("4") || last.equals("5") || last.equals("6") || last.equals("7") || last.equals("8") || last.equals("9")) {
                            //Add a multiplication sign and add it to the priority list
                            mathQuestion = mathQuestion.substring(0, i) + "*" + mathQuestion.substring(i, mathQuestion.length());
                            priorityOfOperations[numberOfOperations][1] = 2 + 10 * bracketsInside;
                            ;
                            //increase the counter
                            numberOfOperations += 1;
                        }
                    }                    //Not part of the priority but needs it's own unique number as it will be used soon
                    bracketsInside += 1;
                } else if (mathQuestion.substring(i, i + 1).equals(")")) {
                    bracketsInside -= 1;
                    if (i + 1 < mathQuestion.length()) {
                        //Get the character after the closing bracket
                        String last = mathQuestion.substring(i + 1, i + 2);
                        if ((last.equals("(") || last.equals("0") || last.equals("1") || last.equals("3") || last.equals("2") || last.equals("4") || last.equals("5") || last.equals("6") || last.equals("7") || last.equals("8") || last.equals("9"))) {
                            //Add a multiplication sign and add it to the priority list
                            mathQuestion = mathQuestion.substring(0, i + 1) + "*" + mathQuestion.substring(i + 1, mathQuestion.length());
                        }
                    }
                }
            }
            //Change all the operations to stars for ease since what the characters are do not have a point now
            mathQuestion = mathQuestion.replace('+', '*');
            mathQuestion = mathQuestion.replace('-', '*');
            mathQuestion = mathQuestion.replace('/', '*');
            mathQuestion = mathQuestion.replace('*', '*');
            mathQuestion = mathQuestion.replace('^', '*');
            mathQuestion = mathQuestion.replace('(', ' ');
            mathQuestion = mathQuestion.replace(')', ' ');
            mathQuestion = mathQuestion.trim();
            //If there is only one number
            if (numberOfOperations == 1) {
                int biggest;
                int whichOperation;
                if (priorityOfOperations[0][0] > priorityOfOperations[0][1]) {
                    biggest = priorityOfOperations[0][0];
                    whichOperation = 0;
                } else {
                    biggest = priorityOfOperations[0][1];
                    whichOperation = 1;
                }
                int pos = mathQuestion.indexOf("*");
                double first = Double.parseDouble(mathQuestion.substring(0, pos));
                double second = Double.parseDouble(mathQuestion.substring(pos + 1, mathQuestion.length()));
                if (biggest % 10 == 1) {
                    if (whichOperation == 0) {
                        first = first + second;
                        int checker = (int) first;
                        if(checker == first)
                        {
                            ScreenOfNumber.setText(Integer.toString(checker));
                        }
                        else ScreenOfNumber.setText(Double.toString(first));
                    } else {
                        first = first - second;
                        int checker = (int) first;
                        if(checker == first)
                        {
                            ScreenOfNumber.setText(Integer.toString(checker));
                        }
                        else ScreenOfNumber.setText(Double.toString(first));
                    }
                } else if (biggest % 10 == 2) {
                    if (whichOperation == 0) {
                        first = first / second;
                        int checker = (int) first;
                        if(checker == first)
                        {
                            ScreenOfNumber.setText(Integer.toString(checker));
                        }
                        else ScreenOfNumber.setText(Double.toString(first));
                    } else {
                        first = first * second;
                        int checker = (int) first;
                        if(checker == first)
                        {
                            ScreenOfNumber.setText(Integer.toString(checker));
                        }
                        else ScreenOfNumber.setText(Double.toString(first));
                    }
                } else {
                    second = Math.pow(first, second);
                    int checker = (int) second;
                    if(second == checker){
                        ScreenOfNumber.setText(Integer.toString(checker));
                    }
                   else ScreenOfNumber.setText(Double.toString(second));
                }
            }
            //Counting loop will find the operation to start with
            else {
                for (int i = 0; i < numberOfOperations; i++) {
                    int biggest = 0;
                    int currentPos = 0;
                    int whichOperation = 0;
                    for (int j = 0; j < numberOfOperations; j++) {
                        if (biggest < priorityOfOperations[j][0]) {
                            biggest = priorityOfOperations[j][0];
                            currentPos = j;
                            whichOperation = 0;
                        } else if (biggest < priorityOfOperations[j][1]) {
                            biggest = priorityOfOperations[j][1];
                            currentPos = j;
                            whichOperation = 1;
                        }


                    }
                    for (int j = 0; j < numberOfOperations; j++) {
                        //If it's the only one then find the location of it
                        if (currentPos == 0) {

                            i = numberOfOperations;
                            j = numberOfOperations;
                        }
                    }
                }
            }
        }
    }
}


//Will add this later (probably will use an array or so)
//public void ButtonPositiveNegative(View v){    }

