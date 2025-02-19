import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TransactionManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // predicate : 조건에 맞는 트랜잭션만 반환. 함수형 인터페이스 true 또는 false 반환
    public List<Transaction> filterTransactions(Predicate<Transaction> predicate) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            // predicate.test(t) 값으로 판단
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    // Function : 트랜잭션 금액 변환 (매핑)
    public List<Double> mapAmounts(Function<Transaction, Double> function) {
        List<Double> result = new ArrayList<>();
        for(Transaction t : transactions) {
            result.add(function.apply(t));
        }
        return result;
    }

    // Consumer: 트랙잭션 처리(출력, 로깅 등)
    public void processTransactions(Consumer<Transaction> consumer) {
        for (Transaction t : transactions) {
            consumer.accept(t);
        }
    }
}
