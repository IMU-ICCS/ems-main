package mockups;

import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl;

public class CpVariableImplMockup extends NumericExpressionImpl implements CpVariable {
    private String id;
    private VariableType type;
    private Domain domain;
    private String componentId;
    public CpVariableImplMockup(String id, VariableType type) {
        this.id = id;
        this.type = type;
    }
    public CpVariableImplMockup(String id, VariableType type, Domain domain, String componentId) {
        this.id = id;
        this.type = type;
        this.domain = domain;
        this.componentId= componentId;
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
        this.domain = value;
    }

    @Override
    public VariableType getVariableType() {
        return type;
    }

    @Override
    public void setVariableType(VariableType value) {
        this.type = value;
    }

    @Override
    public String getComponentId() {
        return componentId;
    }

    @Override
    public void setComponentId(String value) {
        this.componentId = componentId;
    }
}
