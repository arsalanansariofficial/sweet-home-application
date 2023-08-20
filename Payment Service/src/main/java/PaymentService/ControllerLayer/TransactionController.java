package PaymentService.ControllerLayer;
import PaymentService.DatabaseLayer.Entities.TransactionDetailsEntity;
import PaymentService.ServiceLayer.ServiceClasses.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
This is the Controller class which maps the request with the specific URL to the specific classes.
 */

@RestController
@RequestMapping(value = "/payment")
public class TransactionController {
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /*
    This method returns the unique transaction id generated for the corresponding booking id.
     */

    @PostMapping(value = "/transaction")
    public ResponseEntity<Integer> createPayment(@RequestBody TransactionDetailsEntity transactionDetailsEntity) {
        TransactionDetailsEntity savedTransactionDetails = transactionService.createPayment(transactionDetailsEntity);
        return new ResponseEntity<>(savedTransactionDetails.getBookingId(), HttpStatus.CREATED);
    }

    /*
    This method returns the transaction details for the corresponding transaction id.
     */

    @GetMapping(value = "/transaction/{transactionId}")
    public ResponseEntity<TransactionDetailsEntity> getPaymentDetails(@PathVariable(name = "transactionId") int transactionId) throws Exception {
        return new ResponseEntity<>(transactionService.getTransactionDetails(transactionId), HttpStatus.OK);
    }
}
