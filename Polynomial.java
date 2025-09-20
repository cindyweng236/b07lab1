class Polynomial {
    private double[] coef;

    Polynomial() {
        coef = new double[1];
        coef[0] = 0;
    }

    Polynomial(double[] tempCoef) {
        int size = tempCoef.length;
        coef = new double[size];
        for (int i = 0; i < size; i++) {
            coef[i] = tempCoef[i];
        }
    }

    double[] getCoef(){
        return coef;
    }

    Polynomial padding(int lengthWanted) {
        int currSize = coef.length;
        double[] newCoef = new double[lengthWanted];
        if (currSize < lengthWanted) {
            int i;
            for (i = 0; i < currSize; i++) {
                newCoef[i] = coef[i];
            }
            for (; i < lengthWanted; i++) {
                newCoef[i] = 0;
            }
            return new Polynomial(newCoef);
        }
        return new Polynomial(coef);
    }

    Polynomial add(Polynomial p) {
        int argSize = p.coef.length;
        int currSize = coef.length;
        int maxSize = Math.max(argSize, currSize);

        double[] newCoef = new double[maxSize];
        Polynomial newCurrPoly = padding(maxSize);
        Polynomial newP = p.padding(maxSize);


        for (int i = 0; i < maxSize; i++) {
            newCoef[i] = newP.getCoef()[i] + newCurrPoly.getCoef()[i];
        }

        return new Polynomial(newCoef);
    }

    double evaluate(double xVal){
        int numCoef = coef.length;
        double result = 0 ;
        for(int i=0; i<numCoef; i++){
            result += coef[i]*(Math.pow(xVal, i));
        }
        return result;
    }

    boolean hasRoot(double potentialRoot){
        if(evaluate(potentialRoot)==0){
            return true;
        }else{
            return false;
        }
    }
}