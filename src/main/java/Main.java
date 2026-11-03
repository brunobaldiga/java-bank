import application.BankApplication;
import config.DatabaseConfig;
import config.JpaConfig;

public class Main {
    public static void main(String[] args) {
        try {
            new BankApplication().run();
        } finally {
            try {
                JpaConfig.close();
            } finally {
                DatabaseConfig.close();
            }
        }
    }
}
