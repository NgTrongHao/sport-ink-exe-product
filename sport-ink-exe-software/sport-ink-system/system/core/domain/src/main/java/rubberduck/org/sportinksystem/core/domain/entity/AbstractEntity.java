package rubberduck.org.sportinksystem.core.domain.entity;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractEntity<ID extends Serializable> implements Serializable {

    protected ID id;

    public AbstractEntity() {
    }

    public AbstractEntity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return (String) id;
    }
}
