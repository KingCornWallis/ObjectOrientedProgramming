import java.util.ArrayList;

public class Polynomial {
    private final ArrayList<Term> terms;

    //Constructor for getting and parsing a polynomial string
    public Polynomial(String polynomial) {
        this.terms = new ArrayList<>();
        // removing any spaces
        polynomial = polynomial.replace(" ", "");
        
        // spliting and adding all the terms from polynomial
        int last = 0;
        for (int i = 0; i < polynomial.length(); i++) {
            if((polynomial.charAt(i) == '+' || polynomial.charAt(i) == '-') && i != last) {
                if(polynomial.charAt(i - 1) != '^') {
                    this.terms.add(new Term(polynomial.substring(last, i)));
                    last = i;
                }
            }
        }
        this.terms.add(new Term(polynomial.substring(last)));
    }
    
    //This method adds this polynomial with the given polynomial and returns a new polynomial.
    public Polynomial add(Polynomial polynomial) {
        // create a new polynomial using both polynomials
        Polynomial temp;
        if(polynomial.terms.get(0).getCoefficient() < 0)
            temp = new Polynomial(this.toString() + polynomial.toString());
        else        
            temp = new Polynomial(this.toString() + "+" + polynomial.toString());
        
        // traversing through all the terms
        for (int i = 0; i < temp.terms.size() - 1; i++) {
            for (int j = i + 1; j < temp.terms.size(); j++) {

                // if number of variables are same
                if(temp.terms.get(i).getVariables().size() == temp.terms.get(j).getVariables().size()) {
                    
                    // if term is just a number
                    if(temp.terms.get(i).getVariables().isEmpty()) {
                        // update the coefficient by adding
                        temp.terms.get(i).setCoefficient(temp.terms.get(i).getCoefficient() + temp.terms.get(j).getCoefficient());
                        
                        // remove the second term
                        temp.terms.remove(j);
                    }

                    // if term contains variables
                    else {
                        ArrayList<Variable> temp1 = temp.terms.get(i).getVariables();
                        ArrayList<Variable> temp2 = temp.terms.get(j).getVariables();
                        boolean same = false;

                        // comparing variables of both terms
                        for (int k = 0; k < temp.terms.get(i).getVariables().size(); k++) {
                            for (int l = 0; l < temp.terms.get(j).getVariables().size(); l++) {

                                // if variables are same
                                if(temp1.get(k).getVariable() == temp2.get(l).getVariable() &&
                                        temp1.get(k).getExponent() == temp2.get(l).getExponent())
                                    same = true;
                                else
                                    same = false;
                            }
                            if(!same)
                                break;
                        }

                        // if variables are same
                        if(same) {
                            // update the coefficient by adding
                            temp.terms.get(i).setCoefficient(temp.terms.get(i).getCoefficient() + temp.terms.get(j).getCoefficient());

                            // remove the second term
                            temp.terms.remove(j);
                        }
                    }
                }
            }
        }
        
        // return new polynomial
        return temp;
    }

    //This method subtracts this polynomial with the given polynomial and returns a new polynomial.
    public Polynomial sub(Polynomial polynomial) {
        // create a new polynomial using both polynomials
        Polynomial temp;
        if(polynomial.terms.get(0).getCoefficient() < 0)
            temp = new Polynomial(this.toString() + polynomial.toString());
        else        
            temp = new Polynomial(this.toString() + "+" + polynomial.toString());
        
        // traversing through all the terms
        for (int i = 0; i < temp.terms.size() - 1; i++) {
            for (int j = i + 1; j < temp.terms.size(); j++) {

                // if number of variables are same
                if(temp.terms.get(i).getVariables().size() == temp.terms.get(j).getVariables().size()) {
                    
                    // if term is just a number
                    if(temp.terms.get(i).getVariables().isEmpty()) {
                        // update the coefficient by adding
                        temp.terms.get(i).setCoefficient(temp.terms.get(i).getCoefficient() + temp.terms.get(j).getCoefficient());
                        
                        // remove the second term
                        temp.terms.remove(j);
                    }

                    // if term contains variables
                    else {
                        ArrayList<Variable> temp1 = temp.terms.get(i).getVariables();
                        ArrayList<Variable> temp2 = temp.terms.get(j).getVariables();
                        boolean same = false;

                        // comparing variables of both terms
                        for (int k = 0; k < temp.terms.get(i).getVariables().size(); k++) {
                            for (int l = 0; l < temp.terms.get(j).getVariables().size(); l++) {

                                // if variables are same
                                if(temp1.get(k).getVariable() == temp2.get(l).getVariable() &&
                                        temp1.get(k).getExponent() == temp2.get(l).getExponent())
                                    same = true;
                                else
                                    same = false;
                            }
                            if(same)
                                break;
                        }

                        // if variables are same
                        if(same) {
                            // update the coefficient by subtracting
                            temp.terms.get(i).setCoefficient(temp.terms.get(i).getCoefficient() - temp.terms.get(j).getCoefficient());

                            // remove the second term
                            temp.terms.remove(j);
                        }
                    }
                }
            }
        }
        
        // return new polynomial
        return temp;
    }

