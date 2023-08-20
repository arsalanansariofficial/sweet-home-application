package PaymentService.ServiceLayer.ServiceClasses;
import PaymentService.DatabaseLayer.Entities.TransactionDetailsEntity;
import PaymentService.DatabaseLayer.Repositories.TransactionDetailsRepository;
import PaymentService.ServiceLayer.ServiceInterfaces.TransactionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
This class is actually contains the business logic to perform the payment task.
 */

@Service
public class TransactionService implements TransactionServiceInterface {
    TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    public TransactionService(TransactionDetailsRepository transactionDetailsRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
    }

    /*
    This method generates a unique transaction id for each booking id, and if the transaction is already there
    then it will not generate another transaction id, but it returns transaction id that is already present in the database.
    Parameter - TransactionDetailsEntity
     */

    @Override
    public TransactionDetailsEntity createPayment(TransactionDetailsEntity transactionDetailsEntity) {
        TransactionDetailsEntity savedTransactionDetails = transactionDetailsRepository.findTransactionDetailsEntityByBookingId(transactionDetailsEntity.getBookingId());
        if (savedTransactionDetails == null) {
            savedTransactionDetails = transactionDetailsRepository.save(transactionDetailsEntity);
        }
        return savedTransactionDetails;
    }

    /*
    This method returns the transaction details corresponding to the transaction id provided.
    Parameter - transactionId
     */

    @Override
    public TransactionDetailsEntity getTransactionDetails(int transactionId) throws Exception {
        TransactionDetailsEntity savedTransactionDetails = transactionDetailsRepository.findById(transactionId).orElse(null);
        if (savedTransactionDetails != null)
            return savedTransactionDetails;
        throw new Exception("Invalid transaction Id");
    }
}