package groovy.eu.melodic.upperware.utilitygenerator.model

import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class ConfigurationElementTest extends Specification {


    ConfigurationElement first
    ConfigurationElement second
    String firstName = "testName"
    int firstCardinality = 1
    NodeCandidate firstNodeCandidate = Mock(NodeCandidate)

    def setup() {
        first = new ConfigurationElement(firstName, firstNodeCandidate, firstCardinality)

    }

    def "same components"() {

        given:
        second = new ConfigurationElement(firstName, firstNodeCandidate, firstCardinality)

        when:
        boolean result = first.equals(second)

        then:
        result
        noExceptionThrown()
    }

    def "different name components"() {

        given:
        second = new ConfigurationElement("secondname", firstNodeCandidate, firstCardinality)

        when:
        boolean result = first.equals(second)

        then:
        !result
        noExceptionThrown()
    }

    def "different nc"() {

        given:
        second = new ConfigurationElement(firstName, Mock(NodeCandidate), firstCardinality)

        when:
        boolean result = first.equals(second)

        then:
        !result
        noExceptionThrown()
    }

    def "the same objects"() {

        given:
        second = first

        when:
        boolean result = first.equals(second)

        then:
        result
        noExceptionThrown()
    }
}
