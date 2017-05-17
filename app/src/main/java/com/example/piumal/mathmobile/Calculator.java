package com.example.piumal.mathmobile;

import java.util.Stack;

/**
 * Created by piumal on 3/11/17.
 */
public class Calculator {
    private Stack operatorStack;

    public Calculator(){
        operatorStack = new Stack();
    }

    public int calculatorEquation(String string){
        String equation=toPrefix(string);
        Integer value = null;
        Stack stack = new Stack();

        int i =0;
        while (i< equation.length()){
            char ch = equation.charAt(i);

            if (Character.isDigit(ch)){
                stack.push(ch);
            }else {
                int num1, num2;
                num2= Integer.parseInt(stack.pop().toString());
                num1 = Integer.parseInt(stack.pop().toString());
                value= cal (num1, num2,ch);
                stack.push(value);
            }
            i++;
        }
        return value;
    }

    private Integer cal(int num1, int num2, char ch) {
        Integer result = null;
        switch (ch){
            case '+':
                result=num1+num2;
                break;
            case '-':
                result = num1-num2;
                break;
            case '/':
                result = num1/num2;
                break;
            case '*':
                result= num1* num2;
                break;

        }
        return result;
    }

    private String toPrefix(String expression) {
        expression = "(" + expression+ ")";
        int i;
        char token;
         String output= "";

        for (i=0; i<expression.length();i++){
            token= expression.charAt(i);

            if (Character.isLetterOrDigit(token)==true){
                output += token;
            }else if (token == '('){
                operatorStack.push(token);

            } else if (token == ')') {
                char seeTop;
                while ((seeTop = seeTop()) != '(') {
                    output += seeTop;
                    popSeeTop();
                }
                operatorStack.pop();
            }else {
                while (priority(token)<= priority(seeTop())){
                    output += seeTop();
                    popSeeTop();
                }
                operatorStack.push(token);
            }
        }
        return output;
    }

    private int priority(char operator) {
       switch (operator){
           case '/':
               return 4;
           case  '*':
               return 3;
           case  '+':
               return 2;
           case  '-':
               return 1;
           default:
               return 0;
       }
    }

    private Character seeTop() {
        if (!operatorStack.empty())
            return (Character)operatorStack.peek();
        else
            return '0';
    }


    private void popSeeTop() {
        if (!operatorStack.empty())
            operatorStack.pop();
    }
}
