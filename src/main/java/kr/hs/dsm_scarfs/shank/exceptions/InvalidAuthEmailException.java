package kr.hs.dsm_scarfs.shank.exceptions;

import kr.hs.dsm_scarfs.shank.error.exception.BusinessException;
import kr.hs.dsm_scarfs.shank.error.exception.ErrorCode;

public class InvalidAuthEmailException extends BusinessException {
    public InvalidAuthEmailException() {
        super(ErrorCode.INVALID_AUTH_EMAIL);
    }

}
