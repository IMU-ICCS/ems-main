package eu.paasage.upperware.security.server.data.repository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "refresh")
@Getter
@AllArgsConstructor
public class RefreshToken implements Serializable {


    @Id
    @Column(name = "id")
    private String id;

    @Setter
    @Column(name = "state")
    private RefreshTokenType state;

    protected RefreshToken() {

    }

    public RefreshToken(String id) {
        this.id = id;
        this.state = RefreshTokenType.NEW;
    }

    @Override
    public String toString() {
        return String.format("RefreshToken[id=%s, state=%s", id, state);
    }


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum RefreshTokenType {

        NEW,
        USED,
        INVALIDATED

    }

}
