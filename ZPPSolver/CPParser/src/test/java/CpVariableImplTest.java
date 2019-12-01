import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl;

public class CpVariableImplTest extends NumericExpressionImpl implements CpVariable {
    private String id;
    private VariableType type;
    private Domain domain;
    public CpVariableImplTest(String id, VariableType type) {
        this.id = id;
        this.type = type;
    }
    @Override
    public String getId() {
        return this.id;
    }
    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public void setDomain(Domain value) {
        this.domain = domain;
    }

    @Override
    public VariableType getVariableType() {
        return type;
    }

    @Override
    public void setVariableType(VariableType value) {
        this.type = type;
    }

    @Override
    public String getComponentId() {
        return id;
    }

    @Override
    public void setComponentId(String value) {
        this.id = id;
    }
}
