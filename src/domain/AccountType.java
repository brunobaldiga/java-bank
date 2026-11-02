package domain;

public enum AccountType {
    CHECKING_ACCOUNT {
        @Override
        public Account create(String fullName, String password) {
            return new CheckingAccount(fullName, password);
        }
    },

    SAVINGS_ACCOUNT {
        @Override
        public Account create(String fullName, String password) {
            return new SavingsAccount(fullName, password);
        }
    };

    public abstract Account create(
            String fullName,
            String password
    );
}
