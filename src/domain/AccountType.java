package domain;

public enum AccountType {
    CHECKING_ACCOUNT {
        @Override
        public Account create(Integer id, String fullName, String password) {
            return new CheckingAccount(id, fullName, password);
        }
    },

    SAVINGS_ACCOUNT {
        @Override
        public Account create(Integer id, String fullName, String password) {
            return new SavingsAccount(id, fullName, password);
        }
    };

    public abstract Account create(
            Integer id,
            String fullName,
            String password
    );
}
