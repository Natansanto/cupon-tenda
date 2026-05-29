package tenda.cupon.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CupomInvalidoException extends RuntimeException {

	public CupomInvalidoException(String mensagem) {
		super(mensagem);
	}

}
