package com.example.android.calculator;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyCharacterMap;
import android.view.View;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringReader;
import java.util.ArrayList;

import static com.example.android.calculator.R.drawable.outline_for_button;

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
        if (currentValue.equals("Infinity")) ScreenOfNumber.setText(numberAdd);
            //Set the text of the textview to the previous plus the number needed to be added using string addition
        else ScreenOfNumber.setText(currentValue + numberAdd);
        //Sets it to tell the users that it is calculating
        isCalculating();
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
            if (last.equals(")") || isEqualNumbers(last)) {
                //Add the operation to the text
                ScreenOfNumber.setText(currentValue + operation);
                //Save the operation added to make sure that certain ones are not able to repeat
                previousOperation = operation;
                if (previousOperation.equals("^(")) numberOfBrackets += 1;
                    //If not complete open and close then it loses it's priority
                    //one less open bracket to match
                else if (previousOperation.equals(")") || previousOperation.equals("--)"))
                    numberOfBrackets -= 1;
            }
            //Will tell that the calculator is calculating
            isCalculating();
            //If not it will not do anything.
        }
    }

    //@para button that needs to temporary change the number
    //Will take in the button, set the timer to activate
    //Will change the backgrouund colour then quickly back to the original colour
    public void ChangeButtonColour(final Button myButton) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myButton.setTextColor(Color.BLACK);
                myButton.setBackgroundResource(R.drawable.outline_for_button);
            }
        }, 15);
        myButton.setBackgroundColor(Color.parseColor("#212121"));
        myButton.setTextColor(Color.WHITE);
    }

    //Onclicks for the buttons to add the numbers and different operations
    public void ButtonOne(View v) {
        final Button myButton = (Button) findViewById(R.id.button_number_1);
        inputNumericalValue("1");
        ChangeButtonColour(myButton);
    }

    public void ButtonTwo(View v) {
        inputNumericalValue("2");
        final Button myButton = (Button) findViewById(R.id.button_number_2);
        ChangeButtonColour(myButton);
    }

    public void ButtonThree(View v) {
        inputNumericalValue("3");
        final Button myButton = (Button) findViewById(R.id.button_number_3);
        ChangeButtonColour(myButton);
    }

    public void ButtonFour(View v) {
        inputNumericalValue("4");
        final Button myButton = (Button) findViewById(R.id.button_number_4);
        ChangeButtonColour(myButton);
    }

    public void ButtonFive(View v) {
        inputNumericalValue("5");
        final Button myButton = (Button) findViewById(R.id.button_number_5);
        ChangeButtonColour(myButton);
    }

    public void ButtonSix(View v) {
        inputNumericalValue("6");
        final Button myButton = (Button) findViewById(R.id.button_number_6);
        ChangeButtonColour(myButton);
    }

    public void ButtonSeven(View v) {
        inputNumericalValue("7");
        final Button myButton = (Button) findViewById(R.id.button_number_7);
        ChangeButtonColour(myButton);
    }

    public void ButtonEight(View v) {
        inputNumericalValue("8");
        final Button myButton = (Button) findViewById(R.id.button_number_8);
        ChangeButtonColour(myButton);
    }

    public void ButtonNine(View v) {
        inputNumericalValue("9");
        final Button myButton = (Button) findViewById(R.id.button_number_9);
        ChangeButtonColour(myButton);
    }

    public void ButtonZero(View v) {
        inputNumericalValue("0");
        final Button myButton = (Button) findViewById(R.id.button_number_0);
        ChangeButtonColour(myButton);
    }

    public void ButtonExpo(View v) {
        addOperations("^(");
        final Button myButton = (Button) findViewById(R.id.button_exponent);
        ChangeButtonColour(myButton);
    }

    public void ButtonPlus(View v) {
        addOperations("+");
        final Button myButton = (Button) findViewById(R.id.button_plus);
        ChangeButtonColour(myButton);
    }

    public void ButtonMinus(View v) {
        addOperations("-");
        final Button myButton = (Button) findViewById(R.id.button_minus);
        ChangeButtonColour(myButton);
    }

    public void ButtonDivision(View v) {
        addOperations("÷");
        final Button myButton = (Button) findViewById(R.id.button_division);
        ChangeButtonColour(myButton);
    }

    public void ButtonMultiply(View v) {
        addOperations("X");
        final Button myButton = (Button) findViewById(R.id.button_multiply);
        ChangeButtonColour(myButton);
    }

    public void ButtonDot(View v) {
        final Button myButton = (Button) findViewById(R.id.button_dot);
        ChangeButtonColour(myButton);
        //Checks if a dot has been used yet
        if (!previousOperation.equals(".")) {
            //Will allow the decimal if one has not been used yet (for the number)
            addOperations(".");
        }
    }

    //Change all operation signs in the string to "X"
    //@param will be the string that needs to be changed
    public String identifyAllOperations(String tempText, boolean closing, boolean negative) {
        tempText = tempText.replace('+', 'X');
        if (negative == true) tempText = tempText.replace('-', 'X');
        tempText = tempText.replace('÷', 'X');
        tempText = tempText.replace('(', 'X');
        tempText = tempText.replace('^', 'X');
        tempText = tempText.replace('(', 'X');
        if (closing == true) tempText = tempText.replace(')', 'X');
        return tempText;
    }

    //Onclick for the negative and postivie number
    public void ButtonNegativePositive(View v) {
        final Button myButton = (Button) findViewById(R.id.button_number_negative_positive);
        ChangeButtonColour(myButton);
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        //Get the current string and save it to a temp string
        String tempText = ScreenOfNumber.getText().toString();
        //Turn all text into '*' so it is easier to find the lsat operation (would use 'previousOPeration' to find it but it may contain a decimal and it's not possible to add a negative sign after a decimal
        if (!tempText.equals("")) {
            //Find the position of the last number
            String findLastNumber = tempText;
            findLastNumber = findLastNumber.replace('0', '*');
            findLastNumber = findLastNumber.replace('1', '*');
            findLastNumber = findLastNumber.replace('2', '*');
            findLastNumber = findLastNumber.replace('3', '*');
            findLastNumber = findLastNumber.replace('4', '*');
            findLastNumber = findLastNumber.replace('5', '*');
            findLastNumber = findLastNumber.replace('6', '*');
            findLastNumber = findLastNumber.replace('8', '*');
            findLastNumber = findLastNumber.replace('7', '*');
            findLastNumber = findLastNumber.replace('9', '*');
            findLastNumber = findLastNumber.replace(')', '&');
            //Figures out if there are any brackets behind it or other operation
            int numberEnds = findLastNumber.lastIndexOf('*');
            int lastClosing = findLastNumber.lastIndexOf('&');
            int lastAnythingElse = identifyAllOperations(findLastNumber, false, true).lastIndexOf('X');
            //Figure out where the other operation starts before the last digit entered
            //Makes sure that there are acutal numbers before changin gthe text to cut off at the last number
            //Will also ensure that any operation does not go before the last number
            if (numberEnds > 0 && lastClosing > numberEnds && lastAnythingElse <= numberEnds)
                tempText = tempText.substring(0, numberEnds + 1);
            //Change the current text to identify all non-numbers
            tempText = identifyAllOperations(tempText, true, true);
            //Get the position
            int positionOfLast = tempText.lastIndexOf('X');
            //Have a string that holds the length of the string
            int textString = ScreenOfNumber.getText().toString().length() - 1;
            //Gets a temporary text that will save the number
            //Makes sure that there are acutal numbers
            if (numberEnds > 0 && lastClosing > numberEnds && lastAnythingElse <= numberEnds)
                tempText = tempText.substring(positionOfLast + 1, numberEnds + 1);
            //Checks to se if the position of the last index for the '*' is and makes sure that the it isn't not in the front
            if ((positionOfLast != textString && positionOfLast > 0)) {
                //Sees if the previous operation or button clicked was the negative/postive and sees if the number was previously negative or positive by looking at the sign
                if ((previousOperation.equals("--") || previousOperation.equals("--)")) && ScreenOfNumber.getText().toString().substring(positionOfLast, positionOfLast + 1).equals("-")) {
                    //Sets the temporary string to the specific negative number
                    tempText = ScreenOfNumber.getText().toString().substring(positionOfLast, numberEnds + 1);
                    double valueOfNumber = Double.parseDouble(tempText);
                    //The value will be multiplied by a negative one
                    valueOfNumber = valueOfNumber * (-1);
                    //Tyrns it back to string and adds it to the rest of the full string
                    tempText = ScreenOfNumber.getText().toString();
                    //Adds the value back to the string
                    int checker = (int) valueOfNumber;
                    if (valueOfNumber == checker) {
                        ScreenOfNumber.setText(ScreenOfNumber.getText().toString().substring(0, positionOfLast) + Integer.toString(checker));
                    } else
                        ScreenOfNumber.setText(ScreenOfNumber.getText().toString().substring(0, positionOfLast) + valueOfNumber);
                    //Since the number is now positive, make the new previous operation a postive sign
                    String addClosingBRackets = "";
                    //Add back all the closing brackets that were missed
                    for (int i = 0; i < findLastNumber.length() - 1 - numberEnds; i++) {
                        addClosingBRackets = addClosingBRackets + ")";
                    }
                    if (findLastNumber.length() - 1 - numberEnds > 0) {
                        ScreenOfNumber.setText(ScreenOfNumber.getText().toString() + addClosingBRackets);
                    }
                    //Change the previous operation to changing positive
                    previousOperation = "++";
                } else {
                    //Gets a temporary text that will save the number
                    tempText = ScreenOfNumber.getText().toString().substring(positionOfLast + 1, numberEnds + 1);
                    double valueOfNumber = Double.parseDouble(tempText);
                    String bracketJustInCase = "";
                    if (ScreenOfNumber.getText().toString().substring(positionOfLast, positionOfLast + 1).equals(")")) {
                        //add a braacket so the calculator would not think that the negative is a minus after a bracket
                        bracketJustInCase = "(";
                        //increase number of bracket
                        numberOfBrackets += 1;
                    }
                    //The value will be multiplied by a negative one
                    valueOfNumber = valueOfNumber * (-1);
                    //Tyrns it back to string and adds it to the rest of the full string
                    tempText = ScreenOfNumber.getText().toString();
                    int checker = (int) valueOfNumber;
                    if (valueOfNumber == checker) {
                        ScreenOfNumber.setText(ScreenOfNumber.getText().toString().substring(0, positionOfLast + 1) + bracketJustInCase + Integer.toString(checker));
                    } else {
                        ScreenOfNumber.setText(ScreenOfNumber.getText().toString().substring(0, positionOfLast + 1) + bracketJustInCase + valueOfNumber);
                    }
                    //Since the number is now positive, make the new previous operation a postive sign
                    String addClosingBRackets = "";
                    //Add back all the closing brackets that were missed
                    for (int i = 0; i < findLastNumber.length() - 1 - numberEnds; i++) {
                        addClosingBRackets = addClosingBRackets + ")";
                    }
                    if (findLastNumber.length() - 1 - numberEnds > 0) {
                        ScreenOfNumber.setText(ScreenOfNumber.getText().toString() + addClosingBRackets);
                    }
                    //Sets it to show it is now negative
                    previousOperation = "--";
                }

            } //If there is no operations at all jsut numbers
            else if (positionOfLast == -1) {
                double valueOfNumber = Double.parseDouble(tempText);
                valueOfNumber = valueOfNumber * (-1);
                //if that was the only operation, return the value of the new number
                int checker = (int) valueOfNumber;
                if (valueOfNumber == checker) {
                    ScreenOfNumber.setText(Integer.toString(checker));
                } else ScreenOfNumber.setText(Double.toString(valueOfNumber));
                //Sets it to show that a negative operation has been completed
                previousOperation = "--";
            } else if (positionOfLast == 0) {
                //Will get the text for the entire text view
                tempText = ScreenOfNumber.getText().toString();
                if (!(tempText.length() == 1 && tempText.contains("("))) {
                    //String to save the only two possibilities: negative sign or open bracket
                    String extra = "";
                    //Will see if it is the negative
                    if (ScreenOfNumber.getText().toString().contains("-")) {
                        //If so then get the entire negative number and set the previous operation to show it is now positive
                        tempText = tempText.substring(positionOfLast, ScreenOfNumber.getText().toString().length());
                        previousOperation = "++";
                    }//Otherwise get just the number and save the open bracket plus save the previous operation to show it is now negative
                    else {
                        tempText = tempText.substring(positionOfLast + 1, ScreenOfNumber.getText().toString().length());
                        extra = "(";
                        previousOperation = "--";
                    }
                    //Get rid of the brackets
                    int counter = 0;
                    //Count the closing brackets and save them to be added later
                    if (tempText.contains(")")) {
                        counter = tempText.length() - tempText.replace(")", "").length();
                        tempText = tempText.replace(")", "");
                    }
                    double valueOfNumber = Double.parseDouble(tempText);
                    //The value will be multiplied by a negative one
                    valueOfNumber = valueOfNumber * (-1);
                    //Tyrns it back to string and adds it to the rest of the full string
                    tempText = ScreenOfNumber.getText().toString();
                    int checker = (int) valueOfNumber;
                    if (valueOfNumber == checker) {
                        ScreenOfNumber.setText(extra + checker);
                    } else
                        ScreenOfNumber.setText(extra + (valueOfNumber));
                    //String will hold all the closing brackets needed to be added
                    String addClosingBRackets = "";
                    for (int i = 0; i < counter; i++) {
                        addClosingBRackets = addClosingBRackets + ")";
                    }
                    //Will add it if there is a point and update the text view text
                    if (counter > 0) {
                        String holdCurrent = ScreenOfNumber.getText().toString() + addClosingBRackets;
                        ScreenOfNumber.setText(holdCurrent);
                    }
                }

            }
        }
    }

    public void ButtonBracketOpen(View v) {
        final Button myButton = (Button) findViewById(R.id.button_open_bracket);
        ChangeButtonColour(myButton);
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
                inputNumericalValue("(");
                previousOperation = "(";
                //Increase number of left brackets
                numberOfBrackets += 1;

            }
        } else {
            //This means the text is empty so it is able to add the open bracket
            inputNumericalValue("(");
            previousOperation = "(";
            //Increase number of left brackets
            numberOfBrackets += 1;
        }

    }

    public void ButtonBracketClose(View v) {
        final Button myButton = (Button) findViewById(R.id.button_closing_bracket);
        ChangeButtonColour(myButton);
        if (numberOfBrackets > 0) {
            boolean negative = false;
            if (previousOperation.equals("--")) negative = true;
            addOperations(")");
            if (negative == true) previousOperation = "--)";

        }
        //Can be added anywhere expect before a dot


    }

    //OnClick for the clear button
    public void ButtonClear(View v) {
        final Button myButton = (Button) findViewById(R.id.button_number_clear);
        ChangeButtonColour(myButton);
        //Will set the text to being empty
        //Updates all the previously saved info
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        ScreenOfNumber.setText("");
        numberOfBrackets = 0;
        previousOperation = "";
        setTitle("Calculator");
    }

    public void ButtonDelete(View v) {
        final Button myButton = (Button) findViewById(R.id.button_number_delete);
        ChangeButtonColour(myButton);
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        String currentValue = ScreenOfNumber.getText().toString();
        //Makes sure that the string is not empty before deleting the last character
        if (currentValue.length() > 0) {
            //Get the last character
            String lastOne = currentValue.substring(currentValue.length() - 1, currentValue.length());
            if (lastOne.equals(")")) {
                numberOfBrackets += 1;

            } else if (lastOne.equals("(")) numberOfBrackets -= 1;
                //Checks to see if the current string has a negative sign in the front of the number
            else if (currentValue.lastIndexOf("-") == currentValue.length() - 2 && previousOperation.equals("--") && (isEqualNumbers(lastOne))) {
                //Get the string except for the last letter of the current text by using substrings
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
            //Check if the previous operation was a negative or not. Gotta make s
            if (lastOne.equals("y")) {
                //Only case where y appaears is if it it infinity.
                //Sets the entire thing blank
                ScreenOfNumber.setText("");
                //change title to calculator once more
                setTitle("Calculator");
                //Make it so there is no positive or negative
                previousOperation = "";
            } else {
                //Get the string except for the last letter of the current text by using substrings
                String deletedOne = currentValue.substring(0, currentValue.length() - 1);
                //Set text to the new updated String
                ScreenOfNumber.setText(deletedOne);
                //Making it easier to find the previous operation
                String findLastOperation = identifyAllOperations(ScreenOfNumber.getText().toString(), true, true);

                //Save the last previous operation index into a string
                int lastPosition = findLastOperation.lastIndexOf('X');
                //Sees if the position actually exists
                if (lastPosition == -1) previousOperation = "";
                else {
                    //If the last position isn't -1 it means at least one operation exist
                    //Find that position
                    findLastOperation = ScreenOfNumber.getText().toString().substring(lastPosition, lastPosition + 1);
                    //Check if it is a negative sign or minus
                    if (findLastOperation.equals(")")) {
                        int OtherthanClosingBracket = identifyAllOperations(ScreenOfNumber.getText().toString(), false, true).lastIndexOf('*');
                        if (OtherthanClosingBracket == -1) findLastOperation = "";
                        else {
                            previousOperation = "Contains')'";
                            findLastOperation = ScreenOfNumber.getText().toString().substring(OtherthanClosingBracket, OtherthanClosingBracket + 1);
                        }
                    }
                    if (findLastOperation.equals("-")) {
                        if (lastPosition == 0) previousOperation = "--";
                        else {
                            lastOne = ScreenOfNumber.getText().toString().substring(lastPosition - 1, lastPosition);
                            if (isEqualOperation(lastOne)) {
                                if (previousOperation.equals("Contains')'"))
                                    previousOperation = "--)";
                                else previousOperation = "--";

                            } else {
                                previousOperation = "-";
                            }
                        }
                    } else {
                        //Set the last operation as the last operation
                        previousOperation = findLastOperation;
                    }
                }
            }

        }

    }

    public void ButtonSubmit(View v) {
        final Button myButton = (Button) findViewById(R.id.button_submit);
        ChangeButtonColour(myButton);
        TextView ScreenOfNumber = (TextView) findViewById(R.id.show_calculations);
        String lastOne = "";
        //Get the math problem the user wants to calculator to solve
        String mathQuestion = ScreenOfNumber.getText().toString();
        if (mathQuestion.length() > 0) {
            //The last letter/character
            lastOne = mathQuestion.substring(mathQuestion.length() - 1, mathQuestion.length());
        }
        //Delete unnecessary info
        if (mathQuestion.length() == 0) {
            Toast.makeText(this, "Nothing there!", Toast.LENGTH_SHORT).show();
        } else if (isEqualOperation(lastOne)) {
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
            ///EXPLAIN
            //Arary list will allow make it easier to erase the element easier
            //One arraylist to hold the priority of the operations that need to be done
            ///ANother arraylist to hold which operation it is
            /// They are connected as the ones digit in the first array list will have at least 1 operation associated with it
            /// The second arraylist confirms the specific one
            ArrayList<Integer> priorityOfOperations = new ArrayList<>();
            ArrayList<Integer> whichIsIt = new ArrayList<>();
            for (int i = 0; i < mathQuestion.length(); i++) {
                //Now to check each indivudal character
                if (mathQuestion.substring(i, i + 1).equals("+")) {
                    //The lower the number the less priority it will receive
                    priorityOfOperations.add(1 + 10 * bracketsInside);
                    whichIsIt.add(0);
                    //Increase the ocunter
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("-")) {
                    //The lower the number the less priority it will receive
                    if (i != 0) {
                        String top = mathQuestion.substring(i - 1, i);
                        if (top.equals(")") || isEqualNumbers(top)) {
                            priorityOfOperations.add(1 + 10 * bracketsInside);
                            whichIsIt.add(1);
                            //Increase the ocunter
                            numberOfOperations += 1;
                        }

                    }
                } else if (mathQuestion.substring(i, i + 1).equals("÷")) {
                    //The priority will be above addition/subtraction but not above the other two
                    priorityOfOperations.add(2 + 10 * bracketsInside);
                    whichIsIt.add(0);

                    //increase the counter
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("X")) {
                    //The priority will be above addition/subtraction but not above the other two
                    priorityOfOperations.add(2 + 10 * bracketsInside);
                    whichIsIt.add(1);

                    //increase the counter
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("^")) {
                    //Give a higher priority to the expoenent
                    priorityOfOperations.add(3 + 10 * bracketsInside);
                    whichIsIt.add(0);
                    numberOfOperations += 1;
                } else if (mathQuestion.substring(i, i + 1).equals("(")) {
                    //Check if it's before/after a number
                    if (i > 0) {
                        //Get the number/operation that was done before the bracket
                        String last = mathQuestion.substring(i - 1, i);
                        //If the previous value was a number or closing bracket
                        if (last.equals(")") || isEqualNumbers(last)) {
                            //Add a multiplication sign and add it to the priority list
                            mathQuestion = mathQuestion.substring(0, i) + "X" + mathQuestion.substring(i, mathQuestion.length());
                            priorityOfOperations.add(2 + 10 * bracketsInside);
                            whichIsIt.add(1);
                            //increase the counter
                            numberOfOperations += 1;
                            i += 1;
                        }
                    }                    //Not part of the priority but needs it's own unique number as it will be used soon
                    bracketsInside += 1;
                } else if (mathQuestion.substring(i, i + 1).equals(")")) {
                    bracketsInside -= 1;
                    if (i + 1 < mathQuestion.length()) {
                        //Get the character after the closing bracket
                        String last = mathQuestion.substring(i + 1, i + 2);
                        if ((last.equals("(") || isEqualNumbers(last))) {
                            //Add a multiplication sign and add it to the priority list
                            mathQuestion = mathQuestion.substring(0, i + 1) + "X" + mathQuestion.substring(i + 1, mathQuestion.length());

                        }
                    }
                }
            }
            //Remove the closing and opening brackets
            mathQuestion = mathQuestion.replace("(", "");
            mathQuestion = mathQuestion.replace(")", "");
            //Change all the operations to stars for ease since what the characters are do not have a point now
            mathQuestion = identifyAllOperations(mathQuestion, false, false);
            //int that will track the index of findings
            int indexOfFindings = mathQuestion.indexOf('-');
            while (indexOfFindings >= 0) {
                //string to hold the character before the '-'
                //Checks to find the specific index of the '-'
                if (indexOfFindings > 0) {
                    //use substring to figure out the character before the one being observed
                    String previousCharacter = mathQuestion.substring(indexOfFindings - 1, indexOfFindings);
                    {
                        //Checks to see if the previous character is a number or brackets
                        //Also check if the location of the last operation is also the last character in the string
                        if (mathQuestion.length() == indexOfFindings + 1 && (previousCharacter.equals(")") || isEqualNumbers(previousCharacter))) {
                            //add it in the end
                            mathQuestion = mathQuestion.substring(0, indexOfFindings) + "X";
                            //Otherwise checks if the numbers/brackets is the previous character meaning
                            //If it is number/brackets then the '-' must be a minus sign and not a negative
                        } else if (previousCharacter.equals(")") || isEqualNumbers(previousCharacter)) {
                            mathQuestion = mathQuestion.substring(0, indexOfFindings) + "X" + mathQuestion.substring(indexOfFindings + 1, mathQuestion.length());
                        } else {
                            //Adds the negative sign to the list
                            mathQuestion = mathQuestion.substring(0, indexOfFindings) + "&" + mathQuestion.substring(indexOfFindings + 1, mathQuestion.length());
                        }
                    }
                } else {
                    mathQuestion = "&" + mathQuestion.substring(1, mathQuestion.length());
                }
                indexOfFindings = mathQuestion.indexOf('-');
            }
            mathQuestion = mathQuestion.replace('&', '-');
            //Create doubles to hold the first and s econd number that will be used
            double second = 0;
            double first = 0;
            //Will continuously run until the number of remaining operations is 0
            while (numberOfOperations > 0) {
                //Integer that will hold the priority value, the current position of the index and an integer thta will help differentiate the operations
                int biggest = 0;
                int currentPos = 0;
                int whichOperation = 0;
                //Finding the biggest out of the number of operations
                for (int j = 0; j < numberOfOperations; j++) {
                    //Will continue to cycle and check for the highest priority
                    if (biggest < priorityOfOperations.get(j)) {
                        //Once found it will assign the current biggest to the bigger value, new position and which operation
                        biggest = priorityOfOperations.get(j);
                        currentPos = j;
                        whichOperation = whichIsIt.get(j);
                    }
                }
                //Integers that will hold the position of the operation before and after the current operatoion
                int firstPosition = 0, secondPosition = mathQuestion.length();
                //Checks to see if there is only one last operation
                if (currentPos == 0 && numberOfOperations == 1) {
                    //If this is the only operation then find the exact location and praseDouble the numebers before and after it
                    secondPosition = mathQuestion.indexOf("X");
                    //From the first number ot the number just before X, the string will be converted into a double
                    first = Double.parseDouble(mathQuestion.substring(0, secondPosition));
                    //Likewise the string from the number after the sign to the last character will be converted into a oduble
                    second = Double.parseDouble(mathQuestion.substring(secondPosition + 1, mathQuestion.length()));
                } else if (currentPos == 0) {
                    //If it's the first operation to appear but there are multiple, find the next operation sign and make the double the string after the first operation and before the second
                    firstPosition = mathQuestion.indexOf("X");
                    //Finds the positions of where each numbers start and end then convert the string to doubles
                    first = Double.parseDouble(mathQuestion.substring(0, firstPosition));
                    String temp = mathQuestion.substring(firstPosition + 1, mathQuestion.length());
                    //Note that a temp string is used to help find the next 'X' using the index of function.
                    //Helps in finding the position of the last character for the second number
                    secondPosition = temp.indexOf("X") + firstPosition + 1;
                    second = Double.parseDouble(mathQuestion.substring(firstPosition + 1, secondPosition));
                    //Checks to see if the operation is the last one in the list
                } else if (currentPos + 1 == numberOfOperations) {
                    //Finds the location by locating the last occurance of the operations
                    secondPosition = mathQuestion.lastIndexOf('X');
                    //Find the first and last character for each number and convert them to doubles
                    second = Double.parseDouble(mathQuestion.substring(secondPosition + 1, mathQuestion.length()));
                    firstPosition = mathQuestion.substring(0, secondPosition - 1).lastIndexOf('X');
                    first = Double.parseDouble(mathQuestion.substring(firstPosition + 1, secondPosition));
                }//Otherwise it is in a random position
                else {
                    //have a string (same as the textView text) that can manipulated and changed without affecting the rest
                    String findingPosition = mathQuestion;
                    //the position of the operation that is done
                    int currentPosition = 0;
                    //Counts the number of characters the .indexOf counts each time and accumulates the total
                    int cont = 0;
                    //loops enough times to cover all the operations
                    for (int k = 0; k < numberOfOperations; k++) {
                        //Finds the 'X' and checks if it is in the current position that is wanted
                        int index = findingPosition.indexOf('X');
                        //totals up the value
                        cont += index;
                        //Checks one less the current position is the operation before the one focused on
                        if (k == currentPos - 1) {
                            //Save the location of the position of when the first number starts
                            firstPosition = cont + k;
                            //Checks the current position is the desirabled one
                        } else if (k == currentPos) {
                            //Save the location of the middle/current operation
                            currentPosition = cont + k;
                            //Sees if the current position is one more than the operation focused
                        } else if (k == currentPos + 1) {
                            //Saves the last position of the number
                            secondPosition = cont + k;
                            //breaks
                            k = numberOfOperations;
                        }
                        //Will continue to manipulate the string to find the next value
                        findingPosition = findingPosition.substring(index + 1, findingPosition.length());

                    }
                    //Save the first and second number after finding out their locations and converting them
                    first = Double.parseDouble(mathQuestion.substring(firstPosition + 1, currentPosition));
                    second = Double.parseDouble(mathQuestion.substring(currentPosition + 1, secondPosition));
                }
                //Remove the current position's priority as it will be done and used
                priorityOfOperations.remove(currentPos);
                whichIsIt.remove(currentPos);
                //Biggest which is the priority will have a specific ones digit that is affiliated with a group of operation
                //1 being "+" or "-" , 2 being "x" or "/", and 3 being only "^"
                //Checks to see if it is subtraction/addition
                if (biggest % 10 == 1) {
                    //Which operation stores the proper type ( 0 equal addition and 1 equals subtraction)
                    if (whichOperation == 0) {
                        //Will update the first
                        first = first + second;
                        //Function that will automatically fix the string and return back the new string
                        mathQuestion = fixedEquation(first, numberOfOperations, mathQuestion, currentPos, secondPosition, firstPosition);
                    } else {
                        //Otherwise it will subtract the two numbers and update first
                        first = first - second;
                        //Uses the function to edit the current string to the new updated version with first and second subtracted
                        mathQuestion = fixedEquation(first, numberOfOperations, mathQuestion, currentPos, secondPosition, firstPosition);
                    }
                    //Check if infinity is already part of the answer
                    if (mathQuestion.contains("Infinity")) {
                        //Set the entire thing to infinity
                        mathQuestion = "Infinity";
                        //stop code from working before it crashes
                        break;
                    }
                    //Check to see if the operation should match multiplication and division
                } else if (biggest % 10 == 2) {
                    //0 equals division and 1 equals multiplication
                    //Each value will help differentiate
                    if (whichOperation == 0) {
                        //Saves the division of the two numbers to a double
                        first = first / second;
                        //Use the function to add the new number to the string
                        mathQuestion = fixedEquation(first, numberOfOperations, mathQuestion, currentPos, secondPosition, firstPosition);
                    } else {
                        //Save the multiplication to a double
                        first = first * second;
                        //Will use a function that will update the String to include the math done
                        mathQuestion = fixedEquation(first, numberOfOperations, mathQuestion, currentPos, secondPosition, firstPosition);
                    }
                    //Checks to see if infinity happened
                    if (mathQuestion.contains("Infinity")) {
                        //Will not function if infinity is found
                        mathQuestion = "Infinity";
                        break;
                    }
                } else {
                    //Last option is exponents
                    //Will use the math.pow function to automatically get the exponent of second to the base of first
                    first = Math.pow(first, second);
                    //Because 3^3 is the only case foudn that does not work correctly
                    if (second == 3 && first == 26.99999999999999) first = 27;
                    //Updates the string value
                    mathQuestion = fixedEquation(first, numberOfOperations, mathQuestion, currentPos, secondPosition, firstPosition);
                    //checks for infinity to ensure code does not fail
                    if (mathQuestion.contains("Infinity")) {
                        mathQuestion = "Infinity";
                        //Once infinity is found break out of the code
                        break;
                    }
                }
                //Decrease the number of operations as each time the code completes one it is no longer important
                numberOfOperations -= 1;
            }

            //Set title to the question
            setTitle(ScreenOfNumber.getText().toString() + " = " + mathQuestion);
            //After all the math, update the textView to the final answer
            ScreenOfNumber.setText(mathQuestion);


        }
    }


    //Function that will return a string that is the edited version that includes the math done
    //@para first is to save the number that was the result of the operations
    //@para numberOfOperation to help use as a check as there are different cases for each number
    //@para mathQuestion that holds the string that needs to be altered to include the math
    //@para currenPos will have the  location of the operation relative to other operation signs
    //@para spos is the position of the operation sign after the current
    //@para pos is the position of th eoperation sign before the current
    public String fixedEquation(double first, int numberOfOperations, String mathQuestion, int currentPos, int spos, int pos) {
        //Checks to see number of operations to account for
        //Checks if there are more than 2 operations as this means it is possible the number needs to be inserted in the front, back, or anywhere in between
        if (numberOfOperations > 2) {
            //Checks if the position is in the front
            if (currentPos == 0) {
                //If it is then cast and check if they are equal
                int checker = (int) first;
                if (first == checker) {
                    //Will add the number infront of the operation sign right after the one that was focused on
                    return checker + mathQuestion.substring(spos, mathQuestion.length());
                }
                return first + mathQuestion.substring(spos, mathQuestion.length());
            } else if (currentPos + 1 == numberOfOperations) {
                //Otherwise if the two numbers were in the end of the string
                int checker = (int) first;
                if (first == checker) {
                    //Will return the string up to the second last operation sign then the new number
                    return mathQuestion.substring(0, pos + 1) + checker;
                }
                return mathQuestion.substring(0, pos + 1) + first;
            } else {
                //This means it could be randomly in the middle
                int checker = (int) first;
                if (first == checker) {
                    //Will add the new numebr in between the operation sign before and after the one that was focused on
                    return mathQuestion.substring(0, pos + 1) + checker + mathQuestion.substring(spos, mathQuestion.length());
                }
                return mathQuestion.substring(0, pos + 1) + first + mathQuestion.substring(spos, mathQuestion.length());
            }
        } else if (numberOfOperations == 2) {
            //If there are only 2 operation signs (which includes the focused on) then there are two cases
            if (currentPos == 0) {
                //The number goes before the second operation sign
                int checker = (int) first;
                if (first == checker) {
                    //Will add the new number infront of the altered string
                    return checker + mathQuestion.substring(spos, mathQuestion.length());
                } else return first + mathQuestion.substring(spos, mathQuestion.length());
            } else {
                //Otherwise it will be added to the back of the string as there were only two and it was the second operation in the list yet had higher priority
                int checker = (int) first;
                if (first == checker) {
                    //add the number to the back of the altered string
                    return mathQuestion.substring(0, pos + 1) + (Integer.toString(checker));
                } else return mathQuestion.substring(0, pos + 1) + first;
            }
        } else {
            //if that was the only operation, return the value of the new number
            int checker = (int) first;
            if (first == checker) {
                return (Integer.toString(checker));
            } else return (Double.toString(first));
        }
    }

    //Will check to see if the character is equal to a number
    //@para last is the last character that needs to be checked
    public boolean isEqualNumbers(String last) {
        //If the string matches one of the numbers then return true
        if (last.equals("0") || last.equals("1") || last.equals("3") || last.equals("2") || last.equals("4") || last.equals("5") || last.equals("6") || last.equals("7") || last.equals("8") || last.equals("9"))
            return true;
            //Otherwise it isn't a number and program should return false
        else return false;
    }

    //Will be used to check if the last character in a string is a operation
    //@para lastOne is the last character of a string that needs to be checked
    public boolean isEqualOperation(String lastOne) {
        //Checks if the last character is an operation sign
        if (lastOne.equals("X") || lastOne.equals("÷") || lastOne.equals("-") || lastOne.equals("+") || lastOne.equals("^") || lastOne.equals("("))
            return true;
            //otherwise return a false as it is not
        else return false;
    }

    //Will set the title to calculating... if a number or operation is pressed if not already calculating
    public void isCalculating(){
        if(!getTitle().equals("Calculating....")) setTitle("Calculating....");

    }
}


//Will add this later (probably will use an array or so)
//public void ButtonPositiveNegative(View v){    }

