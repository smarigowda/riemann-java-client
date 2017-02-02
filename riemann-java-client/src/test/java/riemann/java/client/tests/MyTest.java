package riemann.java.client.tests;

import io.riemann.riemann.client.RiemannClient;

public class MyTest {

    public static void main(String[] args) {
        System.out.println("my test for Riemann client...");

        for( int i=0; i<17; i++) {

            double[] countOK = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4.0, 5.0, 0, 0 };
            double[] countWarning = { 0, 0, 0, 0, 12, 11, 10, 10, 9, 10, 0, 0 };
            double[] countCritical = { 0, 0, 11, 11, 11, 10, 10, 10, 9, 10, 11, 11, 11, 11, 11, 11, 11 };

            double[] count;
//            count = countOK;
//            count = countWarning;
            count = countCritical;
            System.out.println("sending event: " + count[i]);
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

//                List<Proto.Event> le = c.query("tagged \"Kibana\" and metric > 0").deref(5000, java.util.concurrent.TimeUnit.MILLISECONDS);
//                System.out.println(le);
                c.close();

            } catch (Exception e) {
                System.out.println("Exception..." + e);
            }
        }
    }
}