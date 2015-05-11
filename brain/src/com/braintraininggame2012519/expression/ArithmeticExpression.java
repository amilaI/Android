package com.braintraininggame2012519.expression;
/**
 * @author Amila Iddamalgoda (2012519)
 * ECWM511 MOBILE APPLICATION DEVELOPMENT - Assignment 1 (Android)
 * Submission Date : 2015 - 03- 16
 */
import java.util.Random;
import java.util.Vector;

/**
 * This will generate a random arithmetic expression
 * This will be calculated according to the BODMAS law
 */
public class ArithmeticExpression {

	//Instance variables declaration
	private String expression;
	private Vector<Integer> numbers = new Vector<Integer>();
	private Vector<Character> operators = new Vector<Character>();
	private int finalAnswer;
	private int minValue;
	private int maxValue;
	int noOfIntegers = 0;

	public ArithmeticExpression(int min, int max) {
		this.minValue = min;
		this.maxValue = max;
		this.expression = "";
	}
	//Generates the arithmetic expression
	public String generateExpresssion() {
		//Instantiate Random number
		Random rarndomNo = new Random();
		int low = minValue;
		int high = maxValue + 1;
		noOfIntegers = rarndomNo.nextInt(high - low) + low;
		for (int x = noOfIntegers; x > 0; x--) {
			int temp = rarndomNo.nextInt(21) + 1;
			expression += temp;
			numbers.add(temp);
			if (x != 1) {
				char op = getOperator(rarndomNo.nextInt(4));
				expression += op;
				operators.add(op);
			}
		}
		//call evaluate method
		evaluate();
		return expression;
	}
	//Evaluate the expression
	private void evaluate() {
		int size = operators.size();
		for (int i = 0; i < size; i++) {
			int num1 = numbers.firstElement();
			numbers.remove(0);
			int num2 = numbers.firstElement();
			// numbers.remove(0);
			char op = operators.firstElement();
			operators.remove(0);
			numbers.set(0, calculateExpression(num1, num2, op));
		}
		finalAnswer = numbers.firstElement();
	}
	//Calculate the expression
	private int calculateExpression(int number1, int number2, char operation) {
		int answer = 0;
		switch (operation) {
		case '/':
			answer = number1 / number2;
			break;
		case '*':
			answer = number1 * number2;
			break;
		case '+':
			answer = number1 + number2;
			break;
		case '-':
			answer = number1 - number2;
			break;
		}
		return answer;
	}
	//Get corresponding mathematical operator
	private char getOperator(int value) {
		switch (value) {
		case 3:
			return '/';
		case 2:
			return '*';
		case 1:
			return '-';
		default:
			return '+';
		}
	}
	//Get Final answer
	public int getFinalAnswer() {
		return finalAnswer;
	}
}