    //This method multiply this polynomial with the given polynomial and returns a new polynomial.
    public Polynomial mul(Polynomial polynomial) {
        // create a new polynomial
        Polynomial result = new Polynomial("");
        Polynomial temp1 = new Polynomial(this.toString());
        Polynomial temp2 = new Polynomial(polynomial.toString());
        
        // traversing through all the terms
        for (int i = 0; i < this.terms.size(); i++) {
            for (int j = 0; j < polynomial.terms.size(); j++) {

                // calculating and storing coefficient result
                String s = "" + (temp1.terms.get(i).getCoefficient() * temp2.terms.get(j).getCoefficient());
                
                ArrayList<Variable> variables1 = temp1.terms.get(i).getVariables();
                ArrayList<Variable> variables2 = temp2.terms.get(j).getVariables();
                
                // if number of variables in both terms are equal
                if(variables1.size() == variables2.size()) {
                    
                    // traver to every variable
                    for (int k = 0; k < variables1.size(); k++) {
                        
                        // if variables matches
                        if(variables1.get(k).getVariable() == variables2.get(k).getVariable())
                            s += printVariable(variables1.get(k).getVariable(), 
                                    (variables1.get(k).getExponent() + variables2.get(k).getExponent()));

                        // if variables do not matches
                        else {
                            s += printVariable(variables1.get(k).getVariable(), variables1.get(k).getExponent());
                            s += printVariable(variables2.get(k).getVariable(), variables2.get(k).getExponent());
                        }
                    }
                }

                // if number of variables in both terms are not equal
                else {
                    
                    // comparing variables of both terms
                    for (int k = 0; k < variables1.size(); k++) {
                        int exp = variables1.get(k).getExponent();
                        boolean same = false;
                        for (int l = 0; l < variables2.size(); l++) {
                            
                            // if variables are same
                            if(variables1.get(k).getVariable() == variables2.get(l).getVariable()) {
                                exp += variables2.get(l).getExponent();
                                s += printVariable(variables1.get(k).getVariable(), exp);
                                
                                // remove the second term
                                variables2.remove(l);
                                same = true;
                                break;
                            }
                        }
                        
                        // if not found
                        if(!same)
                            s += printVariable(variables1.get(k).getVariable(), exp);
                    }
                    
                    // if there are still variables in second term
                    for (int k = 0; k < variables2.size(); k++)
                        s += printVariable(variables2.get(k).getVariable(), variables2.get(k).getExponent());
                }
                
                // add into the result polynomial to simplfy
                result = result.add(new Polynomial(s));
            }
        }
        
        // return new polynomial
        return result;
    }
    
    private String printVariable(char variable, int exponent) {
        if(exponent != 0 && exponent != 1)
            return variable + "^" + exponent;
        else
            return "" + variable;
    }
    
    
    //This method evaluates the polynomial according to the given values.
    public double evalute(char[] characters, double[] doubles) throws ArithmeticException {
        double result = 0;
        
        // traversing every term
        for (Term term : terms) {
            double temp = term.getCoefficient();
            
            // traversing every variable
            for (Variable variable : term.getVariables()) {
                
                // traversing every character
                boolean found = false;
                for (int k = 0; k < characters.length; k++) {
                    
                    // if variable found
                    if(variable.getVariable() == characters[k]) {
                        
                        // calculate and store value
                        temp *= (Math.pow(doubles[k], variable.getExponent()));
                        found = true;
                        break;
                    }
                }
                
                // if variable not found
                if(!found)
                    throw new ArithmeticException("Variables not found!");
            }
            
            // add to result
            result += temp;
        }
        
        // return the calculated result
        return result;
    }
    
