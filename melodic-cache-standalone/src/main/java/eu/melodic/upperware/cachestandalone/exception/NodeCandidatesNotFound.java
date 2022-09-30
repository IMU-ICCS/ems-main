package eu.melodic.upperware.cachestandalone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NodeCandidatesNotFound extends RuntimeException {

}
