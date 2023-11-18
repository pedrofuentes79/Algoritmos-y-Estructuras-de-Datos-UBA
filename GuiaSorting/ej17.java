public class ej17 {
    public static void main(String[] args) {
        int[] A = {1,3,4,5,2};
        int n = A.length;

        for(int i = n-1; i>0; i--){
            if(A[i] < A[i-1]){
                // swap
                int temp = A[i];
                A[i] = A[i-1];
                A[i-1] = temp;
            }
        }
        // print sorted array
        for(int i = 0; i<n; i++){
            System.out.println(A[i]);
        }
    }
}
