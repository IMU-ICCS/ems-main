package eu.melodic.upperware.cachestandalone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Returned empty NodeCandidates list for query")
public class NodeCandidatesNotFound extends RuntimeException {
}
