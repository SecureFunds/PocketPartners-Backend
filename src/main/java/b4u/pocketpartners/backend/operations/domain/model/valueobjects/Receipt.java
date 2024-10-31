package b4u.pocketpartners.backend.operations.domain.model.valueobjects;

/**
 * @author Nadia Alessandra Lucas Coronel - u202120430
 * @version 1.0
 */
public record Receipt(String receipt) {
    public Receipt {
        if (receipt == null || receipt.isBlank()) {
            throw new IllegalArgumentException("Receipt URL cannot be null or blank");
        }
    }
    public String getReceipt() {return receipt;}
}