    /**
     * This method returns the polynomial in a printable string format.
     * @return polynomial as string
     */
    @Override
    public String toString() {
        String temp = "";
        // printing all the terms except the last one
        for (int i = 0; i < terms.size() - 1; i++) {
            
            // if coefficient of the current term is not 0
            if(terms.get(i).getCoefficient() != 0) {
                
                // if coefficient of the next term is negative
                if(terms.get(i + 1).getCoefficient() < 0)
                    temp += printTerm(terms.get(i));
                
                // if coefficient of the next term is positive
                else
                    temp += printTerm(terms.get(i)) + "+";
            }
        }
        
        // printing last term or the first term when there is only one term
        temp += printTerm(terms.get(terms.size() - 1));
        return temp;
    }
    
     //This method returns the term in a printable string format

    private String printTerm(Term term) {
        String temp = "";

        // if coefficient of the current term is not 1
        if(term.getCoefficient() != 1)
            temp += term.getCoefficient();

        // if there are some variables of the current term
        if(!term.getVariables().isEmpty()) {

            // traverse to every variable
            for(Variable variable : term.getVariables()) {
                temp += variable.getVariable();

                // if exponent of the current term is not 1 and 0
                if(variable.getExponent() != 1 && variable.getExponent() != 0)
                    temp += "^" + variable.getExponent();
            }
        }
        return temp;
    }    
}

class Term {
    private int coefficient;
    
    private final ArrayList<Variable> variables;
    
    /**
     * Constructor for getting and parsing a term string
     * @param term term to parse
     */
    public Term(String term) {
        this.variables = new ArrayList<>();
        // if term exists
        if(term.length() != 0) {
            char[] tempTerm = term.toCharArray();
            int index = 0, sign = 1;
            
            // if term is negative
            if(tempTerm[index] == '-') {
                sign = -1;
                index++;
            }

            // if term is positive
            if(tempTerm[index] == '+')
                index++;

            // if there is no coefficient
            if(!Character.isDigit(tempTerm[index]))
                this.coefficient = 1;

            // if there is coefficient
            else {
                this.coefficient = 0;
                // calculating complete coefficient
                while(index < term.length() && Character.isDigit(tempTerm[index]))
                    this.coefficient = (this.coefficient * 10) + (tempTerm[index++] - '0');
            }
            
            // storing coefficient with sign
            this.coefficient *= sign;
            
            // if there are any variables
            while(index < term.length() && Character.isLowerCase(tempTerm[index])) {
                
                // reusing sign for exponents
                sign = 1;

                // storing variable
                char var = tempTerm[index];
                index++;
                
                // if variable has exponent
                int exp = 1;
                if(index < term.length() && tempTerm[index] == '^') {
                    exp = 0;
                    index++;
                    
                    // if exponent is negative
                    if(index < term.length() && tempTerm[index] == '-') {
                        sign = -1;
                        index++;
                    }
                    
                    // calculating exponent
                    while(index < term.length() && Character.isDigit(tempTerm[index]))
                        exp = (exp * 10) + (tempTerm[index++] - '0');
                    
                    // storing exponent with sign
                    exp *= sign;
                }
                
                // storing exponent
                variables.add(new Variable(var, exp));
            }
        }
    }
    
    
    //This method returns the coefficient.
    public int getCoefficient() {
        return coefficient;
    }

    //This method sets the coefficient of the term
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }


    //This method returns the variables.
    public ArrayList<Variable> getVariables() {
        return variables;
    }
}

class Variable {
    private final char variable;
    private int exponent;

    
    //Constructor method to initialize variable and exponent.
    public Variable(char variable, int exponent) {
        this.variable = variable;
        this.exponent = exponent;
    }


    //This method returns the variable. 
    public char getVariable() {
        return variable;
    }

    
    //This method returns the exponent. 
    public int getExponent() {
        return exponent;
    }
    
    //This method sets the exponent.
    public void setExponent(int exponent) {
        this.exponent = exponent;
    }
}