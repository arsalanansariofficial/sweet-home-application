package PaymentService.ServiceLayer.ServiceInterfaces;
import PaymentService.DatabaseLayer.Entities.TransactionDetailsEntity;

/*
This interface provides two methods to perform payment and get details for the transaction.
 */

public interface TransactionServiceInterface {
    TransactionDetailsEntity createPayment(TransactionDetailsEntity transactionDetailsEntity);
    TransactionDetailsEntity getTransactionDetails(int transactionId) throws Exception;
}
