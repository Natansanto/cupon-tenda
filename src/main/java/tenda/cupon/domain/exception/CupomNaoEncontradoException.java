package tenda.cupon.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CupomNaoEncontradoException extends RuntimeException {

	public CupomNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
