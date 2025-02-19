import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();
        Scanner sc = new Scanner(System.in);

        // Supplier를 통해 임의의 트랙잭션 생성
        Supplier<Transaction> randomTransactionSupplier = () ->{
            // id는 난수, type은 고정, amount도 난수
            int randId = (int)(Math.random()*1000);
            double randAmount = Math.random()*1000000;
            return new Transaction(randId, "PAYMENT", randAmount);
        };

        boolean run = true;
        while (run) {
            System.out.println("\\n=== Menu ===");
            System.out.println("1. Add transactions manually");
            System.out.println("2. Add Transaction Supplier(Supplier)");
            System.out.println("3. Filtering for a specific type (Predicate)");
            System.out.println("4. View amount conversion (Function) results");
            System.out.println("5. Output all transactions (Consumer)");
            System.out.println("6. exit");
            System.out.print("choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("input ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter type (PAYMENT/REFUND, etc.): ");
                    String type = sc.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    Transaction t = new Transaction(id, type, amount);
                    manager.addTransaction(t);
                    System.out.println("[Info] Transaction has been added.");
                    break;

                case 2:
                    // Supplier 이용
                    Transaction randT = randomTransactionSupplier.get();
                    manager.addTransaction(randT);
                    System.out.println("[Info] Add random transaction: " + randT);
                    break;

                case 3:
                    // Predicate 이용하여 조건에 맞는 값만 받아오기
                    System.out.print("Enter the type you want to filter: ");
                    String filterType = sc.nextLine();
                    Predicate<Transaction> byType = tran -> tran.getType().equalsIgnoreCase(filterType);

                    List<Transaction> filtered = manager.filterTransactions(byType);
                    System.out.println("[Results] Filtered transactions: " + filtered);
                    break;

                case 4:
                    // Function 이용
                    System.out.print("Enter discount rate (%): ");
                    double discountPercent = sc.nextDouble();
                    sc.nextLine();

                    Function<Transaction, Double> discountFunc = tran -> tran.getAmount() * (1 - (discountPercent / 100.0));
                    List<Double> discountedAmounts = manager.mapAmounts(discountFunc);
                    System.out.println("[Results] List of converted amounts: " + discountedAmounts);
                    break;

                case 5:
                    // Consumer 이용
                    Consumer<Transaction> printTran = tran -> System.out.println("[Tx] " + tran);
                    manager.processTransactions(printTran);
                    break;

                case 6:
                    run = false;
                    System.out.println("[Info] Exit");
                    break;

                default:
                    System.out.println("[Error]");
            }
        }

        sc.close();
        }
}