package dat044.lab2;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import static java.lang.Double.NaN;
import static java.lang.Math.pow;


/*
 *   A calculator for rather simple arithmetic expressions
 *
 *   This is not the program, it's a class declaration (with methods) in it's
 *   own file (which must be named Calculator.java)
 *
 *   NOTE:
 *   - No negative numbers implemented
 */
public class Calculator {

    // Here are the only allowed instance variables!
    // Error messages (more on static later)
    final static String MISSING_OPERAND = "Missing or bad operand";
    final static String DIV_BY_ZERO = "Division with 0";
    final static String MISSING_OPERATOR = "Missing operator or parenthesis";
    final static String OP_NOT_FOUND = "Operator not found";

    // Definition of operators
    final static String OPERATORS = "+-*/^";

    // Method used in REPL
    double eval(String expr) {
        if (expr.length() == 0) {
            return NaN;
        }
        List<String> tokens = tokenize(expr);
        List<String> postfix = infix2Postfix(tokens);
        return evalPostfix(postfix);
    }

    // ------  Evaluate RPN expression -------------------

    double evalPostfix(List<String> postfix) {
        // TODO
        return 0;
    }

    double applyOperator(String op, double d1, double d2) {
        switch (op) {
            case "+":
                return d1 + d2;
            case "-":
                return d2 - d1;
            case "*":
                return d1 * d2;
            case "/":
                if (d1 == 0) {
                    throw new IllegalArgumentException(DIV_BY_ZERO);
                }
                return d2 / d1;
            case "^":
                return pow(d2, d1);
        }
        throw new RuntimeException(OP_NOT_FOUND);
    }

    // ------- Infix 2 Postfix ------------------------

    List<String> infix2Postfix(List<String> infix) {
        List<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for(int i = 0; i < infix.size(); i++) {
            String token = infix.get(i);
            if (token.equals("(")) {
                stack.push(token);
            }
            else if (token.equals(")")) {
                while(!stack.peek().equals("(")) {
                    String op = stack.pop();
                    postfix.add(op);
                }
                stack.pop();
            }
            else if (Character.isDigit(token.charAt(0))) {
                postfix.add(token);
            }
            else if ("+*/-^".contains(token)) {
                if (getPrecedence(token) > getPrecedence(stack.peek())) {
                    stack.push(token);
                }
                else if (getPrecedence(token) == getPrecedence(stack.peek()) && getAssociativity(token).equals(Assoc.LEFT)) {
                    while (!stack.empty()){
                        postfix.add(stack.pop());
                    }
                    stack.push(token);
                }
                else {
                    while (!stack.empty()){
                        postfix.add(stack.pop());
                    }
                    stack.push(token);
                }

            }
            else {
                System.out.println("Invalid char");
            }
            if (i == infix.size() - 1) {
                while (!stack.empty()) {
                    postfix.add(stack.pop());
                }
            }
        }
        return postfix;
    }

    int getPrecedence(String op) {
        if ("+-".contains(op)) {
            return 2;
        } else if ("*/".contains(op)) {
            return 3;
        } else if ("^".contains(op)) {
            return 4;
        } else {
            throw new RuntimeException(OP_NOT_FOUND);
        }
    }

    Assoc getAssociativity(String op) {
        if ("+-*/".contains(op)) {
            return Assoc.LEFT;
        } else if ("^".contains(op)) {
            return Assoc.RIGHT;
        } else {
            throw new RuntimeException(OP_NOT_FOUND);
        }
    }

    enum Assoc {
        LEFT,
        RIGHT
    }

    // ---------- Tokenize -----------------------

    // List String (not char) because numbers (with many chars)
    List<String> tokenize(String expr) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < expr.length(); i++) {
            if (expr.charAt(i) != ' ') {
                if (expr.length() > i+2 && Character.isDigit(expr.charAt(i)) && Character.isDigit(expr.charAt(i + 1)) && Character.isDigit(expr.charAt(i+2))) {
                    list.add(expr.substring(i, i+3));
                    i += 2;
                }
                else if (expr.length() > i + 1 && Character.isDigit(expr.charAt(i)) && Character.isDigit(expr.charAt(i+1))) {
                    list.add(expr.substring(i, i+2));
                    i++;
                }
                else {
                    String c = String.valueOf(expr.charAt(i));
                    list.add(c);
                }
            }
        }
        return list;
    }

}
