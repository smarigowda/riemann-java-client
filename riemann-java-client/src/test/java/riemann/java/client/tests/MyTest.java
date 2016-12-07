package riemann.java.client.tests;

import io.riemann.riemann.client.RiemannClient;

public class MyTest {

    public static void main(String[] args) {
        System.out.println("my test for Riemann client...");

        for( int i=0; i<10; i++) {

            double[] countOK = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4.0, 5.0, 0, 0 }; // 2 out of 10 (1 out of 5)-- ok
            double[] countWarning = { 0, 0, 0, 0, 12, 11, 10, 10, 9, 10, 0, 0 }; // 6 out of 10 (3 out of 5) -- warning
            double[] countCritical = { 0, 0, 11, 11, 11, 10, 10, 10, 9, 10, 11, 11 }; // 8 out of 10 (2 out of 5)-- critical

            double[] count;
//            count = countOK;
//            count = countWarning;
            count = countCritical;

            try {
                RiemannClient c = RiemannClient.tcp("localhost", 5555);
                c.connect();
                c.event()
                        .service("UknownProblems")
                        .state("tbd")
                        .metric(count[i])
                        .tags("Kibana")
                        .send()
                        .deref(5000, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                System.out.println("Exception..." + e);
            }
        }
    }
}
