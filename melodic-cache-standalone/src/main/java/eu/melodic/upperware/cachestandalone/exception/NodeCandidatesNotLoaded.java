package eu.melodic.upperware.cachestandalone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Couldn't load Node Candidates from Cache")
public class NodeCandidatesNotLoaded extends RuntimeException {
}
