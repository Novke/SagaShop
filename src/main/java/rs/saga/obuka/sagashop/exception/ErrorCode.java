package rs.saga.obuka.sagashop.exception;

/**
 * @author: Ana Dedović
 * Date: 28.06.2021.
 **/
@SuppressWarnings("unused")
public enum ErrorCode {

    // generic errors 5000 -
    ERR_GEN_001(5000, "error.storeFailed"),
    ERR_GEN_002(5002, "error.entityNotFound"),
    ERR_GEN_003(5003, "error.deleteFailed"),
    ERR_GEN_004(5004, "error.staleData"),

    // category 4201 - 4300
    ERR_CAT_001(4201, "error.categoryDoesNotExist"),
    ERR_CAT_002(4202, "error.categoryNameExists"),

    // cart 4301 - 4400
     ERR_CART_001(4301, "error.cartDoesNotExist"),
     ERR_CART_002(4302, "error.cartIllegalState"),
     ERR_CART_003(4303, "error.itemQuantityInvalid");
    // ERR_CART_004(4304, "error.productDoesNotExist"),



    ErrorCode(int responseCode, String messageKey){
        this.responseCode = responseCode;
        this.messageKey = messageKey;
    }

    private final String messageKey;
    private final int responseCode;

    public String getMessageKey() {
        return messageKey;
    }

    public int getResponseCode() {
        return responseCode;
    }


}
