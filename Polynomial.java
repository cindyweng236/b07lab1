import java.io.*;
import java.util.*;

class Polynomial {
    private double[] coef;
    private int[] exp;

    Polynomial() {
        coef = new double[0];
        exp = new int[0];
    }

    Polynomial(double[] coef, int[] exp) {
        if (coef.length != exp.length) {
            return;
        }
        int size = coef.length;
        int[] newExp = new int[size];
        double[] newCoef = new double[size];
        int currIndex = 0;
        for (int i = 0; i < size; i++) {
            int currExp = exp[i];
            double currCoef = coef[i];
            int expIndex = indexInArr(currExp, newExp);
            if (expIndex == -1) {
                newExp[currIndex] = exp[i];
                newCoef[currIndex] = coef[i];
                currIndex++;
            } else {
                newCoef[expIndex] += coef[i];
            }
        }

        this.exp = new int[currIndex];
        this.coef = new double[currIndex];
        for (int i = 0; i < currIndex; i++) {
            this.exp[i] = newExp[i];
            this.coef[i] = newCoef[i];
        }
    }

    Polynomial(File f) {
        try {
            Scanner sc = new Scanner(f);
            String line = sc.nextLine();
            int lineLen = line.length();
            for (int i = 0; i < lineLen; i++) {
                if (line.substring(i, i + 1).equals("-")) {
                    line = line.substring(0, i) + "+" + line.substring(i);
                    i++;
                }
            }

            String[] tempArr = line.split("\\+");
            int tempLen = tempArr.length;

            double[] coef = new double[tempLen];
            int[] exp = new int[tempLen];
            int currIndex = 0;

            for (int i = 0; i < tempLen; i++) {
                double currCoef;
                int currExp;
                if (tempArr[i].contains("x")) {
                    String[] currTerm = tempArr[i].split("x");
                    currCoef = Double.parseDouble(currTerm[0]);
                    if (currTerm.length == 1) {
                        currExp = 1;
                    } else {
                        currExp = Integer.parseInt(currTerm[1]);
                    }
                } else {
                    currCoef = Double.parseDouble(tempArr[0]);
                    currExp = 0;
                }
                int expIndex = indexInArr(currExp, exp);
                if (expIndex == -1) {
                    exp[currIndex] = currExp;
                    coef[currIndex] = currCoef;
                    currIndex++;
                } else {
                    coef[expIndex] += currCoef;
                }
            }

            
            this.exp = new int[currIndex];
            this.coef = new double[currIndex];
            for(int i=0; i<currIndex; i++){
                this.exp[i]=exp[i];
                this.coef[i]=coef[i];
            }
        } catch (Exception e) {
        }
    }

    double[] getCoef() {
        return coef;
    }

    int[] getExp() {
        return exp;
    }

    int indexInArr(int target, int[] arr) {
        int size = arr.length;
        for (int i = 0; i < size; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    Polynomial add(Polynomial p) {
        int argSize = p.coef.length;
        int currSize = coef.length;

        // create arrays with longest needed capacity
        int longestPossibleLen = argSize + currSize;
        int[] tempExp = new int[longestPossibleLen];
        double[] tempCoef = new double[longestPossibleLen];
        for (int i = 0; i < argSize; i++) {
            tempExp[i] = p.getExp()[i];
            tempCoef[i] = p.getCoef()[i];
        }

        // fill the arrays
        int lastValidIndex = argSize - 1;
        for (int i = 0; i < currSize; i++) {
            int currExp = exp[i];
            double currCoef = coef[i];
            int expIndexInNew = indexInArr(currExp, tempExp);
            if (expIndexInNew != -1) {
                tempCoef[expIndexInNew] += currCoef;
            } else {
                lastValidIndex++;
                tempExp[lastValidIndex] = currExp;
                tempCoef[lastValidIndex] = currCoef;
            }
        }

        // return the new Polynomial
        int usedLen = lastValidIndex + 1;
        boolean[] nonZero = new boolean[usedLen];
        int actualLen = usedLen;
        for (int i = 0; i < usedLen; i++) {
            if (tempCoef[i] == 0) {
                nonZero[i] = false;
                usedLen--;
            } else {
                nonZero[i] = true;
            }
        }
        int[] newExp = new int[actualLen];
        double[] newCoef = new double[actualLen];
        if (actualLen == longestPossibleLen) {
            newExp = tempExp;
            newCoef = tempCoef;
        } else {
            int count = 0;
            for (int i = 0; i < usedLen; i++) {
                if (!nonZero[i]) {
                    continue;
                }
                newExp[count] = tempExp[i];
                newCoef[count] = tempCoef[i];
            }
        }

        return new Polynomial(newCoef, newExp);
    }

    double evaluate(double xVal) {
        int size = coef.length;
        double result = 0;
        for (int i = 0; i < size; i++) {
            result += coef[i] * (Math.pow(xVal, exp[i]));
        }

        return result;
    }

    boolean hasRoot(double potentialRoot) {
        if (evaluate(potentialRoot) == 0) {
            return true;
        } else {
            return false;
        }
    }

    Polynomial multiply(Polynomial p) {
        int currPolySize = coef.length;
        int pPolySize = p.getCoef().length;

        // create an array with longest needed capacity
        int longestPossibleLen = currPolySize * pPolySize;
        double[] tempCoefArr = new double[longestPossibleLen];
        int[] tempExpArr = new int[longestPossibleLen];

        // fill the new arrays with corresponding values
        int currIndex = 0;
        for (int i = 0; i < currPolySize; i++) {
            for (int j = 0; j < pPolySize; j++) {
                double newCoef = coef[i] * p.getCoef()[j];
                int newExp = exp[i] + p.getExp()[j];

                int newExpIndex = indexInArr(newExp, tempExpArr);
                if (newExpIndex == -1) {
                    tempCoefArr[currIndex] = newCoef;
                    tempExpArr[currIndex] = newExp;
                    currIndex++;
                } else {
                    tempCoefArr[newExpIndex] += newCoef;
                }
            }
        }

        // return the new Polynomial
        if (currIndex == longestPossibleLen - 1) {
            return new Polynomial(tempCoefArr, tempExpArr);
        } else {
            // the new Polynomial exclude unused elements
            int[] newExpArr = new int[currIndex + 1];
            double[] newCoefArr = new double[currIndex + 1];
            for (int i = 0; i <= currIndex; i++) {
                newExpArr[i] = tempExpArr[i];
                newCoefArr[i] = tempCoefArr[i];
            }
            return new Polynomial(newCoefArr, newExpArr);
        }
    }

    void saveToFile(String filePath) {
        File f = new File(filePath);
        String polyString = "";
        int len = exp.length;
        for (int i = 0; i < len; i++) {
            if (coef[i] >= 0 && polyString == "") {
                polyString += coef[i];
            } else {
                if (coef[i] >= 0) {
                    polyString += "+";
                    polyString += coef[i];
                } else {
                    polyString += coef[i];
                }
            }
            if (coef[i] == 0) {
                continue;
            } else {
                polyString += "x";
                if (coef[i] == 1) {
                    continue;
                }
                polyString += exp[i];
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            f.createNewFile();
            bw.write(polyString);

        } catch (Exception e) {
            System.out.println("save file error");
        }
    }
}