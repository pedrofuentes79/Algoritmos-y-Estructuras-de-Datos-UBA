package aed;

class Funciones {
    int cuadrado(int x) {
        return (int) Math.pow(x,2);
    }

    double distancia(double x, double y) {
        // COMPLETAR
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    boolean esPar(int n) {
        // COMPLETAR
        return (n % 2 == 0);
    }

    boolean esBisiesto(int n) {
        // COMPLETAR
        return ((n % 4 == 0 && n % 100 != 0) || n % 400 == 0);
    }

    int factorialIterativo(int n) {
        // COMPLETAR
        if (n == 0 || n == 1) return 1;
        int res = 1;
        for (int i = 1; i <= n; i++) {
        	res *= i;
        }
        return res;
    }

    int factorialRecursivo(int n) {
        // COMPLETAR
        if (n == 0) return 1;
        return n * factorialRecursivo(n-1);
    }

    boolean esPrimo(int n) {
        // COMPLETAR
        if (n < 2) return false;
        for (int i=2; i<n; i++){
        	if (n % i == 0){
        		return false;
        	}
        }
        return true;
    }

    int sumatoria(int[] numeros) {
        // COMPLETAR
        
        int res = 0;
        for (int i=0; i<numeros.length; i++){
        	res += numeros[i];
        }
        return res;
    }

    int busqueda(int[] numeros, int buscado) {
        // COMPLETAR
	for (int i=0; i<numeros.length; i++) {
		if (numeros[i] == buscado){
			return i;
		}
	}
	return -1;
    }

    boolean tienePrimo(int[] numeros) {
        // COMPLETAR
        for (int i=0; i<numeros.length; i++){
        	if (esPrimo(numeros[i])){
        		return true;
        	}
        }
        return false;
    }

    boolean todosPares(int[] numeros) {
        // COMPLETAR
        for (int i=0; i<numeros.length; i++){
        	if (numeros[i] % 2 != 0){
        		return false;
        	}
        }
        return true;
    }

    boolean esPrefijo(String s1, String s2) {
        // COMPLETAR
        if (s1.length() > s2.length()){
        	return false;
        }
        
        for (int i=0; i<s1.length(); i++){
        	if (s1.charAt(i) != s2.charAt(i)){
        		return false;
        	}
        }
        return true;
    }

    boolean esSufijo(String s1, String s2) {
        if (s1.length() > s2.length()){
        	return false;
        }
        
        int s1_length = s1.length();
        int s2_length = s2.length();
        
        for (int j=0; j<s1.length(); j++){
        	if (s1.charAt(j) != s2.charAt(s2_length-s1_length+j)){
        		return false;
        	}
        }
        return true;
    }}

